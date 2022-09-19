package com.example.musicapplication.allsong

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.musicapplication.PlaySongService
import com.example.musicapplication.R
import com.example.musicapplication.databinding.AllSongFragmentBinding

class AllSongFragment : Fragment(), View.OnClickListener {

    private lateinit var allSongsViewModel: AllSongViewModel
    lateinit var binding: AllSongFragmentBinding
    private lateinit var mService: PlaySongService
    private var mBound: Boolean = false

    private val adapter = AllSongAdapter(DataSongListener { data ->
        binding.bottomNavSong.visibility = View.VISIBLE
        binding.btnPlayPause.setBackgroundResource(R.drawable.ic_pause_black_large)

        if (mBound) {
            mService.playMusic(data)
            allSongsViewModel.isPlayedMusic = true
        }
        allSongsViewModel.onDataSongClicked(data)
    })

    /** Defines callbacks for service binding, passed to bindService()  */
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as PlaySongService.LocalBinder
            mService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.all_song_fragment,
            container,
            false
        )

        val application = requireNotNull(this.activity).application

        //val viewModelFactory = AllSongViewModelFactory(application)
        val viewModelFactory = AllSongViewModelFactory(application)
        // Get a reference to the ViewModel associated with this fragment.
        allSongsViewModel =
            ViewModelProvider(
                this, viewModelFactory
            )[AllSongViewModel::class.java]

        binding.listSong.adapter = adapter

        binding.bottomNavSong.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_allSongFragment_to_mediaPlayBackFragment)
        }

        allSongsViewModel.songs.observe(viewLifecycleOwner, Observer { newSongs ->
            adapter.data = newSongs
        })

        binding.btnPlayPause.setOnClickListener(this)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        // Bind to LocalService
        requireActivity().bindService(
            Intent(requireActivity(), PlaySongService::class.java),
            connection,
            Context.BIND_AUTO_CREATE
        )
        saveStatusBottomNav()
    }

    override fun onStop() {
        super.onStop()
        //requireActivity().unbindService(connection)
        mBound = false
    }

    override fun onDestroy() {
        super.onDestroy()
        allSongsViewModel.isPlayMusic = mService.getStatusMusic()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_play_pause) {
            if (mService.getStatusMusic()){
                binding.btnPlayPause.setBackgroundResource(R.drawable.ic_play_black_round)
            }
            else{
                binding.btnPlayPause.setBackgroundResource(R.drawable.ic_pause_black_large)
            }
            /* Bkav TuanTVb: Play/pause nhạc */
            mService.playAndPauseMusic()
        }
    }

    /**
     * Bkav TuanTVb:
     * Hiển thị thanh điều khiển nhạc và set ảnh hiển thị của button Play/pause
     */
    private fun saveStatusBottomNav() {
        if(allSongsViewModel.isPlayedMusic) {
            binding.bottomNavSong.visibility = View.VISIBLE
            if(allSongsViewModel.isPlayMusic){
                binding.btnPlayPause.setBackgroundResource(R.drawable.ic_pause_black_large)
            }else{
                binding.btnPlayPause.setBackgroundResource(R.drawable.ic_play_black_round)
            }
        }
    }
}



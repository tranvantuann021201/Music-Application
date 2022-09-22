package com.example.musicapplication.allsong

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
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


    private val adapter = AllSongAdapter(

        /**
         * Bkav TuanTVb: Xử lý Click khi người dùng bấm vào bài nhạc
         */
        DataSongListener { data ->
            binding.bgGradient.visibility = View.VISIBLE
            binding.bottomNavSong.visibility = View.VISIBLE
            binding.btnPlayPause.setBackgroundResource(R.drawable.ic_pause_black_large)

            if (mBound) {
                mService.playMusic(data.data!!)
                allSongsViewModel.isPlayedMusic = true
            }
            allSongsViewModel.onDataSongClicked(data.data!!)
            allSongsViewModel.setSongIsPlaying(data)
        })

    /** Defines callbacks for service binding, passed to bindService()  */
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            /* Bkav TuanTVb: Liên kết với LocalService,
            truyền IBinder và lấy phiên bản LocalService*/
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

        /* Bkav TuanTVb: Inflate all_song_fragment layout*/
        binding = DataBindingUtil.inflate<AllSongFragmentBinding?>(
            inflater,
            R.layout.all_song_fragment,
            container,
            false
        )

        val application = requireNotNull(this.activity).application

        val viewModelFactory = AllSongViewModelFactory(application)
        /* Bkav TuanTVb: Tham chiếu allSongViewModel đến AllSongFragment*/
        allSongsViewModel =
            ViewModelProvider(
                this, viewModelFactory
            )[AllSongViewModel::class.java]

        binding.listSong.adapter = adapter

        binding.bottomNavSong.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_allSongFragment_to_mediaPlayBackFragment)
        }

        binding.allSongViewModel = allSongsViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        /* Bkav TuanTVb: Đưa dữ liệu từ adapter vào RecyclerView*/
        allSongsViewModel.songs.observe(viewLifecycleOwner, Observer { newSongs ->
            adapter.data = newSongs
        })

        binding.btnPlayPause.setOnClickListener(this)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        /* Bkav TuanTVb: Gọi startService, onStartCommand chứa hàm hiển thị notification*/
        requireActivity().startService(
            Intent(requireActivity(), PlaySongService::class.java)
        )

        /* Bkav TuanTVb: Liên kết client với service*/
        requireActivity().bindService(
            Intent(requireActivity(), PlaySongService::class.java),
            connection,
            Context.BIND_AUTO_CREATE
        )

        /* Bkav TuanTVb: Hiển thị bottomNavSong đúng trạng thái*/
        allSongsViewModel.isPlayMusic = true
        saveStatusBottomNav()
        Log.i("TitleFragment", "onStart called")

    }

    override fun onResume() {
        super.onResume()
        Log.i("TitleFragment", "onResume called")
    }

    override fun onStop() {
        super.onStop()
        //requireActivity().unbindService(connection)
        mBound = false
        Log.i("TitleFragment", "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        allSongsViewModel.isPlayMusic = mService.getStatusMusic()
        Log.i("TitleFragment", "onDestroy called")
    }

    /**
     * Bkav TuanTVb: Xử lý sự kiện click khi người dùng ấn vào bntPlayPause
     */
    override fun onClick(v: View) {
        if (v.id == R.id.btn_play_pause) {
            if (mService.getStatusMusic()) {
                binding.btnPlayPause.setBackgroundResource(R.drawable.ic_play_black_round)
            } else {
                binding.btnPlayPause.setBackgroundResource(R.drawable.ic_pause_black_large)
            }
            mService.playAndPauseMusic()
        }
    }

    /**
     * Bkav TuanTVb: Giữ nguyên trạng thái của btnPlayPause khi xoay màn hình
     */
    private fun saveStatusBottomNav() {
        if (allSongsViewModel.isPlayedMusic) {
            binding.bottomNavSong.visibility = View.VISIBLE
            binding.bgGradient.visibility = View.VISIBLE
            if (allSongsViewModel.isPlayMusic) {
                binding.btnPlayPause.setBackgroundResource(R.drawable.ic_pause_black_large)
            } else {
                binding.btnPlayPause.setBackgroundResource(R.drawable.ic_play_black_round)
            }
        }
    }
}



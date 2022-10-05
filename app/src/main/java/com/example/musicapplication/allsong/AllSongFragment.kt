package com.example.musicapplication.allsong

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.musicapplication.MainActivity
import com.example.musicapplication.R
import com.example.musicapplication.database.DataSong
import com.example.musicapplication.databinding.AllSongFragmentBinding
/**
 * Created by Bkav TuanTVb on 30/08/2022.
 */
class AllSongFragment : Fragment(), View.OnClickListener {

    private lateinit var allSongsViewModel: AllSongViewModel
    private lateinit var mainActivity: MainActivity
    lateinit var binding: AllSongFragmentBinding

    companion object {
        const val UPDATE_SONG_UI = "song_update_ui"
        const val DATA = "dataSong"
    }

    private var allSongBroadcast = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (UPDATE_SONG_UI == intent?.action) {
                val index: String? = intent.getStringExtra(DATA)
                if(index != null) {
                    updateUISong(index.toInt())
                }
            }
        }
    }

    private val adapter = AllSongAdapter(
        /*Bkav TuanTVb: Xử lý Click khi người dùng bấm vào bài nhạc*/
        DataSongListener {song->
            binding.bgGradient.visibility = View.VISIBLE
            binding.bottomNavSong.visibility = View.VISIBLE
            binding.btnPlayPause.setBackgroundResource(R.drawable.ic_pause_black_large)
            if (mainActivity.getBoundStatus()) {
                mainActivity.getServiceStatus().playMusic(song)
                allSongsViewModel.isPlayedMusic = true
            }
            allSongsViewModel.onDataSongClicked(song.data)
            allSongsViewModel.setSongIsPlaying(song)

            mainActivity.getServiceStatus().showNotification(song)

        })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainActivity = requireActivity() as MainActivity

        /* Bkav TuanTVb: Inflate all_song_fragment layout*/
        binding = DataBindingUtil.inflate<AllSongFragmentBinding?>(
            inflater,
            R.layout.all_song_fragment,
            container,
            false
        )

        /* Bkav TuanTVb: Tham chiếu allSongViewModel đến AllSongFragment*/
        val application = requireNotNull(this.activity).application
        val viewModelFactory = AllSongViewModelFactory(application)
        allSongsViewModel =
            ViewModelProvider(
                this, viewModelFactory
            )[AllSongViewModel::class.java]

        /* Bkav TuanTVb: Gán dữ liệu cho RecyclerView cho adapter*/
        binding.listSong.adapter = adapter

        /* Bkav TuanTVb: Chuyển hướng sang mediaPlayBackFragment khi bấm vào bottomNavSong*/
        binding.bottomNavSong.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_allSongFragment_to_mediaPlayBackFragment)
        }

        /* Bkav TuanTVb: Tham chiếu viewmodel tới biến viewmodel bên layout*/
        binding.allSongViewModel = allSongsViewModel

        /* Bkav TuanTVb: Hiển thị thông tin bài hát ở bottomNavSong ngay khi người dùng chọn bài hát*/
        binding.lifecycleOwner = viewLifecycleOwner

        /* Bkav TuanTVb: Đưa dữ liệu từ adapter vào RecyclerView*/
        allSongsViewModel.songs.observe(viewLifecycleOwner, Observer { newSongs ->
            adapter.data = newSongs
        })
        binding.btnPlayPause.setOnClickListener(this)

        /*Bkav TuanTVb: đăng kí broadcast  */
        val intentFilter = IntentFilter(UPDATE_SONG_UI)
        requireActivity().registerReceiver(allSongBroadcast, intentFilter);
        return binding.root

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        /* Bkav TuanTVb: Hiển thị bottomNavSong đúng trạng thái*/
        allSongsViewModel.isPlayMusic = true
        saveStatusBottomNavigation()
        Log.i("TitleFragment", "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.i("TitleFragment", "onResume called")
    }

    override fun onStop() {
        super.onStop()
        Log.i("TitleFragment", "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        allSongsViewModel.isPlayMusic = (requireActivity() as MainActivity).getServiceStatus().getStatusMusic()
        Log.i("TitleFragment", "onDestroy called")
    }

    /**
     * Bkav TuanTVb: Xử lý sự kiện click khi người dùng ấn vào bntPlayPause
     */
    override fun onClick(v: View) {
        if (v.id == R.id.btn_play_pause) {
            if ((requireActivity() as MainActivity).getServiceStatus().getStatusMusic()) {
                binding.btnPlayPause.setBackgroundResource(R.drawable.ic_play_black_round)
            } else {
                binding.btnPlayPause.setBackgroundResource(R.drawable.ic_pause_black_large)
            }
            //mService k để public, gọi bằng get
            (requireActivity() as MainActivity).getServiceStatus().playAndPauseMusic()
        }
    }

    /**
     * Bkav TuanTVb: Giữ nguyên trạng thái của btnPlayPause khi xoay màn hình
     */
    private fun saveStatusBottomNavigation() {
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

    /**
     * Bkav TuanTVb: update thong tin bai hat
     */
    fun updateUISong(index: Int) {
        val songNext: DataSong = (activity as MainActivity).listSong.value!!.get(index)
        allSongsViewModel.setSongIsPlaying(songNext)
    }


}



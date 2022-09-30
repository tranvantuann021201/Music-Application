package com.example.musicapplication.mediaplayback

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.musicapplication.MainActivity
import com.example.musicapplication.R
import com.example.musicapplication.database.DataSong
import com.example.musicapplication.databinding.MediaPlayBackFragmentBinding

class MediaPlayBackFragment : Fragment(), View.OnClickListener {

    private lateinit var mediaPlayBackViewModel: MediaPlayBackViewModel
    private lateinit var mainActivity: MainActivity
    private lateinit var binding: MediaPlayBackFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<MediaPlayBackFragmentBinding>(
            inflater,
            R.layout.media_play_back_fragment,
            container,
            false
        )

        mainActivity = requireActivity() as MainActivity
        /* Bkav TuanTVb: Tham chiếu mediaPlayBackViewModel đến MediaPlayBackFragment*/
        val application = requireNotNull(this.activity).application
        val viewModelFactory = MediaPlayBackViewModelFactory(application)
        mediaPlayBackViewModel =
            ViewModelProvider(
                this, viewModelFactory
            )[MediaPlayBackViewModel::class.java]

//        mediaPlayBackViewModel = ViewModelProvider(this)[MediaPlayBackViewModel::class.java]

        binding.iconCollectSong.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(R.id.action_mediaPlayBackFragment4_to_allSongFragment4)
        }

        binding.icPauseSong.setOnClickListener(this)
        binding.mediaPlayBackViewModel = mediaPlayBackViewModel

        binding.lifecycleOwner = viewLifecycleOwner

        getDataMusicIsPlaying()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        saveStatusBottomNav()
    }

    private fun saveStatusBottomNav() {
        if(mainActivity.mService.getStatusMusic()){
            binding.icPauseSong.setImageResource(R.drawable.ic_media_pause_dark)
        }
        else{
            binding.icPauseSong.setImageResource(R.drawable.ic_media_play_dark)
        }
    }

    /* Bkav TuanTVb: Xử lý click khi chọn Play/Pause bài nhạc*/
    override fun onClick(v: View) {
        if (v === binding.icPauseSong) {
            if (mainActivity.mService.getStatusMusic()) {
                binding.icPauseSong.setImageResource(R.drawable.ic_media_play_dark)
            } else {
                binding.icPauseSong.setImageResource(R.drawable.ic_media_pause_dark)
            }
            (requireActivity() as MainActivity).mService.playAndPauseMusic()
        }
    }



}
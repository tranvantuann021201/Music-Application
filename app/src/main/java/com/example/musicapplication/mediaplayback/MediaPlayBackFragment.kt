package com.example.musicapplication.mediaplayback

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

/**
 * Created by Bkav TuanTVb on 04/09/2022.
 */

class MediaPlayBackFragment : Fragment(), View.OnClickListener {

    private lateinit var mediaPlayBackViewModel: MediaPlayBackViewModel
    private lateinit var mainActivity: MainActivity
    private lateinit var binding: MediaPlayBackFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /* Bkav TuanTVb: Inflate layout cho frament*/
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

        /* Bkav TuanTVb: Xử lý click khi bấm vào iconCollectSong*/
        binding.iconCollectSong.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(R.id.action_mediaPlayBackFragment4_to_allSongFragment4)
        }

        /*Bkav TuanTVb: update song*/
        setSongIsPlaying(getArgs())

        binding.icPauseSong.setOnClickListener(this)
        binding.mediaPlayBackViewModel = mediaPlayBackViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        saveStatusBottomNavigation()
    }

    /* Bkav TuanTVb: Lưu lại trạng thái của bottomNavigation*/
    private fun saveStatusBottomNavigation() {
        if(mainActivity.getServiceStatus().getStatusMusic()){
            binding.icPauseSong.setImageResource(R.drawable.ic_media_pause_dark)
        }
        else{
            binding.icPauseSong.setImageResource(R.drawable.ic_media_play_dark)
        }
    }

    /**
     *  Bkav TuanTVb: Xử lý click khi chọn Play/Pause bài nhạc
     *  */
    override fun onClick(v: View) {
        if (v === binding.icPauseSong) {
            if (mainActivity.getServiceStatus().getStatusMusic()) {
                binding.icPauseSong.setImageResource(R.drawable.ic_media_play_dark)
                mainActivity.getServiceStatus().pauseMusic()
            } else {
                binding.icPauseSong.setImageResource(R.drawable.ic_media_pause_dark)
                mainActivity.getServiceStatus().onMusic()
            }
        }
    }

    /**
     * Bkav TuanTVb: Đổ dữ liệu cho setSongIsPlaying ở viewModel
     * */
    fun setSongIsPlaying(song: DataSong) {
        mediaPlayBackViewModel.setSongIsPlaying(song)
    }

    /**
     *  Bkav TuanTVb: Lấy đối số được truyền từ AllSongFragment
     **/
    private fun getArgs(): DataSong {
        return MediaPlayBackFragmentArgs.fromBundle(requireArguments()).song
    }
}
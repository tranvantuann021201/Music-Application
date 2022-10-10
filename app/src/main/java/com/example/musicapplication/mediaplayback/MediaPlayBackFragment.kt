package com.example.musicapplication.mediaplayback

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.musicapplication.MainActivity
import com.example.musicapplication.R
import com.example.musicapplication.database.DataSong
import com.example.musicapplication.database.FavoriteSongsDatabase
import com.example.musicapplication.databinding.MediaPlayBackFragmentBinding

/**
 * Created by Bkav TuanTVb on 04/09/2022.
 */

class MediaPlayBackFragment : Fragment(), View.OnClickListener {

    private lateinit var mediaPlayBackViewModel: MediaPlayBackViewModel
    private lateinit var mainActivity: MainActivity
    private lateinit var binding: MediaPlayBackFragmentBinding
    val handler = Handler()
    /**
     * Bkav TuanTVb: nhan data tu service de auto next song
     */
    private var mBroadcast = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (MainActivity.UPDATE_SONG_UI == intent?.action) {
                /*Bkav TuanTVb: nhan data bai hat khi auto next vaf update ui */
                val index: String? = intent.getStringExtra(MainActivity.DATA)
                val song: DataSong? =
                    index?.let { (activity as MainActivity).listSong.value?.get(it.toInt()) }
                if (song != null) {
                    setSongIsPlaying(song)
                    processSeekBar(song)
                }
            }
        }
    }

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
        val roomDatabase = FavoriteSongsDatabase.getInstance(requireContext()).favoriteSongsDatabaseDAO

        /* Bkav TuanTVb: Tham chiếu mediaPlayBackViewModel đến MediaPlayBackFragment*/
        val application = requireNotNull(this.activity).application
        val viewModelFactory = MediaPlayBackViewModelFactory(application,roomDatabase)
        mediaPlayBackViewModel =
            ViewModelProvider(
                this, viewModelFactory
            )[MediaPlayBackViewModel::class.java]

        mediaPlayBackViewModel.roomDatabase.getAllSongs()

        /* Bkav TuanTVb: Xử lý click khi bấm vào iconCollectSong*/
        binding.iconCollectSong.setOnClickListener(this)

        /*Bkav TuanTVb: update UI*/
        updateUI()

        binding.icPauseSong.setOnClickListener(this)

        /* Bkav TuanTVb: Xử lý sự kiện button like*/
        binding.icLikeSong.setOnClickListener { view: View ->
            if(view.id == R.id.ic_like_song) {
                if(mediaPlayBackViewModel.checkExistSong(getArgs().songID!!.toInt())) {
                    mediaPlayBackViewModel.onClickLikeButton(getArgs().songID!!.toInt())
                    Toast.makeText(requireContext(), "FAVORITED", Toast.LENGTH_SHORT).show()
                    binding.icLikeSong.setImageResource(R.drawable.ic_thumbs_up_selected)
                }
                else{
                    mediaPlayBackViewModel.onClickDislikeButton(getArgs().songID!!.toInt())
                    Toast.makeText(requireContext(), "REMOVED", Toast.LENGTH_SHORT).show()
                    binding.icLikeSong.setImageResource(R.drawable.ic_thumbs_up_default)
                }
            }
        }

        binding.mediaPlayBackViewModel = mediaPlayBackViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        saveStatusBottomNavigation()
        val intentFilter = IntentFilter(MainActivity.UPDATE_SONG_UI)
        requireActivity().registerReceiver(mBroadcast, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        requireActivity().unregisterReceiver(mBroadcast)
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
    override fun onClick(view: View) {
        if (view === binding.icPauseSong) {
            if (mainActivity.getServiceStatus().getStatusMusic()) {
                binding.icPauseSong.setImageResource(R.drawable.ic_media_play_dark)
                mainActivity.getServiceStatus().pauseMusic()
            } else {
                binding.icPauseSong.setImageResource(R.drawable.ic_media_pause_dark)
                mainActivity.getServiceStatus().onMusic()
            }
        }

        if(view === binding.iconCollectSong) {
            view.findNavController()
                .navigate(R.id.action_mediaPlayBackFragment4_to_allSongFragment4)
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

    /**
     * Bkav TuanTVb: Update UI
     */
    private fun updateUI() {
        processSeekBar(getArgs())
        setSongIsPlaying(getArgs())
        if(!mediaPlayBackViewModel.checkExistSong(getArgs().songID!!.toInt())){
            binding.icLikeSong.setImageResource(R.drawable.ic_thumbs_up_selected)
        }
        else
        {
            binding.icLikeSong.setImageResource(R.drawable.ic_thumbs_up_default)
        }
    }

    /**
     * Bkav TuanTVb: xử lý thanh Seekbar
     */
    fun processSeekBar(song: DataSong) {
        binding.seakMediaPlayBack.max = song.duration!!.toInt()
        /* Bkav TuanTVb: xử lý tua bài nhạc*/
        binding.seakMediaPlayBack.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (mainActivity.getServiceStatus().player != null && fromUser) {
                        mainActivity.getServiceStatus().player.seekTo(progress)
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            }
        )
        /* Bkav TuanTVb: update seekbar mỗi 1000 mili giây*/
        handler.postDelayed(object : Runnable{
            override fun run() {
                try {
                    binding.seakMediaPlayBack.progress =
                        mainActivity.getServiceStatus().player.currentPosition
                    handler.postDelayed(this,1000)
                }
                catch (e: Exception) {
                    binding.seakMediaPlayBack.progress = 0
                }
            }
        },0)
    }
}


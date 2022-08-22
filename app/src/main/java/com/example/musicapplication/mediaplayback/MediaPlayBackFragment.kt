package com.example.musicapplication.mediaplayback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.musicapplication.R
import com.example.musicapplication.databinding.MediaPlayBackFragmentBinding

class MediaPlayBackFragment : Fragment() {

    private lateinit var favoriteSongTable: MediaPlayBackViewModel
    private lateinit var binding:MediaPlayBackFragmentBinding

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

        binding.iconCollectSong.setOnClickListener{view: View ->
            view.findNavController().navigate(R.id.action_mediaPlayBackFragment4_to_allSongFragment4)
        }

        return binding.root
    }
}
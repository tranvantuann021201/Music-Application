package com.example.musicapplication.allsong

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.musicapplication.R
import com.example.musicapplication.databinding.AllSongFragmentBinding

class AllSongFragment : Fragment() {
    private lateinit var allSongsViewModel: AllSongViewModel
    lateinit var binding: AllSongFragmentBinding
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

        binding.bottomNavSong.setOnClickListener {view: View ->
            view.findNavController().navigate(R.id.action_allSongFragment_to_mediaPlayBackFragment)
        }


        return binding.root
    }
}



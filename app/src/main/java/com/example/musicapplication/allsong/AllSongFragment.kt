package com.example.musicapplication.allsong

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.musicapplication.R
import com.example.musicapplication.databinding.AllSongFragmentBinding

class AllSongFragment : Fragment() {
    private lateinit var allSongsViewModel: AllSongViewModel
    lateinit var binding: AllSongFragmentBinding
    private val adapter = AllSongAdapter()

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
        val sleepTrackerViewModel =
            ViewModelProvider(
                this,viewModelFactory).get(AllSongViewModel::class.java)

        binding.listSong.adapter = adapter

        binding.bottomNavSong.setOnClickListener {view: View ->
            view.findNavController().navigate(R.id.action_allSongFragment_to_mediaPlayBackFragment)
        }

        return binding.root
    }
}



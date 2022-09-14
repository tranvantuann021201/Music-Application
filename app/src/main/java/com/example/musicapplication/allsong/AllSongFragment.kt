package com.example.musicapplication.allsong

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
    private val adapter = AllSongAdapter(DataSongListener { songID ->
        Toast.makeText(context, "$songID", Toast.LENGTH_SHORT).show()
        allSongsViewModel.onDataSongClicked(songID)
    } )

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
                this,viewModelFactory).get(AllSongViewModel::class.java)

        binding.listSong.adapter = adapter

        binding.bottomNavSong.setOnClickListener {view: View ->
            view.findNavController().navigate(R.id.action_allSongFragment_to_mediaPlayBackFragment)
        }

        allSongsViewModel.songs.observe(viewLifecycleOwner, Observer { newSongs ->
            adapter.data = newSongs
        })

        binding.btnPlayPause.setOnClickListener(this)
        binding.imgSearch.setOnClickListener(this)


        return binding.root
    }

    override fun onClick(v: View) {
        if(v === binding.btnPlayPause) {
            requireActivity().startService(Intent(activity, PlaySongService::class.java))
            //context?.startService(Intent(context, PlaySongService::class.java))
        }

        if(v === binding.imgSearch) {
            requireActivity().stopService(Intent(activity, PlaySongService::class.java))
        }
    }
}



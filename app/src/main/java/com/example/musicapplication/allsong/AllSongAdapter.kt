package com.example.musicapplication.allsong

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapplication.database.SongTable
import com.example.musicapplication.databinding.ItemSongBinding


class AllSongAdapter : RecyclerView.Adapter<AllSongAdapter.SongViewHolder>() {

    var data = listOf<SongTable>()

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val item = getItemId(position)
    }

    //khoi tao ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSongBinding.inflate(layoutInflater, parent, false)
        return SongViewHolder(binding)
    }

    //Khai bao cac phan tu trong item cua recyclerView
    class SongViewHolder(binding: ItemSongBinding): RecyclerView.ViewHolder(binding.root) {
        val songName: TextView = binding.songName
        val songTime: TextView = binding.songTime
        val songIndex: TextView = binding.songIndex
    }


}

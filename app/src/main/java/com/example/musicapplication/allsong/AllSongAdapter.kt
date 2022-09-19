package com.example.musicapplication.allsong

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapplication.database.DataSong
import com.example.musicapplication.databinding.ItemSongBinding
import com.example.musicapplication.formatDuration


class AllSongAdapter(private val clickListener: DataSongListener) : RecyclerView.Adapter<AllSongAdapter.ViewHolder>() {
    var data = listOf<DataSong>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //val item = getItemId(position)
        val item = data[position]
        holder.bind(item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder constructor(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root) {

        //val songName: TextView = itemView.findViewById(R.id.song_name)
        //val duration: TextView = itemView.findViewById(R.id.song_duration)
        //val songIndex: TextView = itemView.findViewById(R.id.song_index)

        fun bind(item: DataSong, clickListener: DataSongListener) {
              binding.song = item
              binding.songDuration.text = formatDuration(item).toString()
              binding.songIndex.text = (position+1).toString()

              binding.clickListener = clickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemSongBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class DataSongListener(var clickListener: (song: String) -> Unit) {
    fun onClick(songs: DataSong) = clickListener(songs.data!!)
}


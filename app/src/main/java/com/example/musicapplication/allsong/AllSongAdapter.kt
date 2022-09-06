package com.example.musicapplication.allsong

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapplication.R
import com.example.musicapplication.database.DataSong


class AllSongAdapter : RecyclerView.Adapter<AllSongAdapter.ViewHolder>() {

    var data = listOf<DataSong>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //val item = getItemId(position)
        val item = data[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_song, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val songName: TextView = itemView.findViewById(R.id.song_name)
        val duration: TextView = itemView.findViewById(R.id.song_duration)
        val songIndex: TextView = itemView.findViewById(R.id.song_index)

        fun bind(item: DataSong) {
            val res = itemView.context.resources
            songName.text = item.songName
            duration.text = item.duration.toString()
            //duration.text = convertDurationToFormatted(item.duration, res)
            songIndex.text =  ((position+1).toString())
        }
    }
}

class TextItemViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView) {
}



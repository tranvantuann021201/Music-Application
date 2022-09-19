package com.example.musicapplication.allsong

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapplication.database.DataSong
import com.example.musicapplication.database.DataSongRepository
import kotlinx.coroutines.launch


class AllSongViewModel(private val dataSource: Application) : ViewModel() {

    private val dataSongRepository = DataSongRepository()

    public val songs = dataSongRepository.getSongs(dataSource)

    var isPlayedMusic = false

    var isPlayMusic  = false

    //get attributes data of song
    private val _songName = MutableLiveData<String?>()
    val songName: MutableLiveData<String?>
        get() = _songName

    private val _songArtist = MutableLiveData<String?>()
    val songArtist: MutableLiveData<String?>
        get() = _songArtist

    private val _songPicture = MutableLiveData<Bitmap?>()
    val songPicture: MutableLiveData<Bitmap?>
        get() = _songPicture

    /**
     * Navigation for the SleepDetail fragment.
     */
    private val _songClicked = MutableLiveData<String>()

    private val _statesService = MutableLiveData<Boolean>()

    fun onDataSongClicked(id: String) {
        _songClicked.value = id

    }

    //Refresh data from the repository.
    private fun refreshDataFromRepository(song: DataSong) {
        viewModelScope.launch {
            _songName.value = song.songName
            _songArtist.value = song.artists
        }
    }
}



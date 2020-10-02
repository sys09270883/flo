package com.sys0927.flo.viewmodels

import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.SimpleExoPlayer
import com.sys0927.flo.data.LyricInfo
import com.sys0927.flo.data.LyricManager
import com.sys0927.flo.utils.TimeToMsConverter

class MainViewModel: ViewModel() {
    var player: SimpleExoPlayer? = null
    var playWhenReady = false
    var currentWindow = 0
    var playbackPosition: Long = 0
    var file: String ?= null
    private val lyricManager = LyricManager.getInstance()

    fun setLyrics(lyrics: String) {
        lyricManager.clear()
        val splitLyrics = lyrics.split("\n")
        for (lyric in splitLyrics) {
            val (time, content) = lyric.split("[", "]").filter { it.isNotEmpty() }
            lyricManager.add(LyricInfo(TimeToMsConverter.convert(time), content))
        }
        lyricManager.add(LyricInfo(Long.MAX_VALUE, " "))
        lyricManager.sort()
    }

    fun findLowerBoundOfTime(time: Long): Pair<Int, Int> {
        val (idx1, idx2) = lyricManager.find(time)
        return when {
            idx1 < 0 -> Pair(idx2, idx2 + 1)
            idx2 == lyricManager.last() -> Pair(idx1, idx2)
            else -> Pair(idx1, idx2)
        }
    }
}
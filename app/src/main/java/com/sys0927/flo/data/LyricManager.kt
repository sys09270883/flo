package com.sys0927.flo.data

class LyricManager private constructor() {

    companion object {
        @Volatile
        private var instance: LyricManager? = null

        @JvmStatic
        fun getInstance(): LyricManager =
            instance ?: synchronized(this) {
                instance ?: LyricManager().also {
                    instance = it
                }
            }
    }

    private val lyrics = mutableListOf<LyricInfo>()

    fun add(lyric: LyricInfo) = lyrics.add(lyric)

    fun sort() = lyrics.sort()

    fun clear() = lyrics.clear()

    fun get(idx: Int): LyricInfo? {
        if (isRange(idx))
            return lyrics[idx]
        return null
    }

    fun find(time: Long): Pair<Int, Int> {
        var l = 0
        var r = lyrics.lastIndex
        while (l < r) {
            val m = (l + r) / 2
            if (lyrics[m].time < time)
                l = m + 1
            else
                r = m
        }
        return Pair(l - 1, l)
    }

    private fun isRange(idx: Int): Boolean {
        if (0 <= idx && idx <= lyrics.lastIndex)
            return true
        return false
    }

    fun last() = lyrics.lastIndex

    fun getCurrentLyrics() = lyrics
}
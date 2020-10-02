package com.sys0927.flo.data

data class LyricInfo(val time: Long, val content: String) : Comparable<LyricInfo> {
    override fun compareTo(other: LyricInfo): Int {
        return compareValues(this.time, other.time)
    }
}
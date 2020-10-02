package com.sys0927.flo.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.sys0927.flo.R

object FragmentConverter {
    private lateinit var fragmentTypeConverter: HashMap<FragmentType, Fragment>

    fun build(musicPlayFragment: Fragment, lyricsFragment: Fragment): FragmentConverter {
        fragmentTypeConverter = hashMapOf(
            FragmentType.MusicPlay to musicPlayFragment, FragmentType.Lyrics to lyricsFragment
        )
        return this
    }

    fun convert(
        activity: FragmentActivity,
        fragmentType: FragmentType,
        noBackStack: Boolean = false
    ) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        if (!noBackStack)
            transaction.addToBackStack(null)
        transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up)
        transaction.replace(R.id.fragmentFrameLayout, fragmentTypeConverter[fragmentType]!!)
            .commit()
    }
}
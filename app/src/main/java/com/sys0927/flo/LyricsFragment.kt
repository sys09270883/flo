package com.sys0927.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.sys0927.flo.data.LyricManager
import com.sys0927.flo.databinding.FragmentLyricsBinding
import kotlinx.android.synthetic.main.fragment_lyrics.*

class LyricsFragment : Fragment() {

    private lateinit var binding: FragmentLyricsBinding
    val lyricTextViewList = mutableListOf<TextView>()
    lateinit var onTimeSelectedListener: OnTimeSelectedListener
    lateinit var onBackKeyPressedListener: OnBackKeyPressedListener

    interface OnTimeSelectedListener {
        fun onTimeSelected(changedTime: Long)
    }

    interface OnBackKeyPressedListener {
        fun onBackKeyPressed()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lyrics, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = (requireActivity() as MainActivity).musicPlayFragment.viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        exitButton.setOnClickListener {
            onBackKeyPressedListener.onBackKeyPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        val lyricInfoList = LyricManager.getInstance().getCurrentLyrics()
        for ((idx, lyricInfo) in lyricInfoList.withIndex()) {
            val textView = View.inflate(requireActivity(), R.layout.lyric_item, null) as TextView
            textView.text = lyricInfo.content.trim()
            if (textView.text.isNullOrBlank()) continue

            textView.setOnClickListener {
                if (toggleLyricButton.isChecked) {
                    onTimeSelectedListener.onTimeSelected(
                        LyricManager.getInstance().get(idx)?.time ?: return@setOnClickListener
                    )
                } else
                    onBackKeyPressedListener.onBackKeyPressed()
            }

            lyricTextViewList.add(textView)
        }
        for (textView in lyricTextViewList) {
            lyricVerticalLayout.addView(textView)
        }
    }

    override fun onPause() {
        super.onPause()
        lyricTextViewList.clear()
        lyricVerticalLayout.removeAllViewsInLayout()
    }
}
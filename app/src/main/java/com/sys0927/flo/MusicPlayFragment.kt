package com.sys0927.flo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sys0927.flo.api.MusicService
import com.sys0927.flo.data.MusicInfo
import com.sys0927.flo.databinding.FragmentMusicPlayBinding
import com.sys0927.flo.utils.FragmentConverter
import com.sys0927.flo.utils.FragmentType
import com.sys0927.flo.viewmodels.MusicPlayFragmentViewModel
import kotlinx.android.synthetic.main.fragment_music_play.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MusicPlayFragment : Fragment() {

    private lateinit var binding: FragmentMusicPlayBinding
    val viewModel: MusicPlayFragmentViewModel by viewModels()
    lateinit var onMusicInfoReceivedListener: OnMusicInfoReceivedListener

    interface OnMusicInfoReceivedListener {
        fun onMusicInFoReceived(file: String, lyrics: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_music_play, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getMusic()

        lyricsLayout.setOnClickListener {
            FragmentConverter.convert(requireActivity(), FragmentType.Lyrics)
        }
    }

    private fun getMusic() {
        val call = MusicService.create().getMusicInfo()
        call.enqueue(object : Callback<MusicInfo> {
            override fun onResponse(call: Call<MusicInfo>, response: Response<MusicInfo>) {
                val musicInfo = response.body()
                musicInfo?.apply {
                    viewModel.setTitle(title)
                    viewModel.setSinger(singer)
                    viewModel.setAlbum(album)
                    viewModel.setImage(image)
                    onMusicInfoReceivedListener.onMusicInFoReceived(file, lyrics)
                }
            }

            override fun onFailure(call: Call<MusicInfo>, t: Throwable) {
                Log.e("error", "${t.message}")
            }
        })
    }

}


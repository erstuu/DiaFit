package com.health.diafit.ui.fragment.typediabetes

import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.BulletSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.health.diafit.R
import com.health.diafit.databinding.FragmentSecondTypeDiabetesBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class SecondTypeDiabetesFragment : Fragment() {
    private var _binding: FragmentSecondTypeDiabetesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondTypeDiabetesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        makeBulletsPoint()
        setupToolbar()
        setupYoutubeView()
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        toolbar.isTitleCentered = true
        toolbar.setNavigationIcon(R.drawable.ic_arrow_30)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupYoutubeView() {
        val ytView = binding.youtubePlayerView
        lifecycle.addObserver(ytView)

        ytView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = "fYIjOErJJw4"
                youTubePlayer.cueVideo(videoId, 0f)
            }
        })
    }

    private fun makeBulletsPoint() {
        val items = resources.getStringArray(R.array.gejala_diabetes2)
        val spannableStringBuilder = SpannableStringBuilder()

        for (i in items.indices) {
            val item = items[i]
            val spannableString = SpannableString(item + "\n")
            spannableString.setSpan(
                BulletSpan(20, ContextCompat.getColor(requireContext(), R.color.black)),
                0,
                item.length,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannableStringBuilder.append(spannableString)
        }

        binding.gejalaDiabetes2.text = spannableStringBuilder
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
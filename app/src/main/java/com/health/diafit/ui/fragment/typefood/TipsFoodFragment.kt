package com.health.diafit.ui.fragment.typefood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.health.diafit.R
import com.health.diafit.databinding.FragmentTipsFoodBinding

class TipsFoodFragment : Fragment() {
    private var _binding: FragmentTipsFoodBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTipsFoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
        setupToolbar()
    }

    private fun setupAction() {
        binding.cvTipsMakanan.setOnClickListener {
            findNavController().navigate(R.id.action_tipsFoodFragment_to_tipsFoodPageFragment)
        }
        binding.cvTipsMenuMakanan.setOnClickListener {
            findNavController().navigate(R.id.action_tipsFoodFragment_to_tipsFoodMenuFragment)
        }
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        toolbar.isTitleCentered = true
        toolbar.setNavigationIcon(R.drawable.ic_arrow_30)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
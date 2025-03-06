package com.health.diafit.ui.fragment.typediabetes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.health.diafit.R
import com.health.diafit.databinding.FragmentTypeDiabetesBinding

class TypeDiabetesFragment : Fragment() {
    private var _binding: FragmentTypeDiabetesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTypeDiabetesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupActions()
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        toolbar.isTitleCentered = true
        toolbar.setNavigationIcon(R.drawable.ic_arrow_30)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupActions() {
        binding.cvTipe1Diabetes.setOnClickListener {
            findNavController().navigate(TypeDiabetesFragmentDirections.actionTypeDiabetesFragmentToFirstTypeDiabetesFragment())
        }

        binding.cvTipe2Diabetes.setOnClickListener {
            findNavController().navigate(TypeDiabetesFragmentDirections.actionTypeDiabetesFragmentToSecondTypeDiabetesFragment())
        }

        binding.cvTipeGestasionalDiabetes.setOnClickListener {
            findNavController().navigate(TypeDiabetesFragmentDirections.actionTypeDiabetesFragmentToGestasionalTypeDiabetesFragment())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
package com.health.diafit.ui.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import com.health.diafit.R
import com.health.diafit.adapter.WebLinkAdapter
import com.health.diafit.data.ResultState
import com.health.diafit.databinding.FragmentHomeBinding
import com.health.diafit.models.WebLink
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeNameAndHistory()
        setupShapeBackground()
        setupAction()
        setupRecyclerView()
    }

    private fun setupAction() {
        binding.btnCekDiabetes.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment2_to_inputDataFragment)
        )
        binding.cvTipeDiabetes.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment2_to_typeDiabetesFragment2)
        )
        binding.ivOlahraga.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment2_to_workoutRecommendFragment)
        }
        binding.ivKomplikasi.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment2_to_complicationDiabetesFragment)
        }
        binding.ivTipsMakanan.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment2_to_tipsFoodFragment)
        }
    }

    private fun observeNameAndHistory() {

        viewModel.getUsername().observe(viewLifecycleOwner) { name ->
            if (name.isEmpty()) {
                greeting(getString(R.string.guest))
            } else {
                greeting(name)
            }
        }

        viewModel.getCurrentHistory().observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Success -> {
                    val history = result.data
                    binding.tvGulaDarah.text = history.bloodGlucoseLevel.toString()
                    binding.tvHemoglobin.text = history.hbA1cLevel.toString()
                    binding.tvDiabetRisk.text = history.diabetesCategory.toString()
                }
                is ResultState.Error -> {
                    binding.tvGulaDarah.text = context?.getString(R.string._0)
                    binding.tvHemoglobin.text = context?.getString(R.string._0_0)
                    binding.tvDiabetRisk.text = context?.getString(R.string.normal)
                }
                is ResultState.Loading -> {
                    binding.tvGulaDarah.text = context?.getString(R.string._0)
                    binding.tvHemoglobin.text = context?.getString(R.string._0_0)
                    binding.tvDiabetRisk.text = context?.getString(R.string.normal)
                }
            }
        }

    }

    private fun setupRecyclerView() {
        val listNews = listOf(
            WebLink("14 Cara Menurunkan Gula Darah yang Paling Ampuh",
                "https://www.alodokter.com/cek-cara-menurunkan-gula-darah-di-sini",
                "https://res.cloudinary.com/dk0z4ums3/image/upload/v1613445255/attached_image/cek-cara-menurunkan-gula-darah-di-sini-0-alodokter.jpg",
                "Penderita diabetes perlu memahami cara menurunkan gula darah agar kadarnya tetap stabil. Tidak hanya efektif mencegah lonjakan kadar gula, berbagai cara menurunkan gula darah ini juga dapat meminimalkan risiko terjadinya komplikasi akibat diabetes."),

            WebLink("Diabetes - Gejala, Penyebab, dan Pencegahan",
                "https://www.rspondokindah.co.id/id/news/diabetes-gejala-penyebab-penanganan",
                "https://storage.googleapis.com/rspi-assets-production/rspi-api/uploads/MTcxNjM2MTQ4NzkzOQ==.jpg",
                "Diabetes adalah kondisi tingginya gula darah yang kemudian dapat menyebabkan berbagai komplikasi. Simak jenis, gejala, penyebab, dan penanganan diabetes di sini."),

            WebLink("10 Cara Mengontrol Kadar Gula Darah bagi Orang Diabetes",
                "https://hellosehat.com/diabetes/cara-mengontrol-gula-darah/",
                "https://cdn.hellosehat.com/wp-content/uploads/2017/09/shutterstock_1727756401.jpg?w=750&q=75",
                "Diabetes belum bisa disembuhkan sepenuhnya, tapi penyandang diabetes tetap bisa beraktivitas normal dengan mengontrol kadar gula darah dalam batas normal. Simak beberapa tips menjaga kadar gula darah tetap normal berikut ini."),

            WebLink("Daftar Makanan untuk Gula Darah Tinggi yang Sehat dan Enak",
                "https://www.rspondokindah.co.id/id/news/makanan-untuk-gula-darah-tinggi",
                "https://storage.googleapis.com/rspi-assets-production/rspi-api/uploads/MTcyOTA0NTI5NjkwMA==.jpg", "Essential lifestyle tips and changes you can adopt to help prevent diabetes and improve your overall health."),

            // bermasalah
            WebLink("Diabetes Gestasional: Pengertian, Penyebab, Faktor Resiko, Gejala dan Pengobatannya",
                "https://www.rspondokindah.co.id/id/news/diabetes-gestasional--diabetesnya-ibu-hamil",
                "https://storage.googleapis.com/rspi-assets-production/rspi-api/uploads/5eafc53b5c66e_20200504143315-1.jpg",
                "Diabetes gestasional adalah kondisi meningkatnya kadar gula darah selama kehamilan akibat tubuh tidak mampu memproduksi cukup insulin untuk mengimbangi perubahan hormon.")
        )

        binding.rvArtikel.layoutManager = LinearLayoutManager(requireContext())
        val adapter = WebLinkAdapter { webLink ->
            val linkWeb = WebLink(webLink.title, webLink.url, webLink.imageUrl, webLink.description)
            val action = HomeFragmentDirections.actionHomeFragment2ToNewsFragment(linkWeb)
            Navigation.findNavController(requireView()).navigate(action)
        }
        binding.rvArtikel.adapter = adapter
        adapter.submitList(listNews)
    }

    private fun setupShapeBackground() {
        val cornerSizeBackground = resources.getDimension(R.dimen.corner_radius_grey_background)
        val shapeAppearanceModelBackground = ShapeAppearanceModel.builder()
            .setBottomLeftCorner(RoundedCornerTreatment())
            .setBottomLeftCornerSize(cornerSizeBackground)
            .setBottomRightCorner(RoundedCornerTreatment())
            .setBottomRightCornerSize(cornerSizeBackground)
            .build()

        val shapeDrawableBackground = MaterialShapeDrawable(shapeAppearanceModelBackground)
        shapeDrawableBackground.fillColor = ContextCompat.getColorStateList(requireContext(), R.color.diaPrimary)
        binding.materialCardView.background = shapeDrawableBackground
    }

    private fun greeting(name: String) {
        binding.userName.text = name
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
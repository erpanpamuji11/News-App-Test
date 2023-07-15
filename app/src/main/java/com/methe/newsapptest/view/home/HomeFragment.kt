package com.methe.newsapptest.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.methe.newsapptest.R
import com.methe.newsapptest.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            linearBusiness.setOnClickListener {
                val bundle = bundleOf("category" to "Business")
                findNavController().navigate(R.id.action_homeFragment_to_categoryFragment, bundle)
            }
            linearEntertaiment.setOnClickListener {
                val bundle = bundleOf("category" to "Entertainment")
                findNavController().navigate(R.id.action_homeFragment_to_categoryFragment, bundle)
            }
            linearGeneral.setOnClickListener {
                val bundle = bundleOf("category" to "General")
                findNavController().navigate(R.id.action_homeFragment_to_categoryFragment, bundle)
            }
            linearHealty.setOnClickListener {
                val bundle = bundleOf("category" to "Healthy")
                findNavController().navigate(R.id.action_homeFragment_to_categoryFragment, bundle)
            }
            linearScience.setOnClickListener {
                val bundle = bundleOf("category" to "Science")
                findNavController().navigate(R.id.action_homeFragment_to_categoryFragment, bundle)
            }
            linearSport.setOnClickListener {
                val bundle = bundleOf("category" to "Sport")
                findNavController().navigate(R.id.action_homeFragment_to_categoryFragment, bundle)
            }
            linearTechno.setOnClickListener {
                val bundle = bundleOf("category" to "Technology")
                findNavController().navigate(R.id.action_homeFragment_to_categoryFragment, bundle)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
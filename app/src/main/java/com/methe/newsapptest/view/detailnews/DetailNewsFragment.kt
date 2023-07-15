package com.methe.newsapptest.view.detailnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.methe.newsapptest.databinding.FragmentDetailNewsBinding

class DetailNewsFragment : Fragment() {

    private var _binding: FragmentDetailNewsBinding? = null
    private val binding get() = _binding!!
    private lateinit var webView: WebView

    private var _urlNews: String? = null
    private val urlNews get() = _urlNews!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.webView.webViewClient = WebViewClient()
        _urlNews = arguments?.getString("urlNews")
        webView = WebView(requireContext())
        val settings = webView.settings
        settings.javaScriptEnabled = true

        binding.webView.loadUrl(urlNews)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
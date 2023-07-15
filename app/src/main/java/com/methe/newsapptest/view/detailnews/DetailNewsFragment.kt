package com.methe.newsapptest.view.detailnews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import com.methe.newsapptest.R
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
        binding.webView.webViewClient = WebViewClient() // Menggunakan WebViewClient agar halaman web dibuka di WebView

        _urlNews = arguments?.getString("urlNews")
        // Mengaktifkan JavaScript (opsional, tergantung pada kebutuhan aplikasi Anda)
        webView = WebView(requireContext())
        val settings = webView.settings
        settings.javaScriptEnabled = true

        // Load halaman web
        binding.webView.loadUrl(urlNews)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
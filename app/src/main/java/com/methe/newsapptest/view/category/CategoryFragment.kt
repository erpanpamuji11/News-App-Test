package com.methe.newsapptest.view.category

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.methe.newsapptest.R
import com.methe.newsapptest.data.remote.models.ArticlesItem
import com.methe.newsapptest.databinding.FragmentCategoryBinding
import com.methe.newsapptest.utils.LoaderAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var newsEverythingAdapter: NewsEverythingAdapter
    private val newsEverythingViewModel: NewsEverythingViewModel by viewModels()
    private lateinit var shimmerNews: ShimmerFrameLayout

    private var _category: String? = null
    private val category get() = _category!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _category = arguments?.getString("category")
        binding.tvCategoryTitle.text = category
        newsEverythingAdapter = NewsEverythingAdapter(::onNewsEverythingClick)

        shimmerNews = binding.shimmerNews
        shimmerNews.startShimmer()

        Handler(Looper.getMainLooper()).postDelayed({
            initEverythingNews(category)
        }, 2000)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search query submission
                if (query != null) {
                    initEverythingNews(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == ""){
                    initEverythingNews(category)
                }
                return false
            }
        })

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun onNewsEverythingClick(item: ArticlesItem) {
        val bundle = bundleOf("urlNews" to item.url)
        findNavController().navigate(R.id.action_categoryFragment_to_detailNewsFragment, bundle)
    }

    private fun initEverythingNews(query: String) {

        rvInstance()
        binding.apply {
            lifecycleScope.launch {
                newsEverythingViewModel.getNews(query).catch { e ->
                    Log.d("cek", "dfddffd${e.message}")
                    tvError.visibility = View.VISIBLE
                    tvError.text = e.message
                }.collectLatest{
                    newsEverythingAdapter.addLoadStateListener { loadState ->
                        if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && newsEverythingAdapter.itemCount < 1){
                            shimmerNews.stopShimmer()
                            shimmerNews.visibility = View.INVISIBLE
                        } else {
                            shimmerNews.stopShimmer()
                            shimmerNews.visibility = View.INVISIBLE
                        }
                    }
                    Log.d("guys", "Success")
                    newsEverythingAdapter.submitData(it)

                    Log.d("TAG_GET_DATA", newsEverythingAdapter.snapshot().items.toString())

                }
            }
        }
    }

    private fun searchNewsData(query: String) {
        rvInstance()
        binding.apply {
            lifecycleScope.launch {
                newsEverythingViewModel.getNews(query).catch { e ->
                    Log.d("cek", "dfddffd${e.message}")
                }.collectLatest{
                    Log.d("guys", "Success")
                    newsEverythingAdapter.submitData(it)
                    Log.d("TAG_GET_DATA", newsEverythingAdapter.snapshot().items.toString())
                }
            }
        }
    }

    private fun rvInstance(){
        binding.apply {
            rvNews.setHasFixedSize(true)
            rvNews.layoutManager = LinearLayoutManager(requireContext())
            rvNews.adapter = newsEverythingAdapter.withLoadStateHeaderAndFooter(
                footer = LoaderAdapter(),
                header = LoaderAdapter()
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        shimmerNews.stopShimmer()
    }
}
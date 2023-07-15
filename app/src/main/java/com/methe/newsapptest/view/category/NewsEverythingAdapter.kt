package com.methe.newsapptest.view.category

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.methe.newsapptest.data.remote.models.ArticlesItem
import com.methe.newsapptest.databinding.NewsItemBinding
import com.methe.newsapptest.utils.DateFormatter
import java.util.TimeZone

class NewsEverythingAdapter(private val onNewsClick: (ArticlesItem) -> Unit) :
    PagingDataAdapter<ArticlesItem, NewsEverythingAdapter.NewsViewHolder>(ComparatorDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = getItem(position)
        news?.let {
            holder.bind(it)
        }
    }

    inner class NewsViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(news: ArticlesItem) {
            binding.apply {
                tvTitle.text = news.title
                tvAuthor.text = news.author
                tvDate.text = DateFormatter.formatDate(news.publishedAt, TimeZone.getDefault().id)
                root.setOnClickListener {
                    onNewsClick(news)
                }
            }
        }

    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<ArticlesItem>() {
        override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem == newItem
        }
    }

}
package com.example.newsapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.models.Article
import kotlinx.android.synthetic.main.article_layout.view.*

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view){

    }
    private val callback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.article_layout,parent,false)
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val curArticle = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(curArticle.urlToImage).into(article_image)
            article_image.clipToOutline = true
            article_titile.text = curArticle.title
            article_text_date.text = curArticle.publishedAt

            setOnClickListener() {
                onItemClickListener?.let {
                    it(curArticle)
                }
            }
            icon_like.setOnClickListener {view ->
                    onIconClickListener?.let {
                        it(curArticle) }
                }
            }
        }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit){
        onItemClickListener =  listener
    }

    private var onIconClickListener: ((Article) -> Unit)? = null

    fun setOnIconClickListener(listener: (Any?) -> Unit){
        onIconClickListener =  listener
    }



}
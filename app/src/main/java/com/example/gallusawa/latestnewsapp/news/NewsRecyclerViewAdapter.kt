package com.example.gallusawa.latestnewsapp.news

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by gallusawa on 12/7/17.
 */
class NewsRecyclerViewAdapter(private val listener: ItemClickListener?, private val articleList: MutableList<Article>?) : RecyclerView.Adapter<NewsRecyclerViewAdapter.ItemHolder>() {

    interface ItemClickListener {
        fun onItemClick(article: Article)
    }

    init {
        notifyDataSetChanged()
    }

    fun addArticles(articleList: List<Article>) {
        this.articleList!!.addAll(articleList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.article_item, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.view.setOnClickListener {
            listener?.onItemClick(articleList!![holder.adapterPosition])
        }
        val title = articleList!![position].getTitle()
        val author = articleList[position].getAuthor()
        val date = articleList[position].getPublishedAt()
        holder.author.setText(author)
        holder.title.setText(title)
        try {
            holder.date.text = formatFrom.format(formatTo.parse(date))
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        var imageUrl = articleList[position].getImageUrl()
        if (!TextUtils.isEmpty(imageUrl)) {
            if (!imageUrl.toLowerCase().startsWith("http")) {
                imageUrl = "http:" + imageUrl
            }
            holder.newsImage.setImageURI(Uri.parse(imageUrl))
        }
    }

    override fun getItemCount(): Int {
        return articleList?.size ?: 0
    }

    inner class ItemHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val newsImage: SimpleDraweeView
        private val author: TextView
        private val title: TextView
        private val date: TextView

        init {
            this.newsImage = view.findViewById(R.id.list_item_icon) as SimpleDraweeView
            this.title = view.findViewById(R.id.list_item_name) as TextView
            this.author = view.findViewById(R.id.list_item_sub_name) as TextView
            this.date = view.findViewById(R.id.list_item_date) as TextView
        }
    }

    companion object {
        private val formatTo = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        private val formatFrom = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    }
}

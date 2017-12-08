package com.example.gallusawa.latestnewsapp.sources

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
class SourcesRecyclerViewAdapter(private val listener: ItemClickListener?, private var sourceList: List<Source>?) : RecyclerView.Adapter<SourcesRecyclerViewAdapter.ItemHolder>() {

    interface ItemClickListener {
        fun onItemClick(source: Source)
    }

    init {
        notifyDataSetChanged()
    }

    fun setSourceList(sourceList: List<Source>) {
        this.sourceList = sourceList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.source_item, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.view.setOnClickListener {
            listener?.onItemClick(sourceList!![holder.adapterPosition])
        }
        val name = sourceList!![position].getName()
        val description = sourceList!![position].getDescription()
        holder.name.setText(name)
        holder.description.setText(description)
        var imageUrl = sourceList!![position].getUrlsToLogos().getMedium()
        if (!TextUtils.isEmpty(imageUrl)) {
            if (!imageUrl.toLowerCase().startsWith("http")) {
                imageUrl = "http:" + imageUrl
            }
            holder.newsImage.setImageURI(Uri.parse(imageUrl))
        }
    }

    override fun getItemCount(): Int {
        return if (sourceList != null) sourceList!!.size else 0
    }

    inner class ItemHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val newsImage: SimpleDraweeView
        private val description: TextView
        private val name: TextView

        init {
            this.newsImage = view.findViewById(R.id.list_item_icon) as SimpleDraweeView
            this.description = view.findViewById(R.id.list_item_sub_name) as TextView
            this.name = view.findViewById(R.id.list_item_name) as TextView
        }
    }
}

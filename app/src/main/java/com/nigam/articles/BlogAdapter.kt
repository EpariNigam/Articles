package com.nigam.articles

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.nigam.articles.databinding.ItemArticlesBinding
import com.nigam.articles.model.Blog
import java.util.*

class BlogAdapter(private val context: Context, private val items: List<Blog>) :
    RecyclerView.Adapter<BlogAdapter.BlogViewHolder>() {
    private val inflater = LayoutInflater.from(context)

    inner class BlogViewHolder(val binding: ItemArticlesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        return BlogViewHolder(ItemArticlesBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val blog = items[position]
        holder.binding.ivUser.load(blog.user[0].avatar) {
            transformations(CircleCropTransformation())
        }
        holder.binding.tvName.text =
            String.format("%s %s", blog.user[0].name, blog.user[0].lastname, Locale.ENGLISH)
        holder.binding.tvDesignation.text = blog.user[0].designation
        holder.binding.tvTime.text = blog.createdAt.covertTimeToText()
        if (blog.media.isEmpty()) {
            holder.binding.ivArticleImage.visibility = View.GONE
        } else {
            holder.binding.ivArticleImage.visibility = View.VISIBLE
            holder.binding.ivArticleImage.load(blog.media[0].image)
        }
        holder.binding.tvContent.text = blog.content
        holder.binding.tvLikes.text =
            String.format("%s %s", blog.likes.countWithSuffix(), context.getString(R.string.likes))
        holder.binding.tvComments.text =
            String.format(
                "%s %s",
                blog.comments.countWithSuffix(),
                context.getString(R.string.comments)
            )
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
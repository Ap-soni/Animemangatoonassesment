package com.example.animemangatoon.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animemangatoon.R
import com.example.animemangatoon.modelclass.Webtoon

class FavouritesAdapter(
    private val webtoonList: List<Webtoon>,
    private val onDeleteClick: (Webtoon) -> Unit

) : RecyclerView.Adapter<FavouritesAdapter.WebtoonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebtoonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favourites, parent, false)
        return WebtoonViewHolder(view)
    }

    override fun onBindViewHolder(holder: WebtoonViewHolder, position: Int) {
        val webtoon = webtoonList[position]
        holder.titleTextView.text = webtoon.title
        holder.descriptionTextView.text = webtoon.description
        Glide.with(holder.itemView.context)
            .load(webtoon.imageUrl).placeholder(R.drawable.img_1)
            .into(holder.imageView)

        holder.deleteFavourite.setOnClickListener {
            onDeleteClick(webtoon)
        }
    }

    override fun getItemCount(): Int = webtoonList.size

    class WebtoonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.webtoonTitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.webtoonDescription)
        val imageView: ImageView = itemView.findViewById(R.id.webtoonImage)
        val deleteFavourite: ImageView = itemView.findViewById(R.id.deleteFavourite)

    }
}

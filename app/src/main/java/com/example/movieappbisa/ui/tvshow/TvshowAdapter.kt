package com.example.movieappbisa.ui.tvshow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movieappbisa.R
import com.example.movieappbisa.databinding.ItemCardMovieBinding
import com.example.movieappbisa.entity.local.source.MovieData
import com.example.movieappbisa.entity.local.source.TvShowData
import com.example.movieappbisa.ui.detail.DetailActivity
import com.example.movieappbisa.ui.detail.Type

class TvshowAdapter : PagedListAdapter<TvShowData, TvshowAdapter.TvViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowData>() {
            override fun areItemsTheSame(oldItem: TvShowData, newItem: TvShowData): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: TvShowData, newItem: TvShowData): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun getSwipedData(swipedPosition: Int): TvShowData? = getItem(swipedPosition)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val itemsAcademyBinding = ItemCardMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvViewHolder(itemsAcademyBinding)
    }
    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        val tv = getItem(position)
        if (tv!= null) {
            holder.bind(tv)
        }
    }

    class TvViewHolder(private val binding: ItemCardMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieTv: TvShowData) {

            with(binding) {
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w185"+ movieTv.poster)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                    .into(poster)
                title.text = movieTv.title
                rate.text = movieTv.rate.toString()

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_TYPE, Type.TV_SHOW.ordinal)
                    intent.putExtra(DetailActivity.EXTRA_MOVIE, movieTv.id)
                    itemView.context.startActivity(intent)
                }

            }
        }
    }
}
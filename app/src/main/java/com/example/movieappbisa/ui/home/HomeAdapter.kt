package com.example.movieappbisa.ui.home

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
import com.example.movieappbisa.ui.detail.DetailActivity
import com.example.movieappbisa.ui.detail.Type
import com.example.movieappbisa.entity.local.source.MovieData

class HomeAdapter : PagedListAdapter<MovieData, HomeAdapter.MovieViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieData>() {
            override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
                return oldItem == newItem
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemsAcademyBinding = ItemCardMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemsAcademyBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movies = getItem(position)
        if (movies != null) {
            holder.bind(movies)
        }
    }

    fun getSwipedData(swipedPosition: Int): MovieData? = getItem(swipedPosition)


    class MovieViewHolder(private val binding: ItemCardMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieTv: MovieData) {
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
                    intent.putExtra(DetailActivity.EXTRA_TYPE, Type.MOVIE.ordinal)
                    intent.putExtra(DetailActivity.EXTRA_MOVIE, movieTv.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}
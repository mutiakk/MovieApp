package com.example.movieappbisa.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movieappbisa.R
import com.example.movieappbisa.databinding.ActivityDetailBinding
import com.example.movieappbisa.entity.local.source.MovieData
import com.example.movieappbisa.entity.local.source.TvShowData
import com.example.movieappbisa.ui.modelfactory.ModelFactory
import com.example.movieappbisa.vo.Status

class DetailActivity : AppCompatActivity() {

    companion object{

        const val EXTRA_TYPE="extra_type"
        const val EXTRA_MOVIE="extra_movie"

    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val type = intent.getIntExtra(EXTRA_TYPE, -1)
        val enum: Type = Type.values()[type]

        val idMovie = intent.getIntExtra(EXTRA_MOVIE, -1)

        val factory = ModelFactory.getInstance(this)
        detailModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        //byClick Category

        when (enum) {
            Type.MOVIE -> {
                detailModel.getMovieDetail(idMovie).observe(this,
                    { movie ->
                        if (movie != null){
                            when(movie.status){
                                Status.LOADING -> binding.progressBar.visibility =
                                    View.VISIBLE
                                Status.SUCCESS -> movie.data?.run {
                                    getDetail(
                                        this,
                                        null) }
                                Status.ERROR -> {
                                    binding.progressBar.visibility = View.GONE
                                    Toast.makeText(this, "Something Going Wrong", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    })
            }
            Type.TV_SHOW -> {
                detailModel.getTvDetail(idMovie).observe(this, { tvShow ->
                        if (tvShow != null) {
                            when(tvShow.status){
                                Status.LOADING -> binding.progressBar.visibility =
                                    View.VISIBLE
                                Status.SUCCESS -> tvShow.data?.run {
                                    getDetail(
                                        null,
                                        this)
                                }
                                Status.ERROR -> {
                                    binding.progressBar.visibility = View.GONE
                                    Toast.makeText(this, "Something Going Wrong", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    })
            }
        }
    }

    private fun setFav(movie: MovieData?, tv: TvShowData?) {
        if (movie != null) {
            if (movie.isFavorite){
                Toast.makeText(this, R.string.unFavorite, Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(this, R.string.add_fav, Toast.LENGTH_SHORT).show()
            }
            detailModel.setFavMovie(movie, movie.isFavorite)
        } else{
            if (tv != null) {
                if(tv.isFavorite){
                    Toast.makeText(this, R.string.unFavorite, Toast.LENGTH_SHORT).show()
                } else{
                    Toast.makeText(this, R.string.add_fav, Toast.LENGTH_SHORT).show()
                }
                detailModel.setFavTV(tv, tv.isFavorite)
            }
        }
    }

    private fun setFavState(state: Boolean?) {
        if (state == true){
            binding.favBut.setImageResource(R.drawable.ic_fav_on)
        } else{
            binding.favBut.setImageResource(R.drawable.ic_fav_off)
        }
    }


    private fun getDetail(movie: MovieData?, tv: TvShowData?) {
        binding.progressBar.visibility = View.INVISIBLE
        val titlex = movie?.title ?: tv?.title
        val date = movie?.release ?: tv?.release
        val ratex= movie?.rate?:tv?.rate
        val descx= movie?.descr?:tv?.descr
        val img=movie?.poster?: tv?.poster
        val state = movie?.isFavorite ?: tv?.isFavorite

        setFavState(state)

        binding.apply {
            title.text = titlex
            release.text = date
            rate.text=ratex.toString()
            describe.text=descx
            favBut.setOnClickListener {
                setFav(
                    movie, tv
                )
            }
        }

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w185$img")
            .apply(RequestOptions())
            .into(binding.poster)
    }
}
package com.example.movieappbisa.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.movieappbisa.R
import com.example.movieappbisa.databinding.FragmentFavMovieBinding
import com.example.movieappbisa.ui.home.HomeAdapter
import com.example.movieappbisa.ui.modelfactory.ModelFactory
import com.google.android.material.snackbar.Snackbar


class FavMovieFragment : Fragment() {
    private lateinit var binding: FragmentFavMovieBinding
    private lateinit var adapterHome: HomeAdapter
    private lateinit var viewModel: FavMovieTvModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding.rvGridMovie)
        if (activity != null) {

            showListFavo()

        }
    }

    private fun showListFavo() {
        val factory = ModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[FavMovieTvModel::class.java]

        adapterHome = HomeAdapter()

        viewModel.getFavoMovie().observe(viewLifecycleOwner, { favMovie ->
            adapterHome.submitList(favMovie)
            adapterHome.notifyDataSetChanged()
        })

        binding.rvGridMovie.apply {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = adapterHome
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val movieEntity = adapterHome.getSwipedData(swipedPosition)
                movieEntity?.let { viewModel.setFavoriteMovie(movieEntity, it.isFavorite) }
                val snackbar = Snackbar.make(view as View, R.string.undoMessage, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.ok) { _ ->
                    movieEntity?.let { viewModel.setFavoriteMovie(movieEntity, it.isFavorite) }
                }
                snackbar.show()
            }
        }
    })
}
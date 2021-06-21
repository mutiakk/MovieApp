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
import com.example.movieappbisa.databinding.FragmentFavTvShowBinding
import com.example.movieappbisa.ui.modelfactory.ModelFactory
import com.example.movieappbisa.ui.tvshow.TvshowAdapter
import com.google.android.material.snackbar.Snackbar

class FavTvShowFragment : Fragment() {
    private lateinit var binding: FragmentFavTvShowBinding
    private lateinit var adapterTv: TvshowAdapter
    private lateinit var viewModel: FavMovieTvModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavTvShowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding.rvGridTv)
        if (activity != null) {

            showListFavo()

        }
    }

    private fun showListFavo() {
        val factory = ModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[FavMovieTvModel::class.java]

        adapterTv= TvshowAdapter()

        viewModel.getFavoTv().observe(viewLifecycleOwner, { favTv ->
            adapterTv.submitList(favTv)
            adapterTv.notifyDataSetChanged()
        })

        binding.rvGridTv.apply {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = adapterTv
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
                val tvEntity = adapterTv.getSwipedData(swipedPosition)
                tvEntity?.let { viewModel.setFavoriteTv(tvEntity, it.isFavorite) }
                val snackbar = Snackbar.make(view as View, R.string.undoMessage, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.ok) { _ ->
                    tvEntity?.let { viewModel.setFavoriteTv(tvEntity, it.isFavorite) }
                }
                snackbar.show()
            }
        }
    })
}
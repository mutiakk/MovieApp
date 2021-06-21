package com.example.movieappbisa.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieappbisa.R
import com.example.movieappbisa.databinding.FragmentHomeBinding
import com.example.movieappbisa.ui.favorite.FavoriteActivity
import com.example.movieappbisa.ui.modelfactory.ModelFactory
import com.example.movieappbisa.vo.Status

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.fav->{
                val intent= Intent(activity, FavoriteActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            showGrid()
        }
    }

    private fun showGrid() {
        val factory= ModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(
            this, factory)[HomeViewModel::class.java]

        movieObserver()
        homeAdapter = HomeAdapter()

        with(binding.rvGridMovie) {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = homeAdapter
        }
    }

    private fun movieObserver() {
        viewModel.getMovies().observe(viewLifecycleOwner, { movie ->
            if (movie != null) {
                when (movie.status) {
                    Status.LOADING -> binding.progressBar.visibility =VISIBLE
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        homeAdapter.submitList(movie.data)
                        homeAdapter.notifyDataSetChanged()
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

}


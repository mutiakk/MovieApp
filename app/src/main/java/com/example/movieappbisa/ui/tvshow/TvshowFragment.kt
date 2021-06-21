package com.example.movieappbisa.ui.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieappbisa.R
import com.example.movieappbisa.ui.modelfactory.ModelFactory
import com.example.movieappbisa.databinding.FragmentTvshowBinding
import com.example.movieappbisa.ui.favorite.FavoriteActivity
import com.example.movieappbisa.vo.Status

class TvshowFragment : Fragment() {

    private lateinit var binding: FragmentTvshowBinding
    private lateinit var viewModel:TvshowViewModel
    private lateinit var tvshowAdapter: TvshowAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        binding = FragmentTvshowBinding.inflate(layoutInflater, container, false)
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
            this, factory)[TvshowViewModel::class.java]

        tvshowAdapter = TvshowAdapter()
        movieObserver()

        with(binding.rvGridTv) {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = tvshowAdapter
        }
    }

    private fun movieObserver() {
        viewModel.getTvShows().observe(viewLifecycleOwner, { tv ->
            if (tv != null) {
                when (tv.status) {
                    Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        tvshowAdapter.submitList(tv.data)
                        tvshowAdapter.notifyDataSetChanged()
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context, "Something going Wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}


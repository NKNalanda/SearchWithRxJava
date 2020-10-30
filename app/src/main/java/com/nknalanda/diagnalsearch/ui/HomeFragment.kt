package com.nknalanda.diagnalsearch.ui

import android.content.Context
import android.content.res.Configuration
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.grzegorzojdana.spacingitemdecoration.Spacing
import com.grzegorzojdana.spacingitemdecoration.SpacingItemDecoration
import com.nknalanda.diagnalsearch.R
import com.nknalanda.diagnalsearch.adapter.MovieAdapter
import com.nknalanda.diagnalsearch.databinding.FragmentHomeBinding
import com.nknalanda.diagnalsearch.datamodel.MovieModel
import com.nknalanda.diagnalsearch.utils.EndlessScrollListener
import com.nknalanda.diagnalsearch.viewmodel.HomeViewModel


class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    private var viewModel: HomeViewModel? = null
    private var check = ArrayList<MovieModel>()
    private var movieAdapter: MovieAdapter? = null
    private var movieList = ArrayList<MovieModel>()
    private var isLoading = false
    private var isLastPage: Boolean = false
    private var currentIndex: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        initViews()
        return binding?.root
    }

    //To initialize views
    private fun initViews() {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding?.viewmodel = viewModel
        binding?.lifecycleOwner = this
        binding?.executePendingBindings()
        checkReportAdapter()
        viewModel?.parseMovieJson(currentIndex)
        observeLiveData()
    }

    //To observe live data
    private fun observeLiveData() {
        viewModel?.movieLiveData?.observe(viewLifecycleOwner, Observer {
            check.clear()
            movieList.addAll(it)
            check.addAll(it)
            isLoading = false
            movieAdapter?.notifyDataSetChanged()
            Log.e(TAG, movieList.size.toString())
        })
    }

    override fun onResume() {
        super.onResume()
        try {
            (activity as MainActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(false)
            (activity as MainActivity?)!!.supportActionBar?.title = resources.getString(R.string.romantic_comedy)
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }

    //To handle adapter
    private fun checkReportAdapter() {

        val spacingItemDecoration = SpacingItemDecoration(
            Spacing(
                horizontal = resources.getDimensionPixelSize(R.dimen.dimen_size_10dp),
                vertical = resources.getDimensionPixelSize(R.dimen.dimen_size_30dp),
                edges = Rect(30, 36, 30, 36)
            )
        )
        binding?.rvMovie?.addItemDecoration(spacingItemDecoration)
        movieAdapter = MovieAdapter(requireContext(), movieList)
        binding?.rvMovie?.adapter = movieAdapter

        binding?.rvMovie?.addOnScrollListener(object :
            EndlessScrollListener(binding?.rvMovie?.layoutManager as GridLayoutManager) {
            override fun loadMoreItems() {
                if (check.size >= 20) {
                    isLoading = true
                    currentIndex += 1
                    viewModel?.parseMovieJson(currentIndex)
                }
            }

            override fun getTotalPageCount(): Int {
                return 0
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

        })
    }

    companion object {
        private const val TAG = "HomeFragment"
    }

}
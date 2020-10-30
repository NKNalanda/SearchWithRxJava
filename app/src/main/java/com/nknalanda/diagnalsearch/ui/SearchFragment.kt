package com.nknalanda.diagnalsearch.ui

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.grzegorzojdana.spacingitemdecoration.Spacing
import com.grzegorzojdana.spacingitemdecoration.SpacingItemDecoration
import com.nknalanda.diagnalsearch.R
import com.nknalanda.diagnalsearch.adapter.MovieAdapter
import com.nknalanda.diagnalsearch.databinding.FragmentSearchBinding
import com.nknalanda.diagnalsearch.datamodel.MovieModel
import com.nknalanda.diagnalsearch.viewmodel.SearchViewModel


class SearchFragment : Fragment() {

    private var binding: FragmentSearchBinding? = null
    private var viewModel: SearchViewModel? = null
    private var movieAdapter: MovieAdapter? = null
    private var movieList = ArrayList<MovieModel>()
    private var isFirst: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        initViews()
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        try {
            (activity as MainActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            (activity as MainActivity?)!!.supportActionBar?.title = ""
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }

    //To initialize views
    private fun initViews() {
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        binding?.vm = viewModel
        binding?.lifecycleOwner = this
        binding?.executePendingBindings()
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
        observeLiveData()
    }

    //To observe live data
    private fun observeLiveData() {
        viewModel?.movieLiveData?.observe(viewLifecycleOwner, Observer {
            isFirst = false
            movieList.clear()
            movieList.addAll(it)
            movieAdapter?.notifyDataSetChanged()
            if (it.size > 0) {
                binding?.tvNoMovie?.visibility = View.GONE
            } else {
                binding?.tvNoMovie?.visibility = View.VISIBLE
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.app_bar_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)

        var searchView: SearchView? = null
        searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Search(Type min 3 letters)"
        viewModel?.searchMovie(searchView)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val searchViewMenuItem = menu.findItem(R.id.action_search)
        val mSearchView: SearchView = MenuItemCompat.getActionView(searchViewMenuItem) as SearchView
        val search_close_btnId: Int = androidx.appcompat.R.id.search_close_btn
        mSearchView.findViewById<ImageView>(search_close_btnId).setImageResource(R.drawable.search_cancel)
        mSearchView.maxWidth = Int.MAX_VALUE

        return super.onPrepareOptionsMenu(menu)
    }

    companion object {
        private const val TAG = "SearchFragment"
    }
}
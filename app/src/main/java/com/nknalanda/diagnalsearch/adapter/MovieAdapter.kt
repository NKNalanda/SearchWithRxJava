package com.nknalanda.diagnalsearch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nknalanda.diagnalsearch.databinding.MovieItemBinding
import com.nknalanda.diagnalsearch.datamodel.MovieModel
import com.nknalanda.diagnalsearch.viewmodel.MovieItemViewModel

class MovieAdapter(
    private var context: Context,
    private var movieList: ArrayList<MovieModel>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var binding: MovieItemBinding? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = MovieItemBinding.inflate(layoutInflater, parent, false)
        viewHolder = MovieViewHolder(binding?.root!!)
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val model: MovieModel = movieList[position]
        val viewHolder: MovieViewHolder = holder as MovieViewHolder
        val itemViewModel = MovieItemViewModel(context, model)
        binding?.vm = itemViewModel
        viewHolder.bind(model, position, itemViewModel)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class MovieViewHolder(
        private val view: View
    ): RecyclerView.ViewHolder(view.rootView) {
        fun bind(item: MovieModel, position: Int, viewModel: MovieItemViewModel) {
            binding?.ivPoster?.setImageResource(viewModel.getPoster())

        }

    }
}
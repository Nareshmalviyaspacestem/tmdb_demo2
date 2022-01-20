package com.movies.tmdb_demo2.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.movies.tmdb_demo2.BR
import com.movies.tmdb_demo2.databinding.MovieViewItemBinding
import com.movies.tmdb_demo2.model.Movie

class MoviePagingAdapter(private val clickHandler: ClickHandler) :PagingDataAdapter<Movie,MoviePagingAdapter.MovieViewHolder>(DIFF_UTIL){

    companion object {
        val  DIFF_UTIL  =object :DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
               return oldItem.id==newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
               return oldItem==newItem
            }

        }
    }

    inner class MovieViewHolder(val movieViewItemBinding: MovieViewItemBinding):RecyclerView.ViewHolder(movieViewItemBinding.root)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
      holder.movieViewItemBinding.setVariable(BR.movie,getItem(position))
        holder.movieViewItemBinding.root.setOnClickListener {
            clickHandler.handleSmallClick(getItem(position)!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieViewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieViewHolder(binding)
    }
}
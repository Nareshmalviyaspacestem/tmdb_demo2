package com.movies.tmdb_demo2.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.ExperimentalPagingApi
import com.movies.tmdb_demo2.databinding.FragmentMovielistBinding
import com.movies.tmdb_demo2.model.Movie
import com.movies.tmdb_demo2.paging.ClickHandler
import com.movies.tmdb_demo2.paging.MoviePagingAdapter
import com.movies.tmdb_demo2.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class MovieFragment : Fragment(), ClickHandler {

   private lateinit var navController: NavController

    val viewModel: MovieViewModel by viewModels()


   private lateinit var moviePagingAdapter: MoviePagingAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentMovielistBinding.inflate(inflater, container, false)
        moviePagingAdapter = MoviePagingAdapter(this)

        binding.recycler.adapter = moviePagingAdapter

        // viewModel.data.observe(viewLifecycleOwner, {

        //  moviePagingAdapter.submitData(lifecycle, it)
        // moviePagingAdapter.refresh()
        // })

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {

            viewModel.pager.collectLatest {
                moviePagingAdapter.submitData(it)
                moviePagingAdapter.refresh()
            }
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)

    }


    override fun handleSmallClick(item: Movie) {
        val action = MovieFragmentDirections.actionMainFragmentToDetailsFragment(item)
        navController.navigate(action)
    }

}
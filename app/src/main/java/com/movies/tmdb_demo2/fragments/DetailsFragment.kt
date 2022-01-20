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
import androidx.navigation.fragment.navArgs
import com.movies.tmdb_demo2.databinding.FragmentDetailsBinding
import com.movies.tmdb_demo2.utils.Constants.Companion.apiQuery
import com.movies.tmdb_demo2.viewmodel.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

   private lateinit var navController: NavController

   private val args by navArgs<DetailsFragmentArgs>()

  private  val movieDetailViewModel: MovieDetailViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDetailsBinding.inflate(inflater, container, false)

        lifecycleScope.launchWhenCreated {
            movieDetailViewModel.getMovieDetail(args.movieModel?.id.toString(), apiQuery)
        }
        movieDetailViewModel.getMovieDetailData().observe(viewLifecycleOwner, {
            binding.movieDetail = it
        })

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
    }

}
package com.twins.testapp.ui.movieDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.twins.testapp.databinding.FragmentMovieDetailsBinding
import com.twins.testapp.model.AppResponse
import com.twins.testapp.model.MovieDetails
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

const val ARG_ID = "arg_id"

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels()
    private var id: Int? = null
    private var supportActionBar: ActionBar? = null

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ARG_ID)
            Timber.i("id $id")
        }
        supportActionBar = (activity as AppCompatActivity).supportActionBar
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id?.let { movieDetailsViewModel.getDetails(it) }
        movieDetailsViewModel.movieDetailsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is AppResponse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val movieDetails: MovieDetails = it.data
                    binding.titleView.text = movieDetails.title
                    supportActionBar?.title = movieDetails.title
                    binding.overviewView.text = movieDetails.overview
                    binding.dateView.text = movieDetails.release_date
                }
                is AppResponse.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(view.context, it.errorResponse, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
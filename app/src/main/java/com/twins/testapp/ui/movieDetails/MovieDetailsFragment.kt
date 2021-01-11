package com.twins.testapp.ui.movieDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.twins.testapp.R
import com.twins.testapp.model.AppResponse
import com.twins.testapp.model.MovieDetails
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_details.*
import timber.log.Timber

const val ARG_ID = "arg_id"

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels()
    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ARG_ID)
            Timber.i("id $id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id?.let { movieDetailsViewModel.getDetails(it) }
        movieDetailsViewModel.movieDetailsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is AppResponse.Success -> {
                    progressBar.visibility = View.GONE
                    val movieDetails: MovieDetails = it.data
                    title_view.text = movieDetails.title
                    overview_view.text = movieDetails.overview
                    date_view.text = movieDetails.release_date
                }
                is AppResponse.Error -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(view.context, it.errorResponse, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
package com.twins.testapp.ui.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.twins.testapp.R
import com.twins.testapp.ui.movieDetails.ARG_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private lateinit var movieAdapter: MovieAdapter
    private val movieListViewModel: MovieListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieAdapter = MovieAdapter() {
            val bundle = Bundle().apply {
                putInt(ARG_ID, it.tag as Int)
            }
            Timber.i("id ${it.tag}")
            findNavController().navigate(R.id.action_MovieListFragment_to_movieDetailsFragment, bundle)
        }
        initSwipeToRefresh()
        movieListView.apply {
            layoutManager = LinearLayoutManager(view.context)
            setHasFixedSize(true)
            adapter = movieAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            movieListViewModel.movies.collectLatest {
                movieAdapter.submitData(it)
            }
        }

        movieAdapter.addLoadStateListener { loadState ->
            swipe_refresh.isRefreshing = loadState.refresh is LoadState.Loading
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            title = resources.getString(R.string.app_name)
        }
    }

    private fun initSwipeToRefresh() {
        swipe_refresh.setOnRefreshListener { movieAdapter.refresh() }
    }
}
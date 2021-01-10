package com.twins.testapp.ui.movieList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.twins.testapp.R
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
        Timber.i("onViewCreated")
        movieAdapter = MovieAdapter()
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

//        view.findViewById<Button>(R.id.button_first).setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    private fun initSwipeToRefresh() {
        swipe_refresh.setOnRefreshListener { movieAdapter.refresh() }
    }
}
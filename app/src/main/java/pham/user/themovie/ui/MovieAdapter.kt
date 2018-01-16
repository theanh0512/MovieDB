package pham.user.themovie.ui

import android.databinding.DataBindingUtil
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import pham.user.themovie.R
import pham.user.themovie.common.DataBoundListAdapter
import pham.user.themovie.databinding.ListItemMovieBinding
import pham.user.themovie.vo.Movie

/**
 * Created by Pham on 11/8/17.
 */

open class MovieAdapter(private val onClickCallback: OnClickCallback?, private val callbacks: LoaderCallbacks?) : DataBoundListAdapter<Movie,
        ListItemMovieBinding>() {
    private var isLoading: Boolean = false
    private var pastVisibleItems: Int = 0

    private fun isLoading(): Boolean {
        return isLoading
    }

    fun setLoadingOffset(loadingOffset: Int) {
        this.loadingOffset = loadingOffset
    }

    private var loadingOffset = 0

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            loadNextItemsIfNeeded(recyclerView)
        }
    }

    private fun loadNextItemsIfNeeded(recyclerView: RecyclerView?) {
        if (!isLoading) {
            val manager = recyclerView?.layoutManager as GridLayoutManager?
            val visibleItemCount = manager?.childCount
            val totalItemCount = manager?.itemCount
            val firstVisibleItems = manager?.findFirstVisibleItemPosition()
            if (firstVisibleItems != null) {
                pastVisibleItems = firstVisibleItems
            }

            if (visibleItemCount != null && totalItemCount != null && visibleItemCount + pastVisibleItems >= totalItemCount + loadingOffset && !isLoading) {
                recyclerView?.post { loadNextItems() }
            }
        }
    }

    public fun loadNextItems() {
        if (!isLoading && callbacks != null && callbacks.canLoadNextItems()) {
            isLoading = true
            onLoadingStateChanged()
            callbacks.loadNextItems()
        }
    }

    private fun onLoadingStateChanged() {
        // No-default-op
    }

    fun onNextItemsLoaded() {
        if (isLoading) {
            isLoading = false
            onLoadingStateChanged()
        }
    }

    override fun createBinding(parent: ViewGroup): ListItemMovieBinding {
        val binding = DataBindingUtil.inflate<ListItemMovieBinding>(LayoutInflater.from(parent.context),
                R.layout.list_item_movie, parent, false)!!
        //Todo: binding.getRoot().setOnClickListener and implement call back interface
        binding.root.setOnClickListener {
            val movie = binding.movie
            onClickCallback?.onClick(movie)
        }
        return binding
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView?.addOnScrollListener(scrollListener)
        loadNextItemsIfNeeded(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
        super.onDetachedFromRecyclerView(recyclerView)
        recyclerView?.removeOnScrollListener(scrollListener)
    }

    override fun bind(binding: ListItemMovieBinding, item: Movie) {
        binding.movie = item
    }

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

    interface OnClickCallback {
        fun onClick(item: Movie)
    }

    interface LoaderCallbacks {
        fun canLoadNextItems(): Boolean

        fun loadNextItems()
    }
}
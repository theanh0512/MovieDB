package pham.user.themovie.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import pham.user.themovie.R
import pham.user.themovie.databinding.FragmentDetailBinding
import pham.user.themovie.di.Injectable
import pham.user.themovie.util.Config
import pham.user.themovie.util.Utils
import pham.user.themovie.vo.Movie
import pham.user.themovie.vo.Video
import javax.inject.Inject

class DetailFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: MainViewModel
    private lateinit var viewDataBinding: FragmentDetailBinding
    lateinit var movieAdapter: MovieAdapter
    val movieList = ArrayList<Movie>()
    val trailersList = ArrayList<Video>()
    lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater!!, R.layout.fragment_detail, container, false)!!
        movie = arguments.getParcelable(ARGUMENT_MOVIE)
        viewDataBinding.movie = movie
        Utils.setUpActionBar(activity, viewDataBinding.toolbar, movie.title ?: R.string.app_name, true)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.apply {
            page = 1
            totalPage = null
            clickedMovieId = movie.id
            startLoadRelatedMovies.value = true
            startLoadVideos.value = true
        }

        movieAdapter = MovieAdapter(object : MovieAdapter.OnClickCallback {
            override fun onClick(item: Movie) {
                val detailFragment = DetailFragment.newInstance(item)
                fragmentManager.beginTransaction()
                        .replace(R.id.container, detailFragment)
                        .addToBackStack(null)
                        .commitAllowingStateLoss()
            }
        }, object : MovieAdapter.LoaderCallbacks {
            override fun canLoadNextItems(): Boolean {
                return viewModel.canLoadMore()
            }

            override fun loadNextItems() {
                viewModel.loadRelatedMore()
            }

        })
        movieAdapter.setLoadingOffset(3)
        viewDataBinding.relatedMoviesGridView.apply {
            isNestedScrollingEnabled = false
            adapter = movieAdapter
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 3)
        }
        viewModel.relatedMovieList.observe(this@DetailFragment, Observer { movieResponse ->
            if (movieResponse?.results != null) {
                movieAdapter.onNextItemsLoaded()
                viewModel.page = movieResponse.page
                viewModel.totalPage = movieResponse.totalPages
                movieList.addAll(movieResponse.results)
                movieAdapter.replace2(movieList)
            }
        })
        viewDataBinding.scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (v != null && scrollY == (v.getChildAt(0).measuredHeight - v.measuredHeight)) {
                movieAdapter.loadNextItems()
            }
        })

        viewModel.videoList.observe(this@DetailFragment, Observer {
            if (it != null) {
                trailersList.addAll(it.results)
                addYoutubeFragment()
            }
        })
    }

    private fun addYoutubeFragment() {
        val youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance()
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit()

        youTubePlayerFragment.initialize(Config.YOUTUBE_API_KEY, object : YouTubePlayer.OnInitializedListener {

            override fun onInitializationSuccess(arg0: YouTubePlayer.Provider, youTubePlayer: YouTubePlayer, b: Boolean) {
                if (!b) {
                    if (trailersList.size != 0) {
                        val video = trailersList[0].key
                        youTubePlayer.cueVideo(video)
                    }
                }
            }

            override fun onInitializationFailure(arg0: YouTubePlayer.Provider, arg1: YouTubeInitializationResult) {
                // TODO Auto-generated method stub

            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                activity.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val ARGUMENT_MOVIE = "MOVIE"
        fun newInstance(movie: Movie) = DetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARGUMENT_MOVIE, movie)
            }
        }
    }
}
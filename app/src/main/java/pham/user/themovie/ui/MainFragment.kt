package pham.user.themovie.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pham.user.themovie.R
import pham.user.themovie.databinding.FragmentMainBinding
import pham.user.themovie.di.Injectable
import pham.user.themovie.util.Utils
import pham.user.themovie.vo.Movie
import javax.inject.Inject

class MainFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: MainViewModel
    private lateinit var viewDataBinding: FragmentMainBinding
    lateinit var movieAdapter: MovieAdapter
    val movieList = ArrayList<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater!!, R.layout.fragment_main, container, false)!!
        Utils.setUpActionBar(activity, viewDataBinding.toolbar, R.string.app_name, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.startLoad.value = true
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
                viewModel.loadMore()
            }

        })
        viewDataBinding.gridView.apply {
            adapter = movieAdapter
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 3)
        }
        viewModel.movieList.observe(this@MainFragment, Observer { movieResponse ->
            if (movieResponse?.results != null) {
                movieAdapter.onNextItemsLoaded()
                viewModel.page = movieResponse.page
                viewModel.totalPage = movieResponse.totalPages
                movieList.addAll(movieResponse.results)
                movieAdapter.replace2(movieList)
            }
        })
    }
}
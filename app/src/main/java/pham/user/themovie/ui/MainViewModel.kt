package pham.user.themovie.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import pham.user.themovie.repository.MovieRepository
import pham.user.themovie.util.AbsentLiveData
import pham.user.themovie.vo.MovieResponse
import pham.user.themovie.vo.VideoResponse
import javax.inject.Inject

/**
 * Created by Pham on 4/9/17.
 */
class MainViewModel @Inject constructor(val giftRepository: MovieRepository) : ViewModel() {
    var page = DEFAULT_PAGE
    var totalPage: Int? = null
    val movieList: LiveData<MovieResponse>
    val relatedMovieList: LiveData<MovieResponse>
    val videoList: LiveData<VideoResponse>
    var clickedMovieId: Int? = null
    val startLoad = MutableLiveData<Boolean>()
    val startLoadRelatedMovies = MutableLiveData<Boolean>()
    val startLoadVideos = MutableLiveData<Boolean>()

    init {
        movieList = Transformations.switchMap(startLoad) { startLoad ->
            if (startLoad == null) return@switchMap AbsentLiveData.create<MovieResponse>()
            else return@switchMap giftRepository.getMovieByPage(page)
        }
        relatedMovieList = Transformations.switchMap(startLoadRelatedMovies) { startLoad ->
            when {
                startLoad == null -> return@switchMap AbsentLiveData.create<MovieResponse>()
                clickedMovieId == null -> return@switchMap AbsentLiveData.create<MovieResponse>()
                else -> return@switchMap giftRepository.getRelatedMovieByPage(clickedMovieId!!, page)
            }
        }
        videoList = Transformations.switchMap(startLoadVideos) { startLoad ->
            when {
                startLoad == null -> return@switchMap AbsentLiveData.create<VideoResponse>()
                clickedMovieId == null -> return@switchMap AbsentLiveData.create<VideoResponse>()
                else -> return@switchMap giftRepository.getVideos(clickedMovieId!!)
            }
        }
    }

    fun canLoadMore(): Boolean {
        if (totalPage != null && page < totalPage!!)
            return true
        return false
    }

    fun loadRelatedMore() {
        page++
        startLoadRelatedMovies.value = true
    }

    fun loadMore() {
        page++
        startLoad.value = true
    }

    companion object {
        private const val DEFAULT_PAGE = 1
    }
}

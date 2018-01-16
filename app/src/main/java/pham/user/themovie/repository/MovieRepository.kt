package pham.user.themovie.repository

import android.arch.lifecycle.LiveData
import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import pham.user.themovie.AppExecutors
import pham.user.themovie.api.TheMovieService
import pham.user.themovie.util.Config
import pham.user.themovie.util.ext.toLiveData
import pham.user.themovie.vo.MovieResponse
import pham.user.themovie.vo.VideoResponse
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by Pham on 4/9/17.
 */
@Singleton
class MovieRepository @Inject constructor(val context: Context,
                                          val theMovieService: TheMovieService, val appExecutors: AppExecutors) {

    fun getMovieByPage(page: Int): LiveData<MovieResponse> {
        return theMovieService.getMovies(Config.THE_MOVIE_DB_API_KEY, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toLiveData()
    }

    fun getRelatedMovieByPage(movieId: Int, page: Int): LiveData<MovieResponse> {
        return theMovieService.getRelatedMovies(movieId, Config.THE_MOVIE_DB_API_KEY, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toLiveData()
    }

    fun getVideos(movieId: Int): LiveData<VideoResponse> {
        return theMovieService.getVideos(movieId, Config.THE_MOVIE_DB_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toLiveData()
    }

    companion object {
        private val TAG = "TheMovie"
    }
}
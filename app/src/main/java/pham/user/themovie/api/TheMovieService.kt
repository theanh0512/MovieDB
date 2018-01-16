package pham.user.themovie.api

import io.reactivex.Flowable
import pham.user.themovie.vo.MovieResponse
import pham.user.themovie.vo.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Pham on 14/1/2018.
 */

interface TheMovieService {
    @GET("now_playing")
    fun getMovies(@Query("api_key") apiKey: String, @Query("page") page: Int): Flowable<MovieResponse>

    @GET("{movieId}/similar")
    fun getRelatedMovies(@Path("movieId") movieId: Int, @Query("api_key") apiKey: String, @Query("page") page: Int): Flowable<MovieResponse>

    @GET("{movieId}/videos")
    fun getVideos(@Path("movieId") movieId: Int, @Query("api_key") apiKey: String): Flowable<VideoResponse>
}

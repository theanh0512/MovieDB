package pham.user.themovie.vo

import com.google.gson.annotations.SerializedName

/**
 * Created by Pham on 14/1/2018.
 */
data class MovieResponse(val results: List<Movie>?, val page: Int, @SerializedName("total_pages") val totalPages: Int)

data class VideoResponse(val results: List<Video>)
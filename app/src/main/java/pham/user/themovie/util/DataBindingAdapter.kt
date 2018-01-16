package pham.user.themovie.util

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import pham.user.themovie.R

/**
 * Created by Pham on 14/1/2018.
 */
@BindingAdapter("imageUrlRectangle")
fun setImageUrlRectangle(view: ImageView, url: String?) {
    if (url == null) {
        view.setImageDrawable(null)
    } else {
        val fullUrl = (Config.IMG_BASE_URL
                + url + Config.PREFIX_API_KEY + Config.THE_MOVIE_DB_API_KEY)
        Glide.with(view.context).load(fullUrl).apply(RequestOptions
                .noTransformation()
                .placeholder(R.drawable.ic_place_holder)
                .fallback(R.drawable.ic_place_holder)
                .error(R.drawable.ic_place_holder)).into(view)
    }
}
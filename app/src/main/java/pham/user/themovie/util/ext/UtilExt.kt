package pham.user.themovie.util.ext

import android.content.Context
import android.support.v4.content.ContextCompat

/**
 * Created by Pham on 19/9/17.
 */
fun Context.getColorFromResId(resId: Int) = ContextCompat.getColor(this, resId)

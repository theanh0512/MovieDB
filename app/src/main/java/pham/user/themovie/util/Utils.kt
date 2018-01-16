package pham.user.themovie.util

import android.app.Activity
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import pham.user.themovie.R
import pham.user.themovie.util.ext.getColorFromResId

/**
 * Created by Pham on 14/1/2018.
 */
object Utils {
    fun setUpActionBar(activity: Activity, toolbar: Toolbar, title: Any, showBackButton: Boolean, color: Int = R.color.colorPrimaryLight) {
        Utils.setWindowsWithBackgroundColor(activity, ContextCompat.getColor(activity, color))
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        val actionBar = activity.supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true)
            actionBar.setDisplayShowTitleEnabled(false)
            val inflaterActionbar = LayoutInflater.from(activity)
            val v = inflaterActionbar.inflate(R.layout.action_bar_layout, null)
            val params = ActionBar.LayoutParams(
                    ActionBar.LayoutParams.WRAP_CONTENT,
                    ActionBar.LayoutParams.MATCH_PARENT,
                    Gravity.CENTER)
            actionBar.setCustomView(v, params)
            if (title is String)
                (v.findViewById<View>(R.id.text_view_action_bar) as TextView).text = title
            else (v.findViewById<View>(R.id.text_view_action_bar) as TextView).setText(title as Int)
            actionBar.setDisplayHomeAsUpEnabled(showBackButton)
        }
    }

    fun setWindowsWithBackgroundColor(activity: Activity, color: Int) {
        val window = activity.window
        window.decorView.setBackgroundColor(color)
        window.statusBarColor = activity.getColorFromResId(R.color.colorPrimaryDark)
    }
}
package com.nknalanda.diagnalsearch.viewmodel

import android.content.Context
import com.nknalanda.diagnalsearch.R
import com.nknalanda.diagnalsearch.datamodel.MovieModel

class MovieItemViewModel(
    private var context: Context,
    private var model: MovieModel
) {
    //To get movie name
    fun getName(): String {
        return model.name!!
    }

    //To get movie poster
    fun getPoster(): Int {
        return when(model.thumbnail) {
            "poster1.jpg" -> R.drawable.poster1
            "poster2.jpg" -> R.drawable.poster2
            "poster3.jpg" -> R.drawable.poster3
            "poster4.jpg" -> R.drawable.poster4
            "poster5.jpg" -> R.drawable.poster5
            "poster6.jpg" -> R.drawable.poster6
            "poster7.jpg" -> R.drawable.poster7
            "poster8.jpg" -> R.drawable.poster8
            "poster9.jpg" -> R.drawable.poster9
            else -> {
                R.drawable.placeholder_for_missing_posters
            }
        }
    }

}
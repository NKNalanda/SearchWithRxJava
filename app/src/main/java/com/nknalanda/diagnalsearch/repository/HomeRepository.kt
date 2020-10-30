package com.nknalanda.diagnalsearch.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nknalanda.diagnalsearch.datamodel.MovieModel
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Type

class HomeRepository(private var context: Context) {
    private val gson = Gson()

    //To get movie details from movie json objects
    fun movieJson(
        movieJson: JSONObject?,
        movieLiveData: MutableLiveData<ArrayList<MovieModel>>
    ) {
        try {
            val contentJsonArr: JSONArray = movieJson!!
                .getJSONObject("page")
                .getJSONObject("content-items")
                .getJSONArray("content")

            val type: Type = object : TypeToken<ArrayList<MovieModel>>() {}.type
            movieLiveData.apply {
                movieLiveData.value = gson.fromJson(contentJsonArr.toString(), type)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, e.toString())
        }

    }

    companion object {
        private const val TAG = "HomeRepository"
    }
}
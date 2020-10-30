package com.nknalanda.diagnalsearch.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nknalanda.diagnalsearch.datamodel.MovieModel
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit


class SearchRepository(
    private var context: Context
) {
    private val gson = Gson()

    //To search movie by name
    fun searchMovie(
        searchView: SearchView,
        movieLiveData: MutableLiveData<ArrayList<MovieModel>>,
        jsonList: ArrayList<JSONObject?>
    ) {
        Observable
            .create(ObservableOnSubscribe<String> { subscriber ->
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextChange(newText: String?): Boolean {
                        subscriber.onNext(newText!!)
                        return true
                    }

                    override fun onQueryTextSubmit(query: String?): Boolean {
                        subscriber.onNext(query!!)
                        return true
                    }
                })
            })
            .debounce(250, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .filter { text -> !text.isEmpty() && text.length >= 3 }
            .subscribe { text ->
                Log.d(TAG, "subscriber: $text")
                searchInAll(text, jsonList, movieLiveData)
            }
    }

    //To search movie in all json files
    private fun searchInAll(
        str: String,
        jsonList: ArrayList<JSONObject?>,
        movieLiveData: MutableLiveData<ArrayList<MovieModel>>
    ) {
        try {
            val allMovieList: ArrayList<MovieModel> = ArrayList()
            val filteredMovieList: ArrayList<MovieModel> = ArrayList()
            for (json in jsonList) {
                val contentJsonArr: JSONArray = json!!
                    .getJSONObject("page")
                    .getJSONObject("content-items")
                    .getJSONArray("content")

                val type: Type = object : TypeToken<ArrayList<MovieModel>>() {}.type

                allMovieList.addAll(gson.fromJson(contentJsonArr.toString(), type))
                for (movie in allMovieList) {
                    if (movie.name?.trim()?.contains(str, true)!!) {
                        filteredMovieList.add(movie)
                    }
                }
            }

            movieLiveData.postValue(filteredMovieList)
            Toast.makeText(context, movieLiveData?.value?.size.toString(),
                Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, e.toString())
        }
    }

    companion object{
        private const val TAG = "SearchRepository"
    }
}
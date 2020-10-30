package com.nknalanda.diagnalsearch.viewmodel

import android.app.Application
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.nknalanda.diagnalsearch.datamodel.MovieModel
import com.nknalanda.diagnalsearch.repository.SearchRepository
import com.nknalanda.diagnalsearch.utils.JsonUtil
import org.json.JSONObject

class SearchViewModel(
    private var appln: Application
): AndroidViewModel(appln) {
    private var repository: SearchRepository = SearchRepository(appln)
    var movieLiveData = MutableLiveData<ArrayList<MovieModel>>()

    //To search movie across json files
    fun searchMovie(searchView: SearchView) {
        val jsonList: ArrayList<JSONObject?> = ArrayList()
        jsonList.add(JsonUtil.getJsonFromAssets(appln, "contentslisting_page1.json"))
        jsonList.add(JsonUtil.getJsonFromAssets(appln, "contentslisting_page2.json"))
        jsonList.add(JsonUtil.getJsonFromAssets(appln, "contentslisting_page3.json"))

        repository.searchMovie(searchView, movieLiveData, jsonList)
    }
}
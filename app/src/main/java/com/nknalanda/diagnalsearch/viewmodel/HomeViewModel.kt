package com.nknalanda.diagnalsearch.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.nknalanda.diagnalsearch.datamodel.MovieModel
import com.nknalanda.diagnalsearch.repository.HomeRepository
import com.nknalanda.diagnalsearch.utils.JsonUtil
import org.json.JSONObject

class HomeViewModel(private var appln: Application): AndroidViewModel(appln) {
    private var repository: HomeRepository = HomeRepository(appln)
    var movieLiveData = MutableLiveData<ArrayList<MovieModel>>()

    fun parseMovieJson(currentPage: Int) {
        repository.movieJson(getJsonVal(currentPage), movieLiveData)
    }

    //To get json based on page number
    private fun getJsonVal(currentPage: Int): JSONObject? {
        return when(currentPage) {
            1 -> JsonUtil.getJsonFromAssets(appln, "contentslisting_page1.json")
            2 -> JsonUtil.getJsonFromAssets(appln, "contentslisting_page2.json")
            3 -> JsonUtil.getJsonFromAssets(appln, "contentslisting_page3.json")
            else -> {
                null
            }
        }
    }

}
package com.nknalanda.diagnalsearch.utils

import android.content.Context
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

object JsonUtil{

    //To get json object from json file
    fun getJsonFromAssets(context: Context, fileName: String?): JSONObject? {
        val jsonString: String
        jsonString = try {
            val inputStream: InputStream = context.assets.open(fileName!!)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return JSONObject(jsonString)
    }
}
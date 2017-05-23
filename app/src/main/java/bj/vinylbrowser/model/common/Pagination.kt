package bj.vinylbrowser.model.common

import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Pagination(@SerializedName("per_page") val perPage: Int, val pages: Int, val Page: Int,
                      val items: Int, val urls: Urls)
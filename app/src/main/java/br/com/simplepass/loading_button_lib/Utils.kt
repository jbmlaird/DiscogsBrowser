package br.com.simplepass.loading_button_lib

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v4.content.ContextCompat


/**
 * Created by hinovamobile on 27/12/16.
 */
class Utils {

    companion object {
        fun getColorWrapper(context: Context, id: Int): Int {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return context.getColor(id)
            } else {
                return ContextCompat.getColor(context, id)
            }
        }
    }

    fun getDrawable(context: Context, id: Int): Drawable {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(id)
        } else {
            return ContextCompat.getDrawable(context, id)
        }
    }

}
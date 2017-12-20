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
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                context.getColor(id)
            } else {
                ContextCompat.getColor(context, id)
            }
        }
    }

    fun getDrawable(context: Context, id: Int): Drawable? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.getDrawable(id)
        } else {
            ContextCompat.getDrawable(context, id)
        }
    }

}
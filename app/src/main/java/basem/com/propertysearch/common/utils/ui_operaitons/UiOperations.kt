package basem.com.propertysearch.common.utils.ui_operaitons

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import basem.com.propertysearch.R

class UiOperations
{
     fun renderToast(context:Context?,id: Int) {
        Toast.makeText(context, context!!.resources.getString(id), Toast.LENGTH_SHORT).show()
    }


     fun changeBtnsColor(btn1: Button, btn2: Button,context: Context?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            btn1.setBackgroundColor(context!!.resources.getColor(R.color.colorPrimary, null))
            btn1.tag = R.color.colorPrimary
            btn2.setBackgroundColor(context.resources.getColor(R.color.colorBackground, null))
            btn2.tag = R.color.colorBackground

        } else {
            btn1.setBackgroundColor(context!!.resources.getColor(R.color.colorPrimary))
            btn1.tag = R.color.colorPrimary
            btn2.setBackgroundColor(context.resources.getColor(R.color.colorBackground))
            btn2.tag = R.color.colorBackground

        }
    }

     fun renderLoadingState(pBar: ProgressBar) {
        pBar.visibility = View.VISIBLE
    }
     fun hideLoadingState(pBar:ProgressBar) {
        pBar.visibility = View.GONE
    }
     fun logError(throwable: Throwable?) {
        Log.e("ERROR", throwable.toString(), throwable)
    }

}
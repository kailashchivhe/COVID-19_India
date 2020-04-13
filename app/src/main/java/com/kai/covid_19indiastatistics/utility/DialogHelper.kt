package com.kai.covid_19indiastatistics.utility

import android.content.Context
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog

class DialogHelper {
    companion object{
        lateinit var alertDialog: LottieAlertDialog
        fun showLoadingDialog( context: Context )
        {
            alertDialog = LottieAlertDialog.Builder(context, DialogTypes.TYPE_LOADING).setTitle("Loading Data").build()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

        fun dismissDialog()
        {
            alertDialog.dismiss()
        }
    }
}
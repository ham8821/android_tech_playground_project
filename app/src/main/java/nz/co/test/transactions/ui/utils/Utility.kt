package nz.co.test.transactions.ui.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

internal object Utility {
    fun hidesSoftKeyboard(activity: Activity) {
        if (activity.window != null) {
            val inputManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
        }
    }

    fun makeToast(context: Context?, text: String?) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun getFormattedCurrentDate(): String {
        val date = Calendar.getInstance().time
        val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        return df.format(date)
    }

}

public infix fun String?.hasQuery(queryString: String): Boolean {
    return this?.contains(queryString, ignoreCase = true) ?: false
}
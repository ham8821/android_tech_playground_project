package nz.co.test.transactions.ui

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast




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
}
package nz.co.test.transactions.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import nz.co.test.transactions.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    companion object{
        private const val TAG_FRAGMENT_TRANSACTIONS = "_transactoin_fragment"
    }
}
package nz.co.test.transactions.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import nz.co.test.transactions.R
import nz.co.test.transactions.ui.fragments.TransactionListFragment


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container_view, TransactionListFragment(), TAG_FRAGMENT_TRANSACTIONS)
            }

        }
    }

    companion object{
        private const val TAG_FRAGMENT_TRANSACTIONS = "_transactoin_fragment"
    }
}
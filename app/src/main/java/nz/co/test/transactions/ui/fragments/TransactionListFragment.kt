package nz.co.test.transactions.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import nz.co.test.transactions.R
import nz.co.test.transactions.TransactionListAdapter
import nz.co.test.transactions.TransactionListViewModel
import nz.co.test.transactions.databinding.FragmentTransactonListBinding
import nz.co.test.transactions.infrastructure.model.Transaction
import javax.inject.Inject

class TransactionListFragment : DaggerFragment(R.layout.fragment_transacton_list) {

    private var _binding: FragmentTransactonListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: TransactionListViewModel by viewModels {
        viewModelFactory
    }

    lateinit var adapter: TransactionListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        AndroidSupportInjection.inject(this)

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransactonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseObserver()
    }

    private fun initialiseObserver() {
        viewModel.sortedTransactionList.observe(viewLifecycleOwner, Observer { transactions ->
            transactions?.let { showTransactions(transactions) }
        })
    }

    private fun showTransactions(transactions: List<Transaction>) {
        adapter.updateItems(transactions)
    }

    override fun onResume() {
        super.onResume()
        viewModel.retrieveTransactions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package nz.co.test.transactions.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import nz.co.test.transactions.R
import nz.co.test.transactions.Status
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
        binding.transactionList.layoutManager = LinearLayoutManager(context)
        adapter = TransactionListAdapter()
        binding.transactionList.addItemDecoration(
            DividerItemDecoration(
                binding.transactionList.context,
                (binding.transactionList.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.transactionList.adapter = adapter
    }

    private fun initialiseObserver() {
        viewModel.retrieveTransactions().observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.transactionList.visibility = View.VISIBLE
                        binding.progressCircular.visibility = View.GONE
                        resource.data?.let { users -> retrieveList(users) }
                        binding.noTransactionFoundText.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        binding.transactionList.visibility = View.VISIBLE
                        binding.progressCircular.visibility = View.GONE
                        binding.noTransactionFoundText.text = it.message
                    }
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                        binding.transactionList.visibility = View.GONE
                        binding.noTransactionFoundText.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(users: List<Transaction>) {
        adapter.updateItems(users)
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
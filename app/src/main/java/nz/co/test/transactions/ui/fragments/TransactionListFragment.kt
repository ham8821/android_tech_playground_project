package nz.co.test.transactions.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import nz.co.test.transactions.*
import nz.co.test.transactions.databinding.FragmentTransactonListBinding
import nz.co.test.transactions.infrastructure.model.Transaction
import nz.co.test.transactions.ui.bundles.TransactionItemBundle
import javax.inject.Inject


class TransactionListFragment : DaggerFragment(R.layout.fragment_transacton_list),
    OnItemClickedListener<Transaction> {

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
        adapter.setItemClickedListener(this)
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

    override fun onItemClicked(item: Transaction) {
        val transactionBundle = TransactionItemBundle(item.id, item.transactionDate, item.summary, item.debit, item.credit)
        val directions = TransactionListFragmentDirections.actionFirstFragmentToSecondFragment(transactionBundle)
        findNavController().navigate(directions)
    }

}
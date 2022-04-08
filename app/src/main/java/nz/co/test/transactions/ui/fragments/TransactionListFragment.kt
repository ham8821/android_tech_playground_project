package nz.co.test.transactions.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import nz.co.test.transactions.*
import nz.co.test.transactions.databinding.FragmentTransactonListBinding
import nz.co.test.transactions.infrastructure.model.Transaction
import nz.co.test.transactions.ui.Utility
import nz.co.test.transactions.ui.activities.MainActivity
import nz.co.test.transactions.ui.bundles.TransactionItemBundle
import org.geeksforgeeks.gfgmodalsheet.BottomSheetDialog
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt


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
    var savedQuery: String? = ""
    var newSearchResult: Array<Transaction>? = null
    var currentTransactions: List<Transaction> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)
        val item = menu.findItem(R.id.search)
        val sv = SearchView(
            (activity as MainActivity?)!!.supportActionBar!!.themedContext
        )
        MenuItemCompat.setShowAsAction(
            item,
            MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItemCompat.SHOW_AS_ACTION_IF_ROOM
        )
        MenuItemCompat.setActionView(item, sv)
        sv.setIconifiedByDefault(false)
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Utility.hidesSoftKeyboard(activity as MainActivity)
                if (activity!!.window != null) {
                    val inputManager =
                        activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.hideSoftInputFromWindow(activity!!.window.decorView.windowToken, 0)}
                if (savedQuery != query) {
                    savedQuery = query
                    newSearchResult = viewModel.searchTransactions(currentTransactions, query)
                    newSearchResult?.toList()?.let {
                        when (it.isNullOrEmpty()) {
                            true -> {
                                showListUI()
                            }
                            false -> {
                                retrieveList(it)
                                showNotFoundUI()
                            }
                        }
                    }
                }
                if (query == "")
                    adapter.updateItems(currentTransactions)
                return true
            }


            override fun onQueryTextChange(newText: String): Boolean {
                if (savedQuery != newText) {
                    savedQuery = newText
                    newSearchResult = viewModel.searchTransactions(currentTransactions, newText)
                    newSearchResult?.toList()?.let {
                        when (it.isNullOrEmpty()) {
                            true -> {
                                showListUI()
                            }
                            false -> {
                                retrieveList(it)
                                showNotFoundUI()
                            }
                        }
                    }
                }
                if (newText == "")
                    adapter.updateItems(currentTransactions)
                return true
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> {
                viewModel.removeAllTransaction()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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
        binding.addTransaction.setOnClickListener {
            val bottomSheet = BottomSheetDialog()
            bottomSheet.show(
                activity?.supportFragmentManager!!,
                "ModalBottomSheet"
            )
//            val type = arrayOf("debit", "credit")
//            val randomType = type.random()
//            val transaction =
//                Transaction(
//                    (0..20).random(),
//                    "2005" + (Math.random() * 10).roundToInt(),
//                    Random().toString(),
//                    randomType,
//                    randomType
//                )
//            viewModel.addTransaction(transaction)
        }
    }

    private fun initialiseObserver() {
        viewModel.isLoading.observe(viewLifecycleOwner, { show ->
            binding.progressCircular.visibility = if (show) View.VISIBLE else View.GONE
        })

        viewModel.showNoTransactonFoundView.observe(viewLifecycleOwner, { show ->
            binding.noTransactionFoundText.visibility = if (show) View.VISIBLE else View.GONE
        })

        viewModel.allTransactions.observe(viewLifecycleOwner, { transactions ->

            when (transactions.isNullOrEmpty()) {
                true -> {
                    showListUI()
                }
                false -> {
                    currentTransactions = transactions
                    retrieveList(transactions)
                    showNotFoundUI()
                }
            }
        })
    }

    private fun showNotFoundUI() {
        binding.noTransactionFoundText.visibility = View.GONE
        binding.transactionList.visibility = View.VISIBLE
        binding.progressCircular.visibility = View.GONE
    }

    private fun showListUI() {
        binding.transactionList.visibility = View.GONE
        binding.noTransactionFoundText.visibility = View.VISIBLE
        binding.progressCircular.visibility = View.GONE
    }

    private fun retrieveList(users: List<Transaction>) {
            adapter.updateItems(users)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(item: Transaction) {
        val transactionBundle = TransactionItemBundle(
            item.id,
            item.transactionDate,
            item.summary,
            item.debit,
            item.credit
        )
        val directions =
            TransactionListFragmentDirections.actionFirstFragmentToSecondFragment(transactionBundle)
        findNavController().navigate(directions)
    }

}
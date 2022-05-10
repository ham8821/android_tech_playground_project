package nz.co.test.transactions.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import nz.co.kiwibank.mobile.ui.utils.ui.NavigationResultsKey
import nz.co.test.transactions.OnItemClickedListener
import nz.co.test.transactions.R
import nz.co.test.transactions.TransactionListAdapter
import nz.co.test.transactions.TaskViewModel
import nz.co.test.transactions.databinding.FragmentTaskListDashboardBinding
import nz.co.test.transactions.infrastructure.model.Task
import nz.co.test.transactions.ui.activities.MainActivity
import nz.co.test.transactions.ui.bundles.TaskItemBundle
import nz.co.test.transactions.ui.utils.Utility
import nz.co.test.transactions.ui.utils.Utility.makeToast
import nz.co.test.transactions.ui.utils.getNavigationResult
import javax.inject.Inject


class TaskDashboardFragment : DaggerFragment(R.layout.fragment_task_list_dashboard),
    OnItemClickedListener<Task> {

    private var _binding: FragmentTaskListDashboardBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: TaskViewModel by viewModels {
        viewModelFactory
    }

    lateinit var adapter: TransactionListAdapter
    var savedQuery: String? = ""
    var newSearchResult: Array<Task>? = null
    var currentTransactions: List<Task> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListDashboardBinding.inflate(inflater, container, false)
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
                    newSearchResult = viewModel.searchTask(currentTransactions, query)
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
                    newSearchResult = viewModel.searchTask(currentTransactions, newText)
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
                viewModel.removeAllTasks()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseObserver()
        setupNavigationResultObserver()
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
        }
    }

    private fun initialiseObserver() {
        viewModel.isLoading.observe(viewLifecycleOwner, { show ->
            binding.progressCircular.visibility = if (show) View.VISIBLE else View.GONE
        })

        viewModel.showNoTransactonFoundView.observe(viewLifecycleOwner, { show ->
            binding.noTransactionFoundText.visibility = if (show) View.VISIBLE else View.GONE
        })

//        viewModel.allTasks.observe(viewLifecycleOwner, { tasks ->
//
//            when (tasks.isNullOrEmpty()) {
//                true -> {
//                    showListUI()
//                }
//                false -> {
//                    currentTransactions = tasks
//                    retrieveList(tasks)
//                    showNotFoundUI()
//                }
//            }
//        })
    }

    private fun setupNavigationResultObserver() {
        this.getNavigationResult<Task>(NavigationResultsKey.TaskResult.TaskUpdateAction)?.observe(viewLifecycleOwner, Observer { task ->
            if (task != null) {
                viewModel.addTask(task)
            } else {
                makeToast(context, "task wasn't added.")
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

    private fun retrieveList(users: List<Task>) {
            adapter.updateItems(users)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(item: Task) {
        val taskItemBundle = TaskItemBundle(
            item.id!!,
            item.date,
            item.title,
            item.description,
        )
        val directions =
            TaskDashboardFragmentDirections.actionFirstFragmentToSecondFragment(taskItemBundle)
        findNavController().navigate(directions)
    }

}
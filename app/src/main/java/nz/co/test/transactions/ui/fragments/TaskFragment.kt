package nz.co.test.transactions.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.android.support.DaggerFragment
import nz.co.test.transactions.R
import nz.co.test.transactions.TaskViewModel
import nz.co.test.transactions.databinding.FragmentTaskBinding
import nz.co.test.transactions.infrastructure.model.Task
import javax.inject.Inject

private var _binding: FragmentTaskBinding? = null
private val binding get() = _binding!!
private lateinit var currentTask: Task

class TaskFragment : DaggerFragment(R.layout.fragment_task) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: TaskViewModel by viewModels {
        viewModelFactory
    }

    val args: TaskFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.action_delete, menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> {
                viewModel.removeTask(currentTask)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialiseObserver()
        with(args.transactionBundle) {
            currentTask = Task(id, date, title, description)
        }
        binding.taskContent.text = args.transactionBundle.description
        binding.taskSummary.text = args.transactionBundle.title
        binding.taskDate.text = args.transactionBundle.date
    }

    private fun initialiseObserver() {
        viewModel.showListScreen.observe(viewLifecycleOwner, { show ->
            findNavController().navigateUp()
        })
    }

}
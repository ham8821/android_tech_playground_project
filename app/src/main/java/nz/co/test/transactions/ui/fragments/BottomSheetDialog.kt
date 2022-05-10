package nz.co.test.transactions.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import nz.co.test.transactions.TaskViewModel
import nz.co.test.transactions.databinding.AddItemLayoutBinding
import nz.co.test.transactions.infrastructure.model.Task
import nz.co.test.transactions.ui.utils.Utility.getFormattedCurrentDate
import nz.co.test.transactions.ui.utils.Utility.makeToast
import javax.inject.Inject

class BottomSheetDialog : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: TaskViewModel by viewModels {
        viewModelFactory
    }

    private var _binding: AddItemLayoutBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddItemLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addButton.setOnClickListener {
            val task = Task(null,
                getFormattedCurrentDate(),
                binding.taskTitle.text.toString(),
                binding.taskDescription.text.toString()
            )
            viewModel.addTask(task)
            makeToast(context, "Task updated.")
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
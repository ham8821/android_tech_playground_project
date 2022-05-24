package nz.co.test.transactions.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import nz.co.test.transactions.databinding.FragmentDashboardBinding
import nz.co.test.transactions.infrastructure.model.PieData
import nz.co.test.transactions.ui.DashboardViewModel

@AndroidEntryPoint
class TaskDashboardFragment() : Fragment() {

    private val viewModel by viewModels<DashboardViewModel>()

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val data = PieData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Setting up LiveData observation relationship **/
        viewModel.taskCount.observe(viewLifecycleOwner, Observer { count ->
            data.add("Active tasks", count.toDouble())
            binding.pieChart.setData(data)
        })

        viewModel.completedTaskCount.observe(viewLifecycleOwner, Observer { count ->
            data.add("Completed tasks", count.toDouble())
            binding.pieChart.setData(data)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
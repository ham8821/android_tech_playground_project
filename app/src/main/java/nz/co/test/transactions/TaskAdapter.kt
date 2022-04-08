package nz.co.test.transactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nz.co.test.transactions.databinding.ListItemTaskBinding
import nz.co.test.transactions.infrastructure.model.Task

class TransactionListAdapter : RecyclerView.Adapter<TransactionListAdapter.BindableViewHolder>() {

    var itemViewModels: List<Task> = emptyList()
    private lateinit var listener: OnItemClickedListener<Task>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder {
        val binding = ListItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BindableViewHolder(binding)
    }

    fun setItemClickedListener(itemClickedListener: OnItemClickedListener<Task>) {
        listener = itemClickedListener
    }

    override fun getItemCount(): Int = itemViewModels.size

    override fun onBindViewHolder(holder: BindableViewHolder, position: Int) {
        holder.bind(itemViewModels[position])
    }

    fun updateItems(items: List<Task>?) {
        itemViewModels = items ?: emptyList()
        notifyDataSetChanged()
    }

    inner class BindableViewHolder(private val binding: ListItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemViewModel: Task) {
            binding.transactionSummary.text = itemViewModel.title
            binding.taskDate.text = itemViewModel.date
            binding.taskId.text = itemViewModel.id.toString()
            binding.taskDescription.text = itemViewModel.description
            binding.root.setOnClickListener {
                listener.onItemClicked(itemViewModel)
            }
        }
    }
}
interface OnItemClickedListener<T> {
    fun onItemClicked(item: T)
}
package nz.co.test.transactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nz.co.test.transactions.databinding.ListItemTransactionBinding
import nz.co.test.transactions.infrastructure.model.Transaction

class TransactionListAdapter : RecyclerView.Adapter<BindableViewHolder>() {

    var itemViewModels: List<Transaction> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder {
        val binding = ListItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BindableViewHolder(binding)
    }

    override fun getItemCount(): Int = itemViewModels.size

    override fun onBindViewHolder(holder: BindableViewHolder, position: Int) {
        holder.bind(itemViewModels[position])
    }

    fun updateItems(items: List<Transaction>?) {
        itemViewModels = items ?: emptyList()
        notifyDataSetChanged()
    }
}

class BindableViewHolder(private val binding: ListItemTransactionBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(itemViewModel: Transaction) {
        binding.transactionSummary.text = itemViewModel.summary
        binding.transactionDate.text = itemViewModel.transactionDate
        binding.transactionDebit.text = itemViewModel.debit
        binding.transactionCredit.text = itemViewModel.credit
    }
}

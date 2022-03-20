package nz.co.test.transactions.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.android.support.DaggerFragment
import nz.co.test.transactions.R
import nz.co.test.transactions.TransactionListViewModel
import nz.co.test.transactions.databinding.FragmentTransactionDetailBinding
import nz.co.test.transactions.infrastructure.model.Transaction
import java.math.BigDecimal
import javax.inject.Inject

private var _binding: FragmentTransactionDetailBinding? = null
private val binding get() = _binding!!
private lateinit var currentTransaction: Transaction
class TransactionDetailFragment: DaggerFragment(R.layout.fragment_transaction_detail) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: TransactionListViewModel by viewModels {
        viewModelFactory
    }

    val args: TransactionDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransactionDetailBinding.inflate(inflater, container, false)
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
                viewModel.removeTransaction(currentTransaction)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseObserver()
        with(args.transactionBundle){
            currentTransaction = Transaction(id, transactionDate, summary, debit, credit)
        }
        binding.transactionId.text = args.transactionBundle.id.toString()
        binding.transactionSummary.text = args.transactionBundle.summary
        binding.transactionCredit.text = args.transactionBundle.credit
        binding.transactionDebit.text = args.transactionBundle.debit
        binding.transactionDate.text = args.transactionBundle.transactionDate
        setGSTDisclaimerText(args.transactionBundle.credit.toFloatOrNull(), args.transactionBundle.debit.toFloatOrNull())
    }

    private fun initialiseObserver() {
        viewModel.showListScreen.observe(viewLifecycleOwner, { show ->
            findNavController().navigateUp()
        })
    }

        private fun setGSTDisclaimerText(credit: Float?, debit:Float?) {
        // calculate gst
        val gst: String
        val gstPer = 0.15f
        gst = when(credit){
            0f -> (debit?.times(gstPer)).toString()
            else -> (credit?.times(gstPer)).toString()
        }
        binding.trasactionGst.text = "approx.gst paid *$gst"
    }
}
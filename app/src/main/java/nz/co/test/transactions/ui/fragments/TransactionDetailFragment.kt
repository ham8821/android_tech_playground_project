package nz.co.test.transactions.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import nz.co.test.transactions.R
import nz.co.test.transactions.databinding.FragmentTransactionDetailBinding

private var _binding: FragmentTransactionDetailBinding? = null
private val binding get() = _binding!!

class TransactionDetailFragment: DaggerFragment(R.layout.fragment_transaction_detail) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransactionDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
}
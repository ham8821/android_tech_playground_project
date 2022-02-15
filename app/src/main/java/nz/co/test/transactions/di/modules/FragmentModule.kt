package nz.co.test.transactions.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import nz.co.test.transactions.ui.fragments.TransactionDetailFragment
import nz.co.test.transactions.ui.fragments.TransactionListFragment

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeTransactionListFragment(): TransactionListFragment

    @ContributesAndroidInjector
    abstract fun contributeTransactionDetailFragment(): TransactionDetailFragment
}

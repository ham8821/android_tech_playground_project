package nz.co.test.transactions.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import nz.co.test.transactions.ui.fragments.TransactionListFragment

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector // <- Do not forget this line.
    abstract fun contributeTransactionListFragment(): TransactionListFragment
}

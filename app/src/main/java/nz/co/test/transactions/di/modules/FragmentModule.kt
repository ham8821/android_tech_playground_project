package nz.co.test.transactions.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import nz.co.test.transactions.ui.fragments.BottomSheetDialog
import nz.co.test.transactions.ui.fragments.TaskFragment
import nz.co.test.transactions.ui.fragments.TaskDashboardFragment

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeTransactionListFragment(): TaskDashboardFragment

    @ContributesAndroidInjector
    abstract fun contributeTransactionDetailFragment(): TaskFragment

    @ContributesAndroidInjector
    abstract fun contributeBottomSheetDialog(): BottomSheetDialog
}

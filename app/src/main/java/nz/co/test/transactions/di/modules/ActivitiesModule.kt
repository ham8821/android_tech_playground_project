package nz.co.test.transactions.di.modules

import android.app.Activity
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import nz.co.test.transactions.di.activities.ActivityClassKey
import nz.co.test.transactions.ui.activities.MainActivity

@Module
class ActivitiesModule {
    @Provides
    @IntoMap
    @ActivityClassKey(MainActivity::class)
    fun providesMainActivity(): Activity = MainActivity()
}
package nz.co.test.transactions.di.activities

import android.app.Activity
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import nz.co.test.transactions.activities.MainActivity

@Module
class ActivitiesModule {

    @Provides
    @IntoMap
    @ActivityClassKey(MainActivity::class)
    fun providesMainActivity(): Activity = MainActivity()
}
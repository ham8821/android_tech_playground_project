package nz.co.test.transactions.di

import dagger.Component
import nz.co.test.transactions.di.activities.ActivitiesModule
import nz.co.test.transactions.di.network.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        ActivitiesModule::class
    ]
)
interface AppComponent {
    fun inject(appComponent: DaggerAppComponentFactory)
}
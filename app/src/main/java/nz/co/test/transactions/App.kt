package nz.co.test.transactions

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import nz.co.test.transactions.di.DaggerAppComponent

class App : DaggerApplication() {
    private val applicationInjector = DaggerAppComponent.builder().application(this).build()
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = applicationInjector
}

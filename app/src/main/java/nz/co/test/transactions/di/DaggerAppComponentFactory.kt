package nz.co.test.transactions.di

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.AppComponentFactory
import androidx.core.app.ComponentActivity
import javax.inject.Inject
import javax.inject.Provider

class DaggerAppComponentFactory : AppComponentFactory() {

    private val component = DaggerAppComponent.create()

    init {
        component.inject(this)
    }

    @Inject
    lateinit var map: Map<Class<out Activity>, @JvmSuppressWildcards Provider<Activity>>
}
package nz.co.test.transactions.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import nz.co.test.transactions.TaskViewModel
import nz.co.test.transactions.ViewModelFactory
import kotlin.reflect.KClass

@InstallIn(SingletonComponent::class)
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TaskViewModel::class)
    abstract fun bindTransactionListViewModel(taskViewModel: TaskViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
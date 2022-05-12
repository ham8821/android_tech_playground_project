package nz.co.test.transactions.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nz.co.test.transactions.App
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

//    @Singleton
//    @Provides
//    fun providesNavigationManager() = NavigationManager()

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context):
            App {
        return app as App
    }
}
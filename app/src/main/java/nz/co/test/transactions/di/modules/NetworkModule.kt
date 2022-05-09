package nz.co.test.transactions.di.modules

import dagger.Module
import dagger.Provides
import nz.co.test.transactions.infrastructure.services.TaskService
import nz.co.test.transactions.infrastructure.services.TransactionsService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    fun providesTransactionService(retrofit: Retrofit): TransactionsService =
        retrofit.create(TransactionsService::class.java)

    @Provides
    fun providesTaskService(retrofit: Retrofit): TaskService =
        retrofit.create(TaskService::class.java)

    @Provides
    @Singleton
    fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }


    @Singleton
    @Provides
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/Josh-Ng/500f2716604dc1e8e2a3c6d31ad01830/raw/4d73acaa7caa1167676445c922835554c5572e82/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesOkhttp(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor { chain: Interceptor.Chain ->
                val newRequest = chain.request().newBuilder()
                    .build()
                chain.proceed(newRequest)
            }.addInterceptor(getLoggingInterceptor())
        return builder.build()
    }
}
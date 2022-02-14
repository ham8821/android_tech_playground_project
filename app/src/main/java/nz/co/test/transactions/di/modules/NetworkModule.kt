package nz.co.test.transactions.di.modules

import dagger.Module
import dagger.Provides
import nz.co.test.transactions.infrastructure.services.TransactionsService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    fun providesTransactionService(retrofit: Retrofit): TransactionsService =
        retrofit.create(TransactionsService::class.java)

    @Singleton
    @Provides
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://60220907ae8f8700177dee68.mockapi.io/api/v1/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
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
            }
        return builder.build()
    }
}
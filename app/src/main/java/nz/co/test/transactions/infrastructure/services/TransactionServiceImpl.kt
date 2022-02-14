package nz.co.test.transactions.infrastructure.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class TransactionServiceImpl {

    fun initTransactionService(): TransactionsService {
        return Retrofit.Builder()
            .baseUrl(Companion.BASE_URL)
            .client(makeOkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(TransactionsService::class.java)
    }

    private fun makeOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .build()
    }

    companion object {
        const val BASE_URL = "https://60220907ae8f8700177dee68.mockapi.io/api/v1"
    }
}

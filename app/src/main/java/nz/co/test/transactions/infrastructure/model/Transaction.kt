package nz.co.test.transactions.infrastructure.model

import com.google.gson.annotations.SerializedName

data class Transaction(
    @SerializedName("id") val id: Int,
    @SerializedName("transactionDate") val transactionDate: String,
    @SerializedName("summary") val summary: String,
    @SerializedName("debit") val debit: String,
    @SerializedName("credit") val credit: String
)
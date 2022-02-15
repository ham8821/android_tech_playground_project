package nz.co.test.transactions.infrastructure.model

data class Transaction(
    val id: Int,
    val transactionDate: String,
    val summary: String,
    val debit: String,
    val credit: String
)
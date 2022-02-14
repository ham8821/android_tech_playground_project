package nz.co.test.transactions.infrastructure.model

import java.math.BigDecimal
import java.time.OffsetDateTime

data class Transaction(
    val id: Int,
    val transactionDate: OffsetDateTime,
    val summary: String,
    val debit: BigDecimal,
    val credit: BigDecimal
)
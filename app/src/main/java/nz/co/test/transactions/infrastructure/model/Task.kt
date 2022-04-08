package nz.co.test.transactions.infrastructure.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "task_table")
data class Task(
    @SerializedName("id") @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int? = null,
    @SerializedName("date") @ColumnInfo(name = "date") val date: String,
    @SerializedName("summary") @ColumnInfo(name = "title") val title: String,
    @SerializedName("debit") @ColumnInfo(name = "description") val description: String
)
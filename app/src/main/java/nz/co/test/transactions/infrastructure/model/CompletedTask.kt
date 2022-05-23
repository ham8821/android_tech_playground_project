package nz.co.test.transactions.infrastructure.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "completed_task_table")
data class CompletedTask(
    @SerializedName("id") @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int? = null,
    @SerializedName("date") @ColumnInfo(name = "date") val date: String,
    @SerializedName("completedDate") @ColumnInfo(name = "completedDate") val completedDate: String,
    @SerializedName("title") @ColumnInfo(name = "title") val title: String,
    @SerializedName("description") @ColumnInfo(name = "description") val description: String
)
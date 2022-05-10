package nz.co.test.transactions.ui.states

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskViewHolderState(
    var taskName: String,
    var taskDescription: String,
    var date: String,
    var taskIdentifier: String
) : Parcelable

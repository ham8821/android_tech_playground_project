package nz.co.test.transactions.ui.bundles

import android.os.Parcel
import android.os.Parcelable

data class TaskItemBundle(
    val id: Int,
    val date: String,
    val title: String,
    val description: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(date)
        parcel.writeString(title)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskItemBundle> {
        override fun createFromParcel(parcel: Parcel): TaskItemBundle {
            return TaskItemBundle(parcel)
        }

        override fun newArray(size: Int): Array<TaskItemBundle?> {
            return arrayOfNulls(size)
        }
    }

}
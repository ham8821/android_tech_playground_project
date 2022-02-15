package nz.co.test.transactions.ui.bundles

import android.os.BaseBundle
import android.os.Parcel
import android.os.Parcelable

data class TransactionItemBundle(
    val id: Int,
    val transactionDate: String,
    val summary: String,
    val debit: String,
    val credit: String): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!) {
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(transactionDate)
        parcel.writeString(summary)
        parcel.writeString(transactionDate)
        parcel.writeString(summary)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TransactionItemBundle> {
        override fun createFromParcel(parcel: Parcel): TransactionItemBundle {
            return TransactionItemBundle(parcel)
        }

        override fun newArray(size: Int): Array<TransactionItemBundle?> {
            return arrayOfNulls(size)
        }
    }

}
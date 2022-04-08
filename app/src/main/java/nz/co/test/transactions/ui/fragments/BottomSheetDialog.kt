package org.geeksforgeeks.gfgmodalsheet

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.Button
import nz.co.test.transactions.R
import android.widget.Toast

class BottomSheetDialog : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(
            R.layout.add_item_layout,
            container, false
        )
        val algo_button = v.findViewById<Button>(R.id.algo_button)
        val course_button = v.findViewById<Button>(R.id.course_button)
        algo_button.setOnClickListener {
            Toast.makeText(
                activity,
                "Algorithm Shared", Toast.LENGTH_SHORT
            )
                .show()
            dismiss()
        }
        course_button.setOnClickListener {
            Toast.makeText(
                activity,
                "Course Shared", Toast.LENGTH_SHORT
            )
                .show()
            dismiss()
        }
        return v
    }
}
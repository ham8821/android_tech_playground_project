package nz.co.test.transactions.ui.compose

import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TestViewHodler(modifier: Modifier){
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Click Here", modifier = Modifier.fillMaxWidth().padding(12.dp))
    }
}
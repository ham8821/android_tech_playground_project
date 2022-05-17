package nz.co.test.transactions.ui

import androidx.annotation.DimenRes
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp

@Composable
@ReadOnlyComposable
fun fontDimensionResource(@DimenRes id: Int) = dimensionResource(id = id).value.sp

@Composable
fun InputField(
    text: String,
    isEnabled: Boolean = false,
    label: String,
    modifier: Modifier
) {
    TextField(value = text, onValueChange = {}, placeholder = { Text(text = text) }, label = {
        Text(
            text = label
        )
    }, enabled = isEnabled, modifier = modifier)
}
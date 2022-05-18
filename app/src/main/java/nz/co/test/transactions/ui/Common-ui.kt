package nz.co.test.transactions.ui

import androidx.annotation.DimenRes
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import nz.co.test.transactions.R

@Composable
@ReadOnlyComposable
fun fontDimensionResource(@DimenRes id: Int) = dimensionResource(id = id).value.sp

@Composable
fun InputField(
    text: String,
    isEnabled: Boolean = false,
    label: String,
    onValueChanged: () -> Unit,
    modifier: Modifier
) {
    TextField(
        value = text,
        onValueChange = { onValueChanged },
        placeholder = { Text(text = text) },
        label = {
            Text(
                text = label
            )
        },
        enabled = isEnabled,
        modifier = modifier
    )
}

@Composable
fun AppIcon(
    resourceId: Int,
    contentDes: String?,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current
) {
    Icon(
        ImageVector.vectorResource(id = resourceId),
        contentDescription = contentDes,
        tint = tint,
        modifier = modifier
    )
}
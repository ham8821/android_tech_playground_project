package nz.co.test.transactions.ui

import androidx.annotation.DimenRes
import androidx.compose.foundation.Image
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
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

@Composable
fun NavigationIcon(
    icon: ImageVector,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    tintColor: Color? = null,
) {
    val imageAlpha = if (isSelected) {
        1f
    } else {
        0.6f
    }

    val iconTintColor = tintColor ?: if (isSelected) {
        MaterialTheme.colors.primary
    } else {
        MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
    }

    Image(
        modifier = modifier,
        imageVector = icon,
        contentDescription = contentDescription,
        contentScale = ContentScale.Inside,
        colorFilter = ColorFilter.tint(iconTintColor),
        alpha = imageAlpha
    )
}
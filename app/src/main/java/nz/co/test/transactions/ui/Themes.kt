package nz.co.test.transactions.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

/* implementation of MaterialTheme (in this case SpaceAppTheme) can be defined.*/
@Composable
fun playgroundTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {

    val colors = if (!useDarkTheme) LightColors else DarkColors
    val shapes = Shapes(
        small = RoundedCornerShape(percent = 50),
        medium = RoundedCornerShape(0f),
        large = CutCornerShape(
            topStart = 16.dp,
            topEnd = 0.dp,
            bottomEnd = 0.dp,
            bottomStart = 16.dp
        )
    )

    MaterialTheme(
        colors = colors,
        shapes = shapes,
        content = content
    )
}
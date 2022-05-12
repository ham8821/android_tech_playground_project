package nz.co.test.transactions.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import nz.co.test.transactions.R

/* implementation of MaterialTheme (in this case SpaceAppTheme) can be defined.*/
private val LightThemeColors = lightColors(
    primary = orange_500,
    primaryVariant = orange_300,
    onPrimary = black2,
    secondary = Color.White,
    secondaryVariant = warm_grey,
    onSecondary = black2,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = white,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = black2,
)

private val DarkThemeColors = darkColors(
    primary = orange_600,
    primaryVariant = Color.White,
    onPrimary = Color.White,
    secondary = black1,
    onSecondary = Color.White,
    error = RedErrorLight,
    background = Color.Black,
    onBackground = Color.White,
    surface = black1,
    onSurface = Color.White,
)

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {

    val colors = if (!useDarkTheme) LightThemeColors else DarkThemeColors
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
        shapes = shapes
    ) {
        content()
    }
}
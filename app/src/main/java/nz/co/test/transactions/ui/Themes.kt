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
import androidx.compose.ui.unit.dp
import com.example.jetnews.ui.theme.AppTypography

/* implementation of MaterialTheme (in this case SpaceAppTheme) can be defined.*/
private val LightThemeColors = lightColors(
    primary = orange_500,
    primaryVariant = orange_300,
    onPrimary = black2,
    secondary = Color.White,
    secondaryVariant = dark_grey,
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
    secondaryVariant = light_grey,
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

    MaterialTheme(
        colors = colors,
        shapes = Shapes,
        typography = AppTypography
    ) {
        content()
    }
}
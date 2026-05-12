package com.deymervilla.testfakestore.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.deymervilla.testfakestore.R
import com.deymervilla.testfakestore.presentation.theme.DarkSurface
import com.deymervilla.testfakestore.presentation.theme.FakeStoreTheme
import com.deymervilla.testfakestore.presentation.theme.tagButton

@Composable
fun TagCompose(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = Black,
    color: Color = DarkSurface,
    onClick: () -> Unit = {},
    icon: Int? = null,
) {
    val cornerShape = RoundedCornerShape(
        dimensionResource(id = R.dimen.dimen_4)
    )
    val paddingValues = PaddingValues(
        horizontal = dimensionResource(id = R.dimen.dimen_8),
        vertical = dimensionResource(id = R.dimen.dimen_6)
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clickable { onClick.invoke() }
            .background(color, cornerShape)
            .padding(paddingValues)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            icon?.let {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.dimen_18))
                        .padding(end = dimensionResource(id = R.dimen.dimen_2)),
                    tint = Black
                )
            }
            Text(
                text = text,
                style = tagButton.copy(
                    textAlign = TextAlign.Center,
                    color = textColor
                )
            )
        }
    }
}

@Preview(
    name = "Tag – With icon",
    showBackground = false
)
@Composable
private fun TagComposeWithIconPreview() {
    FakeStoreTheme {
        TagCompose(
            text = "Test",
            icon = android.R.drawable.ic_menu_info_details
        )
    }
}

@Preview(
    name = "Tag – With icon",
    showBackground = false
)
@Composable
private fun TagComposeWithoutIconPreview() {
    FakeStoreTheme {
        TagCompose(
            text = "Test",
        )
    }
}
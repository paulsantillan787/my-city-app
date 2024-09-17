package com.example.mycity.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycity.data.LocalPlacesDataProvider
import com.example.mycity.model.Category
import com.example.mycity.model.CategoryItem

@Composable
fun CityCategoriesScreen(
    cityCategories: List<CategoryItem>,
    onCategoryPressed: (Category) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        cityCategories.forEach { category ->
            CityCategoryItem(
                image = category.image,
                text = category.name,
                modifier = Modifier.clickable {
                    onCategoryPressed(category.category)
                }
            )
        }
    }
}

@Composable
private fun CityCategoryItem(
    @DrawableRes image: Int,
    @StringRes text: Int,
    modifier: Modifier = Modifier,
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth().height(72.dp)
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            modifier = Modifier.padding(8.dp).clip(CircleShape)
        )
        Text(
            text = stringResource(text),
            fontSize = 32.sp,
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CityCategoriesScreenPreview() {
    CityCategoriesScreen(
        cityCategories = LocalPlacesDataProvider.allCategories,
        onCategoryPressed = {},
        modifier = Modifier.fillMaxSize()
    )
}
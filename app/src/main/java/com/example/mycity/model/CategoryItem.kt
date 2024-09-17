package com.example.mycity.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class CategoryItem(
    @StringRes val name: Int,
    @DrawableRes val image: Int,
    val category: Category
)
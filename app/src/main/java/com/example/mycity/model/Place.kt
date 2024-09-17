package com.example.mycity.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Place (
    val id: Int,
    val category: Category = Category.ALL,
    @StringRes val nameResource: Int = -1,
    @StringRes val descriptionResource: Int = -1,
    @DrawableRes val imageResource: Int = -1
)
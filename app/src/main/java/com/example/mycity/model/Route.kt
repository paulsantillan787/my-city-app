package com.example.mycity.model

import androidx.annotation.StringRes
import com.example.mycity.R

enum class Route(@StringRes val title: Int) {
    CategorySelection(title = R.string.app_name),
    PlaceList(title = R.string.select_place),
    PlaceDetails(title = R.string.place_details)
}
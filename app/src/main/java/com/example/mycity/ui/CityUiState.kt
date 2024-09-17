package com.example.mycity.ui

import com.example.mycity.data.LocalPlacesDataProvider
import com.example.mycity.model.Category
import com.example.mycity.model.CategoryItem
import com.example.mycity.model.Place

data class CityUiState (
    val categories: List<CategoryItem> = emptyList(),
    val placesCategories: Map<Category, List<Place>> = emptyMap(),
    val currentCategory: Category = Category.ALL,
    val currentSelectedPlace: Place = LocalPlacesDataProvider.defaultPlace,
    val isShowingHomePage: Boolean = true,
    val isMultiWindow: Boolean = false
) {
    val currentCategoryPlaces: List<Place> by lazy {
        if (currentCategory == Category.ALL) {
            placesCategories.values.flatten()
        } else {
            placesCategories[currentCategory]!!
        }
    }
}
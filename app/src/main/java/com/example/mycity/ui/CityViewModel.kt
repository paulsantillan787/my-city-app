package com.example.mycity.ui

import androidx.lifecycle.ViewModel
import com.example.mycity.data.LocalPlacesDataProvider
import com.example.mycity.model.Category
import com.example.mycity.model.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CityViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CityUiState())
    val uiState: StateFlow<CityUiState> = _uiState

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        val placesCategories = LocalPlacesDataProvider.allPlaces.groupBy { it.category }
        val categories = LocalPlacesDataProvider.allCategories
        _uiState.value = CityUiState(
            categories = categories,
            placesCategories = placesCategories,
            currentSelectedPlace = placesCategories[Category.RESTAURANTS]?.get(0)
                ?: LocalPlacesDataProvider.defaultPlace
        )
    }

    fun updateDetailsScreenStates(place: Place) {
        _uiState.update {
            it.copy(
                currentSelectedPlace = place,
                isShowingHomePage = false
            )
        }
    }

    fun resetHomeScreenStates() {
        _uiState.update {
            it.copy(
                currentSelectedPlace = if (uiState.value.currentCategory == Category.ALL)
                    it.currentSelectedPlace
                else
                    it.placesCategories[it.currentCategory]?.get(0)
                        ?: LocalPlacesDataProvider.defaultPlace,
                isShowingHomePage = true
            )
        }
    }

    fun updateCurrentCategory(category: Category) {
        _uiState.update {
            it.copy(
                currentCategory = category
            )
        }
    }

    fun changeMultiWindow(isMultiWindow: Boolean) {
        _uiState.update {
            it.copy(
                isMultiWindow = isMultiWindow
            )
        }
    }
}
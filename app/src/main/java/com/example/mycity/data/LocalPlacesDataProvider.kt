package com.example.mycity.data

import com.example.mycity.R
import com.example.mycity.model.Category
import com.example.mycity.model.CategoryItem
import com.example.mycity.model.Place

object LocalPlacesDataProvider {

    val defaultPlace: Place = Place(-1)

    val allPlaces = listOf(
        // Restaurantes
        Place(
            1,
            Category.RESTAURANTS,
            R.string.restaurant1_name,
            R.string.restaurant1_description,
            R.drawable.image
        ),
        Place(
            2,
            Category.RESTAURANTS,
            R.string.restaurant2_name,
            R.string.restaurant2_description,
            R.drawable.image
        ),
        Place(
            3,
            Category.RESTAURANTS,
            R.string.restaurant3_name,
            R.string.restaurant3_description,
            R.drawable.image
        ),
        Place(
            4,
            Category.RESTAURANTS,
            R.string.restaurant4_name,
            R.string.restaurant4_description,
            R.drawable.image
        ),
        Place(
            5,
            Category.RESTAURANTS,
            R.string.restaurant5_name,
            R.string.restaurant5_description,
            R.drawable.image
        ),

        // Parques
        Place(
            6,
            Category.PARKS,
            R.string.park1_name,
            R.string.park1_description,
            R.drawable.image
        ),
        Place(
            7,
            Category.PARKS,
            R.string.park2_name,
            R.string.park2_description,
            R.drawable.image
        ),
        Place(
            8,
            Category.PARKS,
            R.string.park3_name,
            R.string.park3_description,
            R.drawable.image
        ),
        Place(
            9,
            Category.PARKS,
            R.string.park4_name,
            R.string.park4_description,
            R.drawable.image
        ),
        Place(
            10,
            Category.PARKS,
            R.string.park5_name,
            R.string.park5_description,
            R.drawable.image
        ),

        // Museos
        Place(
            11,
            Category.MUSEUMS,
            R.string.museum1_name,
            R.string.museum1_description,
            R.drawable.image
        ),
        Place(
            12,
            Category.MUSEUMS,
            R.string.museum2_name,
            R.string.museum2_description,
            R.drawable.image
        ),
        Place(
            13,
            Category.MUSEUMS,
            R.string.museum3_name,
            R.string.museum3_description,
            R.drawable.image
        ),
        Place(
            14,
            Category.MUSEUMS,
            R.string.museum4_name,
            R.string.museum4_description,
            R.drawable.image
        ),
        Place(
            15,
            Category.MUSEUMS,
            R.string.museum5_name,
            R.string.museum5_description,
            R.drawable.image
        ),

        // Compras
        Place(
            16,
            Category.SHOPPING,
            R.string.shopping1_name,
            R.string.shopping1_description,
            R.drawable.image
        ),
        Place(
            17,
            Category.SHOPPING,
            R.string.shopping2_name,
            R.string.shopping2_description,
            R.drawable.image
        ),
        Place(
            18,
            Category.SHOPPING,
            R.string.shopping3_name,
            R.string.shopping3_description,
            R.drawable.image
        ),
        Place(
            19,
            Category.SHOPPING,
            R.string.shopping4_name,
            R.string.shopping4_description,
            R.drawable.image
        ),
        Place(
            20,
            Category.SHOPPING,
            R.string.shopping5_name,
            R.string.shopping5_description,
            R.drawable.image
        )
    )

    val allCategories = listOf(
        CategoryItem(R.string.restaurant, R.drawable.restaurant_icon, Category.RESTAURANTS),
        CategoryItem(R.string.park, R.drawable.park_icon, Category.PARKS),
        CategoryItem(R.string.museum, R.drawable.museum_icon, Category.MUSEUMS),
        CategoryItem(R.string.shopping, R.drawable.shop_icon, Category.SHOPPING)
    )

    fun get(id: Int): Place? {
        return allPlaces.firstOrNull { it.id == id }
    }
}
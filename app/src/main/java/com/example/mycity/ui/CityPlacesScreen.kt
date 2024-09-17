package com.example.mycity.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.mycity.R
import com.example.mycity.data.LocalPlacesDataProvider
import com.example.mycity.model.Category
import com.example.mycity.model.Place

@Composable
fun CityPlacesScreen(
    cityPlacesList: List<Place>,
    onPlacePressed: (Place) -> Unit,
    modifier: Modifier = Modifier
    ) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 0.dp)
    ) {
        items(cityPlacesList) { place ->
            CityPlaceItem(
                place = place,
                onPlacePressed = onPlacePressed,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
        }
    }
}

@Composable
private fun CityPlaceItem(
    place: Place,
    onPlacePressed: (Place) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(72.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onPlacePressed(place) }
    ) {
        Image(
            painter = painterResource(id = place.imageResource),
            contentDescription = null,
            modifier = Modifier.padding(4.dp).clip(CircleShape)
        )
        Text(
            text = stringResource(place.nameResource),
            fontSize = 24.sp,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCityPlaceItem() {
    CityPlaceItem(
        place = Place(
            1,
            Category.RESTAURANTS,
            R.string.restaurant1_name,
            R.string.restaurant1_description,
            R.drawable.image
        ),
        onPlacePressed = {}
    )
}

@Preview(showBackground = true)
@Composable
fun CityPlacesScreenPreview() {
    CityPlacesScreen(
        cityPlacesList = LocalPlacesDataProvider.allPlaces,
        onPlacePressed = {},
        modifier = Modifier.fillMaxSize()
    )
}

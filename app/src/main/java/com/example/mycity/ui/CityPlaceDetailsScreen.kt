package com.example.mycity.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycity.R
import com.example.mycity.model.Category
import com.example.mycity.model.Place

import androidx.compose.foundation.lazy.LazyColumn

@Composable
fun CityPlaceDetailsScreen(
    place: Place,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Image(
                painter = painterResource(id = place.imageResource),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            Text(
                text = stringResource(place.nameResource),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }
        item {
            Text(
                text = stringResource(place.descriptionResource),
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCityPlaceDetailsScreen() {
    CityPlaceDetailsScreen(
        place = Place(
            1,
            Category.RESTAURANTS,
            R.string.restaurant1_name,
            R.string.restaurant1_description,
            R.drawable.image
        ),
        modifier = Modifier.fillMaxSize()
    )
}
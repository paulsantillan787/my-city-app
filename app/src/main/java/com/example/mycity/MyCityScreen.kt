package com.example.mycity

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mycity.model.Category
import com.example.mycity.model.CategoryItem
import com.example.mycity.model.Place
import com.example.mycity.model.Route
import com.example.mycity.ui.CityCategoriesScreen
import com.example.mycity.ui.CityPlaceDetailsScreen
import com.example.mycity.ui.CityPlacesScreen
import com.example.mycity.ui.CityUiState
import com.example.mycity.ui.CityViewModel
import com.example.mycity.ui.utils.CityContentType
import com.example.mycity.ui.utils.CityNavigationType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityAppBar(
    title: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(title)
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color(0xFF77E0EF)
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.label_back)
                    )
                }
            }
        }
    )
}


@Composable
fun CityApp(
    windowSize: WindowWidthSizeClass,
    viewModel: CityViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Route.valueOf(
        backStackEntry?.destination?.route ?: Route.CategorySelection.name
    )

    val uiState by viewModel.uiState.collectAsState()
    val title = when (currentScreen) {
        Route.CategorySelection -> stringResource(R.string.app_name)
        Route.PlaceList -> stringResource(R.string.select_place, uiState.currentCategory.name)
        Route.PlaceDetails -> stringResource(R.string.place_details)
    }

    val navigationType: CityNavigationType
    val contentType: CityContentType

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            navigationType = CityNavigationType.BOTTOM_NAVIGATION
            contentType = CityContentType.LIST_ONLY
            viewModel.changeMultiWindow(false)
        }

        WindowWidthSizeClass.Medium -> {
            navigationType = CityNavigationType.NAVIGATION_RAIL
            contentType = CityContentType.LIST_ONLY
            viewModel.changeMultiWindow(true)
        }

        WindowWidthSizeClass.Expanded -> {
            navigationType = CityNavigationType.PERMANENT_NAVIGATION_DRAWER
            contentType = CityContentType.LIST_WITH_DETAILS
            viewModel.changeMultiWindow(true)
        }

        else -> {
            navigationType = CityNavigationType.BOTTOM_NAVIGATION
            contentType = CityContentType.LIST_ONLY
            viewModel.changeMultiWindow(false)
        }
    }

    val isMultiWindow = uiState.isMultiWindow


    if (!isMultiWindow) {
        CityNoMultiWindowScreen(
            title = title,
            viewModel = viewModel,
            navController = navController,
            uiState = uiState
        )
    } else {
        CityMultiWindowScreen(
            title = title,
            navigationType = navigationType,
            contentType = contentType,
            viewModel = viewModel,
            uiState = uiState
        )
    }


}

@Composable
fun CityMultiWindowScreen(
    title: String,
    navigationType: CityNavigationType,
    contentType: CityContentType,
    viewModel: CityViewModel,
    uiState: CityUiState
) {
    val navigationItemContentList = listOf(
        NavigationItemContent(
            category = Category.RESTAURANTS,
            icon = painterResource(R.drawable.restaurant_icon),
            text = stringResource(R.string.restaurant)
        ),
        NavigationItemContent(
            category = Category.PARKS,
            icon = painterResource(R.drawable.park_icon),
            text = stringResource(R.string.park)
        ),
        NavigationItemContent(
            category = Category.MUSEUMS,
            icon = painterResource(R.drawable.museum_icon),
            text = stringResource(R.string.museum)
        ),
        NavigationItemContent(
            category = Category.SHOPPING,
            icon = painterResource(R.drawable.shop_icon),
            text = stringResource(R.string.shopping)
        ),
    )
    Scaffold(
        topBar = {
            CityAppBar(
                title = title,
                canNavigateBack = if (contentType == CityContentType.LIST_WITH_DETAILS) {
                    false
                } else {
                    !uiState.isShowingHomePage
                },
                navigateUp = { viewModel.resetHomeScreenStates() }
            )
        },
    ) { innerPadding ->
        if (navigationType == CityNavigationType.PERMANENT_NAVIGATION_DRAWER) {
            val navigationDrawerContentDescription = stringResource(R.string.navigation_rail)
            PermanentNavigationDrawer(
                drawerContent = {
                    PermanentDrawerSheet(
                        modifier = Modifier
                            .width(240.dp)
                            .padding(top = 64.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .wrapContentWidth()
                                .fillMaxHeight()
//                                .background(Color(0xFFE5E5E5))
                                .padding(8.dp)
                        ) {
                            for (item in navigationItemContentList) {
                                NavigationDrawerItem(
                                    selected = uiState.currentCategory == item.category,
                                    label = { Text(item.text, modifier = Modifier.padding(16.dp)) },
                                    icon = {
                                        Icon(
                                            painter = item.icon,
                                            contentDescription = null,
                                            tint = Color.Unspecified
                                        )
                                    },
                                    onClick = {
                                        viewModel.updateCurrentCategory(item.category)
                                    },
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }

                        }
                    }
                }
            ) {
                CityAppContent(
                    navigationType = navigationType,
                    contentType = contentType,
                    navigationItemContentList = navigationItemContentList,
                    viewModel = viewModel,
                    uiState = uiState,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        } else {
            CityAppContent(
                navigationType = navigationType,
                contentType = contentType,
                navigationItemContentList = navigationItemContentList,
                viewModel = viewModel,
                uiState = uiState,
                modifier = Modifier.padding(top = 64.dp)
            )
        }
    }
}

@Composable
private fun CityAppContent(
    navigationType: CityNavigationType,
    contentType: CityContentType,
    navigationItemContentList: List<NavigationItemContent>,
    viewModel: CityViewModel,
    uiState: CityUiState,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Row(modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(visible = navigationType == CityNavigationType.NAVIGATION_RAIL) {
                val navigationRailContentDescription = stringResource(R.string.navigation_rail)
                CityNavigationRail(
                    currentCategory = uiState.currentCategory,
                    onCategorySelected = { category ->
                        viewModel.updateCurrentCategory(category)
                    },
                    navigationItemContentList = navigationItemContentList,
                    modifier = Modifier.testTag(navigationRailContentDescription),
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFE5E5E5))
            ) {
                if (contentType == CityContentType.LIST_WITH_DETAILS) {
                    CityListAndDetailContent(
                        cityUiState = uiState,
                        onPlacePressed = { place ->
                            viewModel.updateDetailsScreenStates(place)
                        },
                        modifier = Modifier
                    )

                } else {
                    if (uiState.isShowingHomePage) {
                        CityPlacesScreen(
                            cityPlacesList = uiState.currentCategoryPlaces,
                            onPlacePressed = { place ->
                                viewModel.updateDetailsScreenStates(place)
                            },
                            modifier = Modifier
                                .statusBarsPadding()
                                .weight(1f)
                        )
                    } else {
                        CityPlaceDetailsScreen(
                            place = uiState.currentSelectedPlace,
                            modifier = Modifier
                                .statusBarsPadding()
                                .weight(1f)
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun CityListAndDetailContent(
    cityUiState: CityUiState,
    onPlacePressed: (Place) -> Unit,
    modifier: Modifier
) {
    Row(
        modifier = modifier
    ) {
        CityPlacesScreen(
            cityPlacesList = cityUiState.currentCategoryPlaces,
            onPlacePressed = onPlacePressed,
            modifier = Modifier.weight(1f)
        )
        CityPlaceDetailsScreen(
            place = cityUiState.currentSelectedPlace,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun CityNavigationRail(
    currentCategory: Category,
    onCategorySelected: (Category) -> Unit,
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier
) {
    NavigationRail {
        navigationItemContentList.forEach { item ->
            NavigationRailItem(
                selected = item.category == currentCategory,
                onClick = { onCategorySelected(item.category) },
                icon = {
                    Icon(
                        painter = item.icon,
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                },
                modifier = modifier
                    .padding(16.dp)
                    .height(72.dp)
            )
        }
    }
}

private data class NavigationItemContent(
    val category: Category,
    val icon: Painter,
    val text: String
)


@Composable
fun CityNoMultiWindowScreen(
    title: String,
    viewModel: CityViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    uiState: CityUiState
) {
    Scaffold(
        topBar = {
            CityAppBar(
                title = title,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        },
    ) { innerPadding ->


        NavHost(
            navController = navController,
            startDestination = Route.CategorySelection.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = Route.CategorySelection.name) {
                CityCategoriesScreen(
                    cityCategories = uiState.categories,
                    onCategoryPressed = { category ->
                        viewModel.updateCurrentCategory(category)
                        navController.navigate(Route.PlaceList.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }

            composable(route = Route.PlaceList.name) {
                CityPlacesScreen(
                    cityPlacesList = uiState.currentCategoryPlaces,
                    onPlacePressed = { place ->
                        viewModel.updateDetailsScreenStates(place)
                        navController.navigate(Route.PlaceDetails.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
//                        .padding(16.dp)
                )
            }

            composable(route = Route.PlaceDetails.name) {
                CityPlaceDetailsScreen(
                    place = uiState.currentSelectedPlace,
                    modifier = Modifier.fillMaxSize()
                )
            }


        }
    }
}
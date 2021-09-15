package com.example.myapplication.presentation.ui.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Tune
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.model.enums.SortFilter
import com.example.myapplication.model.enums.SortFilterRegion
import com.example.myapplication.model.enums.SortOptions
import com.example.myapplication.presentation.theme.primaryLight
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SearchAppBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    expanded: Boolean,
    toggleAppBar: (Boolean) -> Unit,
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit = {},
    selectedOption: SortOptions,
    onSelectedOptionChanged: (String) -> Unit,
    selectedFilter: SortFilter,
    onSelectedFilterChanged: (String) -> Unit,
    selectedFilterRegion: SortFilterRegion,
    onSelectedFilterRegionChanged: (String) -> Unit,
) {

    val focusManager = LocalFocusManager.current

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = primaryLight,
        elevation = 8.dp,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }
                ) {
                    Icon(Icons.Filled.Menu, "Menu")
                }

                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth(.85f)
                        .padding(start = 12.dp)
                        .background(
                            color = MaterialTheme.colors.surface,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(horizontal = 18.dp, vertical = 10.dp),
                    value = query,
                    onValueChange = onQueryChanged,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search,
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = { focusManager.clearFocus(force = true) }
                    ),
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                IconButton(
                    modifier = Modifier.padding(2.dp),
                    onClick = {
                        toggleAppBar(!expanded)
                    },
                ) {
                    Icon(Icons.Default.Tune, contentDescription = "Sort")
                }
            }
            if (expanded) {
                val optionScrollState = rememberLazyListState()
                LazyRow(
                    modifier = Modifier.padding(bottom = 8.dp),
                    state = optionScrollState
                ) {
                    items(SortOptions.values()) { options ->
                        SortChip(
                            category = options.name,
                            isSelected = selectedOption == options,
                            onSelectedCategoryChanged = onSelectedOptionChanged,
                            onExecuteSearch = onExecuteSearch,
                        )
                    }
                }

                if (selectedOption == SortOptions.Region){
                    val regionScrollState = rememberLazyListState()
                    LazyRow(
                        modifier = Modifier.padding(bottom = 8.dp),
                        state = regionScrollState
                    ) {
                        items(SortFilterRegion.values()) { region ->
                            SortChip(
                                category = region.name,
                                isSelected = selectedFilterRegion == region,
                                onSelectedCategoryChanged = onSelectedFilterRegionChanged,
                                onExecuteSearch = onExecuteSearch,
                            )
                        }
                    }
                } else {
                    val filterScrollState = rememberLazyListState()
                    LazyRow(
                        modifier = Modifier.padding(bottom = 8.dp),
                        state = filterScrollState
                    ) {
                        items(SortFilter.values()) { filter ->
                            SortChip(
                                category = filter.name,
                                isSelected = selectedFilter == filter,
                                onSelectedCategoryChanged = onSelectedFilterChanged,
                                onExecuteSearch = onExecuteSearch,
                            )
                        }
                    }
                }
            }
        }
    }
}
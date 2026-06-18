package com.davant.cefiremybookshelf.screens.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavKey
import com.davant.cefiremybookshelf.R
import com.davant.cefiremybookshelf.ui.theme.Main
import com.davant.cefiremybookshelf.ui.theme.Secondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    user: String,
    books: Int,
    onBack: () -> Unit,
    goToEditScreen: () -> Unit
) {
    TopAppBar(
        title = {
            Text("$user's Bookshelf [$books]",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold)
        },
        navigationIcon = {
            IconButton(onClick = { onBack() }) {
                Icon(painter = painterResource(R.drawable.ic_back),
                    contentDescription = "Go back")
            }
        },
        actions = {
            IconButton(onClick = { goToEditScreen() }) {
                Icon(painter = painterResource(R.drawable.ic_edit),
                    contentDescription = "Edit book")
            }
            IconButton(onClick = {  }) {
                Icon(painter = painterResource(R.drawable.ic_delete),
                    contentDescription = "Delete book")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Main,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White,
            titleContentColor = Secondary),
        scrollBehavior = scrollBehavior
    )
}
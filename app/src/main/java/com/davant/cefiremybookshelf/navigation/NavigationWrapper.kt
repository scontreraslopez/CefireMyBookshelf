package com.davant.cefiremybookshelf.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.davant.cefiremybookshelf.data.firestore.FirebaseBooksRepository
import com.davant.cefiremybookshelf.domain.model.Book
import com.davant.cefiremybookshelf.navigation.Routes.*
import com.davant.cefiremybookshelf.screens.addedit.AddEditScreen
import com.davant.cefiremybookshelf.screens.addedit.AddEditViewModel
import com.davant.cefiremybookshelf.screens.home.HomeScreen
import com.davant.cefiremybookshelf.screens.home.HomeViewModel
import com.davant.cefiremybookshelf.screens.login.LoginScreen
import com.davant.cefiremybookshelf.screens.login.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun NavigationWrapper() {
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    val repository = FirebaseBooksRepository(firestore)

    val backStack = rememberNavBackStack(Login)
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeAt(backStack.lastIndex) },
        entryProvider = entryProvider {
            entry<Login> {
                LoginScreen(
                    LoginViewModel(
                        auth = auth,
                        navigateToHome = { userName ->
                            backStack.add(Home(userName))
                        })
                )
            }
            entry<Home> { key ->
                HomeScreen(
                    homeViewModel = HomeViewModel(
                        repository = repository,
                        userName = key.name,
                        userId = auth.currentUser?.uid ?: "",
                        goToAddEditScreen = { backStack.add(AddEdit(it)) },
                        goBack = { backStack.removeAt(backStack.lastIndex) }
                    )
                )
            }
            entry<AddEdit> { key ->
                AddEditScreen(
                    AddEditViewModel(
                        inBook = key.book,
                        repository = repository,
                        userId = auth.currentUser?.uid ?: "",
                        navigateBack = { backStack.removeAt(backStack.lastIndex) }
                    )
                )
            }
        }
    )
}

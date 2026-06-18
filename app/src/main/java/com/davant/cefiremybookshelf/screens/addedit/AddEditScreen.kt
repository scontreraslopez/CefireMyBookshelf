package com.davant.cefiremybookshelf.screens.addedit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davant.cefiremybookshelf.R
import com.davant.cefiremybookshelf.domain.model.Book
import com.davant.cefiremybookshelf.ui.theme.Main
import com.davant.cefiremybookshelf.ui.theme.Secondary
import com.davant.cefiremybookshelf.screens.addedit.AddEditViewModel.*

@Composable
fun AddEditScreen(addEditViewModel: AddEditViewModel) {
    val newBook by addEditViewModel.newBook.observeAsState(true)
    val isError by addEditViewModel.isError.observeAsState(false)
    val book by addEditViewModel.book.observeAsState(Book())
    val coverUIState = addEditViewModel.coverUIState
    val bookInfoUIState = addEditViewModel.bookInfoUIState

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Main)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Text(
                text = if (newBook) "Add a new Book"
                else "Editing ${book.title}",
                color = Secondary,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    modifier = Modifier.weight(1f),
                    value = book.isbn,
                    onValueChange = { addEditViewModel.updateBook(book.copy(isbn = it)) },
                    placeholder = { Text("ISBN") },
                    isError = isError || bookInfoUIState is BookInfoUIState.Error
                )
                Box(contentAlignment = Alignment.Center) {
                    Button(
                        modifier = Modifier.size(48.dp),
                        onClick = { addEditViewModel.getBookInfo() }) {
                    }
                    Icon(
                        painterResource(R.drawable.ic_find),
                        "Find",
                        modifier = Modifier.size(24.dp)
                    )
                    if (bookInfoUIState is BookInfoUIState.Loading)
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(36.dp),
                            color = Secondary
                        )
                }
            }
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = book.title,
                onValueChange = { addEditViewModel.updateBook(book.copy(title = it)) },
                placeholder = { Text("Title") },
                isError = isError
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = book.author,
                onValueChange = { addEditViewModel.updateBook(book.copy(author = it)) },
                placeholder = { Text("Author") },
                isError = isError
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = if (book.year == null) ""
                else book.year.toString(),
                onValueChange = {
                    if (it.isEmpty())
                        addEditViewModel.updateBook(book.copy(year = null))
                    else
                        addEditViewModel.updateBook(book.copy(year = it.toInt()))
                },
                placeholder = { Text("Year") },
                isError = isError
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    modifier = Modifier.weight(1f),
                    value = book.cover,
                    onValueChange = { addEditViewModel.updateBook(book.copy(cover = it)) },
                    placeholder = { Text("Cover") },
                    isError = isError || coverUIState is CoverUIState.Error
                )
                Box(contentAlignment = Alignment.Center) {
                    Button(
                        modifier = Modifier.size(48.dp),
                        onClick = { addEditViewModel.getCoverId() }) {
                    }
                    Icon(
                        painterResource(R.drawable.ic_find),
                        "Find",
                        modifier = Modifier.size(24.dp)
                    )
                    if (coverUIState is CoverUIState.Loading)
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(36.dp),
                            color = Secondary
                        )
                }
            }
            if (coverUIState is CoverUIState.Error)
                Text(
                    text = "Error: ISBN not found",
                    color = Color.Red
                )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Favourite?",
                    fontSize = 18.sp
                )
                Checkbox(
                    checked = book.fav,
                    onCheckedChange = { addEditViewModel.updateBook(book.copy(fav = it)) })
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Have you read it?",
                    fontSize = 18.sp
                )
                Checkbox(
                    checked = book.read,
                    onCheckedChange = { addEditViewModel.updateBook(book.copy(read = it)) })
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp))
            {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        addEditViewModel.navigateBack()
                    }) {
                    Text(
                        text = "BACK",
                        fontSize = 18.sp
                    )
                }
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if (newBook)
                            addEditViewModel.addBookFirebase()
                        else
                            addEditViewModel.updateBookFirebase()
                    }) {
                    Text(
                        text = "SAVE",
                        fontSize = 18.sp
                    )
                }
                Button(
                    modifier = Modifier
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    enabled = !newBook,
                    onClick = {
                        addEditViewModel.deleteBookFirebase()
                    }) {
                    Text(
                        text = "DELETE",
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}
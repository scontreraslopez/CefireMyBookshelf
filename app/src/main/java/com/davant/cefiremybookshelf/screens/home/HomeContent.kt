package com.davant.cefiremybookshelf.screens.home

import android.annotation.SuppressLint
import android.util.Log
import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.davant.cefiremybookshelf.R
import com.davant.cefiremybookshelf.domain.model.Book


@Composable
fun HomeContent(innerPadding: PaddingValues, booksFiltered: List<Book>, navigateToEdit: (Book) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = innerPadding
    ) {
        items(booksFiltered) { book ->
            BookCard(book) { navigateToEdit(it) }
        }
    }
}

@SuppressLint("LocalContextResourcesRead", "DiscouragedApi")
@Composable
fun BookCard(book: Book, navigateToEdit: (Book) -> Unit) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = { navigateToEdit(book) })
    ) {
        if(Patterns.WEB_URL.matcher(book.cover).matches())
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(book.cover)
                    .crossfade(true)
                    .build(),
                contentDescription = "HP Character",
                contentScale = ContentScale.Crop,
                modifier = Modifier.width(200.dp)
            )
        Text(
            modifier = Modifier
                .padding(top = 8.dp, start = 6.dp),
            text = book.title,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = book.author,
            fontStyle = FontStyle.Italic
        )
        Text(
            modifier = Modifier
                .padding(4.dp),
            text = book.isbn,
            fontSize = 14.sp
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(
                painter = painterResource(
                    if (book.read) R.drawable.ic_read
                    else R.drawable.ic_not_read
                ),
                contentDescription = "Read or not",
                modifier = Modifier.padding(6.dp)
            )
            Icon(
                painter = painterResource(
                    if (book.fav) R.drawable.ic_fav
                    else R.drawable.ic_not_fav
                ),
                contentDescription = "Favourite or not",
                modifier = Modifier.padding(6.dp)
            )
            Text(
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth(),
                text = "${book.year}",
                textAlign = TextAlign.End
            )
        }
    }
}
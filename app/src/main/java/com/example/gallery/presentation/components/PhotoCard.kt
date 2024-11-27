package com.example.gallery.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gallery.R
import com.example.gallery.ui.theme.GalleryTheme

@Composable
fun PhotoCard(
    photoTitle: String,
    albumName: String,
    userName: String
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.padding(8.dp)
    ) {
        // Image with specified region
        Box(
            modifier = Modifier
                .size(60.dp) // Adjust size as needed
                .clip(RectangleShape) // Crop into a rectangle (you can use CircleShape or others)
                .background(Color.Gray) // Background for previewing
        ) {
            Image(
                painter = painterResource(id = R.drawable.cat),
                contentDescription = "cat",
                contentScale = ContentScale.Crop, // Ensures the content is cropped
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.width(60.dp))

        // Information Column
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = photoTitle, style = MaterialTheme.typography.body1)
            Text(text = albumName, style = MaterialTheme.typography.body2)
            Text(text = userName, style = MaterialTheme.typography.caption)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PhotoCardPreview() {
    GalleryTheme {
        PhotoCard(photoTitle = "A Mew Cat", albumName = "Cats", userName = "Al-Amin Kawsar")
    }

}
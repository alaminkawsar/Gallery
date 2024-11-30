package com.example.gallery.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp)),
        backgroundColor = Color.White
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.padding(8.dp)
        ) {
            // Image with specified region
            Box(
                modifier = Modifier
                    .size(80.dp) // Adjust size as needed
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
            Spacer(modifier = Modifier.width(30.dp))

            // Information Column
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = photoTitle.replaceFirstChar { it.uppercaseChar() },
                    style = MaterialTheme.typography.h6,
                    color = Color.Black
                )
                Text(
                    text = albumName.replaceFirstChar { it.uppercaseChar() },
                    style = MaterialTheme.typography.body1,
                    color = Color.Black
                )
                Text(
                    text = userName.replaceFirstChar { it.uppercaseChar() },
                    style = MaterialTheme.typography.caption,
                    color = Color.Black
                )
            }
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
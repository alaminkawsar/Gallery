package com.example.gallery.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gallery.R
import com.example.gallery.ui.theme.GalleryTheme

@Composable
fun CustomTopAppBar(
    navBack: () -> Unit
) {
    val iconSize = 25.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(shape = RectangleShape, elevation = 1.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            IconButton(onClick = { navBack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back_24),
                    contentDescription = "Back Navigation",
                    modifier = Modifier.size(iconSize)
                )
            }
            Row(modifier = Modifier.fillMaxWidth(0.7f)) {
                Text(
                    text = "Photos",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = Color.Black
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_more_ver_dot_24),
                        contentDescription = "Edit something",
                        tint = Color.Black,
                        modifier = Modifier.size(iconSize)
                    )
                }

            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }

}

@Preview(showBackground = true)
@Composable
fun TopAppBar() {
    GalleryTheme {
        CustomTopAppBar(){

        }
    }
}
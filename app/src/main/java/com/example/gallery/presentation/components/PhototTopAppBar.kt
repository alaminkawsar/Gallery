package com.example.gallery.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.gallery.R
import com.example.gallery.utils.TestTag

@Composable
fun PhotoTopAppBar(
    imageTitle: String,
    backButtonPress: () -> Unit
) {
    val iconSize = 25.dp
    val menuItem = listOf("Clear")
    var menuSelected = remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var size by remember { mutableStateOf(Size.Zero) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { position ->
                size = position.size.toSize()
            }
            .shadow(shape = RectangleShape, elevation = 1.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            IconButton(
                onClick = {backButtonPress()}
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back_24),
                    contentDescription = "Back Button",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(iconSize)
                        .testTag(TestTag.PHOTO_BACK_BUTTON)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = imageTitle,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_more_ver_dot_24),
                        contentDescription = "menu",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(iconSize)
                            .testTag(TestTag.MENU_BUTTON)
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { (size.width / 2f).toDp() }) // Match width with TextField
                        .testTag(TestTag.CLEAR_BUTTON)
                ) {
                    menuItem.forEach {
                        DropdownMenuItem(onClick = {
                            menuSelected.value = it
                            expanded = false
                        }) {
                            Text(text = it)
                        }
                    }
                }


            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }

}
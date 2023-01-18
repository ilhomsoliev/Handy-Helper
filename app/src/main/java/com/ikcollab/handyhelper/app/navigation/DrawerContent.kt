package com.ikcollab.handyhelper.app.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DrawerContent() {

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background)) {
        Text(
            modifier = Modifier.padding(12.dp),
            text = "Handy Helper",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary
        )
        Divider()
        DrawerItem(
            "Dark Mode",
            icon = Icons.Default.Mode,
            onClick = {  },
        )
        DrawerItem(
            "Themes",
            icon = Icons.Default.Palette,
            onClick = {  },
        )
        DrawerItem(
            "Language",
            icon = Icons.Default.Language,
            onClick = {  },
        )
        DrawerItem(
            "Settings",
            icon = Icons.Default.Archive,
            onClick = {  },
        )
        DrawerItem(
            "Donations",
            icon = Icons.Default.Favorite,
            onClick = {  },
        )
        Divider()
        DrawerItem(
            "Share",
            icon = Icons.Default.Settings,
            onClick = {  },
        )
        DrawerItem(
            "FAQ",
            icon = Icons.Default.Settings,
            onClick = {  },
        )
        DrawerItem(
            "Feedback",
            icon = Icons.Default.Settings,
            onClick = {  },
        )
        DrawerItem(
            "Rate Us",
            icon = Icons.Default.Settings,
            onClick = {  },
        )
    }
}

@Composable
fun DrawerItem(text: String, icon: ImageVector, onClick: () -> Unit) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable {
                onClick()
            }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp)
                    .clip(RoundedCornerShape(6.dp))
            ) {
                Row( modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "",
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = text    )
                }
            }
        }

}
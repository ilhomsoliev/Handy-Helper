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
fun DrawerContent(
    openLanguagesScreen: () -> Unit,
    openSettingsScreen: () -> Unit,
    openDonationsLink: () -> Unit,
    openGithubPage: () -> Unit,
    shareApp: () -> Unit,
    sendEmail: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Text(
            modifier = Modifier.padding(12.dp),
            text = "Handy Helper",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onBackground
        )
        Divider()
        /*DrawerItem(
            "Dark Mode",
            icon = Icons.Default.Mode,
            onClick = {  },
        )*/
        /* DrawerItem(
             "Themes",
             icon = Icons.Default.Palette,
             onClick = {  },
         )*/
        DrawerItem(
            "Language",
            icon = Icons.Default.Language,
            onClick = {
                openLanguagesScreen()
            },
        )
        DrawerItem(
            "Settings",
            icon = Icons.Default.Settings,
            onClick = {
                openSettingsScreen()
            },
        )
        DrawerItem(
            "Donations",
            icon = Icons.Default.VolunteerActivism,
            onClick = {
                openDonationsLink()
            },
        )
        DrawerItem(
            "Contribute",
            icon = Icons.Default.Favorite,
            onClick = {
                openGithubPage()
            },
        )
        Divider()
        DrawerItem(
            "Share",
            icon = Icons.Default.Share,
            onClick = {
                shareApp()
            },
        )
        DrawerItem(
            "Feedback",
            icon = Icons.Default.Chat,
            onClick = {
                sendEmail()
            },
        )
        DrawerItem(
            "Rate Us",
            icon = Icons.Default.Star,
            onClick = { },
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "",
                    tint = MaterialTheme.colors.onBackground
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = text, color = MaterialTheme.colors.onBackground)
            }
        }
    }

}
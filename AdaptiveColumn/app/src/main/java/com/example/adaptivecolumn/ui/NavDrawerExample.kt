package com.example.adaptivecolumn.ui

import android.app.Activity
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import com.example.adaptivecolumn.Content
import com.example.adaptivecolumn.ExampleType
import com.example.adaptivecolumn.outline
import com.google.accompanist.adaptive.calculateDisplayFeatures

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavDrawerExample(
    activity: Activity,
    exampleType: ExampleType,
    updateExampleType: (ExampleType) -> Unit
) {
    val icons = listOf(
        Icons.Default.List,
        Icons.Default.Done,
        Icons.Default.Face,
        Icons.Default.Lock,
        Icons.Default.Search,
        Icons.Default.ThumbUp,
        Icons.Default.Warning,
        Icons.Default.Star
    )

    var selectedIcon by remember { mutableStateOf(icons[0]) }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                AdaptiveColumn(displayFeatures = calculateDisplayFeatures(activity)) {
                    icons.forEach {
                        NavigationDrawerItem(
                            modifier = Modifier
                                .padding(5.dp)
                                .outline(CircleShape),
                            icon = { Icon(imageVector = it, contentDescription = it.name) },
                            label = { Text(it.name.substringAfter('.')) },
                            selected = it == selectedIcon,
                            onClick = { selectedIcon = it }
                        )
                    }
                }
            }
        },
        content = {
            Content(exampleType, updateExampleType)
        }
    )
}
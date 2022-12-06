package com.example.adaptivecolumn.ui

import android.app.Activity
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
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

@Composable
fun NavRailExample(
    activity: Activity,
    exampleType: ExampleType,
    updateExampleType: (ExampleType) -> Unit
) {
    Row {
        NavRail(activity)
        Content(exampleType, updateExampleType)
    }
}

@Composable
fun NavRail(activity: Activity) {
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

    NavigationRail {
        AdaptiveColumn(displayFeatures = calculateDisplayFeatures(activity)) {
            icons.forEach {
                NavigationRailItem(
                    modifier = Modifier
                        .padding(5.dp)
                        .outline(),
                    selected = it == selectedIcon,
                    onClick = { selectedIcon = it },
                    icon = { Icon(imageVector = it, contentDescription = it.name) },
                    label = { Text(it.name.substringAfter('.')) }
                )
            }
        }
    }
}
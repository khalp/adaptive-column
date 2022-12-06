@file:JvmName("DraggableExampleKt")

package com.example.adaptivecolumn.ui

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import com.example.adaptivecolumn.Content
import com.example.adaptivecolumn.ExampleType
import com.example.adaptivecolumn.R
import com.example.adaptivecolumn.outline
import com.google.accompanist.adaptive.calculateDisplayFeatures
import kotlin.math.roundToInt

@Composable
fun DraggableExample(
    activity: Activity,
    exampleType: ExampleType,
    updateExampleType: (ExampleType) -> Unit
) {
    var offset by remember { mutableStateOf(Offset(0f, 0f)) }

    Box(modifier = Modifier.fillMaxSize()) {
        Content(exampleType, updateExampleType)
        AdaptiveColumn(
            modifier = Modifier
                .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        offset = Offset(offset.x + dragAmount.x, offset.y + dragAmount.y)
                    }
                }
                .width(400.dp)
                .outline(color = Color.White),
            displayFeatures = calculateDisplayFeatures(activity)
        ) {
            Icon(
                modifier = Modifier
                    .outline(color = MaterialTheme.colors.secondaryVariant)
                    .padding(20.dp),
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = null
            )
            Text(
                modifier = Modifier.outline(color = MaterialTheme.colors.secondaryVariant),
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
            )
            Image(
                modifier = Modifier.outline(color = MaterialTheme.colors.secondaryVariant),
                painter = painterResource(id = R.drawable.empty_state_illustration),
                contentDescription = null
            )
        }
    }
}
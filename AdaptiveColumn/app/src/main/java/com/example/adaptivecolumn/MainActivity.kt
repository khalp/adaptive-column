package com.example.adaptivecolumn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.example.adaptivecolumn.ui.DraggableExample
import com.example.adaptivecolumn.ui.NavDrawerExample
import com.example.adaptivecolumn.ui.NavRailExample
import com.example.adaptivecolumn.ui.theme.AdaptiveColumnTheme
import com.google.accompanist.adaptive.calculateDisplayFeatures

enum class ExampleType { NavRail, NavDrawer, Draggable }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdaptiveColumnTheme {
                var exampleType by rememberSaveable { mutableStateOf(ExampleType.NavRail) }
                val updateExampleType: (ExampleType) -> Unit = { exampleType = it }

                when (exampleType) {
                    ExampleType.NavRail -> NavRailExample(this@MainActivity, exampleType, updateExampleType)
                    ExampleType.NavDrawer -> NavDrawerExample(this@MainActivity, exampleType, updateExampleType)
                    ExampleType.Draggable -> DraggableExample(this@MainActivity, exampleType, updateExampleType)
                }
            }
        }
    }
}

@Composable
fun Content(exampleType: ExampleType, updateExampleType: (ExampleType) -> Unit) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.secondary)
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        Column(modifier = Modifier.offset(x = 100.dp)) {
            ExampleType.values().forEach {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = exampleType == it,
                        onClick = { updateExampleType(it) },
                        colors = RadioButtonDefaults.colors(selectedColor = Color.White)
                    )
                    Text(it.name + " Example")
                }
            }
        }
    }
}

fun Modifier.outline(shape: Shape? = null, color: Color? = null): Modifier = composed {
    this.border(2.dp, color ?: MaterialTheme.colors.secondary, shape ?: MaterialTheme.shapes.medium)
}

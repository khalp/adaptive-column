package com.example.foldawarecolumn.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.toComposeRect
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature

/**
 * A simplified version of [Column](Column) that places children in a fold-aware manner.
 *
 * @param displayFeatures the list of known display features to automatically avoid
 * @param modifier an optional modifier for the layout
 * @param foldPadding the optional padding to add around a fold
 * @param horizontalAlignment the horizontal alignment of the layout's children.
 *
 * @see [Column](Column)
 */
@Composable
fun FoldAwareColumn(
    displayFeatures: List<DisplayFeature>,
    modifier: Modifier = Modifier,
    foldPadding: PaddingValues = PaddingValues(),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable FoldAwareColumnScope.() -> Unit,
) {
    // Extract folding feature if horizontal
    val fold = displayFeatures.find {
        it is FoldingFeature && it.orientation == FoldingFeature.Orientation.HORIZONTAL
    } as FoldingFeature?

    // Calculate fold bounds in pixels (including any added fold padding)
    val foldBoundsPx = with(LocalDensity.current) {
        val topPaddingPx = foldPadding.calculateTopPadding().roundToPx()
        val bottomPaddingPx = foldPadding.calculateBottomPadding().roundToPx()

        fold?.bounds?.toComposeRect()?.let {
            Rect(
                top = it.top - topPaddingPx,
                left = it.left,
                right = it.right,
                bottom = it.bottom + bottomPaddingPx
            )
        }
    }

    // Extract other folding feature properties
    val foldIsSeparating = fold?.isSeparating

    Layout(
        modifier = modifier,
        measurePolicy = foldAwareColumnMeasurePolicy(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = horizontalAlignment,
            foldBoundsPx = foldBoundsPx,
            foldIsSeparating = foldIsSeparating
        ),
        content = { FoldAwareColumnScopeInstance.content() }
    )
}

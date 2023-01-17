package com.example.foldawarecolumn.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.IntrinsicMeasureScope
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

/**
 * FoldAwareColumn version of [rowColumnMeasurePolicy] that uses [RowColumnMeasurementHelper.foldAwarePlaceHelper]
 * method instead of [RowColumnMeasurementHelper.placeHelper]
 */
@Composable
internal fun foldAwareColumnMeasurePolicy(
    verticalArrangement: Arrangement.Vertical,
    horizontalAlignment: Alignment.Horizontal,
    foldBoundsPx: Rect?,
    foldIsSeparating: Boolean?
) = remember(verticalArrangement, horizontalAlignment, foldBoundsPx, foldIsSeparating) {

    val orientation = LayoutOrientation.Vertical
    val arrangement: (Int, IntArray, LayoutDirection, Density, IntArray) -> Unit =
        { totalSize, size, _, density, outPosition ->
            with(verticalArrangement) { density.arrange(totalSize, size, outPosition) }
        }
    val arrangementSpacing = verticalArrangement.spacing
    val crossAxisAlignment = CrossAxisAlignment.horizontal(horizontalAlignment)
    val crossAxisSize = SizeMode.Wrap

    object : MeasurePolicy {
        override fun MeasureScope.measure(
            measurables: List<Measurable>,
            constraints: Constraints
        ): MeasureResult {
            val placeables = arrayOfNulls<Placeable?>(measurables.size)
            val rowColumnMeasureHelper =
                RowColumnMeasurementHelper(
                    orientation,
                    arrangement,
                    arrangementSpacing,
                    crossAxisSize,
                    crossAxisAlignment,
                    measurables,
                    placeables
                )

            val measureResult = rowColumnMeasureHelper
                .measureWithoutPlacing(
                    this,
                    constraints, 0, measurables.size
                )

            val layoutWidth: Int
            val layoutHeight: Int
            if (orientation == LayoutOrientation.Horizontal) {
                layoutWidth = measureResult.mainAxisSize
                layoutHeight = measureResult.crossAxisSize
            } else {
                layoutWidth = measureResult.crossAxisSize
                layoutHeight = measureResult.mainAxisSize
            }

            return layout(layoutWidth, layoutHeight) {
                rowColumnMeasureHelper.foldAwarePlaceHelper(
                    this,
                    measureResult,
                    0,
                    layoutDirection,
                    foldIsSeparating,
                    foldBoundsPx
                )
            }
        }

        override fun IntrinsicMeasureScope.minIntrinsicWidth(
            measurables: List<IntrinsicMeasurable>,
            height: Int
        ) = MinIntrinsicWidthMeasureBlock(orientation)(
            measurables,
            height,
            arrangementSpacing.roundToPx()
        )

        override fun IntrinsicMeasureScope.minIntrinsicHeight(
            measurables: List<IntrinsicMeasurable>,
            width: Int
        ) = MinIntrinsicHeightMeasureBlock(orientation)(
            measurables,
            width,
            arrangementSpacing.roundToPx()
        )

        override fun IntrinsicMeasureScope.maxIntrinsicWidth(
            measurables: List<IntrinsicMeasurable>,
            height: Int
        ) = MaxIntrinsicWidthMeasureBlock(orientation)(
            measurables,
            height,
            arrangementSpacing.roundToPx()
        )

        override fun IntrinsicMeasureScope.maxIntrinsicHeight(
            measurables: List<IntrinsicMeasurable>,
            width: Int
        ) = MaxIntrinsicHeightMeasureBlock(orientation)(
            measurables,
            width,
            arrangementSpacing.roundToPx()
        )
    }
}

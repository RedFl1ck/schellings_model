package com.example.schellings_model.calculations

import android.graphics.Color
import kotlin.math.round
import kotlin.random.Random.Default.nextInt

/**
 * Service to fill grid.
 */
class GridService {
    companion object {

        fun fillArray(grid: Array<IntArray>, coloredCellsPercentage: Int, sideLength: Int) {
            val percent: Int =
                round((coloredCellsPercentage.toFloat() / 100 * sideLength * sideLength)).toInt()

            fillColor(grid, percent, Color.RED, sideLength)
            fillColor(grid, percent, Color.BLUE, sideLength)
            fillEmpty(grid, sideLength)
        }

        private fun fillColor(
            grid: Array<IntArray>, coloredCellsPercentage: Int, color: Int, sideLength: Int
        ) {
            var coloredCellsCount = 0
            while (coloredCellsCount != coloredCellsPercentage) {
                val x = nextInt(0, sideLength)
                val y = nextInt(0, sideLength)
                if (grid[x][y] == 0) {
                    grid[x][y] = color
                    coloredCellsCount++
                }
            }
        }

        private fun fillEmpty(
            grid: Array<IntArray>, sideLength: Int
        ) {
            for (x in 0 until sideLength) {
                for (y in 0 until sideLength) {
                    if (grid[x][y] == 0)
                        grid[x][y] = Color.WHITE
                }
            }
        }
    }
}
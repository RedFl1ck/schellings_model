package com.example.schellings_model.calculations

import android.graphics.Color
import kotlin.random.Random

/**
 * Service to make steps.
 */
class StepService {
    companion object {

        @Synchronized
        fun makeStep(
            grid: Array<IntArray>, sideLength: Int
        ): Pair<Triple<Int, Int, Int>, Triple<Int, Int, Int>> {
            var equalNeighboursCount = Int.MAX_VALUE
            var x1 = Int.MAX_VALUE
            var y1 = Int.MAX_VALUE

            // find random "unhappy" cell
            while (equalNeighboursCount > 1) {
                x1 = Random.nextInt(0, sideLength)
                y1 = Random.nextInt(0, sideLength)
                if (grid[x1][y1] != Color.WHITE) {
                    equalNeighboursCount = calculateEqualNeighbours(grid, x1, y1)
                }
            }

            // find random "empty" cell
            val emptyCells = mutableListOf<Pair<Int, Int>>()
            for (x in 0 until sideLength) {
                for (y in 0 until sideLength) {
                    if (grid[x][y] == Color.WHITE) emptyCells.add(x to y)
                }
            }
            val pair = emptyCells[Random.nextInt(0, emptyCells.size)]
            val x2 = pair.first
            val y2 = pair.second
            val color = grid[x1][y1]

            // swipe
            grid[x2][y2] = color
            grid[x1][y1] = Color.WHITE

            return Pair(Triple(x1, y1, Color.WHITE), Triple(x2, y2, color))
        }

        private fun calculateEqualNeighbours(
            grid: Array<IntArray>, x: Int, y: Int
        ): Int {
            var equalNeighboursCount = 8
            val color = grid[x][y]

            equalNeighboursCount += countNeighbour(grid, x - 1, y - 1, color)
            equalNeighboursCount += countNeighbour(grid, x - 1, y + 0, color)
            equalNeighboursCount += countNeighbour(grid, x - 1, y + 1, color)
            equalNeighboursCount += countNeighbour(grid, x + 0, y + 1, color)
            equalNeighboursCount += countNeighbour(grid, x + 1, y + 1, color)
            equalNeighboursCount += countNeighbour(grid, x + 1, y + 0, color)
            equalNeighboursCount += countNeighbour(grid, x - 1, y + 1, color)
            equalNeighboursCount += countNeighbour(grid, x + 0, y - 1, color)

            return equalNeighboursCount
        }

        private fun countNeighbour(
            grid: Array<IntArray>, x: Int, y: Int, color: Int
        ): Int {
            val row = grid.getOrElse(x) { grid[grid.size - kotlin.math.abs(x)] }
            val neighbourColor = row.getOrElse(y) { row[row.size - kotlin.math.abs(y)] }

            return if (neighbourColor != color) {
                -1
            } else {
                0
            }
        }

    }
}
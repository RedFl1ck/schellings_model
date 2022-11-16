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
        ): Pair<Triple<Int, Int, Int>, Triple<Int, Int, Int>>? {

            // find random "unhappy" cell
            val unhappyCells = mutableListOf<Pair<Int, Int>>()
            for (x in 0 until sideLength) {
                for (y in 0 until sideLength) {
                    if (grid[x][y] != Color.WHITE && calculateEqualNeighbours(grid, x, y) < 2)
                        unhappyCells.add(x to y)
                }
            }
            if (unhappyCells.isEmpty()) {
                return null
            }
            val pairUnhappy = unhappyCells[Random.nextInt(0, unhappyCells.size)]

            // find random "empty" cell
            val emptyCells = mutableListOf<Pair<Int, Int>>()
            for (x in 0 until sideLength) {
                for (y in 0 until sideLength) {
                    if (grid[x][y] == Color.WHITE) emptyCells.add(x to y)
                }
            }
            val pairEmpty = emptyCells[Random.nextInt(0, emptyCells.size)]

            // get data
            val x1 = pairUnhappy.first
            val y1 = pairUnhappy.second
            val x2 = pairEmpty.first
            val y2 = pairEmpty.second
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
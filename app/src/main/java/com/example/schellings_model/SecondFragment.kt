package com.example.schellings_model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.schellings_model.calculations.GridService
import com.example.schellings_model.calculations.StepService
import com.example.schellings_model.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SecondFragment : Fragment() {

    var SIDE_LENGTH = 50
    var COLORED_CELLS_PERCENTAGE = 45

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments?.getInt("size") != null) {
            SIDE_LENGTH = arguments?.getInt("size")!!
        }

        val bounds = requireActivity().windowManager.currentWindowMetrics.bounds
        val cellWidth: Int = bounds.width() / SIDE_LENGTH

        val grid: Array<IntArray> =
            Array(SIDE_LENGTH) { IntArray(SIDE_LENGTH) }
        GridService.fillArray(grid, COLORED_CELLS_PERCENTAGE, SIDE_LENGTH)

        updateTable(cellWidth, grid)
        binding.buttonStep.setOnClickListener {
            binding.buttonStep.isEnabled = false
            StepService.makeSteps(grid, SIDE_LENGTH, binding.seekBar.progress)
            updateTable(cellWidth, grid)
            binding.buttonStep.isEnabled = true
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                binding.textCount.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // you can probably leave this empty
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // you can probably leave this empty
            }
        })
    }

    private fun updateTable(cellWidth: Int, grid: Array<IntArray>) {
        binding.field.removeAllViews()
        for (x in 0 until SIDE_LENGTH) {
            val row = TableRow(this.context)
            row.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            for (y in 0 until SIDE_LENGTH) {
                val textView = TextView(this.context)
                textView.text = " "
                textView.layoutParams = TableRow.LayoutParams(
                    cellWidth,
                    cellWidth
                )
                textView.setBackgroundColor(grid[x][y])
                row.addView(textView)
            }
            binding.field.addView(row)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
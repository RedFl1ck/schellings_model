package com.example.schellings_model

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.schellings_model.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRun.visibility = View.VISIBLE
        binding.editTextSize.visibility = View.VISIBLE
        binding.textSize.visibility = View.VISIBLE

        binding.buttonRun.setOnClickListener {
            if (validateSize(binding.editTextSize)) {
                val size = Integer.parseInt(binding.editTextSize.text.toString())
                val bundle = bundleOf("size" to size)
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
                binding.buttonRun.visibility = View.INVISIBLE
                binding.editTextSize.visibility = View.INVISIBLE
                binding.textSize.visibility = View.INVISIBLE
            }
        }
        binding.editTextSize.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateSize(binding.editTextSize)

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateSize(binding.editTextSize)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validateSize(field: EditText): Boolean {
        val temp = field.text.toString()
        return when {
            temp.isEmpty() -> {
                field.error = resources.getString(R.string.error_empty)
                false
            }
            Integer.parseInt(temp) < 10 -> {
                field.error = resources.getString(R.string.error_invalid_size)
                false
            }
            Integer.parseInt(temp) > 100 -> {
                field.error = resources.getString(R.string.error_invalid_size)
                false
            }
            else -> {
                field.error = null
                true
            }
        }
    }

}
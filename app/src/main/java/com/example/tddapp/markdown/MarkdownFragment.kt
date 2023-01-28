package com.example.tddapp.markdown

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.core.text.italic
import androidx.core.text.underline
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tddapp.databinding.FragmentMarkdownBinding

class MarkdownFragment : Fragment() {

    private lateinit var _binding: FragmentMarkdownBinding
    private val viewModel: MarkdownViewModel by viewModels()

    private val boldMarker = "**"
    private val italicMarker = "$$"
    private val underlineMarker = "%%"

    private val text = "Hello! \n My name is ${boldMarker}Alexander${boldMarker}, and I`m ${italicMarker}android developer${italicMarker}, \n This is my git hub: ${underlineMarker}https://github.com/AlezZgo${underlineMarker}"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMarkdownBinding.inflate(inflater, container, false)
        return _binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.run {
            tv.text = text
        }
    }

}
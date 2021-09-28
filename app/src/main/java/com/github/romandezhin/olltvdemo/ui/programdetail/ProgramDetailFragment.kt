package com.github.romandezhin.olltvdemo.ui.programdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.github.romandezhin.olltvdemo.databinding.FragmentProgramDetailBinding
import com.github.romandezhin.olltvdemo.ui.programlist.SharedViewModel

class ProgramDetailFragment : Fragment() {
    private var _binding: FragmentProgramDetailBinding? = null
    private val binding get() = _binding!!
    private val args: ProgramDetailFragmentArgs by navArgs()
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProgramDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val program = viewModel.getProgram(args.item)
        program?.let { it ->
            with(binding) {
                Glide
                    .with(view)
                    .load(it.icon)
                    .override(Target.SIZE_ORIGINAL)
                    .into(icon)
                programName.text = it.name
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
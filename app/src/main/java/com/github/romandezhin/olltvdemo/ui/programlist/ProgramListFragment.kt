package com.github.romandezhin.olltvdemo.ui.programlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.romandezhin.olltvdemo.R
import com.github.romandezhin.olltvdemo.databinding.FragmentProgramListBinding
import com.github.romandezhin.olltvdemo.domain.exception.NetworkConnectionException
import com.github.romandezhin.olltvdemo.domain.model.Program

class ProgramListFragment : Fragment() {

    private var _binding: FragmentProgramListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProgramListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.programList

        val onClickListener = View.OnClickListener { itemView ->
            val position = itemView.tag as Int
            val action = ProgramListFragmentDirections.showProgramDetail(position)
            itemView.findNavController().navigate(action)
        }
        val adapter = SimpleItemRecyclerViewAdapter(
            mutableListOf(),
            onClickListener
        )
        recyclerView.adapter = adapter

        viewModel.getPrograms().observe(viewLifecycleOwner, { programs ->
            addPrograms(adapter, programs)
        })

        viewModel.error.observe(viewLifecycleOwner, { exception ->
            val errorMessage = when (exception) {
                is NetworkConnectionException -> getString(R.string.error_network_connection)
                else -> getString(R.string.error_server_unavailable)
            }
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        })
        viewModel.loading.observe(viewLifecycleOwner, { isLoading ->
            showLoading(isLoading)
        })
    }

    private fun addPrograms(adapter: SimpleItemRecyclerViewAdapter, list: List<Program>) {
        showLoading(false)
        adapter.addItems(list)
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.isEnabled = show
        binding.progressBar.isVisible = show
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
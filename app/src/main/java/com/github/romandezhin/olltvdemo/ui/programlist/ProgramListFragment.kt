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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.romandezhin.olltvdemo.R
import com.github.romandezhin.olltvdemo.databinding.FragmentProgramListBinding
import com.github.romandezhin.olltvdemo.domain.exception.NetworkConnectionException
import com.google.android.material.transition.MaterialFadeThrough

class ProgramListFragment : Fragment() {
    private var _binding: FragmentProgramListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exitTransition = MaterialFadeThrough()
        reenterTransition = MaterialFadeThrough()
    }

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
        val adapter = ProgramAdapter(
            mutableListOf(),
            onClickListener
        )
        recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        recyclerView.addOnScrollListener(object : TwoWayOnScrollListener(adapter, layoutManager) {
            override fun loadMore(borderId: Int, direction: Int) {
                viewModel.loadMorePrograms(borderId, direction)
            }
        })

        viewModel.getPrograms().observe(viewLifecycleOwner, { programs ->
            if (programs.isEmpty()) {
                Toast.makeText(context, getString(R.string.no_more_programs), Toast.LENGTH_SHORT)
                    .show()
            } else {
                adapter.addItems(programs)
            }

        })

        viewModel.error.observe(viewLifecycleOwner, { exception ->
            val errorMessage = when (exception) {
                is NetworkConnectionException -> getString(R.string.error_network_connection)
                else -> getString(R.string.error_server_unavailable) + exception.message
            }
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        })
        viewModel.loading.observe(viewLifecycleOwner, { isLoading ->
            binding.progressBar.isVisible = isLoading
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
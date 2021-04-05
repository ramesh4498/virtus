package com.virtus.demo.hits.ui.hitslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.virtus.demo.hits.R
import com.virtus.demo.hits.databinding.FragmentHitsListBinding
import com.virtus.demo.hits.utils.Resource
import com.virtus.demo.hits.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HitsListFragment : Fragment(), HitsListAdapter.HitsItemListener {

    private var binding: FragmentHitsListBinding by autoCleared()
    private val viewModel: HitsListViewModel by viewModels()
    private lateinit var adapter: HitsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHitsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = HitsListAdapter(this)
        binding.rvHits.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHits.adapter = adapter

        val layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,
            false
        ).apply {
            // specify the layout manager for recycler view
            binding.rvHits.layoutManager = this
        }

        // initialize an instance of divider item decoration
        DividerItemDecoration(
            requireContext(), // context
            layoutManager.orientation
        ).apply {
            // add divider item decoration to recycler view
            // this will show divider line between items
            binding.rvHits.addItemDecoration(this)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            setupObservers()
            adapter.notifyDataSetChanged()
        }

        val swipeHandler = object : SwipeToDeleteCallback(context) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                Thread {
//                    viewModel.deleteHitById(adapter.getHitsAt(viewHolder.layoutPosition))
//                }.start()
                val adapter = binding.rvHits.adapter as HitsListAdapter
                adapter.removeAt(viewHolder.layoutPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.rvHits)
    }

    private fun setupObservers() {

        viewModel.hits.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
            binding.swipeRefreshLayout.isRefreshing = false
        })
    }

    override fun onClickedHits(url: String?) {
        findNavController().navigate(
            R.id.action_hitsFragment_to_hitsDetailFragment,
            bundleOf("url" to url)
        )
    }

}

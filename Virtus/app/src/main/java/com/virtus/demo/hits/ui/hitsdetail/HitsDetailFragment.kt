package com.virtus.demo.hits.ui.hitsdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.virtus.demo.hits.databinding.FragmentHitsDetailBinding
import com.virtus.demo.hits.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_hits_detail.view.*

@AndroidEntryPoint
class HitsDetailFragment : Fragment() {

    private var binding: FragmentHitsDetailBinding by autoCleared()
    private val viewModel: HitsDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHitsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.getString("url")
        if(!url.isNullOrEmpty()){
            viewModel.showWebview(url, view.webview, view.progress_bar)
        }else {
            Toast.makeText(requireContext(),  "Url not found", Toast.LENGTH_SHORT).show()
        }
    }
}

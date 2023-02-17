package com.esaudev.ecommerceyt.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.text.input.ImeAction
import androidx.fragment.app.viewModels
import com.esaudev.ecommerceyt.R
import com.esaudev.ecommerceyt.databinding.FragmentSearchBinding
import com.esaudev.ecommerceyt.domain.model.RecentSearch
import com.esaudev.ecommerceyt.ui.utils.hideKeyboard
import com.esaudev.ecommerceyt.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val recentSearchAdapter: RecentSearchListAdapter = RecentSearchListAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUiComponents()
        setObservers()
        setClickListeners()

    }

    private fun setUiComponents() {
        binding.rvRecentSearch.apply {
            adapter = recentSearchAdapter
        }
    }

    private fun setObservers() {
        viewModel.recentSearch.observe(viewLifecycleOwner) {
            recentSearchAdapter.submitList(it)
        }
    }

    private fun setClickListeners() {
        recentSearchAdapter.setRecentSearchClickListener {
            Toast.makeText(requireContext(), it.query, Toast.LENGTH_SHORT).show()
        }

        binding.cSearchView.etSearch.setOnEditorActionListener { _, _, _ ->
            hideKeyboard()
            viewModel.saveRecentSearch(
                RecentSearch(query = binding.cSearchView.etSearch.text.toString())
            )
            true
        }
    }

}
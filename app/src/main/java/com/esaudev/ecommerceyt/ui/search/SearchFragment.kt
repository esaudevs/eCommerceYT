package com.esaudev.ecommerceyt.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.esaudev.ecommerceyt.databinding.FragmentSearchBinding
import com.esaudev.ecommerceyt.domain.model.RecentSearch
import com.esaudev.ecommerceyt.ui.utils.hideKeyboard
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
            val action = SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(nameQuery = it.query)
            findNavController().navigate(action)
        }

        binding.cSearchView.etSearch.setOnEditorActionListener { _, _, _ ->
            hideKeyboard()
            val nameQuery = binding.cSearchView.etSearch.text.toString()
            viewModel.saveRecentSearch(
                RecentSearch(query = nameQuery)
            )
            val action = SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(nameQuery = nameQuery)
            findNavController().navigate(action)
            true
        }
    }

}
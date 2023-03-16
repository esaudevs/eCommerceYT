package com.esaudev.ecommerceyt.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.esaudev.ecommerceyt.R
import com.esaudev.ecommerceyt.databinding.FragmentSearchResultBinding
import com.esaudev.ecommerceyt.domain.model.Product
import com.esaudev.ecommerceyt.utils.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchResultFragment : Fragment() {

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchResultViewModel by viewModels()
    private val args: SearchResultFragmentArgs by navArgs()
    private val productAdapter: ProductListAdapter = ProductListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUiComponents()
        setObservers()
        setClickListeners()
    }

    private fun setUiComponents() {
        binding.cSearchResultTopBar.tvQuery.text = args.nameQuery
        binding.rvSearchResult.apply {
            adapter = productAdapter
        }
    }

    private fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productsFlow.collect() { productsResult ->
                    when(productsResult) {
                        is UiState.Success -> {
                            handleLoading(isLoading = false)
                            handleSuccessSearch(products = productsResult.data)
                        }
                        is UiState.Error -> {
                            handleLoading(isLoading = false)
                            showEmptyScreen(shouldShow = true, message = getString(R.string.search_result__error_disclaimer))
                        }
                        is UiState.Loading -> {
                            handleLoading(isLoading = true)
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.cSearchResultTopBar.tvResultsQty.visibility = if (isLoading) View.GONE else View.VISIBLE

        binding.rvSearchResult.visibility = if (isLoading) View.GONE else View.VISIBLE

        binding.lsLoadingScreen.root.apply {
            visibility = if (isLoading) {
                startShimmer()
                View.VISIBLE
            } else {
                stopShimmer()
                View.GONE
            }
        }
    }

    private fun handleSuccessSearch(products: List<Product>) {

        if (products.isEmpty()) {
            showEmptyScreen(shouldShow = true)
        }

        binding.cSearchResultTopBar.tvResultsQty.text = resources.getQuantityString(R.plurals.search__quantity_results, products.size, products.size)

        productAdapter.submitList(products)
    }

    private fun showEmptyScreen(message: String? = null, shouldShow: Boolean) {
        binding.lsEmptyScreen.root.visibility = if (shouldShow) View.VISIBLE else View.GONE

        if (message != null) {
            binding.lsEmptyScreen.tvMessage.text = message
        }
    }

    private fun setClickListeners() {

        productAdapter.apply {
            setProductClickListener {
                Toast.makeText(requireContext(), it.name, Toast.LENGTH_SHORT).show()
            }

            setFavoriteClickListener {
                if (it.isFavorite) {
                    viewModel.deleteFavorite(it.id)
                } else {
                    viewModel.saveFavorite(it.id)
                }
            }
        }

        binding.cSearchResultTopBar.bSearch.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.cSearchResultTopBar.bBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }


}
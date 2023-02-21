package com.esaudev.ecommerceyt.ui.result

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.esaudev.ecommerceyt.databinding.FragmentSearchResultBinding
import com.esaudev.ecommerceyt.domain.model.Product
import com.esaudev.ecommerceyt.domain.model.mapToProductUiList
import com.esaudev.ecommerceyt.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

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
        viewModel.products.observe(viewLifecycleOwner) { productsResult ->
            when(productsResult) {
                is Resource.Success -> {
                    handleSuccessSearch(products = productsResult.data)
                }
                else -> Unit
            }
        }
    }

    private fun handleSuccessSearch(products: List<Product>) {
        val productsQtyFormatted = "${products.size} resultados"
        binding.cSearchResultTopBar.tvResultsQty.text = productsQtyFormatted

        productAdapter.submitList(products.mapToProductUiList())
    }

    private fun setClickListeners() {
        productAdapter.setProductClickListener {
            Toast.makeText(requireContext(), it.name, Toast.LENGTH_SHORT).show()
        }

        binding.cSearchResultTopBar.bSearch.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.cSearchResultTopBar.bBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }


}
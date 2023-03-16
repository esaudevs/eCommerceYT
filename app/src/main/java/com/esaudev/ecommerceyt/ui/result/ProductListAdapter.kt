package com.esaudev.ecommerceyt.ui.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.esaudev.ecommerceyt.R
import com.esaudev.ecommerceyt.databinding.ItemProductBinding
import com.esaudev.ecommerceyt.domain.model.Product
import com.esaudev.ecommerceyt.ui.utils.capitalizeWithLocal
import com.esaudev.ecommerceyt.ui.utils.load

class ProductListAdapter: ListAdapter<Product, ProductListAdapter.ProductViewHolder>(Companion) {

    companion object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    inner class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = currentList[position]

        val priceFormatted = holder.binding.root.context.getString(R.string.product__formatted_price, product.price)
        val brandFormatted = holder.binding.root.context.getString(R.string.product__formatted_brand, product.brand.capitalizeWithLocal())

        holder.binding.tvName.text = product.name
        holder.binding.tvPrice.text = priceFormatted
        holder.binding.tvBrand.text = brandFormatted

        holder.binding.ivImage.load(product.image)

        holder.binding.bFav.setImageResource(if (product.isFavorite) R.drawable.ic_fav else R.drawable.ic_fav_border)

        holder.binding.clProductParent.setOnClickListener {
            onProductClickListener?.let { click ->
                click(product)
            }
        }

        holder.binding.bFav.setOnClickListener {
            onFavoriteClickListener?.let { click ->
                click(product)
            }
        }
    }

    protected var onProductClickListener : ((Product) -> Unit)? = null
    protected var onFavoriteClickListener : ((Product) -> Unit)? = null

    fun setProductClickListener(listener: (Product) -> Unit){
        onProductClickListener = listener
    }

    fun setFavoriteClickListener(listener: (Product) -> Unit) {
        onFavoriteClickListener = listener
    }
}
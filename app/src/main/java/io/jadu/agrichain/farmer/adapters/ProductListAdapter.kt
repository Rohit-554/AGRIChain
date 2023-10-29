package io.jadu.agrichain.farmer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.jadu.agrichain.R
import io.jadu.agrichain.farmer.models.dtos.AddProduct
import io.jadu.agrichain.farmer.models.dtos.productDetails

class ProductListAdapter:RecyclerView.Adapter<ProductListAdapter.ProudctListViewHolder>() {
    private var count = 0
    var itemTypes: List<productDetails> = emptyList()
    class ProudctListViewHolder(itemview:View) :RecyclerView.ViewHolder(itemview) {
        val productImage: ImageView = itemView.findViewById(R.id.iv_vegetables)
        val productName: TextView = itemView.findViewById(R.id.cart_product_name)
        val productPrice: TextView = itemView.findViewById(R.id.cart_product_price)
        val productType: TextView = itemView.findViewById(R.id.cart_product_type)
        val cartItemCardView: CardView = itemView.findViewById(R.id.cart_item_card_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProudctListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_of_cart,parent,false)
        return ProudctListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemTypes.size
    }

    override fun onBindViewHolder(holder: ProudctListViewHolder, position: Int) {
        val currentItem = itemTypes[position]
        //remove the surrounding quotes
        holder.productName.text = currentItem.name.removeSurrounding("\"")
        holder.productPrice.text = "₹"+currentItem.price.toString()
        Glide.with(holder.itemView.context).load(currentItem.image_url).into(holder.productImage)
    }
}
package com.example.e_commerce_swipe

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide


class ProductAdapter(var con : Context, var list : List<ProductItem>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    inner class ViewHolder(itemview : View ) : RecyclerView.ViewHolder(itemview){
        val img = itemview.findViewById<ImageView>(R.id.productImage)
        val name = itemview.findViewById<TextView>(R.id.productNameTextView)
        val type = itemview.findViewById<TextView>(R.id.productTypeTextView)
        val price = itemview.findViewById<TextView>(R.id.priceTextView)
        val tax = itemview.findViewById<TextView>(R.id.taxTextView);

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       var view = LayoutInflater.from(con).inflate(R.layout.product_item, parent, false)
        return  ViewHolder(view);
    }

    override fun getItemCount(): Int {
        return list.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(con).load(list[position].image).into(holder.img)
        holder.name.text =  list[position].product_name
        holder.type.text = list[position].product_type
        holder.tax.text = "Tax : " + list[position].price.toString()+"$"
        holder.price.text = "Price : " + list[position].price.toString()+"$"
    }
}
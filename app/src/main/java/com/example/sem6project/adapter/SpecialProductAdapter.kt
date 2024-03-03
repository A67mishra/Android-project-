package com.example.sem6project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sem6project.data.Product
import com.example.sem6project.databinding.SpecialRvItemBinding


class SpecialProductAdapter: RecyclerView.Adapter<SpecialProductAdapter.SpecialProductViewHolder>() {

    inner class SpecialProductViewHolder(private val binding: SpecialRvItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product){
            binding.apply {
                Glide.with(itemView).load(product.images[0]).into(imageSpecialRvItem)
                tvSpecialProductName.text=product.name
                tvSpecialPrdouctPrice.text=product.price.toString()
            }
        }
    }

    //diffCallback is mechanism that help recyclerview to loa item easily without refreshing the page everytime
    private val diffCallback = object : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem==newItem
        }
    }

    //differ is responsible for updating and getting the list
    val differ= AsyncListDiffer(this,diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialProductViewHolder {
        return SpecialProductViewHolder(
            SpecialRvItemBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SpecialProductViewHolder, position: Int) {
        val product=differ.currentList[position]
        holder.bind(product)

        holder.itemView.setOnClickListener {
            onClick?.invoke(product)
        }
    }
    var onClick: ((Product) -> Unit)? = null
}
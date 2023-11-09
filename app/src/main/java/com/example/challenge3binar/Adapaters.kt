package com.example.challenge3binar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge3binar.databinding.ListMenuGridBinding
import com.example.challenge3binar.databinding.ListMenuLinearBinding
import com.example.challenge3binar.network.model.product.ProductItemResponse

class Adapaters(private val listMenu: ArrayList<ProductItemResponse>, val isGrid:Boolean)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var onItemClick : ((ProductItemResponse) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (isGrid) {
            val binding =
                ListMenuGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return GridMenuHolder(binding)
        } else {
            val binding =
                ListMenuLinearBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return LinearMenuHolder(binding)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isGrid) {
            val gridHolder = holder as GridMenuHolder
            gridHolder.onBind(listMenu[position])
        } else {
            val linearHolder = holder as LinearMenuHolder
            linearHolder.onBind(listMenu[position])
        }
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(listMenu[position])
        }

    }

    override fun getItemCount(): Int {
        return listMenu.size
    }
}

class GridMenuHolder(val binding: ListMenuGridBinding) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(data: ProductItemResponse) {
        Glide
            .with(itemView)
            .load(data.imageUrl)
            .centerCrop()
            .into(binding.ivGambar);
        binding.tvNamaMenu.text = data.nama
        binding.tvHargaMenu.text = "Rp. ${data.harga}"

    }
}

class LinearMenuHolder(val binding: ListMenuLinearBinding) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(data: ProductItemResponse) {
        Glide
            .with(itemView)
            .load(data.imageUrl)
            .centerCrop()
            .into(binding.ivGambar)
        binding.tvNamaMenu.text = data.nama
        binding.tvHargaMenu.text = "Rp. ${data.harga}"
    }
}


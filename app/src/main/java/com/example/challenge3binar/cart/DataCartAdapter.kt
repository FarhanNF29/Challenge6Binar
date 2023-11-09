package com.example.challenge3binar.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge3binar.database.CartDao
import com.example.challenge3binar.R

class DataCartAdapter(private val context: Context, private val dataCartDao: CartDao) : RecyclerView.Adapter<DataCartAdapter.DataCartViewHolder>() {
    private var dataCartList: List<DataCart> = emptyList()

    inner class DataCartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNameTextView: TextView = itemView.findViewById(R.id.tv_namaMenu)
        val itemImageView: ImageView = itemView.findViewById(R.id.iv_gambarMenu)
        val itemPriceTextView: TextView = itemView.findViewById(R.id.tv_hargaMenu)
        val itemQuantityTextView: TextView = itemView.findViewById(R.id.tv_jumlahMenu)
        val btnPlus: ImageView = itemView.findViewById(R.id.iv_add2)
        val btnMinus: ImageView = itemView.findViewById(R.id.iv_min)
        val btnDelete: ImageView = itemView.findViewById(R.id.iv_delete)
    }

    fun getDataCartList(): List<DataCart> {
        return dataCartList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataCartViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_keranjang, parent, false)
        return DataCartViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DataCartViewHolder, position: Int) {
        val currentDataCart = dataCartList[position]

        holder.itemNameTextView.text = currentDataCart.itemName
        Glide
            .with(holder.itemView)
            .load(currentDataCart.itemImage)
            .centerCrop()
            .into(holder.itemImageView);
        holder.itemQuantityTextView.text = "${currentDataCart.itemQuantity}"

        // Menghitung harga total dan mengupdate itemPriceTextView
        val totalPrice = currentDataCart.itemPrice?.times(currentDataCart.itemQuantity) ?: 0
        holder.itemPriceTextView.text = "Rp. ${totalPrice}"


        // Tombol untuk menambah quantity
        holder.btnPlus.setOnClickListener {
            currentDataCart.itemQuantity++
            updateDataCart(currentDataCart)
            notifyItemChanged(position)
        }

        // Tombol untuk mengurangi quantity
        holder.btnMinus.setOnClickListener {
            if (currentDataCart.itemQuantity > 1) { // Memeriksa bahwa itemQuantity lebih besar dari 1
                currentDataCart.itemQuantity--
                updateDataCart(currentDataCart)
                notifyItemChanged(position)
            }
        }


        // Tombol untuk menghapus item
        holder.btnDelete.setOnClickListener {
            deleteDataCart(currentDataCart)
            notifyItemRemoved(position)
        }


    }

    override fun getItemCount(): Int {
        return dataCartList.size
    }

    fun setDataCartList(dataCartList: List<DataCart>) {
        this.dataCartList = dataCartList
        notifyDataSetChanged()
    }

    private fun updateDataCart(dataCart: DataCart) {
        // Update dataCart ke dalam database
        dataCartDao.updateQuantityByItemId(dataCart.itemQuantity, dataCart.itemId)
    }

    private fun deleteDataCart(dataCart: DataCart) {
        // Hapus dataCart dari database
        dataCartDao.deleteByItemId(dataCart.itemId)
    }
}

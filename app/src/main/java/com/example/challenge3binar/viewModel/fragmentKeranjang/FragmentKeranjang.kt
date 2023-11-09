package com.example.challenge3binar.viewModel.fragmentKeranjang

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.challenge3binar.R
import com.example.challenge3binar.cart.DataCartAdapter
import com.example.challenge3binar.database.CartDao
import com.example.challenge3binar.database.DatabaseCart
import com.example.challenge3binar.databinding.FragmentKeranjangBinding


class FragmentKeranjang : Fragment() {

    private lateinit var dataCartAdapter: DataCartAdapter
    private lateinit var dataCartDao: CartDao
    lateinit var binding: FragmentKeranjangBinding
    private lateinit var viewModel: ViewModelKeranjang

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKeranjangBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        dataCartDao = DatabaseCart.getInstance(requireContext()).simpleChartDao

        dataCartAdapter = DataCartAdapter(requireContext(), dataCartDao)
        recyclerView.adapter = dataCartAdapter

        viewModel = ViewModelProvider(this).get(ViewModelKeranjang::class.java)
        viewModel.init(dataCartDao)

        viewModel.getAllItems().observe(viewLifecycleOwner, Observer { dataCartList ->
            dataCartAdapter.setDataCartList(dataCartList)

            var totalHarga = 0
            for (item in dataCartList) {
                val itemTotalPrice = item.itemPrice?.times(item.itemQuantity) ?: 0
                totalHarga += itemTotalPrice
            }

            val totalHargaTextView: TextView = view.findViewById(R.id.tv_totalHargaCart)
            totalHargaTextView.text = "Rp. $totalHarga"

            if (dataCartList.isNotEmpty()) {
                binding.btnPesanToKonfirmasi.setOnClickListener {
                    findNavController().navigate(R.id.action_fragmentKeranjang_to_fragmentKonfirmasi)
                }
            } else {
                binding.btnPesanToKonfirmasi.isEnabled = false
                binding.btnPesanToKonfirmasi.text = "Cart Is Empty"
            }
        })
    }

}
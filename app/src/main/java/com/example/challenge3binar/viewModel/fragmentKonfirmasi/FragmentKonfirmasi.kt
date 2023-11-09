package com.example.challenge3binar.viewModel.fragmentKonfirmasi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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
import com.example.challenge3binar.order.OrderDao

class FragmentKonfirmasi : Fragment() {

    private lateinit var viewModel: ViewModelKonfirmasi
    private lateinit var dataCartAdapter: DataCartAdapter
    private lateinit var dataCartDao: CartDao
    private lateinit var orderDao: OrderDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_konfirmasi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ViewModelKonfirmasi::class.java)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        dataCartDao = DatabaseCart.getInstance(requireContext()).simpleChartDao
        orderDao = DatabaseCart.getInstance(requireContext()).orderDao

        dataCartAdapter = DataCartAdapter(requireContext(), dataCartDao)
        recyclerView.adapter = dataCartAdapter

        // Inisialisasi database
        val database = DatabaseCart.getInstance(requireContext())
        val dataCartDao = database.simpleChartDao

        // Mengamati perubahan data dari database dan memperbarui adapter
        dataCartDao.getAllItem().observe(viewLifecycleOwner, Observer { dataCartList ->
            dataCartAdapter.setDataCartList(dataCartList)

            // Hitung total harga dari dataCartList
            viewModel.hitungTotalHarga(dataCartList)

            // Tampilkan total harga di TextView
            val totalHargaTextView: TextView = view.findViewById(R.id.tv_ringkasanPembayaran)
            viewModel.totalHarga.observe(viewLifecycleOwner, Observer { totalHarga ->
                totalHargaTextView.text = "Total Harga = Rp. $totalHarga"
            })

            val btnPesanBerhasil: Button = view.findViewById(R.id.btn_pesananBerhasil)

            // Tambahkan kondisi jika dataCartList tidak kosong
            if (dataCartList.isNotEmpty()) {
                // Tombol untuk berpindah ke FragmentHome
                btnPesanBerhasil.setOnClickListener {
                    viewModel.pesanBerhasil(dataCartAdapter, dataCartDao)
                }
            } else {
                // Jika dataCartList kosong, nonaktifkan tombol atau berikan pesan kepada pengguna
                btnPesanBerhasil.isEnabled = false
                btnPesanBerhasil.text = "Keranjang Kosong"
            }
        })

        // Mengobservasi event dari viewmodel
        viewModel.pesanBerhasilEvent.observe(viewLifecycleOwner, Observer {
            if (it) {
                // Tampilkan pesan "Pesanan Anda Berhasil"
                Toast.makeText(requireContext(), "Pesanan Anda Berhasil", Toast.LENGTH_SHORT).show()
                // Navigasi ke FragmentHome
                findNavController().navigate(R.id.action_fragmentKonfirmasi_to_fragmentHome)
            }
        })

        viewModel.errorEvent.observe(viewLifecycleOwner, Observer { errorMessage ->
            // Menghandle Error
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        })
    }
}


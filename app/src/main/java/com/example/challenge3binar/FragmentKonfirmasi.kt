package com.example.challenge3binar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.challenge3binar.network.model.order.OrderItemRequest
import com.example.challenge3binar.network.model.order.OrderRequest
import com.example.challenge3binar.network.service.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentKonfirmasi.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentKonfirmasi : Fragment() {
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
            var totalHarga = 0
            for (item in dataCartList) {
                val itemTotalPrice = item.itemPrice?.times(item.itemQuantity) ?: 0
                totalHarga += itemTotalPrice
            }

            // Tampilkan total harga di TextView
            val totalHargaTextView: TextView = view.findViewById(R.id.tv_ringkasanPembayaran)
            totalHargaTextView.text = "Total Harga = Rp. $totalHarga"
            val btnPesanBerhasil: Button = view.findViewById(R.id.btn_pesananBerhasil)

            // Tambahkan kondisi jika dataCartList tidak kosong
            if (dataCartList.isNotEmpty()) {
                // Tombol untuk berpindah ke FragmentHome
                btnPesanBerhasil.setOnClickListener {
                    val pesananList = dataCartAdapter.getDataCartList()
                    val orderItems = mutableListOf<OrderItemRequest>()
                    for (item in pesananList) {
                        val orderItem = OrderItemRequest(
                            catatan = item.itemName,
                            orderId = item.itemId,
                            qty = item.itemQuantity
                        )
                        orderItems.add(orderItem)
                    }
                    val orderRequest = OrderRequest(orders = orderItems)

                    val apiService = ApiClient.instance
                    GlobalScope.launch(Dispatchers.IO) {
                        try {
                            val response = apiService.createOrder(orderRequest)
                            if (response.code == 201 && response.status == true) {
                                withContext(Dispatchers.Main) {
                                    // Tampilkan pesan "Pesanan Anda Berhasil"
                                    Toast.makeText(requireContext(), "Pesanan Anda Berhasil", Toast.LENGTH_SHORT).show()
                                    // Hapus semua item di keranjang setelah pesanan berhasil
                                    dataCartDao.deleteAllItems()
                                    // Navigasi ke FragmentHome
                                    findNavController().navigate(R.id.action_fragmentKonfirmasi_to_fragmentHome)
                                }
                            } else {
                                // Menghandle error respone
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(requireContext(), "Gagal membuat pesanan", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            // Handle network or other exceptions
                            withContext(Dispatchers.Main) {
                                Toast.makeText(requireContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            } else {
                // Jika dataCartList kosong, nonaktifkan tombol atau berikan pesan kepada pengguna
                btnPesanBerhasil.isEnabled = false
                btnPesanBerhasil.text = "Keranjang Kosong"
            }
        })
    }
}

package com.example.challenge3binar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge3binar.databinding.FragmentHomeBinding
import com.example.challenge3binar.network.model.product.ProductItemResponse

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentHome.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentHome : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var sharedPreferences: SharedPreference
    private var isGrid = true
    private var listMenu: ArrayList<ProductItemResponse> = ArrayList()
    private val viewModel: ViewModelFragmentHome by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = SharedPreference(requireContext())

        binding.cardNasi.setOnClickListener{
            viewModel.getListProduct("burger")
        }

        binding.cardMie.setOnClickListener{
            viewModel.getListProduct("mie")
        }

        binding.cardMinuman.setOnClickListener{
            viewModel.getListProduct("minuman")
        }

        binding.cardSnack.setOnClickListener{
            viewModel.getListProduct("snack")
        }

//        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bot_nav)
//        if (bottomNav != null){
//            bottomNav.visibility = View.VISIBLE
//        }
        isGrid = sharedPreferences.isGrid

        val listDataMenu = arrayListOf(
            DataMenu(R.drawable.ayam_bakar, "Ayam Goreng", "Rp. 50.000", "Ayam Goreng khas gorengan dengan minyak bimoli.","Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345"),
            DataMenu(R.drawable.ayam_geprek, "Ayam Geprek", "Rp. 45.000", "Ayam Geprek khas geprekan dengan cabai merah.","Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345"),
            DataMenu(R.drawable.ayam_bakar_ori, "Ayam Bakar", "RP. 45.000", "Ayam Bakar khas bakaran dengan sambal bakar yang dicampur aduk.","Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345"),
            DataMenu(R.drawable.ayam_rica, "Ayam Rica-rica", "Rp. 55.000", "Ayam Rica-Rica khas sambal dengan kepedasan ekstra.","Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345")
        )
//        listMenu.clear()
//        listMenu.addAll(listDataMenu)

        Log.e("IS_GRID", isGrid.toString())
        Log.e("IS_GRID_SHAREDPREF", sharedPreferences.isGrid.toString())
        setupActionChangeLayout()

        viewModel.getListProduct()

        viewModel.listProduct.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                Log.i("Dataproduct", it.toString())
                setupRecyclerView(isGrid, it as ArrayList<ProductItemResponse>)
                listMenu.clear()
                listMenu.addAll(it)
            }
        }
    }

    fun setupRecyclerView(isGrid: Boolean, data: ArrayList<ProductItemResponse>) {
        val adapterMenu = Adapaters(data, isGrid)
        binding.recycleView.adapter = adapterMenu

        if (isGrid) {
            binding.recycleView.layoutManager = GridLayoutManager(context, 2)
        } else {
            binding.recycleView.layoutManager = LinearLayoutManager(context)
        }
        adapterMenu.onItemClick = {
            val nBundle = Bundle()
            nBundle.putParcelable("DataMenu", it)
            findNavController().navigate(R.id.action_fragmentHome_to_fragmentDetail, nBundle)
        }
    }

    fun setupActionChangeLayout() {
        //button diklik
        binding.imageView7.setOnClickListener {
            isGrid = !isGrid
            sharedPreferences.isGrid = isGrid
            setupRecyclerView(isGrid, listMenu)
            Log.e("IS_GRID", isGrid.toString())
            Log.e("IS_GRID_SHAREDPREF", sharedPreferences.isGrid.toString())
        }
    }

}
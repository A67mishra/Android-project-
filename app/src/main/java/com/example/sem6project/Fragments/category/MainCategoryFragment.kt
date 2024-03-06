package com.example.sem6project.Fragments.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.sem6project.R
import com.example.sem6project.adapter.BestDealsAdapter
import com.example.sem6project.adapter.BestProductsAdapter
import com.example.sem6project.adapter.SpecialProductAdapter
import com.example.sem6project.databinding.FragmentMainCategoryBinding
import com.example.sem6project.util.Resource
import com.example.sem6project.viewmodel.MainCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest


private val TAG="MainCategoryFragment"
@AndroidEntryPoint
class MainCategoryFragment:Fragment(R.layout.fragment_main_category) {

    private lateinit var binding: FragmentMainCategoryBinding
    private lateinit var specialProductAdapter: SpecialProductAdapter
    private lateinit var bestProductsAdapter: BestProductsAdapter
    private lateinit var bestDealsAdapter: BestDealsAdapter
    private val viewModel by viewModels<MainCategoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSpecialProductsRv()
        setupBestDealsRv()
        setupBestProductsRv()

        lifecycleScope.launchWhenStarted {
            viewModel.specialProduct.collectLatest {
                when(it){
                    is Resource.Loading->{
                        showLoading()
                    }
                    is Resource.Success->{
                        specialProductAdapter.differ.submitList(it.data)
                        hideLoading()
                    }
                    is Resource.Error->{
                        hideLoading()
                        Log.e(TAG,it.message.toString())
                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                    }
                    else-> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.bestdealProducts.collectLatest {
                when(it){
                    is Resource.Loading->{
                        showLoading()
                    }
                    is Resource.Success->{
                        bestDealsAdapter.differ.submitList(it.data)
                        hideLoading()
                    }
                    is Resource.Error->{
                        hideLoading()
                        Log.e(TAG,it.message.toString())
                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                    }
                    else-> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.bestProducts.collectLatest {
                when(it){
                    is Resource.Loading->{
                        showLoading()
                    }
                    is Resource.Success->{
                        bestProductsAdapter.differ.submitList(it.data)
                        hideLoading()
                    }
                    is Resource.Error->{
                        hideLoading()
                        Log.e(TAG,it.message.toString())
                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                    }
                    else-> Unit
                }
            }
        }

    }

    private fun setupBestDealsRv() {
        bestDealsAdapter= BestDealsAdapter()
        binding.rvBestDealsProducts.apply {
            layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter=bestDealsAdapter
        }
    }

    private fun setupBestProductsRv() {
        bestProductsAdapter= BestProductsAdapter()
        binding.rvBestProducts.apply {
            layoutManager=GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
            adapter=bestProductsAdapter
        }
    }

    private fun hideLoading() {
        binding.mainCategoryProgressbar.visibility=View.GONE
    }

    private fun showLoading() {
        binding.mainCategoryProgressbar.visibility=View.VISIBLE
    }

    private fun setupSpecialProductsRv() {
        specialProductAdapter= SpecialProductAdapter()
        binding.rvSpecialProducts.apply {
            layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter=specialProductAdapter
        }
    }
}
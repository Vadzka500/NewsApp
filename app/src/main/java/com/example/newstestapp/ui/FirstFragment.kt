package com.example.newstestapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newstestapp.databinding.FragmentFirstBinding
import com.example.newstestapp.utils.MyResponse
import com.example.newstestapp.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel : NewsViewModel by viewModels()

    @Inject
    lateinit var newsAdapter: AdapterNews

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("main", "start fragment")
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        setupViews()
        observeNewsData()
        viewModel.getAllNotes()
        //_binding!!.firstCard.shimmer.startShimmer()

       // _binding!!.root.startShimmer()
        return binding.root

    }

    private fun observeNewsData(){
        viewModel.newsData.observe(this@FirstFragment.viewLifecycleOwner) { it ->
            when (it.status) {
                MyResponse.Status.LOADING ->{
                    binding.loading.visibility = View.VISIBLE
                }

                MyResponse.Status.SUCCESS ->{
                    binding.loading.visibility = View.GONE
                    it?.data?.articles?.let {
                        newsAdapter.submitList(it)
                    }
                }

                MyResponse.Status.ERROR ->{
                    binding.loading.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupViews(){
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = newsAdapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       /* binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }*/


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
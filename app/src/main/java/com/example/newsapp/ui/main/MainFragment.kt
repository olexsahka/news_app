package com.example.newsapp.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentMainBinding
import com.example.newsapp.models.Article
import com.example.newsapp.ui.adapters.NewsAdapter
import com.example.newsapp.ui.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding : FragmentMainBinding? = null
    private val mBinding get() = _binding!!
    private val viewModel by viewModels<MainViewModel>()
    private var newsAdapter: NewsAdapter ?= null
    private var favNews: List<Article> ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(layoutInflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        newsAdapter?.setOnItemClickListener { article ->
            val bundle = bundleOf("article" to article)
            view.findNavController().navigate(R.id.action_mainFragment_to_detailFragment,bundle)
        }
        newsAdapter?.setOnIconClickListener{
            article ->

        }
        viewModel.newsLiveData.observe(viewLifecycleOwner) {
            res -> when (res){
                is Resource.Success ->{
                    progressBar.visibility = View.INVISIBLE
                    res.data?.let {
                        newsAdapter?.differ?.submitList(it.articles)
                    }
                }
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE

                }
                is Resource.Error -> {
                    progressBar.visibility = View.INVISIBLE
                    Log.d("MainFragment Error", res.message.toString())

                }
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun addDeleteArticle(article: Article) {

        viewModel.addFavouriteArticle(article)
        iconFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)

    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter()
        newsRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}
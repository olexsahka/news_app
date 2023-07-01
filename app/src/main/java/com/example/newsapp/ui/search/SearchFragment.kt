package com.example.newsapp.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentSearchBinding
import com.example.newsapp.models.Article
import com.example.newsapp.ui.adapters.NewsAdapter
import com.example.newsapp.ui.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding : FragmentSearchBinding? = null
    private val mBinding get() = _binding!!
    private val viewModel by viewModels<SerachViewModel>()
    private var newsAdapter: NewsAdapter?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(layoutInflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        newsAdapter?.setOnItemClickListener { article ->
            val bundle = bundleOf("article" to article)
            view.findNavController().navigate(R.id.action_searchFragment_to_detailFragment,bundle)
        }

        newsAdapter?.setOnIconClickListener { article ->
            viewModel.addFavouriteArticle(article as Article)
        }
        var job : Job? = null
        search_news_edit_text.addTextChangedListener{ text ->
            job?.cancel()
            job = MainScope().launch {
                delay(500)
                text.let {
                    if (it.toString().isNotEmpty()){
                        viewModel.getSearchNews(it.toString())
                    }
                }
            }
        }
        viewModel.searchLiveData.observe(viewLifecycleOwner) {
                res -> when (res){
            is Resource.Success ->{
                searchProgressBar.visibility = View.INVISIBLE
                res.data?.let {
                    newsAdapter?.differ?.submitList(it.articles)
                }
            }
            is Resource.Loading -> {
                searchProgressBar.visibility = View.VISIBLE

            }
            is Resource.Error -> {
                searchProgressBar.visibility = View.INVISIBLE
                Log.d("MainFragment Error", res.message.toString())

            }
        }
        }
    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter()
        searhRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}
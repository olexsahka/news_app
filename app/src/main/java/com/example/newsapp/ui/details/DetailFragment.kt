package com.example.newsapp.ui.details
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding : FragmentDetailBinding? = null
    private val mBinding get() = _binding!!
    private val bundleArticle: DetailFragmentArgs by navArgs()
    private val viewModel by viewModels<DetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(layoutInflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = bundleArticle.article
        article.let { artDetail ->
            articleDetailTittle.text = artDetail.title
            articleDetailTittleDescr.text = artDetail.publishedAt
            articleDetailDescription.text= artDetail.description
            mBinding.articleDetailBtn.setOnClickListener{
                try {
                    Intent()
                        .setAction(Intent.ACTION_VIEW)
                        .addCategory(Intent.CATEGORY_BROWSABLE)
                        .setData(Uri.parse(takeIf  { URLUtil.isValidUrl(article.url)}
                            .let {
                                artDetail.url
                            } ?: "https://www.google.com"))
                        .let {
                            ContextCompat.startActivity(requireContext(),it,null)
                            }
                    artDetail.url
                }
                catch (e: Exception){
                    Log.d("test", "open ERror$e")
                }
            }
            mBinding.iconFavorite.setOnClickListener{
                viewModel.addFavouriteArticle(artDetail)
                Log.d("test", "open ERror")

            }

        }
        mBinding.iconBack.setOnClickListener {
            view.findNavController().navigateUp();
        }
    }
}
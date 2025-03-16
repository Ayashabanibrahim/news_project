package com.example.newsapplication.uiModel

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.Toast
import com.example.newsapplication.R
import com.example.newsapplication.databinding.FragmentWepViewBinding


class WepViewFragment : Fragment() {
    private lateinit var binding:FragmentWepViewBinding
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentWepViewBinding.inflate(inflater,container,false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.show()
        val wepView=binding.wepView
        val url:String?=arguments?.getString("url")
        wepView.loadUrl(url!!)
        wepView.apply {
            settings.javaScriptEnabled=true
            webViewClient=WebViewClient()
            loadUrl(url)
        }

        binding.progressBar.hide()
        wepView.setOnKeyListener { view, keyCode, event ->
            if(keyCode===KeyEvent.KEYCODE_BREAK&&event.action==KeyEvent.ACTION_UP){
                if(wepView.canGoBack()) {
                    wepView.goBack()
                    true
                }else{
                    requireActivity().onBackPressed()
                    true
                }
            }
            else{
                false
            }
        }
        shareUel(wepView)

    }

    private fun shareUel(wepView: WebView) {
        val activity=requireActivity()
        val share=activity.findViewById <ImageView>(R.id.share)
        val search=activity.findViewById <ImageView>(R.id.search)
        share.visibility=View.VISIBLE
        search.visibility=View.GONE
        share.setOnClickListener {
            if(wepView.url.isNullOrEmpty()){
                Toast.makeText(requireActivity(),"No URL to share!",Toast.LENGTH_SHORT).show()
            }else {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "Check this out :${wepView.url}")
                }
                startActivity(Intent.createChooser(intent, "Share via"))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val activity=requireActivity()
        val share=activity.findViewById <ImageView>(R.id.share)
        val search=activity.findViewById <ImageView>(R.id.search)
        share.visibility=View.GONE
       search.visibility=View.VISIBLE
    }


}
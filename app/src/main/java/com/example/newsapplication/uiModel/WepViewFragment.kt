package com.example.newsapplication.uiModel

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.addCallback
import com.example.newsapplication.R


class WepViewFragment : Fragment() {


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_wep_view, container, false)
        val wepView=view.findViewById<WebView>(R.id.wepView)
        wepView.settings.javaScriptEnabled=true
        wepView.settings.domStorageEnabled=true
        wepView.webViewClient= WebViewClient()
        val url:String?=arguments?.getString("url")
        wepView.loadUrl(url!!)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            if (wepView.canGoBack()){
                wepView.goBack()
            }else{
//                isEnabled=false
//                requireActivity().onBackPressed()
                parentFragmentManager.popBackStack()
            }
        }
        return  view
    }
/*
    companion object{
        fun newInstance(url:String):WepViewFragment{
            val fragment=WepViewFragment()
            val args=Bundle()
            args.putString("url",url)
            fragment.arguments=args
            return fragment

        }
    }

 */




}
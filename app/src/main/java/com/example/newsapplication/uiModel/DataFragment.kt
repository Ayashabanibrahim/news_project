package com.example.newsapplication.uiModel

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.R
import com.example.newsapplication.apiModel.RetrofitInstance.Companion.apiInterface
import com.example.newsapplication.adapterModel.Adapter
import com.example.newsapplication.dataModel.Article
import com.example.newsapplication.dataModel.NewsResponse


class DataFragment( ) : Fragment()  {
  private lateinit var recycler:RecyclerView
   private lateinit var adapter: Adapter
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view= inflater.inflate(R.layout.fragment_data, container, false)
        recycler=view.findViewById(R.id.recycler)
        adapter= Adapter(requireContext())
        recycler.layoutManager=LinearLayoutManager(context)
        recycler.adapter= this.adapter

        val sourceId=arguments?.getString("sourceId")?:""
        val category=arguments?.getString("category")?:""
        var articles:MutableList<Article> = mutableListOf()
          Log.d("log3","source id: $sourceId")
         apiInterface.getArticles(sourceId, category).enqueue(object :retrofit2.Callback<NewsResponse>{
            override fun onResponse(
                call: retrofit2.Call<NewsResponse>,
                response: retrofit2.Response<NewsResponse>
            ) {
             if(response.isSuccessful) {
                 articles.clear()
                 response.body()?.articles?.let { articles.addAll(it) }
                 adapter.submitList(articles)
             }
                else{
                 Log.d("log2","error:${response.errorBody()?.string()}")
             }
            }

            override fun onFailure(call: retrofit2.Call<NewsResponse>, t: Throwable) {
                Log.d("log2","source error")
            }


        })


      //  adapter.submitList(articles)
            return view
    }


    companion object {
        fun newInstance(sourceId: String, category: String): DataFragment {
         val fragment= DataFragment()
           val args=Bundle()
           args.putString("sourceId",sourceId)
            args.putString("category",category)
           fragment.arguments=args
            return  fragment
        }
    }




}
package com.example.newsapplication.uiModel

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.R
import com.example.newsapplication.adapterModel.Adapter
import com.example.newsapplication.apiModel.RetrofitInstance.Companion.apiInterface
import com.example.newsapplication.dataModel.Article
import com.example.newsapplication.dataModel.NewsResponse
import retrofit2.Call
import retrofit2.Response


class SearchFragment : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_search, container, false)
      val recycler=view.findViewById<RecyclerView>(R.id.recycler)
       val adapter= Adapter(requireContext())
        recycler.layoutManager= LinearLayoutManager(context)
        recycler.adapter=adapter
        val searchView=view.findViewById<SearchView>(R.id.search_view)
        var articles:MutableList<Article> = mutableListOf()
        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query!=null) {
                    apiInterface.getResultsOfSearch(query!!)
                        .enqueue(object : retrofit2.Callback<NewsResponse> {
                            override fun onResponse(
                                call: Call<NewsResponse>,
                                response: Response<NewsResponse>
                            ) {
                                if (response.isSuccessful) {
                                    articles = response.body()?.articles as MutableList<Article>
                                    adapter.submitList(articles)
                                } else {
                                    Log.d("log2", "error:${response.errorBody()?.string()}")
                                }
                            }

                            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                                Log.d("log2", "source error")
                            }

                        })
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
              return false
            }
        })



        return view
    }


}
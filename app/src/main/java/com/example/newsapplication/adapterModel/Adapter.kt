package com.example.newsapplication.adapterModel

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapplication.R
import com.example.newsapplication.apiModel.Constants.Companion.SIZE_KEY
import com.example.newsapplication.dataModel.Article
import com.example.newsapplication.uiModel.NewsFragment
import com.example.newsapplication.viewModel.NewsViewModel

class Adapter(
    val sharedPreferences: SharedPreferences,
    val newsViewModel: NewsViewModel,
    val sourceId:String
):RecyclerView.Adapter<Adapter.ViewHolder>() {
    private   val TAG = "Adapter"
    var newsList:List<Article> = listOf()
    class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        val itemLayout=view.findViewById<LinearLayout>(R.id.item_layout)
       val tv_news=view.findViewById<TextView>(R.id.tv_text)
       val description=view.findViewById<TextView>(R.id.description)
       val time=view.findViewById<TextView>(R.id.time)
        val img=view.findViewById<ImageView>(R.id.news_img)


        fun bind(new: Article,size:Float){
            tv_news.text= new.source.id
            tv_news.textSize=size-10
            description.text=new.description
            description.textSize=size
            time.text=new.publishedAt
            time.textSize=size-10
            //load image using glide
            Glide.with(itemView.context).load(new.urlToImage).into(img)
           itemView.setOnClickListener {
              
           }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         val new=newsList[position]
        val size=sharedPreferences.getFloat(SIZE_KEY,25f)
        holder.bind(new,size)
        holder.itemLayout.setOnClickListener {
            newsViewModel.selectedPositionStates[sourceId]=position
            Log.d(TAG, "onBindViewHolderMap: ${newsViewModel.selectedPositionStates}")
            val args=Bundle()
            args.putString("url",new.url)
           val navController=holder.itemView.findNavController()
            navController.navigate(R.id.wepViewFragment,args)
        }



    }

    override fun getItemCount(): Int {
      return newsList.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Article>){
        newsList= emptyList()
        newsList=list
        notifyDataSetChanged()
    }

}
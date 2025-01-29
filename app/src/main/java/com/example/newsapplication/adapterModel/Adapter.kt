package com.example.newsapplication.adapterModel

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapplication.R
import com.example.newsapplication.uiModel.WepViewFragment
import com.example.newsapplication.dataModel.Article

class Adapter(private val context:Context):RecyclerView.Adapter<Adapter.ViewHolder>() {
    var newsList:List<Article> = listOf()
    val fragmentManager=(context as? AppCompatActivity)?.supportFragmentManager

    class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        val itemLayout=view.findViewById<LinearLayout>(R.id.item_layout)
       val tv_news=view.findViewById<TextView>(R.id.tv_text)
       val description=view.findViewById<TextView>(R.id.description)
       val time=view.findViewById<TextView>(R.id.time)
        val img=view.findViewById<ImageView>(R.id.news_img)
       val isFavoraite=view.findViewById<CheckBox>(R.id.favourite)

        fun bind(new: Article){
            tv_news.text= new.source.id
            description.text=new.description
            time.text=new.publishedAt
            //load image using glide
            Glide.with(itemView.context).load(new.urlToImage).into(img)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         val new=newsList[position]
        holder.bind(new)
        holder.itemLayout.setOnClickListener {
            val fragment= WepViewFragment()
            val args=Bundle()
            args.putString("url",new.url)
            fragment.arguments=args
            fragmentManager?.beginTransaction()?.replace(R.id.frame_layout,fragment)?.addToBackStack(null)?.commit()
        }


    }

    override fun getItemCount(): Int {
      return newsList.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Article>){
        newsList=list
        notifyDataSetChanged()
    }

}
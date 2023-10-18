package com.example.newstestapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newstestapp.databinding.CardNewsBinding
import com.example.newstestapp.models.Article
import com.example.newstestapp.models.NewsResponse
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class AdapterNews @Inject constructor(@ActivityContext private  val context: Context) :
    RecyclerView.Adapter<AdapterNews.MyViewHolder>() {

    private lateinit var binding: CardNewsBinding

    private var newsList = emptyList<Article>()


    inner  class MyViewHolder : RecyclerView.ViewHolder(binding.root)
    {
     fun setData(data: Article){
         binding.apply {
             titleNew.text = data.title
             textNew.text = data.description
             Glide.with(context)
                 .load(data.urlToImage)
                 .into(imageNew)

         }
     }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = CardNewsBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder()
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setData(newsList[position])
    }

    fun submitList(data : List<Article>){
        val newDiffUtils = NewsDiffUtils(newsList, data)
        val diffutils = DiffUtil.calculateDiff(newDiffUtils)
        newsList = data
        diffutils.dispatchUpdatesTo(this)
    }

    class NewsDiffUtils(
       private val oldItem : List<Article>,
        private val newItem : List<Article>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return  oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }
    }


}
package com.firstapp.miniprojectandroid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firstapp.miniprojectandroid.databinding.RowItemsBinding

class MyAdapter(var userList: List<MyDataItem>): RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(val  binding: RowItemsBinding): RecyclerView.ViewHolder(binding.root) {
//        lateinit var userId: TextView
    }

//    init {
//        userId = itemView.userId
//   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       // var itemView = LayoutInflater.from(context).inflate(R.layout.row_items,parent,false)
        return ViewHolder(RowItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val user = userList[position]
            title.text = user.title
            //userId.text = user.id
        }
    // holder.userId.text = userList[position].userId.toString()
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}
package com.example.listmaker.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listmaker.databinding.ListSelectionViewHolderBinding
import com.example.listmaker.models.TaskList

class ListSelectioRecyclerViewAdapter(private val lists: MutableList<TaskList>,val clickListener: ListSelectionRecyclerViewClickListerner ) : RecyclerView.Adapter<ListSelectionViewHolder>() {
    interface  ListSelectionRecyclerViewClickListerner {
        fun listItemClicked(list:TaskList)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSelectionViewHolder {
        val binding = ListSelectionViewHolderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListSelectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListSelectionViewHolder, position: Int) {
        holder.binding.itemnumber.text = (position+1).toString()
        holder.binding.itemname.text = lists[position].name
        holder.itemView.setOnClickListener{
            clickListener.listItemClicked(lists[position])
        }
    }

    override fun getItemCount(): Int {
        return lists.size
    }
    fun listsUpdated(){
        notifyItemInserted(lists.size -1)
    }



}

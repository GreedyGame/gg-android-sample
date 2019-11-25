package com.greedygames.sample.sdk8.showcase.nongames.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greedygames.sample.sdk8.R
import com.greedygames.sample.sdk8.showcase.nongames.ItemTypes
import kotlinx.android.synthetic.main.todo_list_item.view.*

class TodoListAdapter : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {

    private var data: MutableList<TodoItem> = mutableListOf(
        TodoItem(ItemTypes.CONTENT,"Buy Milk"),
        TodoItem(ItemTypes.CONTENT,"Buy Coffee"),
        TodoItem(ItemTypes.CONTENT,"Buy Shirt")
    )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.todo_list_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
            holder.bind(data[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: TodoItem) = with(itemView) {
            checklistItem.text = item.value
            checklistItem.isChecked = item.isDone
            if(item.isDone){
                checklistItem.setCheckMarkDrawable(R.drawable.ic_check_circle_black_24dp)
            }
            else{
                checklistItem.checkMarkDrawable = null
            }
            setOnClickListener {
            checklistItem.toggle()
                item.isDone = !item.isDone
                checklistItem.isChecked = item.isDone
                if(checklistItem.isChecked){
                    checklistItem.setCheckMarkDrawable(R.drawable.ic_check_circle_black_24dp)
                    val oldIndex = data.indexOf(item)
                    data.removeAt(oldIndex)
                    data.add(item)
                    val newIndex = data.size-1
                    notifyItemMoved(oldIndex,newIndex)
                }
                else{
                    checklistItem.checkMarkDrawable = null
                    val oldIndex = data.indexOf(item)
                    data.removeAt(oldIndex)
                    data.add(0,item)
                    val newIndex = 0
                    notifyItemMoved(oldIndex,newIndex)
                }
            }
        }
    }
}


data class TodoItem(val itemTypes: ItemTypes,var value:String,var isDone:Boolean = false)
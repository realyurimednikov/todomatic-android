package me.mednikov.todomatic.fragments.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import me.mednikov.todomatic.R
import me.mednikov.todomatic.data.models.Priority
import me.mednikov.todomatic.data.models.TodoEntity

class ListAdapter: RecyclerView.Adapter<ListAdapter.TodoItemViewHolder> (){

    private var items = emptyList<TodoEntity>()

    class TodoItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return TodoItemViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        val title = holder.itemView.findViewById<TextView>(R.id.row_title)
        val description = holder.itemView.findViewById<TextView>(R.id.row_description)
        val priority = holder.itemView.findViewById<CardView>(R.id.priority_indicator)

        title.text = items[position].title
        description.text = items[position].description

        when(items[position].priority){
            Priority.HIGH -> {priority.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.priority_high))}
            Priority.MEDIUM -> {priority.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.priority_medium))}
            Priority.LOW -> {priority.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.priority_low))}
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData (data: List<TodoEntity>){
        this.items = data
        notifyDataSetChanged()
    }
}
package me.mednikov.todomatic.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.mednikov.todomatic.data.models.TodoEntity
import me.mednikov.todomatic.databinding.RowItemBinding

class ListAdapter: RecyclerView.Adapter<ListAdapter.TodoItemViewHolder> (){

    private var items = emptyList<TodoEntity>()

    class TodoItemViewHolder(private val binding:RowItemBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(todoEntity: TodoEntity) {
            binding.todoItem = todoEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TodoItemViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = RowItemBinding.inflate(inflater, parent, false)
                return TodoItemViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
         return TodoItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData (data: List<TodoEntity>){
        this.items = data
        notifyDataSetChanged()
    }
}
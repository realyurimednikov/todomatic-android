package me.mednikov.todomatic.fragments.list

import androidx.recyclerview.widget.DiffUtil
import me.mednikov.todomatic.data.models.TodoEntity

class TodoDiffUtil(val oldList: List<TodoEntity>, val newList: List<TodoEntity>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id &&
                oldItem.title == newItem.title &&
                oldItem.description == newItem.description &&
                oldItem.priority == newItem.priority
    }
}
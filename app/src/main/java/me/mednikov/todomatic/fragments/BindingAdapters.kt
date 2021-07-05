package me.mednikov.todomatic.fragments

import android.view.View
import android.widget.Spinner
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import me.mednikov.todomatic.R
import me.mednikov.todomatic.data.models.Priority
import me.mednikov.todomatic.data.models.TodoEntity
import me.mednikov.todomatic.fragments.list.ListFragmentDirections

class BindingAdapters {

    companion object {

        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, value: Boolean) {
            view.setOnClickListener {
                if (value) {
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, data: MutableLiveData<Boolean>) {
            if (data.value == true) {
                view.visibility = View.VISIBLE
            } else {
                view.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("android:parsePriorityToInt")
        @JvmStatic
        fun parsePriorityToInt(view: Spinner, priority: Priority) {
            when (priority) {
                Priority.HIGH -> {view.setSelection(0)}
                Priority.MEDIUM -> {view.setSelection(1)}
                Priority.LOW -> {view.setSelection(2)}
            }
        }

        @BindingAdapter("android:setPriorityCardColor")
        @JvmStatic
        fun setPriorityCardColor(view: CardView, priority: Priority){
            when (priority) {
                Priority.HIGH -> {view.setCardBackgroundColor(view.context.getColor(R.color.priority_high))}
                Priority.MEDIUM -> {view.setCardBackgroundColor(view.context.getColor(R.color.priority_medium))}
                Priority.LOW -> {view.setCardBackgroundColor(view.context.getColor(R.color.priority_low))}
            }
        }

        @BindingAdapter("android:sendDataToUpdateFragment")
        @JvmStatic
        fun sendDataToUpdateFragment(view: ConstraintLayout, data: TodoEntity){
            view.setOnClickListener{
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(data)
                view.findNavController().navigate(action)
            }
        }

        @BindingAdapter("android:displayTasksCounter")
        @JvmStatic
        fun displayTasksCounter(view: TextView, count: Int){
            val text = "You have ${count} tasks"
            view.text = text
        }
    }
}
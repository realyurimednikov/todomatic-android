package me.mednikov.todomatic.fragments.update

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import me.mednikov.todomatic.R
import me.mednikov.todomatic.data.models.Priority
import me.mednikov.todomatic.data.models.TodoEntity
import me.mednikov.todomatic.viewmodels.SharedViewModel
import me.mednikov.todomatic.viewmodels.TodoViewModel

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mTodoViewModel: TodoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        val title = view.findViewById<EditText>(R.id.update_et_title)
        val description = view.findViewById<EditText>(R.id.update_et_description)
        val spinner = view.findViewById<Spinner>(R.id.update_sp_priority)

        title.setText(args.todoItem.title)
        description.setText(args.todoItem.description)
        val priorityVal = mSharedViewModel.parsePriorityToInt(args.todoItem.priority)
        spinner.setSelection(priorityVal)

        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_update){
            update()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun update() {
        val titleView = view?.findViewById<EditText>(R.id.update_et_title)
        val descriptionView = view?.findViewById<EditText>(R.id.update_et_description)
        val priorityView = view?.findViewById<Spinner>(R.id.update_sp_priority)

        val title = titleView?.text.toString()
        val description = descriptionView?.text.toString()
        val priority = mSharedViewModel.parsePriority(priorityView?.selectedItem.toString())
        val validate = mSharedViewModel.validate(title, description)

        if (validate) {
            val updated = TodoEntity(
                id = args.todoItem.id,
                title = title,
                priority = priority,
                description = description
            )

            mTodoViewModel.update(updated)

            Toast.makeText(context, "Item was updated", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(context, "Please check all fields", Toast.LENGTH_SHORT).show()
        }
    }

}
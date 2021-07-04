package me.mednikov.todomatic.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.mednikov.todomatic.R
import me.mednikov.todomatic.data.models.TodoEntity
import me.mednikov.todomatic.viewmodels.SharedViewModel
import me.mednikov.todomatic.viewmodels.TodoViewModel


@AndroidEntryPoint
class AddFragment : Fragment() {

    private val mTodoViewModel: TodoViewModel by hiltNavGraphViewModels(R.id.app_nav)
    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            insertTodoToDatabase()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertTodoToDatabase() {
        val title = view?.findViewById<EditText>(R.id.et_title)?.text.toString()
        val description = view?.findViewById<EditText>(R.id.et_description)?.text.toString()
        val priority = view?.findViewById<Spinner>(R.id.sp_priority)?.selectedItem.toString()

        val validation = mSharedViewModel.validate(title, description)
        if (validation) {
            val entity = TodoEntity(
                id = 0,
                title = title,
                priority = mSharedViewModel.parsePriority(priority),
                description = description
            )

            mTodoViewModel.insert(entity)

            Toast.makeText(requireContext(), "Task was added", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "You missed some fields!", Toast.LENGTH_SHORT).show()
        }

    }



}
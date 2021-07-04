package me.mednikov.todomatic.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import me.mednikov.todomatic.R
import me.mednikov.todomatic.data.models.Priority
import me.mednikov.todomatic.data.models.TodoEntity
import me.mednikov.todomatic.databinding.FragmentUpdateBinding
import me.mednikov.todomatic.viewmodels.SharedViewModel
import me.mednikov.todomatic.viewmodels.TodoViewModel

@AndroidEntryPoint
class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mTodoViewModel: TodoViewModel by hiltNavGraphViewModels(R.id.app_nav)

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding.currentItem = args.todoItem

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_update -> update()
            R.id.menu_remove -> delete()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun delete() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_ ->
            mTodoViewModel.delete(args.todoItem)
            Toast.makeText(context, "Item was removed", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){_,_->}
        builder.setTitle("Delete?")
        builder.setMessage("Do you want to delete ${args.todoItem.title}?")
        builder.create().show()
    }

    private fun update() {
        val title = binding.updateEtTitle.text.toString()
        val description = binding.updateEtDescription.text.toString()
        val priority = mSharedViewModel.parsePriority(binding.updateSpPriority.selectedItem.toString())

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
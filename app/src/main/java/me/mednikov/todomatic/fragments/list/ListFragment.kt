package me.mednikov.todomatic.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import me.mednikov.todomatic.R
import me.mednikov.todomatic.databinding.FragmentListBinding
import me.mednikov.todomatic.viewmodels.SharedViewModel
import me.mednikov.todomatic.viewmodels.TodoViewModel


class ListFragment : Fragment() {

    val adapter: ListAdapter by lazy { ListAdapter() }
    val mTodoViewModel : TodoViewModel by viewModels()
    val mSharedViewModel: SharedViewModel by viewModels()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mSharedViewModel = mSharedViewModel

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        mTodoViewModel.getAll.observe(viewLifecycleOwner, Observer { data ->
            adapter.setData(data)
            mSharedViewModel.checkDataEmpty(data)
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_clear_all) {
            deleteAll()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAll() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_ ->
            mTodoViewModel.deleteAll()
            Toast.makeText(context, "Everything was removed", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){_,_->}
        builder.setTitle("Delete?")
        builder.setMessage("Do you want to delete everything")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
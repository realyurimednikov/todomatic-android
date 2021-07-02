package me.mednikov.todomatic.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import me.mednikov.todomatic.R
import me.mednikov.todomatic.viewmodels.SharedViewModel
import me.mednikov.todomatic.viewmodels.TodoViewModel


class ListFragment : Fragment() {

    val adapter: ListAdapter by lazy { ListAdapter() }
    val mTodoViewModel : TodoViewModel by viewModels()
    val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        
        val fab = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val noDataImage = view.findViewById<ImageView>(R.id.noDataImageView)
        val noDataText = view.findViewById<TextView>(R.id.noDataTextView)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        mTodoViewModel.getAll.observe(viewLifecycleOwner, Observer { data ->
            adapter.setData(data)
            mSharedViewModel.checkDataEmpty(data)
        })

        mSharedViewModel.emptyData.observe(viewLifecycleOwner, Observer {value ->
            if (value == true){
                noDataImage.visibility = View.VISIBLE
                noDataText.visibility = View.VISIBLE
            } else {
                noDataImage.visibility = View.INVISIBLE
                noDataText.visibility = View.INVISIBLE
            }
        })

        fab.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        setHasOptionsMenu(true)

        return view
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
}
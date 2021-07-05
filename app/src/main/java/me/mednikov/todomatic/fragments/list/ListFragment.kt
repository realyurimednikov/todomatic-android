package me.mednikov.todomatic.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import me.mednikov.todomatic.R
import me.mednikov.todomatic.data.models.TodoEntity
import me.mednikov.todomatic.databinding.FragmentListBinding
import me.mednikov.todomatic.viewmodels.SharedViewModel
import me.mednikov.todomatic.viewmodels.TodoViewModel

@AndroidEntryPoint
class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    val adapter: ListAdapter by lazy { ListAdapter() }
    val mTodoViewModel: TodoViewModel by hiltNavGraphViewModels(R.id.app_nav)
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
        val swipeToDeleteCallback = object : SwipeToDeleteCallback(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.items[viewHolder.adapterPosition]
                mTodoViewModel.delete(deletedItem)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                restoreDeletedItem(viewHolder.itemView, deletedItem, viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        mTodoViewModel.getAll().observe(viewLifecycleOwner, Observer { data ->
            adapter.setData(data)
//            mSharedViewModel.checkDataEmpty(data)
            mSharedViewModel.setTasks(data)
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
        val searchMenu = menu.findItem(R.id.menu_search)
        val searchView = searchMenu.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
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

    private fun restoreDeletedItem(view: View, item: TodoEntity, position: Int){
        val snackbar = Snackbar.make(view, "${item.title} was removed", Snackbar.LENGTH_LONG)
        snackbar.setAction("Restore"){
            mTodoViewModel.insert(item)
            adapter.notifyItemChanged(position)
        }
        snackbar.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query!=null){
            searchInDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query!=null){
            searchInDatabase(query)
        }
        return true
    }

    private fun searchInDatabase(query: String){
        val searchQuery = "%$query%"
        mTodoViewModel.search(searchQuery).observe(this, Observer { data->
            data?.let {list ->
                adapter.setData(list)
            }
        })
    }
}
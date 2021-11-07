package com.example.todoapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.TodoApplication
import com.example.todoapp.databinding.FragmentTodoBinding
import com.example.todoapp.databinding.FragmentTodoEmptyBinding
import com.example.todoapp.model.TodoListModel

class TodoFragment : Fragment() {
    private var _fragmentTodoBinding: FragmentTodoBinding? = null
    private val fragmentTodoBinding get() = _fragmentTodoBinding!!

    private var _fragmentEmptyTodoBinding: FragmentTodoEmptyBinding? = null
    private val fragmentEmptyTodoBinding get() = _fragmentEmptyTodoBinding!!



    private val viewListModel: TodoListModel by activityViewModels {
        TodoListModel.TodoListViewModelFactory(
            (activity?.application as TodoApplication).database.todoDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentEmptyTodoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_todo_empty, container, false)
        _fragmentTodoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_todo, container, false)

        return fragmentTodoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewListModel.allTodoData.observe(this.viewLifecycleOwner) { todoList ->
            if (todoList.isNotEmpty()) {
                setExistTodoVisible(fragmentTodoBinding)
                todoList.let {
                    viewListModel.updateTodoList(it)
                }
            }
            else setEmptyTodoVisible(fragmentTodoBinding)

            fragmentTodoBinding.todoRecyclerView.layoutManager = LinearLayoutManager(this.context)
            fragmentTodoBinding.todoRecyclerView.adapter = viewListModel.getCurrentAdapter()
        }

        fragmentTodoBinding.apply {
            todoEmptyFragment = this@TodoFragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentTodoBinding = null
    }

    fun setExistTodoVisible(binding: FragmentTodoBinding) {
        binding.todoRecyclerView.visibility = View.VISIBLE
        binding.button.visibility = View.VISIBLE
        binding.incFragmentEmpty.emptyLayout.visibility = View.GONE
    }

    fun setEmptyTodoVisible(binding: FragmentTodoBinding) {
        binding.todoRecyclerView.visibility = View.GONE
        binding.button.visibility = View.GONE
        binding.incFragmentEmpty.emptyLayout.visibility = View.VISIBLE
    }

    fun goToNextScreen() {
        val action = TodoFragmentDirections.actionTodoFragmentToSelectionFragment()
        findNavController().navigate(action)
    }
}
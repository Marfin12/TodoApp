package com.example.todoapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.FragmentTodoBinding
import com.example.todoapp.databinding.FragmentTodoEmptyBinding

class TodoFragment : Fragment() {
    private var _fragmentTodoBinding: FragmentTodoBinding? = null
    private val fragmentTodoBinding get() = _fragmentTodoBinding!!

    private var _fragmentEmptyTodoBinding: FragmentTodoEmptyBinding? = null
    private val fragmentEmptyTodoBinding get() = _fragmentEmptyTodoBinding!!

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentEmptyTodoBinding = FragmentTodoEmptyBinding.inflate(inflater, container, false)
        return fragmentEmptyTodoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentEmptyTodoBinding.apply {
            todoEmptyFragment = this@TodoFragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentTodoBinding = null
    }

    fun goToNextScreen() {
        val action = TodoFragmentDirections.actionTodoFragmentToSelectionFragment()
        findNavController().navigate(action)
    }
}
package com.example.todoapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.TodoApplication
import com.example.todoapp.databinding.FragmentSubmissionBinding
import com.example.todoapp.fragment.SubmissionFragmentArgs.Companion.fromBundle
import com.example.todoapp.model.TodoListModel
import com.example.todoapp.model.TodoModel

class SubmissionFragment : Fragment() {
    private var _binding: FragmentSubmissionBinding? = null
//
    private val binding get() = _binding!!

    private val viewListModel: TodoListModel by activityViewModels {
        TodoListModel.TodoListViewModelFactory(
            (activity?.application as TodoApplication).database.todoDao()
        )
    }

    val todos by lazy {
        fromBundle(requireArguments()).todo
    }

    private val viewModel: TodoModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.addTodoItem(todos)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_submission, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerView.adapter = viewModel.getSubmissionAdapter()

        binding.apply {
            submiss = this@SubmissionFragment
        }
    }

    fun submit() {
        viewListModel.submitItem(viewModel.getSubmissionTodoList()) { discard() }
    }

    fun discard() {
        val action = SubmissionFragmentDirections.actionSubmissionFragmentToTodoFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
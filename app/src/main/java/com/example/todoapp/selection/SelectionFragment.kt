package com.example.todoapp.selection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentSelectionBinding

/**
 * Display a [Selection] screen. User can add todo item & remove from their addition action
 */
class SelectionFragment : Fragment() {
    private var _binding: FragmentSelectionBinding? = null

    private val binding get() = _binding!!

    private val viewModel: SelectionModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_selection, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = viewModel.getTodoAdapter()

        binding.apply {
            selectionFragment = this@SelectionFragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun goToNextScreen() {
        val action = SelectionFragmentDirections.actionSelectionFragmentToSubmissionFragment(todo = viewModel.getTodoList())
        findNavController().navigate(action)
    }

    fun addTodoList() {
        val editText = binding.todoEditText
        viewModel.addTodoItem(editText.text.toString(), R.drawable.ic_cancel)
    }
}
package com.example.todoapp.submission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.TodoApplication
import com.example.todoapp.databinding.FragmentSubmissionBinding

/**
 * Display a [Submission] screen. User can either discard or submit their todo item.
 */
class SubmissionFragment : Fragment() {
    private var _binding: FragmentSubmissionBinding? = null

    private val binding get() = _binding!!

    private val viewListModel: SubmissionModel by activityViewModels {
        SubmissionModel.SubmissionViewModelFactory(
            (activity?.application as TodoApplication).database.todoDao()
        )
    }

    val todos by lazy {
        SubmissionFragmentArgs.fromBundle(requireArguments()).todo
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewListModel.addTodoItem(todos)
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
        binding.recyclerView.adapter = viewListModel.getSubmissionAdapter()

        binding.apply {
            submiss = this@SubmissionFragment
        }
    }

    fun submit() {
        viewListModel.submitItem(viewListModel.getSubmissionTodoList()) { discard() }
    }

    fun discard() {
        val action = SubmissionFragmentDirections.actionSubmissionFragmentToTodoFragment()
        viewListModel.clearTodoList()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
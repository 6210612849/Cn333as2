package com.example.listmaker.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listmaker.R
import com.example.listmaker.databinding.MainFragmentBinding
import com.example.listmaker.models.TaskList
import org.intellij.lang.annotations.JdkConstants
import android.util.Log

// layout: main_activity.xml
// Binding Class: MainActivityBinding
// layout:main_fragment.xml
// Binding class: MainFragmentBinding
class MainFragment() : Fragment(),ListSelectioRecyclerViewAdapter.ListSelectionRecyclerViewClickListerner {
    private  lateinit var  binding: MainFragmentBinding
    lateinit var  clickListener: MainFragmentInteractionListener
    interface  MainFragmentInteractionListener {
        fun listItemTapped(list: TaskList)
    }
    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.e("tag",inflater.context.toString())
        binding = MainFragmentBinding.inflate(inflater,container!!.findViewById(R.id.main_fragment_container),false)

        binding.itemRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        return binding.root

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(),
            MainViewModelFactory(PreferenceManager.getDefaultSharedPreferences(requireActivity())))
            .get(MainViewModel::class.java)
        val recyclerViewAdapter = ListSelectioRecyclerViewAdapter(viewModel.lists,this)
        binding.itemRecyclerview.adapter = recyclerViewAdapter

        viewModel.onListAdded = {
            recyclerViewAdapter.listsUpdated ()
        }
    }

    override fun listItemClicked(list: TaskList) {
        clickListener.listItemTapped(list)
    }

}
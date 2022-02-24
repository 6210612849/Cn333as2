package com.example.listmaker.ui.main

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listmaker.MainActivity
import com.example.listmaker.R
import com.example.listmaker.databinding.MainFragmentBinding
import com.example.listmaker.databinding.MainFragmentTabletBinding
import com.example.listmaker.models.TaskList
import org.w3c.dom.Text
import com.example.listmaker.ui.main.MainViewModelFactory
import androidx.fragment.app.FragmentActivity

class MainFragmentTablet(): Fragment() {
    lateinit var  clickListener: MainFragment.MainFragmentInteractionListener
    private lateinit var bindingtablet: MainFragmentTabletBinding
    private lateinit var viewModel: MainActivity
    lateinit var test: Button
    private lateinit var editTextTablet: EditText



    companion object {
        fun newInstance() = MainFragmentTablet()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        bindingtablet = MainFragmentTabletBinding.inflate(
            inflater,
            container!!.findViewById(R.id.container_tablet),
            false
        )

        return bindingtablet.root

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        editTextTablet = bindingtablet.editTextTextMultiLine

        bindingtablet.buttonTask.setOnClickListener{
            Log.e("tag", "success clink")
            editTextTablet.inputType = InputType.TYPE_CLASS_TEXT

            if (editTextTablet != null){
                var text :String = editTextTablet.text.toString()


            }
            else {
                Log.e("tag", "dog clink")
            }
        }

    }




}
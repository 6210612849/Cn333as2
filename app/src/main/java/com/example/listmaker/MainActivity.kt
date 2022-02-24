package com.example.listmaker

import android.app.Activity
import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.listmaker.databinding.MainActivityBinding
import com.example.listmaker.databinding.MainFragmentBinding
import com.example.listmaker.models.TaskList
import com.example.listmaker.ui.detail.ListDetailFragment
import com.example.listmaker.ui.main.MainFragment
import com.example.listmaker.ui.main.MainViewModel
import com.example.listmaker.ui.main.MainViewModelFactory
import java.util.prefs.Preferences
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentContainer
import androidx.lifecycle.ViewModel
import com.example.listmaker.databinding.MainFragmentTabletBinding
import com.example.listmaker.ui.main.MainFragmentTablet

class MainActivity : AppCompatActivity(),MainFragment.MainFragmentInteractionListener {

    private  lateinit var  binding: MainActivityBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var bindingtablet: MainFragmentTabletBinding
    companion object{
        const val  INTENT_LIST_KEY = "list"
        const val LIST_DETAIL_REQUEST_CODE = 123
        val INTENT_LIST_KEY_TABLE = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this,
            MainViewModelFactory(PreferenceManager.getDefaultSharedPreferences(this))
        )
            .get(MainViewModel::class.java)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            val mainFragment = MainFragment.newInstance()
            mainFragment.clickListener = this
            val fragmentContainerViewId: Int = if (binding.mainFragmentContainer == null) {
                R.id.container }
            else {
                R.id.main_fragment_container
            }

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(fragmentContainerViewId, mainFragment)
            }
        }

        binding.FabAddNote.setOnClickListener {
            showCreateListDialog()
        }
    }

    private fun showCreateListDialogTeblet(){


        val mainFragmentTablet = MainFragmentTablet.newInstance()
        val  fragmentContainerViewId : Int = R.id.list_detail_fragment_container


            supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(fragmentContainerViewId, mainFragmentTablet)
        }



        }







    private fun showCreateListDialog() {
        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)

        val builder = AlertDialog.Builder(this)
        val listTitleEditText = EditText(this)
        listTitleEditText.inputType = InputType.TYPE_CLASS_TEXT

        builder.setTitle(dialogTitle)
        builder.setView(listTitleEditText)

        builder.setPositiveButton(positiveButtonTitle) { dialog, _ ->
            dialog.dismiss()
            val taskList = TaskList(listTitleEditText.text.toString())
            viewModel.saveList(taskList)
            showListDetail(taskList)
        }

        builder.create().show()
    }
    private fun showListDetail(list: TaskList) {
            if (binding.mainFragmentContainer == null) {

                val listDetailIntent = Intent(this, ListDetailActivity::class.java)
                listDetailIntent.putExtra(INTENT_LIST_KEY, list)
                startActivityForResult(listDetailIntent, LIST_DETAIL_REQUEST_CODE)
            } else {

                val bundle = bundleOf(INTENT_LIST_KEY to list)
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace(R.id.list_detail_fragment_container, ListDetailFragment::class.java, bundle, null)
                }
                binding.FabAddNote.setOnClickListener {
                    showCreateTaskDialog()
                }
            }
        }

    private fun showCreateTaskDialog() {
        val taskEditText = EditText(this)
        taskEditText.inputType = InputType.TYPE_CLASS_TEXT

        AlertDialog.Builder(this)
            .setTitle(R.string.task_to_add)
            .setView(taskEditText)
            .setPositiveButton(R.string.add_task) { dialog, _ ->
                val task = taskEditText.text.toString()
                viewModel.addTask(task)
                dialog.dismiss()
            }
            .create()
            .show()
    }



    override fun listItemTapped(list: TaskList) {
        showListDetail(list)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LIST_DETAIL_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            data?.let {
                viewModel.updateList(data.getParcelableExtra(INTENT_LIST_KEY)!!)
                viewModel.refreshLists()
            }
        }
    }

    override fun onBackPressed() {
        val listDetailFragment = supportFragmentManager.findFragmentById(R.id.list_detail_fragment_container)
        if (listDetailFragment == null){
            super.onBackPressed()
        }
        else{
            title = resources.getString(R.string.app_name)
            supportFragmentManager.commit{
                setReorderingAllowed(true)
                remove(listDetailFragment)
            }
        }
        binding.FabAddNote.setOnClickListener {
            showCreateListDialog()
        }

    }
}
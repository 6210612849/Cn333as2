package com.example.listmaker.ui.detail

import androidx.lifecycle.ViewModel
import com.example.listmaker.models.TaskList

class ListDetailViewModel : ViewModel() {
    lateinit var list: TaskList
    lateinit var onTaskAdded : () -> Unit
    fun addTask(task: String) {
        list.tasks.add(task)
        onTaskAdded.invoke()
    }
}
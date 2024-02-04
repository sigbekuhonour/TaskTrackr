package com.example.tasktrackr

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore

data class Task(var title: String, var task: String)

class MyViewModel : ViewModel() {
    private val db = Firebase.firestore
    var listOfTask = mutableStateListOf<Task>()
    // add each val in mutableList to firebase console
    val firebaseDb = db.collection("tasks").get().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            for (document in task.result) {
                val todoTask = document.data["task"].toString()
                val todoTitle = document.data["title"].toString()
                listOfTask.add(Task(todoTitle, todoTask))
            }
        }
    }
    fun createdTask(title: String, task: String) {
            val user = hashMapOf(
                "title" to title,
                "task" to task
            )
            db.collection("tasks").add(user).addOnSuccessListener { documentReference ->
                Log.d(TAG, "Task added with ID: ${documentReference.id}")

            }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding task", e)
                }
            listOfTask.add(0,Task(title,task))
        }
    fun updateTask(title: String,task:String){

        val user = hashMapOf(
            "title" to title,
            "task" to task
        )
        db.collection("tasks").whereEqualTo("task",listOfTask[0].task).get().addOnSuccessListener { it ->
            for (document in it){
                db.collection("tasks").document(document.id).set(user, SetOptions.merge()).addOnSuccessListener {
                    Log.d(TAG, "task updated")
                }
            }

        }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updatind task", e)
            }

        listOfTask[0].task = task
        listOfTask[0].title = title

    }
    fun delete() {
        db.collection("tasks").whereEqualTo("task",listOfTask.last().task).get().addOnSuccessListener { it ->
            for (document in it){
                db.collection("tasks").document(document.id).delete().addOnSuccessListener {
                    Log.d(TAG, "task deleted")
                }
            }

        }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting task", e)
            }

        listOfTask.remove(listOfTask.last())
    }
        }





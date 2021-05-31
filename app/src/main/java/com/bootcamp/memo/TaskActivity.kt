package com.bootcamp.memo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bootcamp.memo.local.TaskDatabase
import com.bootcamp.memo.model.TaskEntity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_task.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class TaskActivity : AppCompatActivity() {

    private var mDBTask :  TaskDatabase? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        mDBTask = TaskDatabase.getInstance(this)
        setSupportActionBar(toolbar)

        val list : View = listFloating
        list.setOnClickListener { view ->
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.item_task, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuSave -> {
                val title = edtxtTaskTitle.text.toString()
                val description = edtxDescription.text.toString()
                GlobalScope.async {
                    val result =  mDBTask?.TaskDao()?.insertTask(TaskEntity(null,title,description))
                    result?.let {
                        if (it != 0.toLong() ){
                            runOnUiThread {
                                edtxtTaskTitle.setText("")
                                edtxDescription.setText("")
                                Toast.makeText(this@TaskActivity,"Berhasil Input Data", Toast.LENGTH_LONG).show()
                            }

                        }
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
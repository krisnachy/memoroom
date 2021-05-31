package com.bootcamp.memo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bootcamp.memo.local.TaskDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var mDBTask : TaskDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDBTask = TaskDatabase.getInstance(this)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        fetchData()

        val add : View = addFloating
        add.setOnClickListener { view ->
            val i = Intent(this,TaskActivity::class.java)
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    fun fetchData(){
        GlobalScope.launch {
            val listTask = mDBTask?.TaskDao()?.getAllTask()

            runOnUiThread {
                listTask?.let{
                    val adapter = TaskAdapter(it)
                    recyclerView.adapter = adapter
                }
            }
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        TaskDatabase.destroyInstance()
//    }
}
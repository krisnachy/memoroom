package com.bootcamp.memo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bootcamp.memo.local.TaskDatabase
import com.bootcamp.memo.model.TaskEntity
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_task.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class EditTask : AppCompatActivity() {
    val Task: TaskEntity? by lazy {
        intent.getParcelableExtra<TaskEntity>("Task")
    }

    private var mDBTask: TaskDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        Task?.let {
            edtxtTaskTitleEdit.setText(it.title)
            edtxDescriptionEdit.setText(it.description)
        }

        mDBTask = TaskDatabase.getInstance(this)

        setSupportActionBar(toolbarEdit)

        val listEdit : View = listFloatingEdit
        listEdit.setOnClickListener { view ->
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
                val title = edtxtTaskTitleEdit.text.toString()
                val description = edtxDescriptionEdit.text.toString()
                GlobalScope.async {
                    val result = mDBTask?.TaskDao()?.updateTask(
                        TaskEntity(
                            Task?.id,
                            title,
                            description
                        )
                    )
                    runOnUiThread {
                        if (result != 0) {
                            edtxtTaskTitleEdit.setText("")
                            edtxDescriptionEdit.setText("")
                            Toast.makeText(this@EditTask, "Berhasil Edit Data", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
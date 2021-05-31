package com.bootcamp.memo

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bootcamp.memo.local.TaskDatabase
import com.bootcamp.memo.model.TaskEntity
import kotlinx.android.synthetic.main.list_view_task.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class TaskAdapter(val listTask : List<TaskEntity>) : RecyclerView.Adapter<TaskAdapter.ListTaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListTaskViewHolder {
        return ListTaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_view_task, parent, false))
    }

    override fun getItemCount(): Int {
        return listTask.size
    }

    override fun onBindViewHolder(holder: ListTaskViewHolder, position: Int) {

        listTask.get(position).let {
            holder.title.text = it.title
            holder.description.text = it.description
        }

        holder.itemView.editBtn.setOnClickListener{
            val intentEditActivity = Intent(it.context, EditTask::class.java)

            intentEditActivity.putExtra("Task", listTask[position])
            it.context.startActivity(intentEditActivity)
        }

        holder.itemView.deleteBtn.setOnClickListener{
            AlertDialog.Builder(it.context).setPositiveButton("Ya"){ p0, p1 ->
                val mDb = TaskDatabase.getInstance(holder.itemView.context)

                GlobalScope.async {
                    val result = mDb?.TaskDao()?.deleteTask(listTask[position])

                    (holder.itemView.context as MainActivity).runOnUiThread{
                        if (result != 0){
                            Toast.makeText(it.context, "Data ${listTask[position].id} berhasil dihapus", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(it.context, "Data ${listTask[position].id} tidak berhasil dihapus", Toast.LENGTH_LONG).show()
                        }
                    }

                    (holder.itemView.context as MainActivity).fetchData()
                }
            }.setNegativeButton("Tidak"){ p0, p1 ->
                p0.dismiss()
            }.setMessage("Apakah anda yakin menghapus data ${listTask[position].id}").setTitle("Konfirmasi Hapus").create().show()
        }
    }

    inner class ListTaskViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val title = view.titleList
        val description = view.descriptionList
    }
}
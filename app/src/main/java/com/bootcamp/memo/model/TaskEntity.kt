package com.bootcamp.memo.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class TaskEntity (
    @PrimaryKey(autoGenerate = true) val id : Int?,
    @ColumnInfo(name = "title") val title : String?,
    @ColumnInfo(name = "description") val description : String?) : Parcelable
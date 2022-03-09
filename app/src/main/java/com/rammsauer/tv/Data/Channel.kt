package com.rammsauer.tv.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Channel(
    @PrimaryKey(autoGenerate = true)                            val uid         : Int = 0,
    @ColumnInfo(name = "name")                                  val name        : String?,
    @ColumnInfo(name = "url")                                   val url         : String?,
    @ColumnInfo(name = "logo", typeAffinity = ColumnInfo.BLOB)  val logo        : ByteArray? = null,
    @ColumnInfo(name = "country")                               val group       : String?,
    @ColumnInfo(name = "onlineStatus")                          val status      : Int = 0,
)
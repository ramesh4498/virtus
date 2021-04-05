package com.virtus.demo.hits.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "hits")
data class Hits(
    val created_at: Date,
    val author: String,
    val title: String?,
    val story_title: String?,
    val url: String?,
    val story_url: String?,
    @PrimaryKey
    val created_at_i: Int
)
package com.example.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_community_posts")
data class CommunityPostEntity(
    @PrimaryKey
    val id: String,
    val authorName: String,
    val authorAvatarUrl: String? = null,
    val authorBadge: String? = null, // "Verified Worker", "Community Guide", "Moderator"
    val category: String, // "News", "Warning", "Question", "Housing", "General"
    val titleBurmese: String,
    val contentBurmese: String,
    val locationTag: String? = null, // "Phra Khanong", "Mahachai", "Pratunam", "Sathon"
    val imageUrl: String? = null,
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val isVerifiedPost: Boolean = false,
    val createdAtTimestamp: Long = System.currentTimeMillis(),
    val cachedAtTimestamp: Long = System.currentTimeMillis()
)

package com.example.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_user_profiles")
data class UserProfileEntity(
    @PrimaryKey
    val userId: String,
    val name: String,
    val burmeseName: String,
    val avatarStyle: String, // "Traditional Longyi & Taikpon", "Acheik Silk", "Bangkok Casual"
    val companionName: String = "Royal Peacock (ဒေါင်း)",
    val level: Int = 1,
    val xp: Int = 0,
    val coins: Int = 0,
    val streakDays: Int = 0,
    val isVerifiedWorker: Boolean = false,
    val isGoldenPassMember: Boolean = false,
    val tierName: String = "Free Member",
    val stayDurationMonths: Int = 1,
    val currentCityZone: String = "Phra Khanong",
    val workSector: String = "General",
    val cachedAtTimestamp: Long = System.currentTimeMillis()
)

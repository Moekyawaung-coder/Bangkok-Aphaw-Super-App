package com.example.data.local.dao

import androidx.room.*
import com.example.data.local.entities.UserProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {

    @Query("SELECT * FROM cached_user_profiles WHERE userId = :userId LIMIT 1")
    fun getUserProfile(userId: String): Flow<UserProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(profile: UserProfileEntity)

    @Query("DELETE FROM cached_user_profiles WHERE userId = :userId")
    suspend fun deleteUserProfile(userId: String)

    @Query("DELETE FROM cached_user_profiles")
    suspend fun clearUserProfiles()
}

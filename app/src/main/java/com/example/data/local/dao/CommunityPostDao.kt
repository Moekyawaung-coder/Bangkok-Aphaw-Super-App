package com.example.data.local.dao

import androidx.room.*
import com.example.data.local.entities.CommunityPostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CommunityPostDao {

    @Query("SELECT * FROM cached_community_posts ORDER BY createdAtTimestamp DESC")
    fun getCachedPosts(): Flow<List<CommunityPostEntity>>

    @Query("SELECT * FROM cached_community_posts WHERE category = :category ORDER BY createdAtTimestamp DESC")
    fun getCachedPostsByCategory(category: String): Flow<List<CommunityPostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<CommunityPostEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: CommunityPostEntity)

    @Query("DELETE FROM cached_community_posts WHERE id = :postId")
    suspend fun deletePostById(postId: String)

    @Query("DELETE FROM cached_community_posts")
    suspend fun clearCachedPosts()
}

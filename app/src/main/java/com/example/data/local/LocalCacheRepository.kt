package com.example.data.local

import com.example.data.local.dao.CommunityPostDao
import com.example.data.local.dao.UserProfileDao
import com.example.data.local.entities.CommunityPostEntity
import com.example.data.local.entities.UserProfileEntity
import kotlinx.coroutines.flow.Flow

class LocalCacheRepository(
    private val communityPostDao: CommunityPostDao,
    private val userProfileDao: UserProfileDao
) {

    val allCachedPosts: Flow<List<CommunityPostEntity>> = communityPostDao.getCachedPosts()

    fun getCachedPostsByCategory(category: String): Flow<List<CommunityPostEntity>> {
        return communityPostDao.getCachedPostsByCategory(category)
    }

    suspend fun cachePosts(posts: List<CommunityPostEntity>) {
        communityPostDao.insertPosts(posts)
    }

    suspend fun cachePost(post: CommunityPostEntity) {
        communityPostDao.insertPost(post)
    }

    suspend fun removePost(postId: String) {
        communityPostDao.deletePostById(postId)
    }

    suspend fun clearPostCache() {
        communityPostDao.clearCachedPosts()
    }

    fun getCachedUserProfile(userId: String): Flow<UserProfileEntity?> {
        return userProfileDao.getUserProfile(userId)
    }

    suspend fun cacheUserProfile(profile: UserProfileEntity) {
        userProfileDao.insertUserProfile(profile)
    }

    suspend fun deleteUserProfile(userId: String) {
        userProfileDao.deleteUserProfile(userId)
    }
}

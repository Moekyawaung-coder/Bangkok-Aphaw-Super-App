package com.example.data.models

data class Chapter(
    val id: Int,
    val titleBurmese: String,
    val titleEnglish: String,
    val descriptionBurmese: String,
    val iconName: String,
    val quests: List<Quest>
)

data class Quest(
    val id: String,
    val chapterId: Int,
    val titleBurmese: String,
    val titleEnglish: String,
    val xpReward: Int,
    val isCompleted: Boolean,
    val guideContentBurmese: String,
    val actionLabel: String,
    val practicalTip: String
)

data class Badge(
    val id: String,
    val nameBurmese: String,
    val nameEnglish: String,
    val description: String,
    val iconEmoji: String,
    val isUnlocked: Boolean
)

data class ThaiLesson(
    val id: String,
    val thaiText: String,
    val thaiPhoneticBurmese: String,
    val burmeseMeaning: String,
    val audioCategory: String // "Emergency", "Directions", "Market", "Immigration", "Work"
)

data class AvatarProfile(
    val name: String,
    val level: Int,
    val currentXp: Int,
    val nextLevelXp: Int,
    val streakDays: Int,
    val outfitStyle: String, // "Traditional Longyi & Taikpon", "Acheik Silk", "Bangkok Casual", "Executive"
    val companion: String, // "Royal Peacock (ဒေါင်း)", "Friendly Cat", "Elephant", "None"
    val coins: Int,
    val isGoldenPassActive: Boolean = true,
    val currentTierName: String = "ရွှေလုပ်သား Golden Pass"
)

data class GoldenPassTier(
    val id: String,
    val titleBurmese: String,
    val titleEnglish: String,
    val priceThbMonthly: Int,
    val badgeLabel: String,
    val featuresBurmese: List<String>,
    val isRecommended: Boolean = false,
    val isCurrent: Boolean = false
)

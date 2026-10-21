package com.example.data.models

data class VoiceQueryPrompt(
    val queryBurmese: String,
    val queryEnglish: String,
    val category: String, // "Transit", "Legal", "Health", "Jobs", "Daily Life"
    val answerBurmese: String,
    val thaiPhrase: String?,
    val relatedScreenRoute: String?
)

data class TranslationRecord(
    val burmeseText: String,
    val thaiText: String,
    val thaiPronunciationBurmese: String,
    val category: String
)

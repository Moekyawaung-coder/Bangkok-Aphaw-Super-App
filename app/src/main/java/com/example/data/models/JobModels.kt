package com.example.data.models

data class JobPost(
    val id: String,
    val titleBurmese: String,
    val titleEnglish: String,
    val category: String, // "Factory", "Restaurant", "Construction", "Cleaning", "Logistics", "Domestic"
    val companyName: String,
    val locationArea: String,
    val nearestStation: String,
    val salaryThb: String,
    val salaryType: String, // "Daily", "Monthly"
    val otPayThbPerHour: Int,
    val isVerifiedEmployer: Boolean,
    val hasScamAlert: Boolean,
    val scamWarningText: String?,
    val requirementsBurmese: List<String>,
    val benefitsBurmese: List<String>,
    val contactPhone: String,
    val contactLineId: String,
    val isSaved: Boolean = false,
    val postedDate: String
)

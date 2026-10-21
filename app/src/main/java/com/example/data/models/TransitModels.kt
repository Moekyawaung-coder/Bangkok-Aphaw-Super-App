package com.example.data.models

enum class TransitType(val titleBurmese: String, val titleEnglish: String) {
    BTS("ဘီတီအက်စ် (BTS SkyTrain)", "BTS SkyTrain"),
    MRT("အမ်အာရ်တီ (MRT Subway)", "MRT Metro"),
    ARL("လေဆိပ်ရထား (ARL)", "Airport Rail Link"),
    BOAT("ကျောက်ဖရားလှေ (Boat)", "Express & Canal Boat"),
    BUS("ဘတ်စ်ကား (BMTA Bus)", "BMTA City Bus"),
    WIN_MOTO("ကယ်ရီဆိုင်ကယ် (Win Moto)", "Motorcycle Taxi")
}

data class TransitStation(
    val id: String,
    val nameEnglish: String,
    val nameBurmese: String,
    val nameThai: String,
    val type: TransitType,
    val lineName: String,
    val zone: String,
    val latitude: Double,
    val longitude: Double,
    val fareBaseThb: Int
)

data class OfflineMapPack(
    val id: String,
    val nameBurmese: String,
    val nameEnglish: String,
    val zone: String,
    val sizeMb: Int,
    val isDownloaded: Boolean,
    val isPremiumOnly: Boolean,
    val downloadProgress: Float = 0f
)

data class LittleMyanmarPlace(
    val id: String,
    val nameBurmese: String,
    val nameEnglish: String,
    val category: String, // "Food & Grocery", "Clinic", "Remittance & Banking", "Dormitory", "Temple", "Embassy", "Legal Aid"
    val area: String, // "Phra Khanong", "Bang Bon / Mahachai", "Pratunam", "Onnut", "Sathon", "Nonthaburi", "Samut Prakan"
    val address: String,
    val nearestTransit: String,
    val phone: String,
    val rating: Double,
    val descriptionBurmese: String,
    val openingHours: String,
    val isVerifiedPremium: Boolean = true,
    val features: List<String> = emptyList()
)

data class RoutePlan(
    val origin: String,
    val destination: String,
    val transitModes: List<TransitType>,
    val estimatedDurationMin: Int,
    val fareThb: Int,
    val fareMmkEstimate: Int,
    val stepsBurmese: List<String>,
    val isEmergencyRoute: Boolean = false
)

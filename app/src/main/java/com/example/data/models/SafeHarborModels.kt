package com.example.data.models

data class EmergencyContact(
    val id: String,
    val serviceNameBurmese: String,
    val serviceNameEnglish: String,
    val phoneNumber: String,
    val descriptionBurmese: String,
    val iconType: String // "POLICE", "AMBULANCE", "EMBASSY", "LABOR"
)

data class LegalDocument(
    val id: String,
    val titleBurmese: String,
    val titleEnglish: String,
    val documentType: String, // "90_DAYS", "WORK_PERMIT", "VISA", "PASSPORT_CI", "TM30"
    val documentNumber: String,
    val expiryDateString: String,
    val daysRemaining: Int,
    val isUrgent: Boolean,
    val actionGuideBurmese: String
)

data class CheckpointPhrase(
    val thaiScript: String,
    val burmesePhonetic: String,
    val burmeseMeaning: String,
    val context: String
)

data class SymptomTriageItem(
    val id: String,
    val symptomNameBurmese: String,
    val symptomNameEnglish: String,
    val severityLevel: String, // "EMERGENCY_RED", "MODERATE_YELLOW", "MILD_GREEN"
    val adviceBurmese: String,
    val recommendedFacility: String, // "Emergency Hospital (၁၆၆၉)", "Clinic", "Pharmacy"
    val thaiCommunicationCard: String
)

data class MedicineGuide(
    val id: String,
    val tradeNameThai: String,
    val genericName: String,
    val categoryBurmese: String, // "အဖျားပျောက်ဆေး", "အစာအိမ်ဆေး", "အကိုက်အခဲပျောက်ဆေး", "ဓာတ်ဆား"
    val indicationBurmese: String,
    val dosageBurmese: String,
    val precautionsBurmese: String
)

data class TelemedDoctor(
    val id: String,
    val doctorName: String,
    val specialtyBurmese: String,
    val languages: List<String>,
    val hospitalAffiliation: String,
    val consultationFeeThb: Int,
    val availableHours: String,
    val rating: Double,
    val isOnlineNow: Boolean
)

data class RemittanceProvider(
    val id: String,
    val name: String,
    val type: String, // "Official Remittance App", "Bank Transfer", "Agent Counter"
    val rateThbToMmk: Double,
    val transferFeeThb: Int,
    val deliveryTimeBurmese: String,
    val isVerifiedLicense: Boolean,
    val fraudRiskBurmese: String
)

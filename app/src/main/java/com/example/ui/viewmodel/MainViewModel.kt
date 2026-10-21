package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.models.*
import com.example.data.repository.AppRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Locale

enum class AppTab(val titleBurmese: String, val titleEnglish: String) {
    HOME_MAP("မြေပုံ", "Home Map"),
    ROUTES("လမ်းကြောင်း", "Routes"),
    COMMUNITY("အသိုက်အဝန်း", "Community"),
    JOBS("အလုပ်အကိုင်", "Jobs"),
    GOLDEN_JOURNEY("ရွှေခရီး", "Golden Journey"),
    SAFE_HARBOR("ဘေးကင်းရေး", "Safe Harbor"),
    BUSINESS_CIRCLE("စီးပွားရေး", "Business Circle"),
    ASSISTANT("လက်ထောက်", "Assistant")
}

class MainViewModel(application: Application) : AndroidViewModel(application), TextToSpeech.OnInitListener {

    val repository = AppRepository()

    private val _currentTab = MutableStateFlow(AppTab.HOME_MAP)
    val currentTab: StateFlow<AppTab> = _currentTab.asStateFlow()

    // Sub-screen and modal states
    private val _selectedPlace = MutableStateFlow<LittleMyanmarPlace?>(null)
    val selectedPlace: StateFlow<LittleMyanmarPlace?> = _selectedPlace.asStateFlow()

    private val _selectedJob = MutableStateFlow<JobPost?>(null)
    val selectedJob: StateFlow<JobPost?> = _selectedJob.asStateFlow()

    private val _selectedEmergencyContact = MutableStateFlow<EmergencyContact?>(null)
    val selectedEmergencyContact: StateFlow<EmergencyContact?> = _selectedEmergencyContact.asStateFlow()

    // Transit Route Calculator
    private val _selectedOriginStation = MutableStateFlow<TransitStation?>(null)
    val selectedOriginStation: StateFlow<TransitStation?> = _selectedOriginStation.asStateFlow()

    private val _selectedDestStation = MutableStateFlow<TransitStation?>(null)
    val selectedDestStation: StateFlow<TransitStation?> = _selectedDestStation.asStateFlow()

    private val _calculatedRoute = MutableStateFlow<RoutePlan?>(null)
    val calculatedRoute: StateFlow<RoutePlan?> = _calculatedRoute.asStateFlow()

    // Filters & Search
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategoryFilter = MutableStateFlow("All")
    val selectedCategoryFilter: StateFlow<String> = _selectedCategoryFilter.asStateFlow()

    // User preferences & Accessibility
    val isHighContrastMode = MutableStateFlow(false)
    val isLowDataMode = MutableStateFlow(false)
    val isVoiceConciergeSheetOpen = MutableStateFlow(false)
    val isDevDeployGuideOpen = MutableStateFlow(false)
    val isOfflineMapDialogVisible = MutableStateFlow(false)
    val isGoldenPassDialogVisible = MutableStateFlow(false)
    val isTelemedDialogVisible = MutableStateFlow(false)
    val isRemittanceCalculatorVisible = MutableStateFlow(false)

    // TTS Engine for Thai audio pronunciation
    private var tts: TextToSpeech? = null
    private var isTtsReady = false

    init {
        tts = TextToSpeech(application, this)
        // Set default origin and destination
        viewModelScope.launch {
            val stationsList = repository.stations.value
            if (stationsList.size >= 2) {
                _selectedOriginStation.value = stationsList[0] // Mo Chit
                _selectedDestStation.value = stationsList[5] // Surasak (Embassy)
                calculateRoute()
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale("th", "TH"))
            isTtsReady = result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED
            if (!isTtsReady) {
                tts?.setLanguage(Locale.US)
                isTtsReady = true
            }
        }
    }

    fun speakThaiText(text: String) {
        if (isTtsReady && text.isNotBlank()) {
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "TTS_${System.currentTimeMillis()}")
        } else {
            Toast.makeText(getApplication(), "Audio: $text", Toast.LENGTH_SHORT).show()
        }
    }

    fun selectTab(tab: AppTab) {
        _currentTab.value = tab
    }

    fun selectPlace(place: LittleMyanmarPlace?) {
        _selectedPlace.value = place
    }

    fun selectJob(job: JobPost?) {
        _selectedJob.value = job
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setCategoryFilter(category: String) {
        _selectedCategoryFilter.value = category
    }

    fun setOriginStation(station: TransitStation) {
        _selectedOriginStation.value = station
        calculateRoute()
    }

    fun setDestStation(station: TransitStation) {
        _selectedDestStation.value = station
        calculateRoute()
    }

    fun calculateRoute() {
        val origin = _selectedOriginStation.value ?: return
        val dest = _selectedDestStation.value ?: return

        val duration = when {
            origin.id == dest.id -> 5
            origin.lineName == dest.lineName -> 22
            else -> 38
        }
        val fareThb = if (origin.id == dest.id) 17 else (origin.fareBaseThb + dest.fareBaseThb) / 2 + 10
        val fareMmk = (fareThb * repository.currentThbToMmkRate.value).toInt()

        val steps = listOf(
            "${origin.nameBurmese} (${origin.nameEnglish}) ဘူတာသို့ ဝင်ရောက်ပါ",
            "${origin.lineName} ရထားစောင့်ဆိုင်း၍ စီးနင်းပါ (ကြာချိန် ~${duration - 10} မိနစ်)",
            if (origin.lineName != dest.lineName) "ဆီယမ် (Siam) ဘူတာတွင် Silom Line သို့ ရထားလဲလှယ်စီးနင်းပါ" else "တိုက်ရိုက် ဆက်လက်စီးနင်းပါ",
            "${dest.nameBurmese} (${dest.nameEnglish}) သို့ ဆိုက်ရောက်ပါပြီ (ခန့်မှန်းကုန်ကျငွေ ${fareThb} ဘတ် / ~${fareMmk} ကျပ်)"
        )

        _calculatedRoute.value = RoutePlan(
            origin = origin.nameEnglish,
            destination = dest.nameEnglish,
            transitModes = listOf(origin.type, dest.type),
            estimatedDurationMin = duration,
            fareThb = fareThb,
            fareMmkEstimate = fareMmk,
            stepsBurmese = steps
        )
    }

    fun dialPhoneNumber(context: Context, phoneNumber: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${phoneNumber.replace("-", "").trim()}")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "ခေါ်ဆိုရန်: $phoneNumber", Toast.LENGTH_LONG).show()
        }
    }

    fun openWebUrl(context: Context, url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "URL: $url", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCleared() {
        tts?.stop()
        tts?.shutdown()
        super.onCleared()
    }
}

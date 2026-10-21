package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.SymptomTriageItem
import com.example.ui.theme.*
import com.example.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SafeHarborScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val emergencyContacts by viewModel.repository.emergencyContacts.collectAsState()
    val legalDocs by viewModel.repository.legalDocuments.collectAsState()
    val checkpointPhrases by viewModel.repository.checkpointPhrases.collectAsState()
    val symptoms by viewModel.repository.symptomTriageList.collectAsState()
    val medicines by viewModel.repository.medicines.collectAsState()

    var activeSubTab by remember { mutableStateOf("Emergency") }
    val subTabs = listOf("Emergency", "Legal & 90-Days", "Police Guide", "Health & Clinic", "Medicines")

    var selectedTriage by remember { mutableStateOf<SymptomTriageItem?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("ဘေးကင်းဆိပ်ကမ်း (Safe Harbor)", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("အရေးပေါ်၊ ဥပဒေရေးရာ၊ ကျန်းမာရေးနှင့် လုံခြုံရေး လမ်းညွှန်", fontSize = 11.sp, color = EmergencyRed)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // One-Tap SOS Header Banner
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = EmergencyRed),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier.testTag("sos_emergency_banner")
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = CircleShape,
                                    color = Color.White.copy(alpha = 0.25f),
                                    modifier = Modifier.size(40.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(Icons.Default.Call, contentDescription = "Emergency", tint = Color.White)
                                    }
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text("တစ်ချက်နှိပ် အရေးပေါ်ခေါ်ဆိုမှု (One-Tap SOS)", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text("ထိုင်းရဲတပ်ဖွဲ့ • လူနာတင်ယာဉ် • မြန်မာသံရုံး", color = Color.White.copy(alpha = 0.9f), fontSize = 11.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Quick Call Buttons Grid
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { viewModel.dialPhoneNumber(context, "191") },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                modifier = Modifier.weight(1f).height(44.dp).testTag("call_police_191")
                            ) {
                                Text("ရဲတပ်ဖွဲ့ 191", color = EmergencyRed, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }

                            Button(
                                onClick = { viewModel.dialPhoneNumber(context, "1669") },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                modifier = Modifier.weight(1f).height(44.dp).testTag("call_ambulance_1669")
                            ) {
                                Text("လူနာတင် 1669", color = EmergencyRed, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }

                            Button(
                                onClick = { viewModel.dialPhoneNumber(context, "02-637-9404") },
                                colors = ButtonDefaults.buttonColors(containerColor = ShwedagonGold),
                                modifier = Modifier.weight(1f).height(44.dp).testTag("call_embassy")
                            ) {
                                Text("မြန်မာသံရုံး", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Premium Quick Service Shortcuts
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FilledTonalButton(
                                onClick = { viewModel.isTelemedDialogVisible.value = true },
                                modifier = Modifier.weight(1f).height(36.dp).testTag("open_telemed_button")
                            ) {
                                Icon(Icons.Default.VideoCall, contentDescription = "Telemed", modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("ဆရာဝန်နှင့်ပြရန်", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }

                            FilledTonalButton(
                                onClick = { viewModel.isRemittanceCalculatorVisible.value = true },
                                modifier = Modifier.weight(1f).height(36.dp).testTag("open_remittance_calc_button")
                            ) {
                                Icon(Icons.Default.CurrencyExchange, contentDescription = "Remittance", modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("ငွေလွှဲနှုန်းတွက်ရန်", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // Sub Navigation Chips
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(subTabs) { tab ->
                        val isSelected = activeSubTab == tab
                        FilterChip(
                            selected = isSelected,
                            onClick = { activeSubTab = tab },
                            label = {
                                Text(
                                    text = when (tab) {
                                        "Emergency" -> "အရေးပေါ် ဖုန်းများ"
                                        "Legal & 90-Days" -> "ရက် ၉၀ နှင့် ပါမစ်"
                                        "Police Guide" -> "ရဲစစ်ဆေးရေး လမ်းညွှန်"
                                        "Health & Clinic" -> "ရောဂါ လက္ခဏာ စစ်ဆေးမှု"
                                        "Medicines" -> "ဆေးဝါးလမ်းညွှန်"
                                        else -> tab
                                    },
                                    fontSize = 12.sp
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            // Sub Tab Content
            when (activeSubTab) {
                "Emergency" -> {
                    items(emergencyContacts) { contact ->
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = contact.serviceNameBurmese, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text(text = contact.serviceNameEnglish, fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = contact.descriptionBurmese, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Button(
                                    onClick = { viewModel.dialPhoneNumber(context, contact.phoneNumber) },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                                    modifier = Modifier.height(38.dp)
                                ) {
                                    Icon(Icons.Default.Phone, contentDescription = "Call", modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(contact.phoneNumber, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }

                "Legal & 90-Days" -> {
                    item {
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = JadeContainer)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text("တရားဝင် နေထိုင်ခွင့်နှင့် စာရွက်စာတမ်း သက်တမ်းစောင့်ကြည့်ခြင်း", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = OnJadeContainer)
                                Text("ရက် ၉၀ အစီရင်ခံစာ (90-Day Report) နှင့် Work Permit သက်တမ်း ကုန်ဆုံးရက်များကို အချိန်မီ သက်တမ်းတိုးရန် မမေ့ပါနှင့်။", fontSize = 12.sp, color = OnJadeContainer.copy(alpha = 0.85f))
                            }
                        }
                    }

                    items(legalDocs) { doc ->
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(doc.titleBurmese, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                        Text("နံပါတ်: ${doc.documentNumber}", fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = if (doc.daysRemaining <= 45) EmergencyRed.copy(alpha = 0.15f) else SafeGreen.copy(alpha = 0.15f)
                                    ) {
                                        Text(
                                            text = if (doc.daysRemaining > 365) "အမြဲတမ်း" else "${doc.daysRemaining} ရက် ကျန်ရှိ",
                                            color = if (doc.daysRemaining <= 45) EmergencyRed else SafeGreen,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                                Text(doc.actionGuideBurmese, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 17.sp)
                            }
                        }
                    }
                }

                "Police Guide" -> {
                    item {
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = GoldContainer)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text("ရဲစစ်ဆေးရေးဂိတ်များတွင် ပြုမူပြောဆိုရန် လမ်းညွှန်:", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = OnGoldContainer)
                                Text("စိတ်အေးလက်အေး နေပါ။ ပတ်စပို့/CI နှင့် အလုပ်ပါမစ် မူရင်း သို့မဟုတ် မိတ္တူကို အသင့်ထုတ်ပြပါ။ အသံထွက် ဖွင့်ပြနိုင်ပါသည်။", fontSize = 12.sp, color = OnGoldContainer.copy(alpha = 0.85f))
                            }
                        }
                    }

                    items(checkpointPhrases) { phrase ->
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(phrase.context, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                    IconButton(
                                        onClick = { viewModel.speakThaiText(phrase.thaiScript) },
                                        modifier = Modifier.size(32.dp).background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                                    ) {
                                        Icon(Icons.Default.VolumeUp, contentDescription = "Speak", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(phrase.thaiScript, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Text("အသံထွက်: ${phrase.burmesePhonetic}", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("မြန်မာလို: ${phrase.burmeseMeaning}", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }

                "Health & Clinic" -> {
                    items(symptoms) { triage ->
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedTriage = triage }
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(triage.symptomNameBurmese, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = when (triage.severityLevel) {
                                            "EMERGENCY_RED" -> EmergencyRed
                                            "MODERATE_YELLOW" -> WarningAmber
                                            else -> SafeGreen
                                        }
                                    ) {
                                        Text(
                                            text = when (triage.severityLevel) {
                                                "EMERGENCY_RED" -> "အရေးပေါ် (၁၆၆၉)"
                                                "MODERATE_YELLOW" -> "ဆေးရုံ/ဆေးခန်း"
                                                else -> "ဆေးဆိုင်"
                                            },
                                            color = Color.White,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))
                                Text(triage.adviceBurmese, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text("ထိုင်းဆရာဝန် ပြသရန် ကတ်:", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                                        Text(triage.thaiCommunicationCard, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                    }
                                    IconButton(
                                        onClick = { viewModel.speakThaiText(triage.thaiCommunicationCard) },
                                        modifier = Modifier.size(30.dp)
                                    ) {
                                        Icon(Icons.Default.VolumeUp, contentDescription = "Play", modifier = Modifier.size(16.dp))
                                    }
                                }
                            }
                        }
                    }
                }

                "Medicines" -> {
                    items(medicines) { med ->
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(med.tradeNameThai, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Surface(shape = RoundedCornerShape(6.dp), color = JadeContainer) {
                                        Text(med.categoryBurmese, fontSize = 10.sp, color = JadeGreen, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                    }
                                }
                                Text("ဆေးအမည်: ${med.genericName}", fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text("အသုံးပြုပုံ: ${med.indicationBurmese}", fontSize = 12.sp)
                                Text("သောက်သုံးရန် ညွှန်ကြားချက်: ${med.dosageBurmese}", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Medium)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("သတိပြုရန်: ${med.precautionsBurmese}", fontSize = 11.sp, color = EmergencyRed)
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

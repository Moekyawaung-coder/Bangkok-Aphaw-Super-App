package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoiceConciergeScreen(viewModel: MainViewModel) {
    val queries by viewModel.repository.voiceQueries.collectAsState()
    val phrasebook by viewModel.repository.phrasebook.collectAsState()

    var userCustomText by remember { mutableStateOf("") }
    var translatedResult by remember { mutableStateOf<String?>(null) }
    var translatedThaiPronunciation by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("AI Voice Concierge & Translator", fontWeight = FontWeight.Bold, fontSize = 17.sp)
                        Text("မြန်မာဘာသာ အသံလက်ထောက်နှင့် အချိန်နှင့်တပြေးညီ ထိုင်းဘာသာပြန်", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
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
            // Voice Assistant Banner
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = NightSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("မြန်မာစကားပြော အသံလက်ထောက်", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.White)
                                Text("မေးမြန်းလိုသည့် အကြောင်းအရာကို ရွေးချယ်ပါ သို့မဟုတ် ရိုက်ထည့်ပါ", fontSize = 11.sp, color = NeonJade)
                            }
                            Surface(
                                shape = CircleShape,
                                color = ShwedagonGold,
                                modifier = Modifier.size(44.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.Mic, contentDescription = "Mic", tint = Color.Black, modifier = Modifier.size(24.dp))
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Custom Query Input
                        OutlinedTextField(
                            value = userCustomText,
                            onValueChange = { userCustomText = it },
                            placeholder = { Text("မေးခွန်း သို့မဟုတ် ဘာသာပြန်လိုသည်ကို ရိုက်ထည့်ပါ...", fontSize = 12.sp) },
                            modifier = Modifier.fillMaxWidth().testTag("voice_custom_input"),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        if (userCustomText.isNotBlank()) {
                                            // Mock AI concierge smart matcher
                                            translatedResult = "ထိုင်းဘာသာပြန်: " + when {
                                                userCustomText.contains("ဘယ်လောက်", ignoreCase = true) -> "ราคาเท่าไหร่ครับ (Ra-kha thao rai khrap)"
                                                userCustomText.contains("စား", ignoreCase = true) -> "กินข้าวที่ไหนดีครับ (Kin khao tee nai dee khrap)"
                                                userCustomText.contains("သွား", ignoreCase = true) -> "ไปสถานีรถไฟฟ้ายังไงครับ (Pai sathani rotfai fah yang ngai khrap)"
                                                else -> "ช่วยแนะนำข้อมูลหน่อยครับ (Chuai nae nam khor moon noi khrap)"
                                            }
                                            translatedThaiPronunciation = "ထိုင်းစကားပြော စကားပြန်အသံထွက်ကို နားထောင်နိုင်ပါသည်"
                                            viewModel.speakThaiText(translatedResult ?: "")
                                        }
                                    }
                                ) {
                                    Icon(Icons.Default.Send, contentDescription = "Send", tint = ShwedagonGold)
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedContainerColor = Color(0xFF1E2824),
                                unfocusedContainerColor = Color(0xFF1E2824)
                            )
                        )

                        if (translatedResult != null) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFF263832),
                                modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(translatedResult!!, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                        Text(translatedThaiPronunciation ?: "", color = NeonJade, fontSize = 11.sp)
                                    }
                                    IconButton(
                                        onClick = { viewModel.speakThaiText(translatedResult!!) }
                                    ) {
                                        Icon(Icons.Default.VolumeUp, contentDescription = "Audio", tint = ShwedagonGold)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // High Frequency AI Queries
            item {
                Text("မေးလေ့ရှိသော မေးခွန်းများ (Frequent Inquiries)", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }

            items(queries) { q ->
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
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                Icon(Icons.Default.RecordVoiceOver, contentDescription = "Voice", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(q.queryBurmese, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                            Surface(shape = RoundedCornerShape(6.dp), color = MaterialTheme.colorScheme.primaryContainer) {
                                Text(q.category, fontSize = 10.sp, color = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(q.answerBurmese, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 17.sp)

                        if (q.thaiPhrase != null) {
                            Spacer(modifier = Modifier.height(6.dp))
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
                                    Text("ထိုင်းလို ပြောဆိုရန်:", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                                    Text(q.thaiPhrase, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                }
                                IconButton(
                                    onClick = { viewModel.speakThaiText(q.thaiPhrase) },
                                    modifier = Modifier.size(30.dp)
                                ) {
                                    Icon(Icons.Default.VolumeUp, contentDescription = "Audio", modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                }
            }

            // Real-Time Phrasebook
            item {
                Text("လက်တွေ့သုံး မြန်မာ-ထိုင်း စကားပြောကျမ်း (Phrasebook)", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }

            items(phrasebook) { phrase ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(phrase.burmeseText, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text(phrase.thaiText, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                            Text("အသံထွက်: ${phrase.thaiPronunciationBurmese}", fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
                        }

                        IconButton(
                            onClick = { viewModel.speakThaiText(phrase.thaiText) },
                            modifier = Modifier.size(34.dp).background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                        ) {
                            Icon(Icons.Default.VolumeUp, contentDescription = "Play", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
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

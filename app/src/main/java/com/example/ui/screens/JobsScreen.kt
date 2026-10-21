package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.example.data.models.JobPost
import com.example.ui.theme.*
import com.example.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobsScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val jobs by viewModel.repository.jobs.collectAsState()
    val selectedJob by viewModel.selectedJob.collectAsState()

    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Factory", "Restaurant", "Cleaning", "Scam Warning")

    val filteredJobs = jobs.filter { job ->
        if (selectedCategory == "All") true
        else if (selectedCategory == "Scam Warning") job.hasScamAlert
        else job.category.equals(selectedCategory, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Myanmar Jobs & Employment Hub", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("တရားဝင် စစ်ဆေးပြီး အလုပ်အကိုင်များနှင့် လိမ်လည်မှုသတိပေးချက်", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Anti-Scam Banner
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = GoldContainer),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(ShwedagonGold))
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = ShwedagonGold,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Shield, contentDescription = "Security", tint = Color.Black, modifier = Modifier.size(20.dp))
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("လုံခြုံစိတ်ချစွာ အလုပ်လျှောက်ထားရန်:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = OnGoldContainer)
                            Text("အလုပ်မဆင်းမီ ငွေကြိုတင်လွှဲခိုင်းသော အွန်လိုင်းပွဲစားများကို လုံးဝ ငွေမလွှဲပါနှင့်။ စစ်ဆေးပြီး အလုပ်ရှင် တံဆိပ်ကို သတိပြုပါ။", fontSize = 11.sp, color = OnGoldContainer.copy(alpha = 0.85f))
                        }
                    }
                }
            }

            // Category Filter Chips
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(categories) { cat ->
                        val isSelected = selectedCategory == cat
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedCategory = cat },
                            label = {
                                Text(
                                    text = when (cat) {
                                        "All" -> "အားလုံး (${jobs.size})"
                                        "Factory" -> "စက်ရုံလုပ်ငန်း"
                                        "Restaurant" -> "စားသောက်ဆိုင်"
                                        "Cleaning" -> "သန့်ရှင်းရေး"
                                        "Scam Warning" -> "⚠️ လိမ်လည်မှုသတိပေးချက်"
                                        else -> cat
                                    },
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = if (cat == "Scam Warning") EmergencyRed else MaterialTheme.colorScheme.primary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            // Jobs List
            items(filteredJobs) { job ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.selectJob(job) }
                        .testTag("job_card_${job.id}"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (job.hasScamAlert) Color(0xFFFFF0F0) else MaterialTheme.colorScheme.surface
                    ),
                    border = if (job.hasScamAlert) CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(EmergencyRed)) else null,
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                if (job.hasScamAlert) {
                                    Surface(
                                        color = EmergencyRed,
                                        shape = RoundedCornerShape(4.dp),
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    ) {
                                        Text(
                                            "⚠️ သတိပေးချက်: လိမ်လည်မှု ရှောင်ရှားရန်",
                                            color = Color.White,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = job.titleBurmese,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = if (job.hasScamAlert) EmergencyRed else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "${job.companyName} • ${job.locationArea}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            IconButton(
                                onClick = { viewModel.repository.toggleJobSaved(job.id) },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = if (job.isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                    contentDescription = "Save Job",
                                    tint = if (job.isSaved) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Salary & Tags
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "${job.salaryThb} THB",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 16.sp,
                                    color = if (job.hasScamAlert) EmergencyRed else MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "(${job.salaryType})",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }

                            if (job.isVerifiedEmployer) {
                                Surface(
                                    color = JadeContainer,
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Default.Verified, contentDescription = "Verified", tint = JadeGreen, modifier = Modifier.size(12.dp))
                                        Spacer(modifier = Modifier.width(3.dp))
                                        Text("စစ်ဆေးပြီး အလုပ်ရှင်", fontSize = 10.sp, color = JadeGreen, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }

                        if (job.scamWarningText != null) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = job.scamWarningText,
                                fontSize = 12.sp,
                                color = EmergencyRed,
                                lineHeight = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Action Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Train, contentDescription = "Nearby", tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(job.nearestStation, fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(
                                    onClick = { viewModel.dialPhoneNumber(context, job.contactPhone) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (job.hasScamAlert) EmergencyRed else MaterialTheme.colorScheme.primary
                                    ),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                    modifier = Modifier.height(34.dp)
                                ) {
                                    Icon(Icons.Default.Phone, contentDescription = "Call", modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(if (job.hasScamAlert) "တိုင်ကြားရန်" else "ဆက်သွယ်ရန်", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
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

    // Job Detail Modal
    if (selectedJob != null) {
        val job = selectedJob!!
        AlertDialog(
            onDismissRequest = { viewModel.selectJob(null) },
            confirmButton = {
                Button(
                    onClick = { viewModel.dialPhoneNumber(context, job.contactPhone) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (job.hasScamAlert) EmergencyRed else MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(Icons.Default.Phone, contentDescription = "Call", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("ဖုန်းခေါ်ဆိုရန် (${job.contactPhone})")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.selectJob(null) }) {
                    Text("ပိတ်မည်")
                }
            },
            title = {
                Text(job.titleBurmese, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            },
            text = {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        Text("🏢 ကုမ္ပဏီ: ${job.companyName}", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        Text("📍 တည်နေရာ: ${job.locationArea} (အနီးဆုံး: ${job.nearestStation})", fontSize = 12.sp)
                        Text("💰 လုပ်ခလစာ: ${job.salaryThb} THB (${job.salaryType})", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text("⏱️ အချိန်ပို (OT): ${job.otPayThbPerHour} THB / နာရီ", fontSize = 12.sp)
                        Divider(modifier = Modifier.padding(vertical = 6.dp))
                    }

                    item {
                        Text("လိုအပ်ချက်များ (Requirements):", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        job.requirementsBurmese.forEach { req ->
                            Text("• $req", fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("ခံစားခွင့်များ (Benefits):", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        job.benefitsBurmese.forEach { ben ->
                            Text("• $ben", fontSize = 12.sp)
                        }
                    }
                }
            }
        )
    }
}

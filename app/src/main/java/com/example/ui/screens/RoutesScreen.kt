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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.TransitStation
import com.example.data.models.TransitType
import com.example.ui.theme.*
import com.example.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutesScreen(viewModel: MainViewModel) {
    val stations by viewModel.repository.stations.collectAsState()
    val originStation by viewModel.selectedOriginStation.collectAsState()
    val destStation by viewModel.selectedDestStation.collectAsState()
    val routePlan by viewModel.calculatedRoute.collectAsState()
    val rate by viewModel.repository.currentThbToMmkRate.collectAsState()

    var showOriginPicker by remember { mutableStateOf(false) }
    var showDestPicker by remember { mutableStateOf(false) }
    var selectedTransitTab by remember { mutableStateOf(TransitType.BTS) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Transit Route & Fare Planner", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("ဘန်ကောက် ရထား/ဘတ်စ်ကား/လှေ ခရီးစဉ်နှင့် ယာဉ်ခ တွက်ချက်ခြင်း", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
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
            // Mode Selector
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(TransitType.values()) { type ->
                        val isSelected = selectedTransitTab == type
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                            contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { selectedTransitTab = type }
                                .testTag("transit_tab_${type.name}")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = when (type) {
                                        TransitType.BTS -> Icons.Default.DirectionsTransit
                                        TransitType.MRT -> Icons.Default.Subway
                                        TransitType.ARL -> Icons.Default.FlightLand
                                        TransitType.BOAT -> Icons.Default.DirectionsBoat
                                        TransitType.BUS -> Icons.Default.DirectionsBus
                                        TransitType.WIN_MOTO -> Icons.Default.TwoWheeler
                                    },
                                    contentDescription = type.name,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(type.titleEnglish, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }

            // Origin / Destination Route Input Box
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "ခရီးစဉ် စတင်ရာနှင့် သွားလိုသည့် နေရာ ရွေးချယ်ပါ",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        // Origin Selector
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                                .clickable { showOriginPicker = true }
                                .padding(12.dp)
                                .testTag("origin_selector"),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier.size(12.dp).clip(CircleShape).background(SafeGreen)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text("စတင်မည့် ဘူတာ (From)", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                                Text(
                                    text = originStation?.let { "${it.nameBurmese} (${it.nameEnglish})" } ?: "ရွေးချယ်ပါ",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Select")
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Destination Selector
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                                .clickable { showDestPicker = true }
                                .padding(12.dp)
                                .testTag("dest_selector"),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier.size(12.dp).clip(CircleShape).background(EmergencyRed)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text("ဆိုက်ရောက်မည့် ဘူတာ (To)", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                                Text(
                                    text = destStation?.let { "${it.nameBurmese} (${it.nameEnglish})" } ?: "ရွေးချယ်ပါ",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Select")
                        }
                    }
                }
            }

            // Fare & Summary Card
            if (routePlan != null) {
                val plan = routePlan!!
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.DirectionsTransit,
                                        contentDescription = "Train",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "${plan.estimatedDurationMin} မိနစ် ခရီးစဉ်",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "${plan.fareThb} THB",
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 20.sp,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "(~${plan.fareMmkEstimate} Ks)",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }

                            Divider(modifier = Modifier.padding(vertical = 10.dp), color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))

                            Text(
                                text = "ခရီးစဉ်အဆင့်ဆင့် လမ်းညွှန်:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Spacer(modifier = Modifier.height(6.dp))

                            plan.stepsBurmese.forEachIndexed { index, step ->
                                Row(
                                    modifier = Modifier.padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(20.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text("${index + 1}", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = step,
                                        fontSize = 13.sp,
                                        lineHeight = 18.sp,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                TextButton(
                                    onClick = {
                                        val phrase = "ไปสถานี ${destStation?.nameThai ?: ""} ค่าโดยสารเท่าไหร่ครับ"
                                        viewModel.speakThaiText(phrase)
                                    }
                                ) {
                                    Icon(Icons.Default.VolumeUp, contentDescription = "Pronounce", modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("ထိုင်းအသံထွက် ဖွင့်ရန်", fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }

            // Live Line Status & Tips
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "ဘန်ကောက် အများသုံး သယ်ယူပို့ဆောင်ရေး အချက်အလက်များ",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        val transitGuides = listOf(
                            "🟢 BTS SkyTrain: မနက် ၀၆:၀၀ မှ သန်းခေါင်ယံ ၁၂:၀၀ အထိ ပြေးဆွဲသည်။ Rabbit Card ဖြင့် စီးနင်းနိုင်ပါသည်။",
                            "🔵 MRT Subway: မနက် ၀၆:၀၀ မှ သန်းခေါင်ယံ ၁၂:၀၀ အထိ။ EMV Visa/Mastercard ဖြင့် တိုက်ရိုက်ဖြတ်စီးနိုင်သည်။",
                            "🚤 Chao Phraya Boat: စသွန် (Sathorn) ဗဟိုလှေဆိပ်မှ ပြေးဆွဲသည်။ ယာဉ်ခ ၁၆ မှ ၃၀ ဘတ်ခန့်။",
                            "🛵 Win Motorcycle: မီတာမပါဘဲ သတ်မှတ်နှုန်းထား ရှိသည်။ စီးနင်းခ မမေးမီ 'Pai... thao rai khrap?' ဟု မေးမြန်းပါ။"
                        )

                        transitGuides.forEach { guide ->
                            Text(
                                text = guide,
                                fontSize = 12.sp,
                                lineHeight = 17.sp,
                                modifier = Modifier.padding(vertical = 4.dp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    // Origin Picker Dialog
    if (showOriginPicker) {
        AlertDialog(
            onDismissRequest = { showOriginPicker = false },
            confirmButton = {},
            title = { Text("စတင်မည့် ဘူတာ ရွေးချယ်ပါ", fontWeight = FontWeight.Bold) },
            text = {
                LazyColumn(modifier = Modifier.height(280.dp)) {
                    items(stations) { station ->
                        ListItem(
                            headlineContent = { Text(station.nameBurmese, fontWeight = FontWeight.Bold, fontSize = 13.sp) },
                            supportingContent = { Text("${station.nameEnglish} • ${station.lineName}", fontSize = 11.sp) },
                            modifier = Modifier.clickable {
                                viewModel.setOriginStation(station)
                                showOriginPicker = false
                            }
                        )
                        Divider()
                    }
                }
            }
        )
    }

    // Destination Picker Dialog
    if (showDestPicker) {
        AlertDialog(
            onDismissRequest = { showDestPicker = false },
            confirmButton = {},
            title = { Text("ဆိုက်ရောက်မည့် ဘူတာ ရွေးချယ်ပါ", fontWeight = FontWeight.Bold) },
            text = {
                LazyColumn(modifier = Modifier.height(280.dp)) {
                    items(stations) { station ->
                        ListItem(
                            headlineContent = { Text(station.nameBurmese, fontWeight = FontWeight.Bold, fontSize = 13.sp) },
                            supportingContent = { Text("${station.nameEnglish} • ${station.lineName}", fontSize = 11.sp) },
                            modifier = Modifier.clickable {
                                viewModel.setDestStation(station)
                                showDestPicker = false
                            }
                        )
                        Divider()
                    }
                }
            }
        )
    }
}

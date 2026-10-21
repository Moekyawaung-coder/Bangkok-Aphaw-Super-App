package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.LittleMyanmarPlace
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppTab
import com.example.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val places by viewModel.repository.littleMyanmarPlaces.collectAsState()
    val stations by viewModel.repository.stations.collectAsState()
    val selectedPlace by viewModel.selectedPlace.collectAsState()
    val selectedFilter by viewModel.selectedCategoryFilter.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    val zoneOptions = listOf("All", "Phra Khanong", "Bang Bon / Mahachai", "Pratunam", "Sathon", "Nonthaburi", "Onnut")

    val filteredPlaces = places.filter { place ->
        val matchesCategory = if (selectedFilter == "All") true else place.area.contains(selectedFilter, ignoreCase = true)
        val matchesSearch = if (searchQuery.isBlank()) true else {
            place.nameBurmese.contains(searchQuery, ignoreCase = true) ||
            place.nameEnglish.contains(searchQuery, ignoreCase = true) ||
            place.address.contains(searchQuery, ignoreCase = true)
        }
        matchesCategory && matchesSearch
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Bangkok Myanmar Hub",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "ဘန်ကောက် မြန်မာ ဟပ် • လမ်းညွှန်နှင့် အသိုက်အဝန်း",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        IconButton(
                            onClick = { viewModel.isGoldenPassDialogVisible.value = true },
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(ShwedagonGold)
                                .testTag("golden_pass_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Golden Pass VIP",
                                tint = Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        IconButton(
                            onClick = { viewModel.isDevDeployGuideOpen.value = true },
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surface)
                                .testTag("deploy_guide_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Deploy Guide & Credits",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Button(
                            onClick = { viewModel.dialPhoneNumber(context, "191") },
                            colors = ButtonDefaults.buttonColors(containerColor = EmergencyRed),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            modifier = Modifier.height(36.dp).testTag("quick_sos_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "SOS",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("SOS 191", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.setSearchQuery(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("home_search_input"),
                    placeholder = { Text("မြန်မာဆိုင်၊ ဈေး၊ ဆေးခန်း၊ သံရုံး ရှာဖွေပါ...", fontSize = 13.sp) },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.primary)
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.setSearchQuery("") }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear")
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Interactive Map Visualizer Canvas
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(SafeGreen)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Bangkok Transit & Little Myanmar Map",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                            Text(
                                text = "အပြန်အလှန် မြေပုံလွှာ",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Custom Drawn Transit Map Canvas
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(170.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF0F1A17))
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val w = size.width
                                val h = size.height

                                // 1. Chao Phraya River (Blue Curved Ribbon)
                                val riverPath = Path().apply {
                                    moveTo(w * 0.28f, 0f)
                                    cubicTo(w * 0.32f, h * 0.35f, w * 0.20f, h * 0.65f, w * 0.26f, h)
                                }
                                drawPath(
                                    path = riverPath,
                                    color = Color(0xFF007799).copy(alpha = 0.55f),
                                    style = Stroke(width = 24.dp.toPx())
                                )

                                // 2. BTS Sukhumvit Green Line
                                val btsGreenPath = Path().apply {
                                    moveTo(w * 0.40f, h * 0.08f) // Mo Chit
                                    lineTo(w * 0.50f, h * 0.45f) // Siam
                                    lineTo(w * 0.68f, h * 0.55f) // Asok
                                    lineTo(w * 0.85f, h * 0.75f) // Phra Khanong & On Nut
                                }
                                drawPath(
                                    path = btsGreenPath,
                                    color = Color(0xFF00C853),
                                    style = Stroke(width = 6.dp.toPx())
                                )

                                // 3. BTS Silom Dark Green Line
                                val btsSilomPath = Path().apply {
                                    moveTo(w * 0.50f, h * 0.45f) // Siam
                                    lineTo(w * 0.42f, h * 0.70f) // Surasak / Embassy
                                    lineTo(w * 0.28f, h * 0.78f) // Saphan Taksin Pier
                                    lineTo(w * 0.15f, h * 0.85f) // Bang Wa
                                }
                                drawPath(
                                    path = btsSilomPath,
                                    color = Color(0xFF2E7D32),
                                    style = Stroke(width = 5.dp.toPx())
                                )

                                // 4. MRT Blue Line
                                val mrtBluePath = Path().apply {
                                    moveTo(w * 0.35f, h * 0.15f)
                                    lineTo(w * 0.68f, h * 0.55f) // Sukhumvit Interchange
                                    lineTo(w * 0.35f, h * 0.68f) // Hua Lamphong
                                    lineTo(w * 0.15f, h * 0.85f) // Bang Wa
                                }
                                drawPath(
                                    path = mrtBluePath,
                                    color = Color(0xFF1976D2),
                                    style = Stroke(width = 4.dp.toPx())
                                )

                                // Key Station Indicator Nodes
                                drawCircle(Color.White, radius = 5.dp.toPx(), center = Offset(w * 0.40f, h * 0.08f))
                                drawCircle(Color(0xFFFFD54F), radius = 7.dp.toPx(), center = Offset(w * 0.50f, h * 0.45f)) // Siam
                                drawCircle(Color.White, radius = 5.dp.toPx(), center = Offset(w * 0.68f, h * 0.55f)) // Asok
                                drawCircle(Color(0xFFFF5252), radius = 6.dp.toPx(), center = Offset(w * 0.42f, h * 0.70f)) // Surasak (Embassy)
                                drawCircle(Color(0xFF69F0AE), radius = 6.dp.toPx(), center = Offset(w * 0.85f, h * 0.75f)) // Phra Khanong
                                drawCircle(Color(0xFFFF9100), radius = 6.dp.toPx(), center = Offset(w * 0.12f, h * 0.90f)) // Mahachai
                            }

                            // Overlay Labels
                            Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                                Surface(
                                    color = Color.Black.copy(alpha = 0.7f),
                                    shape = RoundedCornerShape(6.dp),
                                    modifier = Modifier.align(Alignment.TopStart)
                                ) {
                                    Text(
                                        "🟢 BTS Mo Chit (မြောက်ပိုင်း)",
                                        color = Color.White,
                                        fontSize = 9.sp,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }

                                Surface(
                                    color = EmergencyRed.copy(alpha = 0.85f),
                                    shape = RoundedCornerShape(6.dp),
                                    modifier = Modifier.align(Alignment.CenterStart).offset(y = 20.dp, x = 30.dp)
                                ) {
                                    Text(
                                        "🏛️ မြန်မာသံရုံး (Surasak)",
                                        color = Color.White,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }

                                Surface(
                                    color = JadeGreen.copy(alpha = 0.85f),
                                    shape = RoundedCornerShape(6.dp),
                                    modifier = Modifier.align(Alignment.BottomEnd).offset(y = (-10).dp)
                                ) {
                                    Text(
                                        "🇲🇲 ပရာခနောင်း မြန်မာဈေး (PK)",
                                        color = Color.White,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Quick Navigation Bar to Routes
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedButton(
                                onClick = { viewModel.isOfflineMapDialogVisible.value = true },
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                modifier = Modifier.height(32.dp).testTag("home_offline_maps_button")
                            ) {
                                Icon(Icons.Default.Download, contentDescription = "Offline Maps", modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("အော့ဖ်လိုင်းမြေပုံ", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }

                            FilledTonalButton(
                                onClick = { viewModel.selectTab(AppTab.ROUTES) },
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                modifier = Modifier.height(32.dp).testTag("plan_route_button")
                            ) {
                                Text("လမ်းကြောင်းတွက်မည်", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // Area Filter Chips
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(zoneOptions) { zone ->
                        val isSelected = selectedFilter == zone
                        FilterChip(
                            selected = isSelected,
                            onClick = { viewModel.setCategoryFilter(zone) },
                            label = {
                                Text(
                                    text = when (zone) {
                                        "All" -> "အားလုံး (All)"
                                        "Phra Khanong" -> "ပရာခနောင်း (PK)"
                                        "Bang Bon / Mahachai" -> "မဟာချိုင် (Mahachai)"
                                        "Pratunam" -> "ပရာတူနမ် (Pratunam)"
                                        "Sathon" -> "သံရုံး / ဆာသွန်"
                                        else -> zone
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

            // Places List Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Little Myanmar Directory (${filteredPlaces.size} နေရာ)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "စစ်ဆေးပြီး အချက်အလက်",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Places List Cards
            items(filteredPlaces) { place ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .clickable { viewModel.selectPlace(place) }
                        .testTag("place_card_${place.id}"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = place.nameBurmese,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = place.nameEnglish,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Rating",
                                        tint = ShwedagonGold,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = "${place.rating}",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = place.descriptionBurmese,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                        )

                        if (place.features.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                place.features.forEach { feat ->
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = MaterialTheme.colorScheme.surfaceVariant
                                    ) {
                                        Text(
                                            text = feat,
                                            fontSize = 9.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.DirectionsTransit,
                                    contentDescription = "Transit",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = place.nearestTransit,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                IconButton(
                                    onClick = { viewModel.dialPhoneNumber(context, place.phone) },
                                    modifier = Modifier.size(34.dp).background(JadeContainer, CircleShape)
                                ) {
                                    Icon(
                                        Icons.Default.Phone,
                                        contentDescription = "Call",
                                        tint = JadeGreen,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }

                                FilledTonalButton(
                                    onClick = { viewModel.selectPlace(place) },
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                    modifier = Modifier.height(34.dp)
                                ) {
                                    Text("အသေးစိတ်", fontSize = 11.sp)
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

    // Place Detail Bottom Sheet Dialog
    if (selectedPlace != null) {
        val place = selectedPlace!!
        AlertDialog(
            onDismissRequest = { viewModel.selectPlace(null) },
            confirmButton = {
                Button(
                    onClick = { viewModel.dialPhoneNumber(context, place.phone) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.Phone, contentDescription = "Call", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("ဖုန်းခေါ်ဆိုရန် (${place.phone})")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.selectPlace(null) }) {
                    Text("ပိတ်မည်")
                }
            },
            title = {
                Column {
                    Text(text = place.nameBurmese, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(text = place.nameEnglish, fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = place.descriptionBurmese, fontSize = 14.sp)
                    Divider(modifier = Modifier.padding(vertical = 4.dp))
                    Row {
                        Text("📍 လိပ်စာ: ", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text(place.address, fontSize = 13.sp)
                    }
                    Row {
                        Text("🚆 အနီးဆုံး ရထား: ", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text(place.nearestTransit, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
                    }
                    Row {
                        Text("⏰ ဖွင့်ချိန်: ", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text(place.openingHours, fontSize = 13.sp)
                    }
                }
            }
        )
    }
}

package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import com.example.data.models.Chapter
import com.example.ui.theme.*
import com.example.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoldenJourneyScreen(viewModel: MainViewModel) {
    val avatar by viewModel.repository.avatarProfile.collectAsState()
    val chapters by viewModel.repository.chapters.collectAsState()
    val thaiLessons by viewModel.repository.thaiLessons.collectAsState()

    var showOutfitDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("ရွှေခရီး (Golden Journey)", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("ထိုင်းရောက် မြန်မာများအတွက် ဂိမ်းပုံစံ ဘဝလမ်းပြ Navigator", fontSize = 11.sp, color = ShwedagonGold)
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
            // Hero RPG Avatar Card
            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = NightSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier.testTag("avatar_hero_card")
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
                                    color = ShwedagonGold,
                                    modifier = Modifier.size(54.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = "🇲🇲",
                                            fontSize = 28.sp
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = avatar.name,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = Color.White
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = ShwedagonGold
                                        ) {
                                            Text(
                                                text = "Level ${avatar.level}",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.Black,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "ဝတ်စုံ: ${avatar.outfitStyle}",
                                        fontSize = 11.sp,
                                        color = NeonJade
                                    )
                                }
                            }

                            FilledTonalButton(
                                onClick = { showOutfitDialog = true },
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                modifier = Modifier.height(32.dp).testTag("change_outfit_button")
                            ) {
                                Text("ဝတ်စုံလဲမည်", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // XP Progress Bar
                        val progress = avatar.currentXp.toFloat() / avatar.nextLevelXp.toFloat()
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("ရွှေခရီး အဆင့်မြှင့်တင်မှု XP", fontSize = 11.sp, color = Color.White.copy(alpha = 0.8f))
                                Text("${avatar.currentXp} / ${avatar.nextLevelXp} XP", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ShwedagonGold)
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            LinearProgressIndicator(
                                progress = { progress.coerceIn(0f, 1f) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = ShwedagonGold,
                                trackColor = Color.DarkGray
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Streaks & Stats Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("🔥", fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("${avatar.streakDays} ရက် ရက်ဆက်", fontSize = 12.sp, color = Color.White)
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("🪙", fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("${avatar.coins} အမှတ်", fontSize = 12.sp, color = ShwedagonGold, fontWeight = FontWeight.Bold)
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("🦚", fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(avatar.companion, fontSize = 11.sp, color = Color.White)
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Golden Pass VIP Banner
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = ShwedagonGold.copy(alpha = 0.15f),
                            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(ShwedagonGold)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.isGoldenPassDialogVisible.value = true }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Star, contentDescription = "VIP", tint = ShwedagonGold, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = avatar.currentTierName,
                                        color = ShwedagonGold,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                }
                                Text(
                                    text = "အဆင့်မြှင့် / စီမံမည် >",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }

            // Daily Thai Micro-Lesson with Audio
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.School, contentDescription = "Learn", tint = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("နေ့စဉ် ထိုင်းစကားလုံး လေ့လာရန်", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            }
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = JadeContainer
                            ) {
                                Text("+50 XP", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = JadeGreen, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        thaiLessons.take(3).forEach { lesson ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                                    .clickable { viewModel.speakThaiText(lesson.thaiText) }
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = lesson.thaiText, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Text(text = "အသံထွက်: ${lesson.thaiPhoneticBurmese}", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                                    Text(text = "အဓိပ္ပာယ်: ${lesson.burmeseMeaning}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }

                                IconButton(
                                    onClick = { viewModel.speakThaiText(lesson.thaiText) },
                                    modifier = Modifier.size(34.dp).background(MaterialTheme.colorScheme.primary, CircleShape)
                                ) {
                                    Icon(Icons.Default.VolumeUp, contentDescription = "Audio", tint = Color.White, modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                }
            }

            // Life Quest Chapters
            item {
                Text(
                    text = "ဘဝခရီး အောင်မြင်ရေး Quests (Chapters)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            items(chapters) { chapter ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = chapter.titleBurmese,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "အခန်း ${chapter.id}",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }

                        Text(
                            text = chapter.descriptionBurmese,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )

                        Divider(modifier = Modifier.padding(vertical = 8.dp))

                        // Quests List inside Chapter
                        chapter.quests.forEach { quest ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(
                                        if (quest.isCompleted) JadeContainer.copy(alpha = 0.5f)
                                        else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                                    )
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    modifier = Modifier.weight(1f),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = quest.isCompleted,
                                        onCheckedChange = { viewModel.repository.completeQuest(quest.id) },
                                        modifier = Modifier.testTag("quest_check_${quest.id}")
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Column {
                                        Text(
                                            text = quest.titleBurmese,
                                            fontWeight = if (quest.isCompleted) FontWeight.Normal else FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = if (quest.isCompleted) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = quest.practicalTip,
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = if (quest.isCompleted) SafeGreen else ShwedagonGold
                                ) {
                                    Text(
                                        text = "+${quest.xpReward} XP",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (quest.isCompleted) Color.White else Color.Black,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
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

    // Outfit Customization Dialog
    if (showOutfitDialog) {
        val outfits = listOf(
            "Traditional Longyi & Taikpon",
            "Acheik Silk (ရိုးရာ အချိတ်)",
            "Bangkok Smart Casual",
            "Modern Streetwear"
        )
        AlertDialog(
            onDismissRequest = { showOutfitDialog = false },
            confirmButton = {
                TextButton(onClick = { showOutfitDialog = false }) {
                    Text("ပြီးပါပြီ")
                }
            },
            title = { Text("ရိုးရာ ဝတ်စုံ ရွေးချယ်ပါ", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    outfits.forEach { outfit ->
                        val isSelected = avatar.outfitStyle == outfit
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent)
                                .clickable {
                                    viewModel.repository.updateOutfit(outfit)
                                    showOutfitDialog = false
                                }
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(outfit, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
                            if (isSelected) {
                                Icon(Icons.Default.Check, contentDescription = "Selected", tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
            }
        )
    }
}

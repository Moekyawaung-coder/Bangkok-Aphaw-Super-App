package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
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
import com.example.data.models.GoldenPassTier
import com.example.ui.theme.JadeGreen
import com.example.ui.theme.NightSurface
import com.example.ui.theme.SafeGreen
import com.example.ui.theme.ShwedagonGold
import com.example.ui.viewmodel.MainViewModel

@Composable
fun GoldenPassDialog(viewModel: MainViewModel) {
    val context = LocalContext.current
    val tiers by viewModel.repository.goldenPassTiers.collectAsState()
    val avatar by viewModel.repository.avatarProfile.collectAsState()

    AlertDialog(
        onDismissRequest = { viewModel.isGoldenPassDialogVisible.value = false },
        confirmButton = {
            Button(
                onClick = { viewModel.isGoldenPassDialogVisible.value = false },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("ပိတ်မည် (Close)")
            }
        },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = "VIP", tint = ShwedagonGold)
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text("ရွှေခရီး Pass (Golden Membership)", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("ပရီမီယံ ဝန်ဆောင်မှုများနှင့် အကျိုးခံစားခွင့်များ", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                    }
                }
                IconButton(onClick = { viewModel.isGoldenPassDialogVisible.value = false }) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().heightIn(max = 440.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(tiers) { tier ->
                    val isCurrentTier = avatar.currentTierName.contains(tier.titleEnglish.split(" ").first(), ignoreCase = true) || tier.isCurrent
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isCurrentTier) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                        ),
                        border = if (tier.isRecommended) CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(ShwedagonGold)) else null,
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    if (tier.isRecommended) {
                                        Surface(
                                            shape = RoundedCornerShape(4.dp),
                                            color = ShwedagonGold,
                                            modifier = Modifier.padding(bottom = 4.dp)
                                        ) {
                                            Text(
                                                "အကြံပြုထားသော ပက်ကေ့ခ်ျ (Recommended)",
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.Black,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                    Text(tier.titleBurmese, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text(tier.titleEnglish, fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text("${tier.priceThbMonthly} THB", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                                    Text("တစ်လလျှင်", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                                }
                            }

                            Divider(modifier = Modifier.padding(vertical = 8.dp))

                            tier.featuresBurmese.forEach { feat ->
                                Row(
                                    modifier = Modifier.padding(vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.Check, contentDescription = "Feature", tint = SafeGreen, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(feat, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Button(
                                onClick = {
                                    viewModel.repository.upgradeGoldenPass(tier.titleBurmese)
                                    Toast.makeText(context, "${tier.titleBurmese} ကို အောင်မြင်စွာ ရွေးချယ်ပြီးပါပြီ!", Toast.LENGTH_SHORT).show()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isCurrentTier) SafeGreen else MaterialTheme.colorScheme.primary
                                ),
                                modifier = Modifier.fillMaxWidth().height(36.dp).testTag("select_tier_${tier.id}")
                            ) {
                                Text(
                                    text = if (isCurrentTier) "လက်ရှိ အသုံးပြုနေသည် (Active)" else "စတင်အသုံးပြုမည် (${tier.priceThbMonthly} THB/လ)",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

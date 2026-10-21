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
import com.example.ui.theme.JadeContainer
import com.example.ui.theme.JadeGreen
import com.example.ui.theme.SafeGreen
import com.example.ui.theme.ShwedagonGold
import com.example.ui.viewmodel.MainViewModel

@Composable
fun OfflineMapsDialog(viewModel: MainViewModel) {
    val mapPacks by viewModel.repository.offlineMapPacks.collectAsState()
    val totalDownloadedMb = mapPacks.filter { it.isDownloaded }.sumOf { it.sizeMb }

    AlertDialog(
        onDismissRequest = { viewModel.isOfflineMapDialogVisible.value = false },
        confirmButton = {
            Button(
                onClick = { viewModel.isOfflineMapDialogVisible.value = false },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("ပြီးပါပြီ (Done)")
            }
        },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("အော့ဖ်လိုင်း မြေပုံများ (Offline Maps)", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("အင်တာနက်မရှိဘဲ လမ်းညွှန်ကြည့်ရှုနိုင်သော မြေပုံလွှာများ", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = { viewModel.isOfflineMapDialogVisible.value = false }) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().heightIn(max = 420.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Storage Usage Header
                item {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = JadeContainer)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.SdStorage, contentDescription = "Storage", tint = JadeGreen, modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text("ဒေါင်းလုဒ်ရယူပြီး သိုလှောင်မှု: $totalDownloadedMb MB", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = JadeGreen)
                                Text("အင်တာနက်လိုင်းမကောင်းချိန် သို့မဟုတ် ဒေတာချွေတာချိန်တွင် အသုံးဝင်ပါသည်", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }

                // Map Packs List
                items(mapPacks) { pack ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(pack.nameBurmese, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    if (pack.isPremiumOnly) {
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Surface(
                                            shape = RoundedCornerShape(4.dp),
                                            color = ShwedagonGold
                                        ) {
                                            Text("PRO", fontSize = 9.sp, fontWeight = FontWeight.ExtraBold, color = Color.Black, modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp))
                                        }
                                    }
                                }
                                Text(pack.nameEnglish, fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text("ဇုန်: ${pack.zone} • ${pack.sizeMb} MB", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Medium)
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            if (pack.isDownloaded) {
                                Button(
                                    onClick = { viewModel.repository.toggleDownloadMapPack(pack.id) },
                                    colors = ButtonDefaults.buttonColors(containerColor = SafeGreen),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                    modifier = Modifier.height(34.dp).testTag("map_pack_downloaded_${pack.id}")
                                ) {
                                    Icon(Icons.Default.Check, contentDescription = "Downloaded", modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("ရရှိပြီး", fontSize = 11.sp)
                                }
                            } else {
                                OutlinedButton(
                                    onClick = { viewModel.repository.toggleDownloadMapPack(pack.id) },
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                    modifier = Modifier.height(34.dp).testTag("map_pack_download_btn_${pack.id}")
                                ) {
                                    Icon(Icons.Default.Download, contentDescription = "Download", modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("ဒေါင်းမည်", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

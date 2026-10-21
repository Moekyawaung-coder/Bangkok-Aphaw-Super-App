package com.example.ui.screens

import android.widget.Toast
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
fun TelemedicineDialog(viewModel: MainViewModel) {
    val context = LocalContext.current
    val doctors by viewModel.repository.telemedDoctors.collectAsState()

    AlertDialog(
        onDismissRequest = { viewModel.isTelemedDialogVisible.value = false },
        confirmButton = {
            Button(
                onClick = { viewModel.isTelemedDialogVisible.value = false },
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
                    Icon(Icons.Default.MedicalServices, contentDescription = "Telemed", tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text("မြန်မာဆရာဝန်များနှင့် ဆေးခန်း (Telemedicine)", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Text("အွန်လိုင်းမှတစ်ဆင့် ဆေးကုသမှု ဆွေးနွေးတိုင်ပင်ရန်", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                    }
                }
                IconButton(onClick = { viewModel.isTelemedDialogVisible.value = false }) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().heightIn(max = 420.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(doctors) { doc ->
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        shape = CircleShape,
                                        color = if (doc.isOnlineNow) SafeGreen else Color.Gray,
                                        modifier = Modifier.size(10.dp)
                                    ) {}
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(doc.doctorName, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Star, contentDescription = "Rating", tint = ShwedagonGold, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Text("${doc.rating}", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))
                            Text(doc.specialtyBurmese, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Medium)
                            Text("ဆေးရုံ/ဌာန: ${doc.hospitalAffiliation}", fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
                            Text("ပြောဆိုနိုင်သော ဘာသာစကား: ${doc.languages.joinToString(", ")}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("ဆွေးနွေးချိန်: ${doc.availableHours}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("ဆွေးနွေးခ: ${doc.consultationFeeThb} THB", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)

                                Button(
                                    onClick = {
                                        Toast.makeText(context, "${doc.doctorName} နှင့် ဆွေးနွေးရန် ရက်ချိန်းယူပြီးပါပြီ (SMS/Line ပေးပို့ပါမည်)", Toast.LENGTH_LONG).show()
                                    },
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                    modifier = Modifier.height(34.dp).testTag("book_doctor_${doc.id}")
                                ) {
                                    Icon(Icons.Default.VideoCall, contentDescription = "Video", modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("ဆွေးနွေးမည်", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

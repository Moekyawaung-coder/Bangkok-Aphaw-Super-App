package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.JadeContainer
import com.example.ui.theme.JadeGreen
import com.example.ui.theme.SafeGreen
import com.example.ui.theme.ShwedagonGold
import com.example.ui.viewmodel.MainViewModel

@Composable
fun RemittanceCalculatorDialog(viewModel: MainViewModel) {
    val providers by viewModel.repository.remittanceProviders.collectAsState()
    val rate by viewModel.repository.currentThbToMmkRate.collectAsState()

    var thbInput by remember { mutableStateOf("10000") }
    val thbAmount = thbInput.toDoubleOrNull() ?: 0.0
    val mmkTotal = (thbAmount * rate).toInt()

    AlertDialog(
        onDismissRequest = { viewModel.isRemittanceCalculatorVisible.value = false },
        confirmButton = {
            Button(
                onClick = { viewModel.isRemittanceCalculatorVisible.value = false },
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
                    Icon(Icons.Default.CurrencyExchange, contentDescription = "Exchange", tint = ShwedagonGold)
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text("တရားဝင် ငွေလွှဲနှုန်းနှင့် တွက်ချက်စက်", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Text("1 THB = ${rate} MMK ကျပ် (နေ့စဉ်ပေါက်ဈေး)", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                    }
                }
                IconButton(onClick = { viewModel.isRemittanceCalculatorVisible.value = false }) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().heightIn(max = 440.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Input Converter Card
                item {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = JadeContainer)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text("လွှဲလိုသည့် ထိုင်းဘတ်ငွေ ရိုက်ထည့်ပါ (THB):", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = thbInput,
                                onValueChange = { thbInput = it },
                                modifier = Modifier.fillMaxWidth().testTag("remittance_thb_input"),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                trailingIcon = { Text("THB", fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 8.dp)) }
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                            Divider()
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("မြန်မာနိုင်ငံတွင် ရရှိမည့်ငွေ:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text(
                                    "${"%,d".format(mmkTotal)} MMK",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = JadeGreen
                                )
                            }
                            Text("ခန့်မှန်းခြေ: ${(mmkTotal / 100000.0).let { "%.2f".format(it) }} သိန်း ကျပ်", fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
                        }
                    }
                }

                item {
                    Text("တရားဝင် လိုင်စင်ရ ငွေလွှဲဌာနများနှင့် နှိုင်းယှဉ်ချက်:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }

                items(providers) { prov ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (!prov.isVerifiedLicense) Color(0xFFFFF0F0) else MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(prov.name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = if (prov.isVerifiedLicense) SafeGreen else EmergencyRed
                                ) {
                                    Text(
                                        text = if (prov.isVerifiedLicense) "လိုင်စင်ရ" else "⚠️ သတိထားရန်",
                                        color = Color.White,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))
                            Text("ငွေလဲနှုန်း: 1 THB = ${prov.rateThbToMmk} MMK • ဝန်ဆောင်ခ: ${prov.transferFeeThb} THB", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                            Text("ရောက်ရှိချိန်: ${prov.deliveryTimeBurmese}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("လုံခြုံရေး: ${prov.fraudRiskBurmese}", fontSize = 11.sp, color = if (!prov.isVerifiedLicense) EmergencyRed else SafeGreen)
                        }
                    }
                }
            }
        }
    )
}

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
import com.example.data.models.PosProduct
import com.example.ui.theme.*
import com.example.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessCircleScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val products by viewModel.repository.posProducts.collectAsState()
    val cart by viewModel.repository.cart.collectAsState()
    val staff by viewModel.repository.staffMembers.collectAsState()
    val suppliers by viewModel.repository.suppliers.collectAsState()
    val rate by viewModel.repository.currentThbToMmkRate.collectAsState()

    var activeTab by remember { mutableStateOf("POS") }
    val tabs = listOf("POS", "Daily P&L", "Staff & Permits", "B2B Wholesale")

    var showPromptPayDialog by remember { mutableStateOf(false) }

    val totalThb = cart.sumOf { it.product.priceThb * it.quantity }
    val totalMmk = (totalThb * rate).toInt()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("မြန်မာ စီးပွားရေး စက်ဝိုင်း (Business Circle)", fontWeight = FontWeight.Bold, fontSize = 17.sp)
                        Text("ဆိုင်ရှင်များနှင့် လုပ်ငန်းရှင်များအတွက် POS နှင့် ကုန်စည်ဒိုင်", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
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
            // Tab Selector Chips
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(tabs) { tab ->
                        val isSelected = activeTab == tab
                        FilterChip(
                            selected = isSelected,
                            onClick = { activeTab = tab },
                            label = {
                                Text(
                                    text = when (tab) {
                                        "POS" -> "အရောင်း POS & PromptPay"
                                        "Daily P&L" -> "နေ့စဉ် ဝင်ငွေ/အမြတ်"
                                        "Staff & Permits" -> "ဝန်ထမ်း ပါမစ်စစ်ဆေးရေး"
                                        "B2B Wholesale" -> "လက်ကား ကုန်စည်ဒိုင်"
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

            when (activeTab) {
                "POS" -> {
                    // Cart Summary Bar
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text("ခြင်းတောင်းစုစုပေါင်း (${cart.sumOf { it.quantity }} ခု)", fontSize = 12.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                                        Text("${totalThb.toInt()} THB", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, color = MaterialTheme.colorScheme.primary)
                                        Text("(~${totalMmk} MMK ကျပ်)", fontSize = 11.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                                    }

                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        if (cart.isNotEmpty()) {
                                            OutlinedButton(
                                                onClick = { viewModel.repository.clearCart() },
                                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                                modifier = Modifier.height(38.dp)
                                            ) {
                                                Text("ရှင်းမည်", fontSize = 11.sp)
                                            }
                                        }

                                        Button(
                                            onClick = { showPromptPayDialog = true },
                                            enabled = cart.isNotEmpty(),
                                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 4.dp),
                                            modifier = Modifier.height(38.dp).testTag("promptpay_checkout_button")
                                        ) {
                                            Icon(Icons.Default.QrCode, contentDescription = "QR", modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("PromptPay QR", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Text("ကုန်ပစ္စည်း ရွေးချယ်ရန် (Tap to Add)", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }

                    // Product Grid / List
                    items(products) { prod ->
                        val inCart = cart.find { it.product.id == prod.id }?.quantity ?: 0
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(prod.nameBurmese, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text("${prod.nameThai} • ${prod.category}", fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("${prod.priceThb.toInt()} THB / ${prod.unit}", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    if (inCart > 0) {
                                        IconButton(
                                            onClick = { viewModel.repository.removeFromCart(prod.id) },
                                            modifier = Modifier.size(32.dp).background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                                        ) {
                                            Text("-", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                        }
                                        Text("$inCart", fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.padding(horizontal = 10.dp))
                                    }

                                    Button(
                                        onClick = { viewModel.repository.addToCart(prod) },
                                        shape = CircleShape,
                                        contentPadding = PaddingValues(0.dp),
                                        modifier = Modifier.size(36.dp)
                                    ) {
                                        Icon(Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(18.dp))
                                    }
                                }
                            }
                        }
                    }
                }

                "Daily P&L" -> {
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("ယနေ့ အရောင်းနှင့် အမြတ် စာရင်း (Daily P&L)", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Spacer(modifier = Modifier.height(14.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text("စုစုပေါင်း ရောင်းရငွေ", fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
                                        Text("18,450 THB", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = SafeGreen)
                                    }
                                    Column {
                                        Text("ကုန်ကျစရိတ် (Cost)", fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
                                        Text("11,200 THB", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = EmergencyRed)
                                    }
                                    Column {
                                        Text("အသားတင် အမြတ်", fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
                                        Text("7,250 THB", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                                    }
                                }

                                Divider(modifier = Modifier.padding(vertical = 12.dp))

                                Text(
                                    text = "မြန်မာငွေလဲလှယ်နှုန်း ခန့်မှန်းချက် (1 THB = ${rate} MMK)",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ShwedagonGold
                                )
                                Text(
                                    text = "ယနေ့ အမြတ် 7,250 THB = ~${(7250 * rate).toInt()} ကျပ် (MMK)",
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                OutlinedButton(
                                    onClick = { viewModel.isRemittanceCalculatorVisible.value = true },
                                    modifier = Modifier.fillMaxWidth().height(36.dp).testTag("open_remittance_calc_pnl")
                                ) {
                                    Icon(Icons.Default.CurrencyExchange, contentDescription = "Exchange", modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("တရားဝင် ငွေလွှဲနှုန်း တွက်စက် ဖွင့်မည်", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }

                "Staff & Permits" -> {
                    item {
                        Text("ဝန်ထမ်းများနှင့် အလုပ်ပါမစ် သက်တမ်း စောင့်ကြည့်ရေး", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }

                    items(staff) { member ->
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
                                        Text(member.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                        Text(member.roleBurmese, fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = if (member.isPermitExpiringSoon) EmergencyRed else SafeGreen
                                    ) {
                                        Text(
                                            text = "${member.daysUntilExpiry} ရက် ကျန်",
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))
                                Text("ပါမစ် သက်တမ်းကုန်ဆုံးမည့်ရက်: ${member.permitExpiryDate}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("ဖုန်းနံပါတ်: ${member.phone}", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }

                "B2B Wholesale" -> {
                    items(suppliers) { sup ->
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
                                    Text(sup.businessName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Surface(shape = RoundedCornerShape(6.dp), color = JadeContainer) {
                                        Text("လက်ကားဒိုင်", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = JadeGreen, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("ကုန်စည်အမျိုးအစား: ${sup.goodsCategoryBurmese}", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Medium)
                                Text("📍 တည်နေရာ: ${sup.locationArea}", fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
                                Text("🚚 ပို့ဆောင်မှု: ${sup.deliverySupport}", fontSize = 12.sp)
                                Text("အနည်းဆုံး မှာယူမှု: ${sup.minOrderThb} THB", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Button(
                                        onClick = { viewModel.dialPhoneNumber(context, sup.phone) },
                                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                        modifier = Modifier.height(34.dp)
                                    ) {
                                        Icon(Icons.Default.Phone, contentDescription = "Call", modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("ဆက်သွယ်မှာယူရန်", fontSize = 11.sp)
                                    }
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

    // PromptPay QR Dialog
    if (showPromptPayDialog) {
        AlertDialog(
            onDismissRequest = { showPromptPayDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.repository.clearCart()
                        showPromptPayDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("ငွေပေးချေမှု အောင်မြင်သည် (Finish)")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPromptPayDialog = false }) {
                    Text("မလုပ်တော့ပါ")
                }
            },
            title = {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text("Thai PromptPay QR", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("ဘန်ကောက် မြန်မာ ဟပ် စတိုး", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                }
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Simulated QR Code Box
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color.White,
                        border = CardDefaults.outlinedCardBorder(),
                        modifier = Modifier.size(180.dp).padding(8.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Default.QrCode2,
                                contentDescription = "PromptPay QR",
                                modifier = Modifier.size(150.dp),
                                tint = Color.Black
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "ပေးချေရန်ငွေ: ${totalThb.toInt()} THB",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "PromptPay ID: 089-123-4567",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        )
    }
}

package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.JadeGreen
import com.example.ui.theme.NightDarkBg
import com.example.ui.theme.ShwedagonGold
import com.example.ui.viewmodel.MainViewModel

@Composable
fun DevDeployGuideDialog(viewModel: MainViewModel) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    AlertDialog(
        onDismissRequest = { viewModel.isDevDeployGuideOpen.value = false },
        confirmButton = {
            Button(
                onClick = { viewModel.isDevDeployGuideOpen.value = false },
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
                Column {
                    Text("Project Credits & Deployment", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("GitHub & Vercel အပြည့်အစုံ လမ်းညွှန်", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = { viewModel.isDevDeployGuideOpen.value = false }) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().heightIn(max = 420.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Credits Card
                item {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("PROJECT CREDITS", fontWeight = FontWeight.ExtraBold, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("• Ideas by DeepSeek", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                            Text("• Development by Moekyawaung", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                            Text("• Email: moekyawaung2027@gmail.com", fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.surface)
                                    .clickable {
                                        viewModel.openWebUrl(context, "https://github.com/Dev-moe-kyawaung/")
                                    }
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "https://github.com/Dev-moe-kyawaung/",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Icon(Icons.Default.OpenInBrowser, contentDescription = "Open", modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }

                // 4-in-1 Architecture Overview
                item {
                    Text("4-in-1 Super App Architecture:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    val modules = listOf(
                        "1. Bangkok Myanmar Hub: Transit routing (BTS, MRT, Boat, Win), Little Myanmar zones (Phra Khanong, Mahachai, Pratunam), verified job board.",
                        "2. Golden Journey (ရွှေခရီး): 5-chapter RPG gamified life navigation, XP rewards, streaks, avatar customizer, daily Thai micro-lessons.",
                        "3. Safe Harbor (ဘေးကင်းဆိပ်ကမ်း): One-Tap SOS (191, 1669, Embassy), 90-day report & work permit tracker, police checkpoint phrases, AI symptom triage, Thai medicine guide.",
                        "4. Business Circle (စီးပွားရေး စက်ဝိုင်း): Shopkeeper POS with PromptPay QR generator, daily P&L, staff work permit monitor, B2B wholesale suppliers."
                    )
                    modules.forEach { m ->
                        Text(m, fontSize = 11.sp, lineHeight = 16.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }

                // GitHub Deployment Commands
                item {
                    Text("GitHub Upload Commands:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    val gitCommands = """
git init
git add .
git commit -m "feat: Bangkok Myanmar Hub 4-in-1 Super App by Moekyawaung"
git branch -M main
git remote add origin https://github.com/Dev-moe-kyawaung/bangkok-myanmar-hub.git
git push -u origin main
                    """.trimIndent()

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF1E2824),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Terminal (Bash)", color = ShwedagonGold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                IconButton(
                                    onClick = {
                                        clipboardManager.setText(AnnotatedString(gitCommands))
                                    },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = Color.White, modifier = Modifier.size(14.dp))
                                }
                            }
                            Text(
                                gitCommands,
                                color = Color.White,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 10.sp,
                                lineHeight = 14.sp
                            )
                        }
                    }
                }

                // Vercel Deployment Instructions
                item {
                    Text("Vercel Web Companion Deployment:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    val vercelGuide = """
1. Push this project to GitHub: https://github.com/Dev-moe-kyawaung/
2. Log in to Vercel (https://vercel.com)
3. Click "Add New..." -> "Project" -> Import your repository
4. Set Framework Preset to "Next.js" or "Vite"
5. Add Environment Variables (GEMINI_API_KEY) in Vercel Settings
6. Click Deploy to go live with free SSL and edge CDN!
                    """.trimIndent()
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            vercelGuide,
                            fontSize = 11.sp,
                            lineHeight = 15.sp,
                            modifier = Modifier.padding(10.dp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    )
}

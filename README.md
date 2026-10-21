# Bangkok Myanmar Hub (ဘန်ကောက် မြန်မာ ဟပ်) 🇲🇲🇹🇭
### 4-in-1 Enterprise Super App for Myanmar People in Thailand

> **PROJECT CREDITS:**
> - **Ideas by:** DeepSeek
> - **Development by:** Moekyawaung
> - **Developer Email:** moekyawaung2027@gmail.com
> - **GitHub Repository:** https://github.com/Dev-moe-kyawaung/

---

## 🌟 Executive Overview & The 4 Pillars

Bangkok Myanmar Hub integrates four critical systems into a single seamless, accessible Android application:

1. **Bangkok Myanmar Hub (ဘန်ကောက် မြန်မာ ဟပ်)**
   - Multi-modal Bangkok transit routing: BTS SkyTrain, MRT Subway, Airport Rail Link (ARL), Chao Phraya Express & Khlong Saen Saep boats, BMTA City Buses, Win Motorcycle Taxis.
   - Live Fare Calculator: calculates route costs in THB with real-time Myanmar Kyats (MMK) conversion.
   - **Premium Offline Vector Map Packs:** Downloadable city zones (Bangkok Central 48MB, Mahachai Industrial 36MB, Sathon & Embassy 24MB, Phra Khanong & Bang Na 32MB, Nonthaburi Riverline 28MB, Eastern Corridor & Suvarnabhumi 42MB) for full internet-free navigation.
   - **Expanded "Little Myanmar" Verified Directory:** Real places covering **Phra Khanong (ပရာခနောင်း)**, **Mahachai Central (မဟာချိုင်)**, **Pratunam (ပရာတူနမ်)**, **Onnut & Bang Na**, **Sathon & Surasak**, and **Nonthaburi**. Features include authentic dining, Burmese medical desks (Yanhee Hospital), Mon-Myanmar monasteries (Wat Prok, Wat Yannawa), and authorized remittance agencies.
   - Verified Jobs & Employment: verified employer badges, scam warnings against predatory agents.

2. **Golden Journey (ရွှေခရီး) - Gamified Life Navigator & Golden Pass**
   - **Golden Pass Subscription Tiers:**
     * *Golden Worker Pass (49 THB/mo):* Unlimited offline vector map packs, ad-free experience, 2x XP quest boost, 24/7 AI voice concierge.
     * *Guardian Family Plus (99 THB/mo):* Unlimited Telemedicine consultations, priority legal aid matching, 5-member family emergency circle.
     * *Business Circle Pro (199 THB/mo):* Multi-branch POS, staff permit alerts, B2B wholesale discounts.
   - 5-Chapter Life Arc ("From Newcomer to Golden Citizen").
   - RPG Hero Profile & Avatar with Level progression, XP rewards, streaks, and Myanmar traditional attire.
   - Daily Thai Micro-Lessons with native audio pronunciation via Android Text-To-Speech (TTS).

3. **Safe Harbor (ဘေးကင်းဆိပ်ကမ်း) - Crisis, Legal & Health Guardian**
   - One-Tap Emergency SOS: Instant dialing for Thai Police (`191`), Ambulance (`1669`), Tourist Police (`1155`), Myanmar Embassy Bangkok (`02-637-9404`), and Labor Helpline (`1506`).
   - **Telemedicine & Burmese Doctor Network:** Direct video/chat medical consultations with certified Burmese doctors in Thailand (General Medicine, Occupational Health, Trauma, Mental Health).
   - **Live Remittance Calculator & Safety Guide:** Interactive THB ↔ MMK converter with fee breakdown and safety comparison (DeeMoney, TrueMoney, Bank-to-Bank vs predatory unverified hundis).
   - Legal Document Expiry Tracker: 90-Day Reports (တင်ပြချက် ၉၀ ရက်), Work Permits (ဘတ်ပန်းရောင်), and Visas.
   - Police Checkpoint Survival Guide with audio and phonetic pronunciation.
   - AI Health Triage & Thai-Burmese Medicine Guide.

4. **Myanmar Business Circle (မြန်မာ စီးပွားရေး စက်ဝိုင်း)**
   - Mobile POS & Thai PromptPay QR generator.
   - Daily Sales & P&L tracker with THB ↔ MMK exchange rates.
   - Staff Work Permit expiry monitor.
   - B2B Wholesale Suppliers Directory (tea leaves, dried fish, spices, cross-border logistics).

---

## 🚀 GitHub Upload & Deployment Guide

To push this complete Android project to your GitHub account:

```bash
# 1. Initialize git repository
git init

# 2. Add all source files
git add .

# 3. Commit with project credits
git commit -m "feat: complete Bangkok Myanmar Hub super app by Moekyawaung"

# 4. Set main branch
git branch -M main

# 5. Connect to your GitHub repository
git remote add origin https://github.com/Dev-moe-kyawaung/bangkok-myanmar-hub.git

# 6. Push to GitHub
git push -u origin main
```

---

## 🌐 Vercel Companion Web Deployment

If deploying a Next.js / React web companion for the admin panel or web view:
1. Fork or push this repository to GitHub: `https://github.com/Dev-moe-kyawaung/`
2. Open [Vercel Dashboard](https://vercel.com) and click **"Add New Project"**.
3. Select your repository and choose framework preset.
4. Add environment variables (`GEMINI_API_KEY`).
5. Click **Deploy**.

---

## 🛠️ Tech Stack & Android Architecture
- **Language:** Kotlin 2.0+
- **UI Toolkit:** Jetpack Compose with Material Design 3 (M3)
- **Architecture:** MVVM (Model-View-ViewModel) + Clean Data Layer + StateFlow
- **Audio & TTS:** Android native TextToSpeech engine configured for Thai (`th-TH`) and English
- **Graphics:** Custom Android Canvas Vector transit visualizer and dynamic badge rendering
- **Minimum SDK:** Android 7.0+ (API 24)
- **Target SDK:** Android 16 (API 36)

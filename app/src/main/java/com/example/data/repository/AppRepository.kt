package com.example.data.repository

import com.example.data.models.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppRepository {

    // Transit & Places
    private val _stations = MutableStateFlow<List<TransitStation>>(emptyList())
    val stations: StateFlow<List<TransitStation>> = _stations.asStateFlow()

    private val _littleMyanmarPlaces = MutableStateFlow<List<LittleMyanmarPlace>>(emptyList())
    val littleMyanmarPlaces: StateFlow<List<LittleMyanmarPlace>> = _littleMyanmarPlaces.asStateFlow()

    // Jobs
    private val _jobs = MutableStateFlow<List<JobPost>>(emptyList())
    val jobs: StateFlow<List<JobPost>> = _jobs.asStateFlow()

    // Golden Journey
    private val _avatarProfile = MutableStateFlow(
        AvatarProfile(
            name = "မောင်မောင် (Maung Maung)",
            level = 2,
            currentXp = 320,
            nextLevelXp = 500,
            streakDays = 7,
            outfitStyle = "Traditional Longyi & Taikpon",
            companion = "Royal Peacock (ဒေါင်း)",
            coins = 1250
        )
    )
    val avatarProfile: StateFlow<AvatarProfile> = _avatarProfile.asStateFlow()

    private val _chapters = MutableStateFlow<List<Chapter>>(emptyList())
    val chapters: StateFlow<List<Chapter>> = _chapters.asStateFlow()

    private val _thaiLessons = MutableStateFlow<List<ThaiLesson>>(emptyList())
    val thaiLessons: StateFlow<List<ThaiLesson>> = _thaiLessons.asStateFlow()

    // Safe Harbor
    private val _emergencyContacts = MutableStateFlow<List<EmergencyContact>>(emptyList())
    val emergencyContacts: StateFlow<List<EmergencyContact>> = _emergencyContacts.asStateFlow()

    private val _legalDocuments = MutableStateFlow<List<LegalDocument>>(emptyList())
    val legalDocuments: StateFlow<List<LegalDocument>> = _legalDocuments.asStateFlow()

    private val _checkpointPhrases = MutableStateFlow<List<CheckpointPhrase>>(emptyList())
    val checkpointPhrases: StateFlow<List<CheckpointPhrase>> = _checkpointPhrases.asStateFlow()

    private val _symptomTriageList = MutableStateFlow<List<SymptomTriageItem>>(emptyList())
    val symptomTriageList: StateFlow<List<SymptomTriageItem>> = _symptomTriageList.asStateFlow()

    private val _medicines = MutableStateFlow<List<MedicineGuide>>(emptyList())
    val medicines: StateFlow<List<MedicineGuide>> = _medicines.asStateFlow()

    // Business Circle
    private val _posProducts = MutableStateFlow<List<PosProduct>>(emptyList())
    val posProducts: StateFlow<List<PosProduct>> = _posProducts.asStateFlow()

    private val _cart = MutableStateFlow<List<CartItem>>(emptyList())
    val cart: StateFlow<List<CartItem>> = _cart.asStateFlow()

    private val _staffMembers = MutableStateFlow<List<StaffMember>>(emptyList())
    val staffMembers: StateFlow<List<StaffMember>> = _staffMembers.asStateFlow()

    private val _suppliers = MutableStateFlow<List<WholesaleSupplier>>(emptyList())
    val suppliers: StateFlow<List<WholesaleSupplier>> = _suppliers.asStateFlow()

    // Exchange Rate & Voice
    val currentThbToMmkRate = MutableStateFlow(128.5) // 1 THB = ~128.5 MMK market rate
    private val _voiceQueries = MutableStateFlow<List<VoiceQueryPrompt>>(emptyList())
    val voiceQueries: StateFlow<List<VoiceQueryPrompt>> = _voiceQueries.asStateFlow()

    private val _phrasebook = MutableStateFlow<List<TranslationRecord>>(emptyList())
    val phrasebook: StateFlow<List<TranslationRecord>> = _phrasebook.asStateFlow()

    // Premium Map Packs & Services
    private val _offlineMapPacks = MutableStateFlow<List<OfflineMapPack>>(emptyList())
    val offlineMapPacks: StateFlow<List<OfflineMapPack>> = _offlineMapPacks.asStateFlow()

    private val _telemedDoctors = MutableStateFlow<List<TelemedDoctor>>(emptyList())
    val telemedDoctors: StateFlow<List<TelemedDoctor>> = _telemedDoctors.asStateFlow()

    private val _remittanceProviders = MutableStateFlow<List<RemittanceProvider>>(emptyList())
    val remittanceProviders: StateFlow<List<RemittanceProvider>> = _remittanceProviders.asStateFlow()

    private val _goldenPassTiers = MutableStateFlow<List<GoldenPassTier>>(emptyList())
    val goldenPassTiers: StateFlow<List<GoldenPassTier>> = _goldenPassTiers.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        // 1. Stations
        _stations.value = listOf(
            TransitStation("bts_mochit", "Mo Chit", "မိုချစ် ဘူတာ (မြောက်ပိုင်း ကားဝင်း)", "หมอชิต", TransitType.BTS, "Sukhumvit Line (Green)", "Chatuchak", 13.8023, 100.5538, 47),
            TransitStation("bts_siam", "Siam", "ဆီယမ် ဘူတာ (အချက်အချာ လဲလှယ်ရာ)", "สยาม", TransitType.BTS, "Interchange (Silom/Sukhumvit)", "Pathum Wan", 13.7456, 100.5342, 17),
            TransitStation("bts_asok", "Asok", "အသောက ဘူတာ (MRT ချိတ်ဆက်ရာ)", "อโศก", TransitType.BTS, "Sukhumvit Line (Green)", "Watthana", 13.7369, 100.5603, 32),
            TransitStation("bts_phrakhanong", "Phra Khanong", "ပရာခနောင်း (မြန်မာတန်း/ဈေး)", "พระโขนง", TransitType.BTS, "Sukhumvit Line (Green)", "Khlong Toei", 13.7149, 100.5908, 40),
            TransitStation("bts_onnut", "On Nut", "အုန်းနွတ် ဘူတာ (အဆောင်များစွာရှိရာ)", "อ่อนนุช", TransitType.BTS, "Sukhumvit Line (Green)", "Khlong Toei", 13.7056, 100.6011, 47),
            TransitStation("bts_surasak", "Surasak", "ဆူရာဆတ် ဘူတာ (မြန်မာသံရုံးအနီး)", "สุรศักดิ์", TransitType.BTS, "Silom Line (Dark Green)", "Sathon", 13.7193, 100.5215, 35),
            TransitStation("bts_saphantaksin", "Saphan Taksin", "စပန်သက်ဆင် (ကျောက်ဖရားလှေဆိပ်)", "สะพานตากสิน", TransitType.BTS, "Silom Line (Dark Green)", "Bang Rak", 13.7188, 100.5143, 38),
            TransitStation("bts_bearing", "Bearing", "ဘယ်ရင်း ဘူတာ (စမွတ်ပရာကန် အစပ်)", "แบริ่ง", TransitType.BTS, "Sukhumvit Line (Green)", "Bang Na", 13.6608, 100.6019, 52),
            TransitStation("bts_samrong", "Samrong", "ဆမ်ရောင်း ဘူတာ (Yellow Line ချိတ်ဆက်ရာ)", "สำโรง", TransitType.BTS, "Sukhumvit / Yellow Line", "Samut Prakan", 13.6472, 100.5960, 59),
            TransitStation("mrt_sukhumvit", "Sukhumvit", "ဆူခွန်ဗစ် အမ်အာရ်တီ", "สุขุมวิท", TransitType.MRT, "Blue Line", "Watthana", 13.7371, 100.5610, 28),
            TransitStation("mrt_hualamphong", "Hua Lamphong", "ဟွာလမ်ပေါင် မီးရထားဘူတာဟောင်း", "หัวลำโพง", TransitType.MRT, "Blue Line", "Pathum Wan", 13.7380, 100.5165, 35),
            TransitStation("mrt_bangwa", "Bang Wa", "ဘန်ဝါး (မဟာချိုင် ကားပြေးဆွဲရာ)", "บางหว้า", TransitType.MRT, "Blue Line / BTS Interchange", "Phasi Charoen", 13.7206, 100.4578, 42),
            TransitStation("mrt_bangsue", "Bang Sue Grand Central", "ဘန်းဆူး မီးရထားဗဟိုဘူတာကြီး (Aphiwat)", "บางซื่อ", TransitType.MRT, "Blue Line / SRT Red Line", "Chatuchak", 13.8038, 100.5401, 38),
            TransitStation("arl_phayathai", "Phaya Thai ARL", "ဖရာထိုင်း လေဆိပ်ရထား", "พญาไท", TransitType.ARL, "Airport Rail Link", "Ratchathewi", 13.7569, 100.5348, 45),
            TransitStation("arl_suvarnabhumi", "Suvarnabhumi Airport", "သုဝဏ္ဏဘူမိ နိုင်ငံတကာလေဆိပ်", "สุวรรณภูมิ", TransitType.ARL, "Airport Rail Link", "Bang Phli", 13.6931, 100.7507, 45),
            TransitStation("boat_sathorn", "Sathorn Pier", "စသွန် လှေဆိပ်မကြီး", "ท่าเรือสาทร", TransitType.BOAT, "Chao Phraya Express", "Bang Rak", 13.7185, 100.5135, 16),
            TransitStation("boat_nonthaburi", "Nonthaburi Pier (N30)", "နွန်သဘူရီ မြစ်ဆိပ်", "ท่าน้ำนนทบุรี", TransitType.BOAT, "Chao Phraya Express", "Nonthaburi", 13.8427, 100.4913, 20)
        )

        // 2. Little Myanmar Community Directory
        _littleMyanmarPlaces.value = listOf(
            LittleMyanmarPlace(
                id = "pk_market",
                nameBurmese = "ပရာခနောင်း မြန်မာဈေးတန်း (Phra Khanong Market)",
                nameEnglish = "Phra Khanong Myanmar Market & Shops",
                category = "Food & Grocery",
                area = "Phra Khanong",
                address = "Soi Sukhumvit 71 (Pridi Banomyong 2)",
                nearestTransit = "BTS Phra Khanong (Exit 3)",
                phone = "082-123-4567",
                rating = 4.8,
                descriptionBurmese = "မြန်မာရိုးရာမုန့်ဟင်းခါး၊ ရှမ်းခေါက်ဆွဲ၊ လက်ဖက်ရည်ဆိုင်၊ မာလာဟင်း၊ ငါးပိ၊ ကုန်စိမ်းနှင့် မြန်မာစတိုးဆိုင် ၃၀ ကျော်ရှိရာ အဓိကဗဟိုချက်ဖြစ်ပါသည်။",
                openingHours = "06:00 - 21:00",
                features = listOf("မြန်မာစတိုး", "လက်ဖက်ရည်ဆိုင်", "ငွေလွှဲကောင်တာ")
            ),
            LittleMyanmarPlace(
                id = "mahachai_hub",
                nameBurmese = "မဟာချိုင် မြန်မာရပ်ကွက်နှင့် စျေး (Mahachai Hub)",
                nameEnglish = "Mahachai Little Myanmar Central",
                category = "Food & Grocery",
                area = "Bang Bon / Mahachai",
                address = "Samut Sakhon Industrial Center & Market",
                nearestTransit = "Minivan from BTS Bang Wa",
                phone = "089-987-6543",
                rating = 4.9,
                descriptionBurmese = "မြန်မာလုပ်သား ထောင်သောင်းချီ အခြေစိုက်ရာ စက်ရုံနှင့် ပင်လယ်စာဈေးကြီး။ မြန်မာဆေးဆိုင်၊ စားသောက်ဆိုင်၊ ငွေလွှဲကိုယ်စားလှယ်များ စုံလင်စွာရှိသည်။",
                openingHours = "05:00 - 22:00",
                features = listOf("ပင်လယ်စာလက်ကား", "ဆေးခန်း", "အလုပ်အကိုင်စင်တာ")
            ),
            LittleMyanmarPlace(
                id = "myanmar_embassy",
                nameBurmese = "ပြည်ထောင်စုသမ္မတမြန်မာနိုင်ငံသံရုံး (ဗန်ကောက်)",
                nameEnglish = "Embassy of the Republic of the Union of Myanmar",
                category = "Embassy",
                area = "Sathon",
                address = "110 North Sathorn Road, Silom, Bang Rak, Bangkok",
                nearestTransit = "BTS Surasak (Exit 3 - လမ်းလျှောက် ၅ မိနစ်)",
                phone = "02-637-9404",
                rating = 4.1,
                descriptionBurmese = "နိုင်ငံကူးလက်မှတ် (Passport) သက်တမ်းတိုးခြင်း၊ CI စာအုပ်ထုတ်ယူခြင်း၊ အလုပ်သမားရေးရာ ကောင်စစ်ဝန်ဌာနနှင့် အထောက်အထားစိစစ်ခြင်းလုပ်ငန်းများ။",
                openingHours = "09:00 - 16:30 (တနင်္လာ မှ သောကြာ)",
                features = listOf("ပတ်စပို့ သက်တမ်းတိုး", "CI ထုတ်ယူခြင်း", "အလုပ်သမားသံအရာရှိ")
            ),
            LittleMyanmarPlace(
                id = "pratunam_market",
                nameBurmese = "ပရာတူနမ် မြန်မာအထည်ဆိုင်များနှင့် လက်ဖက်ရည်ဆိုင်များ",
                nameEnglish = "Pratunam Myanmar Wholesale & Food",
                category = "Food & Grocery",
                area = "Pratunam",
                address = "Soi Phetchaburi 19, Ratchathewi",
                nearestTransit = "BTS Phaya Thai / BTS Ratchathewi",
                phone = "086-555-8899",
                rating = 4.7,
                descriptionBurmese = "လက်ကားအထည်ဈေးကြီးအတွင်းရှိ မြန်မာစားသောက်ဆိုင်များ၊ မြန်မာရိုးရာမုန့်နှင့် ငွေလွှဲလုပ်ငန်းများ အများအပြားတည်ရှိရာ နေရာ။",
                openingHours = "07:00 - 19:00",
                features = listOf("အထည်လက်ကား", "ရိုးရာအစားအစာ", "ငွေလဲစင်တာ")
            ),
            LittleMyanmarPlace(
                id = "wat_yannawa",
                nameBurmese = "ဝပ်ယန်နာဝါ ဘုရားကြီး (မြန်မာဘုရားဖူးဗဟို)",
                nameEnglish = "Wat Yannawa Buddhist Temple",
                category = "Temple",
                area = "Sathon",
                address = "Charoen Krung Road, Yannawa",
                nearestTransit = "BTS Saphan Taksin (Exit 4)",
                phone = "02-211-9317",
                rating = 4.9,
                descriptionBurmese = "မြန်မာဘုန်းတော်ကြီးများနှင့် မြန်မာရဟန်းတော်များ သီတင်းသုံးတော်မူရာ သင်္ဘောပုံစံ စေတီတော်ကြီး။ အလှူဒါနနှင့် ဥပုသ်သီလယူရာနေရာ။",
                openingHours = "06:00 - 18:00",
                features = listOf("ဥပုသ်သီလခံယူခြင်း", "မြန်မာရဟန်းတော်များ", "အလှူဒါန")
            ),
            LittleMyanmarPlace(
                id = "wat_prok",
                nameBurmese = "ဝပ်ပရောက် မြန်မာ-မွန် ဘုန်းတော်ကြီးကျောင်း (Wat Prok)",
                nameEnglish = "Wat Prok Mon-Myanmar Monastery",
                category = "Temple",
                area = "Sathon",
                address = "Soi Charoen Rat 2, Yan Nawa, Sathon",
                nearestTransit = "BTS Surasak",
                phone = "02-211-1234",
                rating = 4.9,
                descriptionBurmese = "နှစ်ပေါင်း ၁၀၀ ကျော် သက်တမ်းရှိ မွန်-မြန်မာ ရိုးရာကျောင်းတိုက်ကြီး။ ပွဲတော်ရက်များတွင် မြန်မာစတုဒီသာနှင့် ရိုးရာသင်္ကြန်ပွဲများ ကျင်းပလေ့ရှိသည်။",
                openingHours = "06:00 - 19:00",
                features = listOf("မွန်-မြန်မာရိုးရာ", "စတုဒီသာ", "လူမှုကူညီရေး")
            ),
            LittleMyanmarPlace(
                id = "thonburi_myanmar_clinic",
                nameBurmese = "သုခချမ်းသာ မြန်မာစကားပြော အထွေထွေဆေးခန်း",
                nameEnglish = "Thonburi Community Myanmar Clinic",
                category = "Clinic",
                area = "Bang Bon / Mahachai",
                address = "Rama 2 Road, Bang Bon",
                nearestTransit = "Bus 140 / 141",
                phone = "02-890-1122",
                rating = 4.8,
                descriptionBurmese = "မြန်မာစကားပြော သူနာပြုနှင့် စကားပြန်ရှိသော ဆေးခန်း။ သွေးစစ်၊ ဆေးကုသမှုနှင့် လူမှုဖူလုံရေးကတ် ဆေးကုသခွင့် လမ်းညွှန်ပေးသည်။",
                openingHours = "08:00 - 20:00",
                features = listOf("မြန်မာစကားပြန်", "သွေးစစ်ခြင်း", "လူမှုဖူလုံရေးကတ် လက်ခံ")
            ),
            LittleMyanmarPlace(
                id = "deemoney_asok",
                nameBurmese = "DeeMoney တရားဝင် ငွေလွှဲဗဟိုဌာန (Asok Branch)",
                nameEnglish = "DeeMoney Official Myanmar Remittance Asok",
                category = "Remittance & Banking",
                area = "Sathon",
                address = "Exchange Tower, Sukhumvit Soi 21, Asok",
                nearestTransit = "BTS Asok / MRT Sukhumvit",
                phone = "02-821-5555",
                rating = 4.8,
                descriptionBurmese = "ထိုင်းဗဟိုဘဏ် (BOT) တရားဝင်လိုင်စင်ရ မြန်မာငွေလွှဲဌာန။ KPay, WavePay, KBZ ဘဏ်များသို့ မိနစ်ပိုင်းအတွင်း တရားဝင်ငွေလွှဲပေးသည်။",
                openingHours = "09:00 - 18:00",
                features = listOf("တရားဝင်လိုင်စင်", "KPay/WavePay အရောက်", "လုံခြုံမှုအာမခံ")
            ),
            LittleMyanmarPlace(
                id = "mandalay_taste_pk",
                nameBurmese = "မန္တလေးအရသာ ရိုးရာစားသောက်ဆိုင်ကြီး",
                nameEnglish = "Mandalay Taste Traditional Burmese Food",
                category = "Food & Grocery",
                area = "Phra Khanong",
                address = "Pridi Banomyong Soi 3, Sukhumvit 71",
                nearestTransit = "BTS Phra Khanong",
                phone = "081-998-1122",
                rating = 4.9,
                descriptionBurmese = "မန္တလေးထမင်းပေါင်း၊ မြီးရှည်၊ ဝက်သားဒုတ်ထိုးနှင့် မြန်မာဟင်းလျာ စစ်စစ်များ နေ့စဉ်ရရှိနိုင်သော နာမည်ကျော် စားသောက်ဆိုင်ကြီး။",
                openingHours = "07:30 - 21:30",
                features = listOf("မန္တလေးဟင်း", "ဝက်သားဒုတ်ထိုး", "Air-Con ခန်း")
            ),
            LittleMyanmarPlace(
                id = "yanhee_myanmar_desk",
                nameBurmese = "ရန်ဟီး နိုင်ငံတကာဆေးရုံကြီး မြန်မာကောင်တာ",
                nameEnglish = "Yanhee International Hospital Myanmar Desk",
                category = "Clinic",
                area = "Nonthaburi",
                address = "Charansanitwong Road, Bang Phlat, Bangkok",
                nearestTransit = "MRT Bang O (Exit 1)",
                phone = "02-879-0300",
                rating = 4.7,
                descriptionBurmese = "အထွေထွေကျန်းမာရေးစစ်ဆေးမှု၊ အရိုးအကြော၊ ခွဲစိတ်ကုသမှုများအတွက် မြန်မာစကားပြန် သီးသန့်ထားရှိသော အဆင့်မြင့်ဆေးရုံကြီး။",
                openingHours = "24 နာရီဖွင့်လှစ်သည်",
                features = listOf("မြန်မာစကားပြန် အမြဲရှိ", "အရေးပေါ်ဌာန", "ခွဲစိတ်ကုသမှု")
            ),
            LittleMyanmarPlace(
                id = "mwg_legal_center",
                nameBurmese = "ရွှေ့ပြောင်းလုပ်သား ဥပဒေအကူအညီပေးရေးဗဟို (MWG Bangkok)",
                nameEnglish = "Migrant Working Group (MWG) Legal Aid",
                category = "Legal Aid",
                area = "Pratunam",
                address = "Phayathai Plaza, Ratchathewi",
                nearestTransit = "BTS Phaya Thai (Exit 1)",
                phone = "02-216-4444",
                rating = 4.9,
                descriptionBurmese = "အလုပ်ရှင်မှ လုပ်ခလစာမပေးခြင်း၊ ပတ်စပို့/CI သိမ်းဆည်းခံရခြင်း၊ မတရားဖမ်းဆီးခံရခြင်းများကို အခမဲ့ ဥပဒေအကြံဉာဏ်ပေးသော ဗဟိုအဖွဲ့။",
                openingHours = "09:00 - 17:00 (တနင်္လာ မှ သောကြာ)",
                features = listOf("အခမဲ့ ဥပဒေအကူအညီ", "ရှေ့နေလိုက်ပါဆောင်ရွက်", "အလုပ်သမားအခွင့်အရေး")
            ),
            LittleMyanmarPlace(
                id = "bangna_dorm_complex",
                nameBurmese = "ဘန်နာ မြန်မာအလုပ်သမား သီးသန့် အဆောင်ကြီး",
                nameEnglish = "Bang Na Myanmar Resident Dormitory",
                category = "Dormitory",
                area = "Onnut",
                address = "Sukhumvit 105 (Soi Lasalle), Bang Na",
                nearestTransit = "BTS Bearing / BTS Bang Na",
                phone = "087-334-1199",
                rating = 4.6,
                descriptionBurmese = "လုံခြုံရေးဂိတ်၊ CCTV၊ Wi-Fi နှင့် မြန်မာကုန်စုံဆိုင်တွဲလျက်ပါရှိသော သန့်ရှင်းလုံခြုံသည့် မြန်မာလုပ်သား အဆောင်ကြီး။",
                openingHours = "ဧည့်ကြို ၂၄ နာရီ",
                features = listOf("CCTV လုံခြုံရေး", "အခမဲ့ Wi-Fi", "မြန်မာစတိုးပါရှိ")
            )
        )

        // 3. Jobs
        _jobs.value = listOf(
            JobPost(
                id = "job_1",
                titleBurmese = "အီလက်ထရောနစ် စက်ရုံလုပ်သား အမြန်အလိုရှိသည် (OT ပေါများ)",
                titleEnglish = "Electronics Assembly Factory Operator",
                category = "Factory",
                companyName = "Apex Micro Precision Co., Ltd.",
                locationArea = "Bang Phli / Samut Prakan (ဖရီကြိုပို့ကားရှိ)",
                nearestStation = "BTS Kheha (Company Shuttle Bus)",
                salaryThb = "400 - 450",
                salaryType = "Daily (နေ့တွက် + OT)",
                otPayThbPerHour = 75,
                isVerifiedEmployer = true,
                hasScamAlert = false,
                scamWarningText = null,
                requirementsBurmese = listOf("CI / ပတ်စပို့ သက်တမ်း အနည်းဆုံး ၆ လကျန်ရမည်", "အသက် ၁၈ မှ ၃၅ နှစ်အတွင်း", "အခြေခံ ထိုင်းစကား နားလည်ပါက ပိုဦးစားပေးမည်"),
                benefitsBurmese = listOf("အခမဲ့ အလုပ်ဆင်း/ပြန် ကြိုပို့ကား", "နေ့လယ်စာ ကျွေးသည်", "လူမှုဖူလုံရေးကတ် ပြုလုပ်ပေးသည်", "တနင်္ဂနွေ ပိတ်ရက်"),
                contactPhone = "081-445-6789",
                contactLineId = "apex_hr_recruit",
                postedDate = "ယနေ့တင်သည်"
            ),
            JobPost(
                id = "job_2",
                titleBurmese = "ဆူခွန်ဗစ် စားသောက်ဆိုင် မီးဖိုချောင် ကူနှင့် ပန်းကန်ဆေး",
                titleEnglish = "Restaurant Kitchen Helper & Dishwasher",
                category = "Restaurant",
                companyName = "Siam Heritage Kitchen",
                locationArea = "Sukhumvit Soi 24, Phrom Phong",
                nearestStation = "BTS Phrom Phong (လမ်းလျှောက် ၃ မိနစ်)",
                salaryThb = "14,500 - 16,000",
                salaryType = "Monthly (လစာ + Tip)",
                otPayThbPerHour = 65,
                isVerifiedEmployer = true,
                hasScamAlert = false,
                scamWarningText = null,
                requirementsBurmese = listOf("သန့်ရှင်းသပ်ရပ်စွာ နေထိုင်တတ်သူ", "အလုပ်ချိန် မနက် ၁၀ နာရီ မှ ည ၉ နာရီ", "အထောက်အထား အပြည့်အစုံရှိရမည်"),
                benefitsBurmese = listOf("အစားအသောက် ၂ နပ် အခမဲ့", "တစ်လလျှင် ၄ ရက် ပိတ်ရက်", "ယူနီဖောင်း အခမဲ့ပေးသည်"),
                contactPhone = "089-223-1144",
                contactLineId = "siamkitchen_jobs",
                postedDate = "၁ ရက်အလို"
            ),
            JobPost(
                id = "job_3",
                titleBurmese = "သတိပြုရန်: အွန်လိုင်း လွယ်ကူသော ငွေသွင်းအလုပ်ကြော်ငြာ (လိမ်လည်မှုသတိပေးချက်)",
                titleEnglish = "SCAM ALERT: Online Easy Task Scam",
                category = "Scam Warning",
                companyName = "အမည်မဖော်လိုသော အွန်လိုင်းအဖွဲ့",
                locationArea = "Telegram / Facebook Page အတုများ",
                nearestStation = "None",
                salaryThb = "1,500 - 3,000 / day (Fake)",
                salaryType = "Daily (ငွေကြိုလွှဲရမည့် လိမ်လည်မှု)",
                otPayThbPerHour = 0,
                isVerifiedEmployer = false,
                hasScamAlert = true,
                scamWarningText = "သတိပေးချက်: အလုပ်မဆင်းမီ ငွေကြိုတင်လွှဲခိုင်းသော ပွဲစား သို့မဟုတ် Telegram Group များကို လုံးဝ ငွေမလွှဲပါနှင့်။ အမှန်တကယ် လိမ်လည်သည့် ဂိုဏ်းများဖြစ်ပါသည်။",
                requirementsBurmese = listOf("ငွေကြိုတင်တောင်းခံခြင်းကို ရှောင်ပါ", "ကိုယ်ရေးအချက်အလက် မပေးပါနှင့်"),
                benefitsBurmese = listOf("အထူးသတိထားရန်"),
                contactPhone = "1155 (Tourist/Anti-Scam Police)",
                contactLineId = "report_scam",
                postedDate = "လုံခြုံရေးသတိပေးချက်"
            ),
            JobPost(
                id = "job_4",
                titleBurmese = "ကွန်ဒိုနှင့် ရုံးခန်း သန့်ရှင်းရေး ဝန်ထမ်း (အဆောင်နေ အဆောင်စား)",
                titleEnglish = "Condominium Housekeeping Staff",
                category = "Cleaning",
                companyName = "Bangkok Clean Pro Services",
                locationArea = "Sathorn & Silom",
                nearestStation = "BTS Chong Nonsi",
                salaryThb = "13,500 - 15,000",
                salaryType = "Monthly (လစာ)",
                otPayThbPerHour = 60,
                isVerifiedEmployer = true,
                hasScamAlert = false,
                scamWarningText = null,
                requirementsBurmese = listOf("အမျိုးသမီး ဦးစားပေးမည်", "အသက် ၂၀ မှ ၄၀ နှစ်အတွင်း", "အလုပ်ကြိုးစားရိုးသားသူ"),
                benefitsBurmese = listOf("အဆောင် အခမဲ့ စီစဉ်ပေးသည်", "ရေဖိုး မီးဖိုး အခမဲ့", "အလုပ်ရှင်မှ စာရွက်စာတမ်း ကူညီဆောင်ရွက်ပေးသည်"),
                contactPhone = "084-778-9900",
                contactLineId = "bkkclean_hr",
                postedDate = "၂ ရက်အလို"
            )
        )

        // 4. Golden Journey Quests & Chapters
        _chapters.value = listOf(
            Chapter(
                id = 1,
                titleBurmese = "အခန်း ၁: ထိုင်းနိုင်ငံသို့ စတင်ဆိုက်ရောက်ခြင်း",
                titleEnglish = "Chapter 1: Safe Arrival & Fundamentals",
                descriptionBurmese = "ဆင်းမ်ကတ်ဝယ်ယူခြင်း၊ နေရပ်လိပ်စာ တိုင်ကြားခြင်း (TM30) နှင့် မရှိမဖြစ် ထိုင်းစကားလုံးများ",
                iconName = "airplane",
                quests = listOf(
                    Quest("q_1_1", 1, "ထိုင်းဆင်းမ်ကတ် (SIM Card) ပတ်စပို့ဖြင့် မှတ်ပုံတင်ဝယ်ယူခြင်း", "Get Registered Thai SIM Card", 50, true, "TrueMove သို့မဟုတ် AIS ဆင်းမ်ကတ်ကို 7-Eleven သို့မဟုတ် ဆိုင်ကြီးများတွင် ပတ်စပို့/CI ပြသ၍ ဝယ်ယူပါ။ အင်တာနက် အကန့်အသတ်မဲ့ ပက်ကေ့ခ်ျ သုံးစွဲရန် အကြံပြုပါသည်။", "ပြီးမြောက်ပြီ", "၇-Eleven တွင် ဝယ်ပါက ပတ်စပို့မူရင်း ပြသရန် လိုအပ်ပါသည်။"),
                    Quest("q_1_2", 1, "TM30 (နေထိုင်ခွင့် အကြောင်းကြားစာ) အိမ်ရှင်ထံမှ တောင်းယူထားရှိခြင်း", "Verify TM30 Address Notification", 100, false, "ထိုင်းနိုင်ငံသို့ ရောက်ရှိပြီး ၂၄ နာရီအတွင်း အိမ်ရှင်/ကွန်ဒိုပိုင်ရှင်မှ လဝက သို့ TM30 တိုင်ကြားပေးရပါမည်။ ဗီဇာသက်တမ်းတိုးရာတွင် အလွန်အရေးကြီးပါသည်။", "စစ်ဆေးရန်", "အိမ်ငှားစာချုပ်ချုပ်သည့်အခါ TM30 စာရွက် ချက်ချင်းတောင်းယူပါ။"),
                    Quest("q_1_3", 1, "အရေးပေါ် လမ်းညွှန်စကား ၅ ခွန်း လေ့လာမှတ်သားခြင်း", "Learn 5 Essential Emergency Thai Phrases", 75, false, "ဘတ်စ်ကား၊ ရဲစစ်ဆေးရေးနှင့် ဆေးရုံသုံး အရေးပေါ် ထိုင်းစကားလုံး ၅ လုံးကို အသံထွက်နှင့်တကွ ကျက်မှတ်ပါ။", "စတင်လေ့လာရန်", "Safe Harbor အခန်းတွင် အသံနားထောင်နိုင်ပါသည်။")
                )
            ),
            Chapter(
                id = 2,
                titleBurmese = "အခန်း ၂: တရားဝင် အလုပ်ခွင်နှင့် အခွင့်အရေး",
                titleEnglish = "Chapter 2: Work Permit & Labor Rights",
                descriptionBurmese = "အလုပ်ပါမစ် (Work Permit / ဘတ်ပန်းရောင်)၊ လုပ်ခလစာ အနိမ့်ဆုံးသတ်မှတ်ချက်နှင့် စာချုပ်စစ်ဆေးနည်း",
                iconName = "work",
                quests = listOf(
                    Quest("q_2_1", 2, "အလုပ်ပါမစ် စာအုပ်/ကတ် သက်တမ်း စစ်ဆေးမှတ်သားခြင်း", "Check Work Permit Validity", 100, false, "မိမိ၏ Work Permit သက်တမ်းကုန်ဆုံးမည့်ရက်ကို ကြိုတင်မှတ်သားပြီး အနည်းဆုံး ၄၅ ရက် ကြိုတင်သက်တမ်းတိုးပါ။", "မှတ်တမ်းတင်ရန်", "အလုပ်ရှင်ပြောင်းပါက ရက် ၃၀ အတွင်း အသစ်ရှာရပါမည်။"),
                    Quest("q_2_2", 2, "ထိုင်းနိုင်ငံ တရားဝင် အနိမ့်ဆုံး လုပ်ခလစာ နှုန်းထား သိရှိခြင်း", "Know Bangkok Minimum Wage Rate", 50, true, "ဘန်ကောက်နှင့် ပတ်ဝန်းကျင်တွင် အနိမ့်ဆုံး တစ်ရက် လုပ်ခလစာမှာ ၃၆၃ မှ ၄၀၀ ဘတ် ဖြစ်ပါသည်။ ဥပဒေမဲ့ လျှော့မပေးစေရန် သိရှိထားပါ။", "ပြီးမြောက်ပြီ", "OT အချိန်ပိုလုပ်ခမှာ ၁.၅ ဆ ရရှိရပါမည်။")
                )
            ),
            Chapter(
                id = 3,
                titleBurmese = "အခန်း ၃: နေထိုင်စရိတ်နှင့် ရပ်ကွက်လုံခြုံရေး",
                titleEnglish = "Chapter 3: Safe Housing & Budgeting",
                descriptionBurmese = "အဆောင်ငှားရမ်းခြင်း၊ ရေဖိုးမီးဖိုးနှင့် ပတ်ဝန်းကျင် လုံခြုံစွာနေထိုင်နည်း",
                iconName = "home",
                quests = listOf(
                    Quest("q_3_1", 3, "အဆောင်စပေါ်ငွေ (Deposit) စာချုပ် သေချာဖတ်ရှုခြင်း", "Verify Dormitory Rental Contract", 80, false, "စပေါ်ငွေ မဆုံးရှုံးစေရန် ရေမီတာ၊ မီးမီတာ နှုန်းထားနှင့် အခန်းအပ်နှံချိန် စည်းကမ်းများကို သေချာမှတ်တမ်းတင်ထားပါ။", "လမ်းညွှန်ဖတ်ရန်", "အစိုးရ မီးယူနစ်ထက် ဆိုင်/အိမ်ရှင်က ပိုကောက်တတ်၍ ကြိုတင်မေးပါ။")
                )
            ),
            Chapter(
                id = 4,
                titleBurmese = "အခန်း ၄: ကျန်းမာရေးနှင့် လူမှုဖူလုံရေးစနစ်",
                titleEnglish = "Chapter 4: Health & Social Security Card",
                descriptionBurmese = "လူမှုဖူလုံရေး ဆေးကုသခွင့်ရယူခြင်းနှင့် မြန်မာစကားပြော ဆေးခန်းများ",
                iconName = "health",
                quests = listOf(
                    Quest("q_4_1", 4, "လူမှုဖူလုံရေးကတ် (ประกันสังคม - ပရကန်ဆန်ခုမ်) ဆေးရုံအမည် စစ်ဆေးခြင်း", "Check Social Security Hospital", 100, false, "မိမိအမည်ပေါက် သတ်မှတ်ထားသော ဆေးရုံကြီးတွင် ဆေးဝါးနှင့် ခွဲစိတ်ကုသမှု အခမဲ့ရရှိနိုင်ပါသည်။", "ဆေးရုံရှာရန်", "၁၅၀၆ သို့ ဖုန်းခေါ်၍ မြန်မာစကားပြန်နှင့် စစ်ဆေးနိုင်ပါသည်။")
                )
            ),
            Chapter(
                id = 5,
                titleBurmese = "အခန်း ၅: ငွေစုခြင်း၊ ငွေလွှဲခြင်းနှင့် ဘဝတိုးတက်ရေး",
                titleEnglish = "Chapter 5: Smart Remittance & Growth",
                descriptionBurmese = "တရားဝင် ဘဏ်အကောင့်ဖွင့်ခြင်း၊ ယုံကြည်စိတ်ချရသော ငွေလွှဲခြင်းနှင့် ထိုင်းစကား ကျွမ်းကျင်ခြင်း",
                iconName = "trending_up",
                quests = listOf(
                    Quest("q_5_1", 5, "တရားဝင် ငွေလွှဲကိုယ်စားလှယ်ဖြင့် အိမ်သို့ လုံခြုံစွာ ငွေလွှဲနည်း လေ့လာခြင်း", "Learn Verified Remittance Methods", 90, false, "ဟွန်ဒီအတုများကြောင့် ငွေလိမ်လည်ခံရခြင်းမှ ကာကွယ်ရန် တရားဝင် အေဂျင်စီများမှသာ လွှဲပြောင်းပါ။", "ငွေလွှဲနှုန်းတွက်ရန်", "Business Circle တွင် နေ့စဉ် ပေါက်ဈေး တွက်နိုင်ပါသည်။")
                )
            )
        )

        // 5. Thai Lessons
        _thaiLessons.value = listOf(
            ThaiLesson("tl_1", "สวัสดีครับ / ค่ะ (Sawatdee khrap/kha)", "မင်္ဂလာပါ", "မင်္ဂလာပါ", "Daily"),
            ThaiLesson("tl_2", "ไป... เท่าไหร่ครับ? (Pai... thao rai khrap?)", "သွားမယ်... ဘယ်လောက်ကျလဲခင်ဗျာ", "... သို့သွားလျှင် ဘယ်လောက်ကျပါသလဲ?", "Directions"),
            ThaiLesson("tl_3", "มีใบอนุญาตทำงานครับ (Mii bai anuyat tham ngan khrap)", "မီ ဘိုင် အနုယတ် ထမ်ငန် ခရတ်", "ကျွန်တော့်မှာ အလုပ်ပါမစ် ရှိပါတယ်ခင်ဗျာ (ရဲစစ်ဆေးချိန်)", "Legal"),
            ThaiLesson("tl_4", "ช่วยด้วยครับ (Chuai duai khrap)", "ချိုင့် ဒွမ့် ခရတ်", "ကူညီပါဦးခင်ဗျာ! (အရေးပေါ်)", "Emergency"),
            ThaiLesson("tl_5", "เจ็บตรงนี้ (Jeb trong nee)", "ဂျက်ပ် ထရွမ် နီး", "ဒီနေရာ နာကျင်နေပါတယ် (ဆေးရုံ/ဆေးခန်းပြချိန်)", "Health"),
            ThaiLesson("tl_6", "ลดหน่อยได้ไหมครับ? (Lod noi dai mai khrap?)", "လွတ် နွိုင် ဒိုင် မိုင် ခရတ်", "ဈေးနည်းနည်း လျှော့ပေးလို့ရမလားခင်ဗျာ?", "Market")
        )

        // 6. Safe Harbor Emergency Contacts
        _emergencyContacts.value = listOf(
            EmergencyContact("em_1", "ထိုင်းတော်ဝင်ရဲတပ်ဖွဲ့ (Police)", "Royal Thai Police Emergency", "191", "ခိုးယူခံရခြင်း၊ တိုက်ခိုက်ခံရခြင်း၊ မှုခင်းအရေးပေါ်များ", "POLICE"),
            EmergencyContact("em_2", "လူနာတင်ယာဉ်နှင့် ဆေးဘက်ဆိုင်ရာ အရေးပေါ်", "National Ambulance Service", "1669", "မတော်တဆထိခိုက်မှု၊ အရေးပေါ်အသက်အန္တရာယ် (အခမဲ့ လူနာတင်ယာဉ်)", "AMBULANCE"),
            EmergencyContact("em_3", "ခရီးသွားရဲတပ်ဖွဲ့ (အင်္ဂလိပ်/စကားပြန်)", "Tourist Police Helpline", "1155", "နိုင်ငံခြားသား အခက်အခဲ၊ လိမ်လည်ခံရမှုများ စုံစမ်းကူညီရေး", "POLICE"),
            EmergencyContact("em_4", "မြန်မာသံရုံး ဗန်ကောက် (အရေးပေါ်ဖုန်း)", "Myanmar Embassy Bangkok Hotline", "02-637-9404", "အလုပ်သမားအရေးအခွင့်အရေး၊ ပတ်စပို့/CI ဆုံးရှုံးမှုနှင့် ကောင်စစ်ဝန်ဌာန", "EMBASSY"),
            EmergencyContact("em_5", "ထိုင်းအလုပ်သမားဝန်ကြီးဌာန တိုင်ကြားရေး", "Labor Protection Hotline (MOL)", "1506", "လုပ်အားခမရရှိခြင်း၊ အလုပ်ရှင် အနိုင်ကျင့်ခံရခြင်း (မြန်မာစကားပြန်ရှိ)", "LABOR")
        )

        // 7. Legal Expiry Documents Tracker
        _legalDocuments.value = listOf(
            LegalDocument("doc_1", "ရက် ၉၀ အစီရင်ခံစာ (90-Day Report)", "90-Day Notification", "90_DAYS", "90D-2026-BKK", "2026-11-20", 33, false, "သက်တမ်းမကုန်မီ ၁၅ ရက်ကြို၍ အွန်လိုင်း သို့မဟုတ် လဝက တွင် တင်ပြရပါမည်။ နောက်ကျပါက ဒဏ်ငွေ ၂,၀၀၀ ဘတ် ပေးဆောင်ရပါမည်။"),
            LegalDocument("doc_2", "အလုပ်ပါမစ် (Work Permit / ဘတ်ပန်းရောင်)", "Work Permit Card", "WORK_PERMIT", "WP-88934-26", "2026-12-31", 74, false, "သက်တမ်းမကုန်မီ ရက် ၄၅ ကြိုတင်၍ အလုပ်ရှင်နှင့်အတူ သက်တမ်းတိုးရပါမည်။"),
            LegalDocument("doc_3", "ဗီဇာ (Visa Non-LA)", "Working Visa Stamp", "VISA", "VS-901123", "2027-02-15", 120, false, "Work Permit သက်တမ်းတိုးပြီးနောက် လဝက တွင် ဗီဇာတံဆိပ်တုံး ထုရပါမည်။"),
            LegalDocument("doc_4", "နေရပ်လိပ်စာ တိုင်ကြားလွှာ (TM30)", "TM30 Notification Receipt", "TM30", "TM30-OK-991", "Permanent", 999, false, "အိမ်ငှားနေရာ ပြောင်းတိုင်း အသစ် ပြန်လည်တင်ပြရပါမည်။")
        )

        // 8. Checkpoint Phrases
        _checkpointPhrases.value = listOf(
            CheckpointPhrase("สวัสดีครับ ผมมีเอกสารครบครับ", "မင်္ဂလာပါခင်ဗျာ၊ ကျွန်တော့်မှာ စာရွက်စာတမ်း အပြည့်အစုံ ရှိပါတယ်", "စစ်ဆေးရေးဂိတ်တွင် ယဉ်ကျေးစွာ ဦးစွာပြောဆိုရန်", "General Checkpoint"),
            CheckpointPhrase("นี่คือใบอนุญาตทำงานและพาสปอร์ตครับ", "ဒါ ကျွန်တော့်ရဲ့ အလုပ်ပါမစ်နဲ့ ပတ်စပို့စာအုပ် ဖြစ်ပါတယ်ခင်ဗျာ", "အထောက်အထားစာရွက်စာတမ်းများ ထုတ်ပြချိန်တွင် ပြောဆိုရန်", "Showing Documents"),
            CheckpointPhrase("กรุณาพูดช้าๆ ได้ไหมครับ ผมยังพูดไทยไม่เก่ง", "ကျေးဇူးပြုပြီး ဖြည်းဖြည်းလေး ပြောပေးလို့ရမလားခင်ဗျာ၊ ထိုင်းစကား သိပ်မကျွမ်းကျင်သေးလို့ပါ", "စကားနားမလည်ပါက စိတ်အေးလက်အေး ပြန်လည်မေးမြန်းရန်", "Language Difficulty"),
            CheckpointPhrase("ผมทำงานที่ร้านอาหาร/โรงงานครับ", "ကျွန်တော် စားသောက်ဆိုင် / စက်ရုံမှာ အလုပ်လုပ်ပါတယ်ခင်ဗျာ", "လုပ်ငန်းခွင်အကြောင်း မေးမြန်းချိန်တွင် ဖြေကြားရန်", "Work Inquiry")
        )

        // 9. Symptom Triage
        _symptomTriageList.value = listOf(
            SymptomTriageItem("sym_1", "ပြင်းထန်စွာ ရင်ဘတ်အောင့်ခြင်း / အသက်ရှူကျပ်ခြင်း", "Severe Chest Pain / Shortness of Breath", "EMERGENCY_RED", "ချက်ချင်း အရေးပေါ်ကား (၁၆၆၉) သို့ ဖုန်းခေါ်ပါ။ အချိန်မဆိုင်းဘဲ အနီးဆုံး ဆေးရုံ အရေးပေါ်ဌာနသို့ သွားရောက်ကုသပါ။", "Emergency Hospital (၁၆၆၉)", "แน่นหน้าอก หายใจไม่ออก (ရင်ဘတ်အောင့်၍ အသက်ရှူမရပါ)"),
            SymptomTriageItem("sym_2", "အလုပ်ခွင် မတော်တဆ ထိခိုက်ပြတ်ရှ / အရိုးကျိုးခြင်း", "Workplace Injury / Fracture", "EMERGENCY_RED", "သွေးတိတ်အောင် ဖိထားပါ။ လူမှုဖူလုံရေးကတ် ဆေးရုံသို့ အလုပ်ရှင်အား အကြောင်းကြားကာ ချက်ချင်းသွားပါ။ ကုန်ကျစရိတ် အလုပ်ခွင်ရန်ပုံငွေမှ ကျခံခွင့်ရှိသည်။", "Social Security Hospital", "เกิดอุบัติเหตุจากการทำงาน (အလုပ်ခွင်မတော်တဆ ဖြစ်ပွားခဲ့သည်)"),
            SymptomTriageItem("sym_3", "ဖျားနာခြင်း၊ ချောင်းဆိုးခြင်း၊ ခေါင်းကိုက်ခြင်း (၃ ရက်ကျော်)", "High Fever, Cough, Headache (>3 days)", "MODERATE_YELLOW", "ရေများများသောက်ပါ၊ ပါရာစီတမောသောက်ပါ။ သွေးလွန်တုပ်ကွေး သို့မဟုတ် ကိုဗစ် စစ်ဆေးရန် အနီးဆုံး ဆေးခန်းသို့ ပြသပါ။", "Local Clinic or General Hospital", "มีไข้สูง ปวดหัว ไอมาหลายวัน (အဖျားကြီး၍ ခေါင်းကိုက်နေပါသည်)"),
            SymptomTriageItem("sym_4", "အစာမကြေခြင်း၊ ဝမ်းလျှောခြင်း၊ အစာအိမ်အောင့်ခြင်း", "Diarrhea, Stomach Cramp, Indigestion", "MILD_GREEN", "ဓာတ်ဆားရည် (ORS) သောက်ပါ။ အဆီအစပ်ရှောင်ပါ။ ဆေးဆိုင်တွင် ဝမ်းပိတ်ဆေး၊ အစာအိမ်ဆေး ဝယ်ယူသောက်သုံးနိုင်ပါသည်။", "Pharmacy / Clinic", "ท้องเสีย ปวดท้อง ต้องการเกลือแร่ (ဝမ်းလျှောနေ၍ ဓာတ်ဆားလိုပါသည်)")
        )

        // 10. Thai-Burmese Medicine Guide
        _medicines.value = listOf(
            MedicineGuide("med_1", "พาราเซตามอล (Paracetamol / Sara / Tylenol)", "Paracetamol 500mg", "အဖျားပျောက်ဆေး / အကိုက်အခဲပျောက်ဆေး", "အဖျားကျစေရန်၊ ခေါင်းကိုက်၊ သွားကိုက်၊ ကိုယ်လက်ကိုက်ခဲသက်သာစေရန်", "လူကြီးတစ်ကြိမ်လျှင် ၁ ပြားမှ ၂ ပြား၊ ၄ နာရီ မှ ၆ နာရီခြားတစ်ခါ သောက်နိုင်သည် (တစ်နေ့ ၈ ပြားထက် မပိုရ)", "အရက်နှင့် တွဲမသောက်ရ၊ အသည်းရောဂါရှိသူ ဆရာဝန်နှင့် တိုင်ပင်ပါ"),
            MedicineGuide("med_2", "ยาลดกรด (Antacid / Eno / Air-X)", "Simethicone / Antacid", "အစာအိမ်လေထိုးလေအောင့် / ရင်ပူ သက်သာဆေး", "အစာမကြေ လေပွခြင်း၊ ဗိုက်ကယ်ခြင်း၊ အစာအိမ်အချဉ်ပေါက် ရင်ပူခြင်း", "ဝါးစားရသော ဆေးပြားဖြစ်ပါက သေချာဝါးစားပါ၊ အစာစားပြီး နာရီဝက်တွင် သောက်ရန်", "ရေများများ သောက်ပါ"),
            MedicineGuide("med_3", "ผงเกลือแร่ โออาร์เอส (ORS Royal-D / Oreda)", "Oral Rehydration Salts (ORS)", "ဝမ်းလျှော/အော့အန် ဓာတ်ဆားထုပ်", "ဝမ်းသွားခြင်း၊ အော့အန်ခြင်းကြောင့် ခန္ဓာကိုယ်မှ ရေနှင့် ဓာတ်ဆားဓာတ် ဆုံးရှုံးမှုကို ဖြည့်ဆည်းရန်", "ရေကျက်အေး ၂၅၀ မီလီလီတာတွင် တစ်ထုပ်ဖျော်၍ နည်းနည်းချင်း မကြာခဏ သောက်ပါ", "ဖျော်ပြီး ၂၄ နာရီထက် ကျော်လွန်ပါက မသောက်ရ"),
            MedicineGuide("med_4", "ยาแก้แพ้ (CPM / Cetirizine)", "Cetirizine 10mg / Chlorpheniramine", "အအေးမိ/နှာစေး/ယားယံ ဓာတ်မတည့်ပျောက်ဆေး", "နှာရည်ယိုခြင်း၊ အင်ပျဉ်ထခြင်း၊ မျက်စိယားယံခြင်း", "ညအိပ်ရာဝင်ချိန် ၁ ပြား သောက်ရန်", "အိပ်ငိုက်စေတတ်သဖြင့် ကားမောင်းခြင်း၊ စက်ယန္တရားကိုင်တွယ်ခြင်း ရှောင်ကြဉ်ပါ")
        )

        // 11. POS Products for Business Circle
        _posProducts.value = listOf(
            PosProduct("p_1", "ရှမ်းရိုးရာ လက်ဖက်စိုနှပ် (၁ ထုပ်)", "ใบชาหมักพม่า", 65.0, "Myanmar Groceries", 45, "ထုပ်"),
            PosProduct("p_2", "မုန့်ဟင်းခါး အနှစ်အထုပ်ကြီး (၅ ယောက်စာ)", "พริกแกงโมนฮี่นခါ", 85.0, "Food & Drinks", 30, "ထုပ်"),
            PosProduct("p_3", "တော်ဝင် ပုစွန်ခြောက် အမွှေး (၁၀၀ ဂရမ်)", "กุ้งแห้งเกรดพรีเมียม", 140.0, "Myanmar Groceries", 18, "ဗူး"),
            PosProduct("p_4", "Royal Myanmar တီးမစ် နို့လက်ဖက်ရည် (အထုပ် ၃၀)", "ชานมพม่า Royal", 130.0, "Food & Drinks", 55, "ထုပ်"),
            PosProduct("p_5", "ရှမ်းခေါက်ဆွဲ ခြောက်ကြော် အထူးပွဲ", "ก๋วยเตี๋ยวไทใหญ่ทรงเครื่อง", 90.0, "Food & Drinks", 100, "ပွဲ"),
            PosProduct("p_6", "ရိုးရာ သနပ်ခါးတုံး မူရင်း (ရွှေဘို)", "ททานาคาไม้แท้", 110.0, "Cosmetics", 22, "တုံး"),
            PosProduct("p_7", "စာရွက်စာတမ်း ဘာသာပြန် ဝန်ဆောင်မှု (၁ စောင်)", "บริการแปลเอกสารพม่า-ไทย", 250.0, "Services", 99, "စောင်")
        )

        // 12. Staff Work Permit Expiry Monitor
        _staffMembers.value = listOf(
            StaffMember("st_1", "ကိုအောင်ကျော် (Aung Kyaw)", "မီးဖိုမှူးချုပ် (Head Chef)", "081-112-3344", "2026-11-15", 28, true),
            StaffMember("st_2", "မနွယ်နွယ် (Nwe Nwe)", "ငွေကိုင်နှင့် စားပွဲထိုး (Cashier)", "089-556-7788", "2027-04-10", 174, false),
            StaffMember("st_3", "ကိုသက်နိုင် (Thet Naing)", "ကုန်ပစ္စည်း ပို့ဆောင်ရေး (Rider)", "086-990-1122", "2026-12-05", 48, false)
        )

        // 13. Wholesale Suppliers Directory
        _suppliers.value = listOf(
            WholesaleSupplier("sup_1", "မဟာချိုင် မြန်မာကုန်စည် လက်ကားဒိုင် (Mahachai Direct)", "ငါးပိ/ငါးခြောက်၊ ပုစွန်ခြောက်၊ ရခိုင်ငပိ", "Mahachai, Samut Sakhon", "089-445-5667", "mahachai_wholesale", 1500, "ဘန်ကောက်အတွင်း ကားဖြင့် အရောက်ပို့ပေးသည်"),
            WholesaleSupplier("sup_2", "မဲဆောက်-ဘန်ကောက် သယ်ယူပို့ဆောင်ရေး အမြန်လိုင်း", "ကုန်စည်ပို့ဆောင်ရေး (Logistics)", "Mae Sot / Phra Khanong", "081-778-8990", "maesot_express_bkk", 500, "နေ့ချင်းရောက် ကားဂိတ်ပို့ဆောင်မှု"),
            WholesaleSupplier("sup_3", "ရွှေရှမ်းပြည် လက်ဖက်နှင့် ကုန်ခြောက် လက်ကား", "ရှမ်းလက်ဖက်/လက်ဖက်ခြောက်၊ တို့ဟူးခြောက်", "Pratunam, Bangkok", "082-334-4556", "shwe_shan_bkk", 1000, "ဆိုင်အရောက် ပို့ဆောင်ခ အခမဲ့")
        )

        // 14. Voice Concierge Prompts & Queries
        _voiceQueries.value = listOf(
            VoiceQueryPrompt(
                queryBurmese = "BTS မိုချစ် ဘူတာ ဘယ်လိုသွားရမလဲ?",
                queryEnglish = "How to get to BTS Mo Chit?",
                category = "Transit",
                answerBurmese = "ဆူခွန်ဗစ်လိုင်း (Green Line) ရထားစီး၍ Mo Chit (N8) သို့ သွားရောက်နိုင်ပါသည်။ ချတုချက်ဈေးနှင့် မြောက်ပိုင်းကားဝင်းသို့ သွားရောက်ရန် အလွန်နီးပါသည်။",
                thaiPhrase = "ไปสถานีรถไฟฟ้าหมอชิตยังไงครับ? (Pai Sathani Rotfai Fah Mo Chit yang ngai khrap?)",
                relatedScreenRoute = "routes"
            ),
            VoiceQueryPrompt(
                queryBurmese = "၉၀ ရက် အစီရင်ခံစာ ဘယ်အချိန်တင်ရမလဲ?",
                queryEnglish = "When should I submit the 90-day report?",
                category = "Legal",
                answerBurmese = "ရက် ၉၀ ပြည့်ခါနီး ၁၅ ရက် ကြိုတင်၍ အွန်လိုင်း (Online) သို့မဟုတ် လဝကရုံးတွင် တင်ပြရပါမည်။ ရက်ကျော်ပါက ဒဏ်ငွေ ၂,၀၀၀ ဘတ် ပေးဆောင်ရပါမည်။",
                thaiPhrase = "รายงานตัว 90 วันต้องทำเมื่อไหร่ครับ? (Rai ngan tua 90 wan tong tham muea rai khrap?)",
                relatedScreenRoute = "safe_harbor"
            ),
            VoiceQueryPrompt(
                queryBurmese = "ဗန်ကောက် မြန်မာသံရုံး ဘယ်နေရာမှာရှိလဲ?",
                queryEnglish = "Where is the Myanmar Embassy in Bangkok?",
                category = "Transit",
                answerBurmese = "သံရုံးသည် ဆာသွန်လမ်း (North Sathorn Road) တွင် တည်ရှိပြီး BTS Surasak ဘူတာ Exit 3 မှ ဆင်း၍ လမ်းလျှောက် ၅ မိနစ်ခန့် သွားရောက်နိုင်ပါသည်။",
                thaiPhrase = "สถานทูตพม่าอยู่ตรงไหนครับ? (Sathantoot Phama yoo trong nai khrap?)",
                relatedScreenRoute = "map"
            ),
            VoiceQueryPrompt(
                queryBurmese = "အလုပ်ရှာဖွေရာမှာ ပွဲစားလိမ်လည်မှု ဘယ်လိုရှောင်ရမလဲ?",
                queryEnglish = "How to avoid job scams in Bangkok?",
                category = "Jobs",
                answerBurmese = "အလုပ်မဆင်းမီ ငွေကြိုလွှဲခိုင်းသော အွန်လိုင်းကြော်ငြာများကို လုံးဝ ငွေမလွှဲပါနှင့်။ စစ်ဆေးပြီး အလုပ်ရှင် တံဆိပ် (Verified Employer) ပါရှိသော အလုပ်များကိုသာ ဦးစားပေး ဆက်သွယ်ပါ။",
                thaiPhrase = "ระวังนายหน้าหลอกลวงหางาน (Rawang nainaa lokluang ha ngan)",
                relatedScreenRoute = "jobs"
            ),
            VoiceQueryPrompt(
                queryBurmese = "အဖျားကြီးပြီး နေမကောင်းဖြစ်ရင် ဘယ်လိုလုပ်ရမလဲ?",
                queryEnglish = "What to do when experiencing high fever?",
                category = "Health",
                answerBurmese = "လူမှုဖူလုံရေးကတ်ရှိပါက သတ်မှတ်ထားသော ဆေးရုံသို့ ပြသပါ။ အရေးပေါ် အသက်အန္တရာယ်ရှိပါက ၁၆၆၉ အရေးပေါ်လူနာတင်ယာဉ်သို့ ချက်ချင်း ဖုန်းခေါ်ဆိုပါ။",
                thaiPhrase = "เรียกรถพยาบาล 1669 (Riak rot phayaban 1669)",
                relatedScreenRoute = "safe_harbor"
            )
        )

        // 15. Phrasebook
        _phrasebook.value = listOf(
            TranslationRecord("ကားငှားခ ဘယ်လောက်ကျပါသလဲ?", "ค่ารถเท่าไหร่ครับ?", "ခါရော့ ထောက်ရိုင် ခရတ်?", "Transit"),
            TranslationRecord("ပရာခနောင်း ဈေးကို သွားချင်ပါတယ်", "อยากไปตลาดพระโขนงครับ", "ယက် ပိုင် သလတ် ပရာခနောင်း ခရတ်", "Transit"),
            TranslationRecord("မီတာဖွင့်ပေးပါခင်ဗျာ (တက္ကစီ)", "ช่วยเปิดมิเตอร์ด้วยครับ", "ချိုင့် ပွင့် မီတာ ဒွမ့် ခရတ်", "Transit"),
            TranslationRecord("အစပ် မထည့်ပါနဲ့ / အစပ် လျှော့ထည့်ပေးပါ", "ไม่เผ็ดครับ / เผ็ดน้อยครับ", "မိုင်ဖက် ခရတ် / ဖက်နွိုင် ခရတ်", "Food"),
            TranslationRecord("သောက်ရေ တစ်ခွက် ပေးပါ", "ขอน้ำเปล่าแก้วหนึ่งครับ", "ခေါ် နမ့်ပလောင် ဂယောက် နိင် ခရတ်", "Food"),
            TranslationRecord("ဒီမှာ ဆေးခန်း/ဆေးဆိုင် ရှိပါသလား?", "แถวนี้มีคลินิกหรือร้านขายยาไหมครับ?", "ထယ်ဝ်နီး မီ ခလင်းနစ် ရူ ရန်းခါင်ယာ မိုင်ခရတ်?", "Health"),
            TranslationRecord("ကျွန်တော့်ကို ကူညီပေးနိုင်မလားခင်ဗျာ?", "ช่วยผมหน่อยได้ไหมครับ?", "ချိုင့် ဖုန် နွိုင် ဒိုင်မိုင် ခရတ်?", "Help"),
            TranslationRecord("ဘတ်စကတ်အမှတ် ၇၇ ဘယ်အချိန်လာမလဲ?", "รถเมล์สาย 77 จะมากี่โมงครับ?", "ရော့မေး စိုင်း ၇၇ ဂျာ မာ ဂီမုန် ခရတ်?", "Transit")
        )

        // 16. Premium Offline Map Packs
        _offlineMapPacks.value = listOf(
            OfflineMapPack("pack_bkk_central", "ဘန်ကောက် ဗဟိုဇုန် (BTS/MRT အပြည့်အစုံ)", "Bangkok Central Hub (BTS & MRT Full)", "Central Bangkok", 48, true, false, 1.0f),
            OfflineMapPack("pack_mahachai", "မဟာချိုင်နှင့် စမွတ်ဆာခွန် စက်မှုဇုန်", "Mahachai & Samut Sakhon Industrial", "Bang Bon / Mahachai", 36, false, true, 0.0f),
            OfflineMapPack("pack_sathon_silom", "ဆာသွန်၊ ဆီလုံနှင့် သံရုံးရပ်ကွက်", "Sathon, Silom & Myanmar Embassy", "Sathon", 24, true, false, 1.0f),
            OfflineMapPack("pack_phrakhanong_onnut", "ပရာခနောင်း၊ အုန်းနွတ်နှင့် ဘန်နာဇုန်", "Phra Khanong, On Nut & Bang Na", "Phra Khanong", 32, false, false, 0.0f),
            OfflineMapPack("pack_nonthaburi", "နွန်သဘူရီနှင့် ကျောက်ဖရားမြစ်ကြောင်း", "Nonthaburi & Chao Phraya Riverline", "Nonthaburi", 28, false, true, 0.0f),
            OfflineMapPack("pack_eastern_corridor", "သုဝဏ္ဏဘူမိနှင့် ဘန်ဖလီ အရှေ့ပိုင်းစက်မှုဇုန်", "Suvarnabhumi & Bang Phli East", "Eastern Bangkok", 42, false, true, 0.0f)
        )

        // 17. Telemedicine Myanmar Doctors
        _telemedDoctors.value = listOf(
            TelemedDoctor("doc_1", "ဒေါက်တာ ကျော်ဇင်ဝင်း (Dr. Kyaw Zin Win)", "အထွေထွေရောဂါကုနှင့် အဖျားရောဂါအထူးကု", listOf("Burmese", "Thai", "English"), "Bangkok Christian Hospital", 150, "08:00 - 20:00", 4.9, true),
            TelemedDoctor("doc_2", "ဒေါက်တာ သီတာအေး (Dr. Thida Aye)", "အလုပ်ခွင်ကျန်းမာရေးနှင့် အရိုးအကြောအထူးကု", listOf("Burmese", "Thai"), "Thonburi Healthcare Hub", 180, "10:00 - 22:00", 4.8, true),
            TelemedDoctor("doc_3", "ဒေါက်တာ မင်းမင်းထွန်း (Dr. Min Min Tun)", "ထိခိုက်ဒဏ်ရာနှင့် ခွဲစိတ်အထူးကု အကြံပေး", listOf("Burmese", "Thai", "English"), "Yanhee International Desk", 200, "09:00 - 18:00", 4.9, false),
            TelemedDoctor("doc_4", "ဒေါက်တာ မေသူ (Dr. May Thu)", "စိတ်ဖိစီးမှုနှင့် စိတ်ပိုင်းဆိုင်ရာ နှစ်သိမ့်ဆွေးနွေးသူ", listOf("Burmese", "English"), "Community Wellness Center", 120, "13:00 - 21:00", 5.0, true)
        )

        // 18. Verified Remittance Providers
        _remittanceProviders.value = listOf(
            RemittanceProvider("rem_1", "DeeMoney Official App", "Official Remittance App", 128.5, 49, "၅ မိနစ်အတွင်း (KPay / WavePay)", true, "အန္တရာယ်ကင်း (BOT လိုင်စင်ရ)"),
            RemittanceProvider("rem_2", "TrueMoney Myanmar Transfer", "Bank & E-Wallet", 128.2, 50, "ချက်ချင်းရောက် (KBZ, CB, AYA)", true, "အန္တရာယ်ကင်း (၇-Eleven တွင် ငွေသွင်းနိုင်)"),
            RemittanceProvider("rem_3", "Kasikorn Bank (K-Bank) Direct", "Official Bank-to-Bank", 127.8, 100, "၁ ရက်အတွင်း (တရားဝင်ဘဏ်လွှဲ)", true, "အလွန်လုံခြုံသည်"),
            RemittanceProvider("rem_4", "တရားမဝင် အွန်လိုင်းဟွန်ဒီများ", "Unverified Agent", 132.0, 0, "မသေချာ (ငွေလိမ်ခံရနိုင်ခြေများ)", false, "⚠️ သတိပြုရန်: အကောင့်ပိတ်ခံရနိုင်ပြီး လိမ်လည်ခံရနိုင်သည်")
        )

        // 19. Golden Pass Subscription Tiers
        _goldenPassTiers.value = listOf(
            GoldenPassTier(
                id = "tier_worker",
                titleBurmese = "ရွှေလုပ်သား ရွှေခရီး Pass (Worker Pass)",
                titleEnglish = "Golden Worker Pass",
                priceThbMonthly = 49,
                badgeLabel = "ရွှေတံဆိပ်",
                featuresBurmese = listOf(
                    "အင်တာနက်မလိုသည့် အော့ဖ်လိုင်းမြေပုံ အပြည့်အစုံ ဒေါင်းလုဒ်ရယူခြင်း",
                    "ကြော်ငြာလုံးဝမပါဝင်သော အတွေ့အကြုံ",
                    "ဂိမ်း Quests အမှတ်များ ၂ ဆ (Double XP)",
                    "၂၄ နာရီ မြန်မာအသံလက်ထောက် အကန့်အသတ်မဲ့ မေးမြန်းနိုင်ခြင်း",
                    "အလုပ်လျှောက်ထားရာတွင် ဦးစားပေး စစ်ဆေးပြီး Profile တံဆိပ်"
                ),
                isRecommended = true,
                isCurrent = true
            ),
            GoldenPassTier(
                id = "tier_guardian",
                titleBurmese = "ဘေးကင်းဆိပ်ကမ်း Guardian Plus (Family Plan)",
                titleEnglish = "Guardian Family Plus",
                priceThbMonthly = 99,
                badgeLabel = "လုံခြုံရေးတံဆိပ်",
                featuresBurmese = listOf(
                    "မြန်မာဆရာဝန်များနှင့် အကန့်အသတ်မဲ့ Telemedicine အွန်လိုင်းဆွေးနွေးခွင့်",
                    "ထိုင်းရှေ့နေနှင့် ဥပဒေအကြံပေး ဦးစားပေး ချိတ်ဆက်မှု",
                    "မိသားစုဝင် ၅ ဦးအထိ အရေးပေါ် တည်နေရာမျှဝေခြင်း",
                    "လုံခြုံစိတ်ချရသော Cloud Document Vault သိမ်းဆည်းမှု"
                ),
                isRecommended = false,
                isCurrent = false
            ),
            GoldenPassTier(
                id = "tier_business",
                titleBurmese = "စီးပွားရေး စက်ဝိုင်း Business Pro",
                titleEnglish = "Business Circle Pro",
                priceThbMonthly = 199,
                badgeLabel = "လုပ်ငန်းရှင်တံဆိပ်",
                featuresBurmese = listOf(
                    "ဆိုင်ခွဲများစွာ POS စနစ်နှင့် PromptPay QR စာရင်းအပြည့်အစုံ",
                    "ဝန်ထမ်း အလုပ်ပါမစ် သက်တမ်းကုန်ဆုံးမှု အလိုအလျောက် သတိပေးချက်",
                    "B2B ကုန်စည်ဒိုင် လက်ကားဈေးနှုန်း အထူးလျှော့စျေးများ",
                    "လစဉ် အခွန်နှင့် အမြတ်စာရင်း အစီရင်ခံစာ ထုတ်ယူနိုင်ခြင်း"
                ),
                isRecommended = false,
                isCurrent = false
            )
        )
    }

    // Interactive helper actions
    fun toggleJobSaved(jobId: String) {
        _jobs.update { list ->
            list.map { if (it.id == jobId) it.copy(isSaved = !it.isSaved) else it }
        }
    }

    fun completeQuest(questId: String) {
        _chapters.update { chaptersList ->
            chaptersList.map { chapter ->
                val updatedQuests = chapter.quests.map { quest ->
                    if (quest.id == questId && !quest.isCompleted) {
                        addXp(quest.xpReward)
                        quest.copy(isCompleted = true)
                    } else quest
                }
                chapter.copy(quests = updatedQuests)
            }
        }
    }

    private fun addXp(earned: Int) {
        _avatarProfile.update { current ->
            val newXp = current.currentXp + earned
            if (newXp >= current.nextLevelXp) {
                current.copy(
                    level = current.level + 1,
                    currentXp = newXp - current.nextLevelXp,
                    nextLevelXp = (current.nextLevelXp * 1.5).toInt(),
                    coins = current.coins + 200
                )
            } else {
                current.copy(currentXp = newXp, coins = current.coins + 50)
            }
        }
    }

    fun updateOutfit(newOutfit: String) {
        _avatarProfile.update { it.copy(outfitStyle = newOutfit) }
    }

    // POS Cart actions
    fun addToCart(product: PosProduct) {
        _cart.update { currentList ->
            val existing = currentList.find { it.product.id == product.id }
            if (existing != null) {
                currentList.map {
                    if (it.product.id == product.id) it.copy(quantity = it.quantity + 1) else it
                }
            } else {
                currentList + CartItem(product, 1)
            }
        }
    }

    fun removeFromCart(productId: String) {
        _cart.update { currentList ->
            val existing = currentList.find { it.product.id == productId }
            if (existing != null && existing.quantity > 1) {
                currentList.map {
                    if (it.product.id == productId) it.copy(quantity = it.quantity - 1) else it
                }
            } else {
                currentList.filter { it.product.id != productId }
            }
        }
    }

    fun clearCart() {
        _cart.value = emptyList()
    }

    fun toggleDownloadMapPack(packId: String) {
        _offlineMapPacks.update { list ->
            list.map {
                if (it.id == packId) {
                    val nextDownloaded = !it.isDownloaded
                    it.copy(isDownloaded = nextDownloaded, downloadProgress = if (nextDownloaded) 1.0f else 0.0f)
                } else it
            }
        }
    }

    fun upgradeGoldenPass(tierTitle: String) {
        _avatarProfile.update { it.copy(isGoldenPassActive = true, currentTierName = tierTitle) }
        _goldenPassTiers.update { list ->
            list.map { it.copy(isCurrent = it.titleBurmese == tierTitle || it.titleEnglish == tierTitle) }
        }
    }
}

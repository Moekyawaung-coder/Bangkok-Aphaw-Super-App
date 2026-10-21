package com.example.data.models

data class PosProduct(
    val id: String,
    val nameBurmese: String,
    val nameThai: String,
    val priceThb: Double,
    val category: String, // "Food & Drinks", "Myanmar Groceries", "Services", "Cosmetics"
    val stockQuantity: Int,
    val unit: String
)

data class CartItem(
    val product: PosProduct,
    var quantity: Int
)

data class StaffMember(
    val id: String,
    val name: String,
    val roleBurmese: String,
    val phone: String,
    val permitExpiryDate: String,
    val daysUntilExpiry: Int,
    val isPermitExpiringSoon: Boolean
)

data class WholesaleSupplier(
    val id: String,
    val businessName: String,
    val goodsCategoryBurmese: String, // "ရှမ်းလက်ဖက်/လက်ဖက်ခြောက်", "ငါးပိ/ငါးခြောက်", "ခေါက်ဆွဲ/မုန့်ဟင်းခါး", "ကုန်စည်ပို့ဆောင်ရေး"
    val locationArea: String,
    val phone: String,
    val lineId: String,
    val minOrderThb: Int,
    val deliverySupport: String
)

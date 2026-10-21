package com.example

import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testRepositoryInitialData() {
    val repository = com.example.data.repository.AppRepository()
    assertTrue(repository.stations.value.isNotEmpty())
    assertTrue(repository.littleMyanmarPlaces.value.isNotEmpty())
    assertTrue(repository.jobs.value.isNotEmpty())
    assertTrue(repository.chapters.value.isNotEmpty())
    assertTrue(repository.emergencyContacts.value.isNotEmpty())
    assertTrue(repository.posProducts.value.isNotEmpty())
  }

  @Test
  fun testPosCartCalculations() {
    val repository = com.example.data.repository.AppRepository()
    val product = repository.posProducts.value.first()
    repository.addToCart(product)
    assertEquals(1, repository.cart.value.size)
    assertEquals(product.id, repository.cart.value.first().product.id)
    assertEquals(1, repository.cart.value.first().quantity)

    repository.addToCart(product)
    assertEquals(2, repository.cart.value.first().quantity)

    repository.removeFromCart(product.id)
    assertEquals(1, repository.cart.value.first().quantity)

    repository.clearCart()
    assertTrue(repository.cart.value.isEmpty())
  }

  @Test
  fun testPremiumFeaturesAndMapPacks() {
    val repository = com.example.data.repository.AppRepository()
    assertTrue(repository.offlineMapPacks.value.isNotEmpty())
    assertTrue(repository.telemedDoctors.value.isNotEmpty())
    assertTrue(repository.remittanceProviders.value.isNotEmpty())
    assertTrue(repository.goldenPassTiers.value.isNotEmpty())

    // Test Map pack toggle
    val pack = repository.offlineMapPacks.value.first()
    val initialState = pack.isDownloaded
    repository.toggleDownloadMapPack(pack.id)
    val toggledState = repository.offlineMapPacks.value.first { it.id == pack.id }.isDownloaded
    assertNotEquals(initialState, toggledState)

    // Test Golden Pass upgrade
    repository.upgradeGoldenPass("Business Circle Pro")
    assertEquals("Business Circle Pro", repository.avatarProfile.value.currentTierName)
    assertTrue(repository.avatarProfile.value.isGoldenPassActive)
  }

  @Test
  fun testRoomDatabaseEntities() {
    val post = com.example.data.local.entities.CommunityPostEntity(
        id = "post_101",
        authorName = "Ko Min Thein",
        authorBadge = "Verified Worker",
        category = "Housing",
        titleBurmese = "ပရာခနောင်း အဆောင်ရှာဖွေသူများအတွက် အခန်းလွတ်ရှိသည်",
        contentBurmese = "BTS Phra Khanong အနီး အဆောင်အခန်းလွတ်ရှိပါသည်။ ရေမီးပါပြီး တစ်လ ၃၅၀၀ ဘတ်။",
        locationTag = "Phra Khanong",
        likesCount = 14,
        commentsCount = 5,
        isVerifiedPost = true
    )
    assertEquals("post_101", post.id)
    assertEquals("Housing", post.category)
    assertTrue(post.isVerifiedPost)

    val profile = com.example.data.local.entities.UserProfileEntity(
        userId = "usr_991",
        name = "Aung Kyaw",
        burmeseName = "အောင်ကျော်",
        avatarStyle = "Traditional Longyi & Taikpon",
        level = 3,
        xp = 450,
        coins = 1600,
        isVerifiedWorker = true,
        isGoldenPassMember = true,
        tierName = "Golden Worker Pass",
        stayDurationMonths = 18,
        currentCityZone = "Mahachai"
    )
    assertEquals("usr_991", profile.userId)
    assertEquals(3, profile.level)
    assertTrue(profile.isGoldenPassMember)
  }
}

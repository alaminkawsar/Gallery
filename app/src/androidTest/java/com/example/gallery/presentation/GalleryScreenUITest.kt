package com.example.gallery.presentation

import android.annotation.SuppressLint
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.espresso.action.ViewActions.swipeUp
import com.example.gallery.MainActivity
import com.example.gallery.di.AppModule
import com.example.gallery.ui.theme.GalleryTheme
import com.example.gallery.utils.Screen
import com.example.gallery.utils.TestTag
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
@UninstallModules(AppModule::class) // tell the application don't use the app module
@HiltAndroidTest
class GalleryScreenUITest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun showSyncingMessageWhenInternetIsActive() {
        composeRule.onNodeWithTag(TestTag.SYNC_MESSAGE).assertExists()
        composeRule.onNodeWithTag(TestTag.SYNC_MESSAGE).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTag.SYNC_MESSAGE)
            .assertHeightIsEqualTo(200.dp)
            .assertWidthIsEqualTo(200.dp)
        Thread.sleep(5000)
    }

    @Test
    fun clickClearButton_isVisible() {
        composeRule.onNodeWithTag(TestTag.CLEAR_BUTTON).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("menu").assertExists()
        composeRule.onNodeWithContentDescription("menu").performClick()
        composeRule.onNodeWithTag(TestTag.CLEAR_BUTTON).assertIsDisplayed()
        Thread.sleep(3000)
    }

    @Test
    fun showTryAgainButtonAfterPressClearButton() {
        clickClearButton_isVisible()
        Thread.sleep(2000)
        composeRule.onNodeWithTag(TestTag.CLEAR_BUTTON).performClick()
        composeRule.onNodeWithTag(TestTag.NO_PHOTO).assertExists()
        composeRule.onNodeWithTag(TestTag.TRY_AGAIN_BUTTON).assertExists()
        Thread.sleep(5000)
    }

    @Test
    fun dataFetchUsingTryAgainButtonPress() {
        showTryAgainButtonAfterPressClearButton()
        composeRule.onNodeWithTag(TestTag.TRY_AGAIN_BUTTON).performClick()
        showSyncingMessageWhenInternetIsActive()
    }

    @SuppressLint("CheckResult")
    @Test
    fun showPhotoListWithScrolling() {
        showSyncingMessageWhenInternetIsActive()
        composeRule.onNodeWithTag(TestTag.SCROLL_VIEW).assertExists()
        composeRule.onNodeWithTag(TestTag.SCROLL_VIEW).assertIsDisplayed()
        repeat(10) {
            composeRule.onNodeWithTag(TestTag.SCROLL_VIEW).performTouchInput {
                swipeUp()
            }
        }
        composeRule.onNodeWithTag(TestTag.SCROLL_VIEW).assertExists()
        Thread.sleep(5000)
    }
}
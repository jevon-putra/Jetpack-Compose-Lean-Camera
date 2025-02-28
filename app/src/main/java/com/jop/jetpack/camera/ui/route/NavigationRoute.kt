package com.jop.jetpack.camera.ui.route

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jop.jetpack.camera.data.model.ImageCapture
import com.jop.jetpack.camera.view.camera.screen.CameraScreen
import com.jop.jetpack.camera.view.home.screen.HomeScreen
import com.jop.jetpack.camera.view.previewPhoto.screen.PreviewPhotoScreen

@Composable
fun NavigationRoute() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.HOME,
        enterTransition = { scaleIn(tween(700), initialScale = 0.5f) + fadeIn(tween(50)) },
        exitTransition = { scaleOut(tween(500), targetScale = 0.5f) + fadeOut(tween(50)) },
        popEnterTransition = { scaleIn(tween(700), initialScale = 0.5f) + fadeIn(tween(50)) },
        popExitTransition = { scaleOut(tween(500), targetScale = 0.5f) + fadeOut(tween(50)) }
    ){
        composable(route = Route.HOME){
            HomeScreen(navController)
        }
        composable(route = Route.CAMERA){
            CameraScreen(navController)
        }
        composable(route = Route.PREVIEW_PHOTO){
            val imageCapture = navController.previousBackStackEntry?.savedStateHandle?.get<ImageCapture>("image")
            PreviewPhotoScreen(navController, imageCapture)
        }
    }
}
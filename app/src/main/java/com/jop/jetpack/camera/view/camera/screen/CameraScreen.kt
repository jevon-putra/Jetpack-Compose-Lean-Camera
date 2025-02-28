package com.jop.jetpack.camera.view.camera.screen

import android.app.Activity
import androidx.camera.core.CameraSelector
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.jop.jetpack.camera.MainActivity
import com.jop.jetpack.camera.data.model.ImageCapture
import com.jop.jetpack.camera.ui.component.CustomToolbar
import com.jop.jetpack.camera.view.camera.viewModel.CameraViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen(navController: NavHostController, viewModel: CameraViewModel = koinViewModel()) {
    val context = LocalContext.current
    val activity = context as Activity

    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
        }
    }

    if(viewModel.imageResult.value == null){
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            CameraPreview(
                controller = controller,
                modifier = Modifier.fillMaxSize()
            )

            Row(
                modifier = Modifier.fillMaxWidth().windowInsetsPadding(WindowInsets.safeContent),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    modifier = Modifier.padding(16.dp).size(32.dp),
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                    )
                }

                IconButton(
                    modifier = Modifier.padding(16.dp).size(32.dp),
                    onClick = {
                        controller.cameraSelector = if(controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA)  CameraSelector.DEFAULT_FRONT_CAMERA
                        else  CameraSelector.DEFAULT_BACK_CAMERA
                    }
                ) {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = Icons.Default.Cameraswitch,
                        contentDescription = "Switch",
                        tint = Color.White,
                    )
                }
            }

            IconButton(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.safeContent)
                    .padding(16.dp)
                    .size(60.dp)
                    .align(Alignment.BottomCenter),
                onClick = {
                    (activity as MainActivity).takePicture(
                        controller,
                        onPhotoTaken = { bitmap -> viewModel.imageResult.value = bitmap }
                    )
                }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize().padding(4.dp),
                    imageVector = Icons.Default.Camera,
                    contentDescription = "Capture",
                    tint = Color.White,
                )
            }
        }
    } else {
        Scaffold(
            topBar = {
                CustomToolbar(
                    title = "Select Image",
                    canNavigateBack = true,
                    navigateUp = { navController.popBackStack() },
                    color = TopAppBarDefaults.topAppBarColors().copy(
                        containerColor = Color.Black,
                        titleContentColor = MaterialTheme.colorScheme.surface,
                        navigationIconContentColor = MaterialTheme.colorScheme.surface
                    )
                )
            },
        ) { padding ->
            Column(modifier = Modifier.fillMaxSize()) {
                Image(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    contentScale = ContentScale.Crop,
                    bitmap = viewModel.imageResult.value!!.asImageBitmap(),
                    contentDescription = "",
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black)
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    IconButton(
                        modifier = Modifier.size(60.dp),
                        onClick = { viewModel.imageResult.value = null }
                    ) {
                        Icon(
                            modifier = Modifier.fillMaxSize().padding(8.dp),
                            imageVector = Icons.Default.Close,
                            contentDescription = "Capture",
                            tint = Color.White,
                        )
                    }

                    IconButton(
                        modifier = Modifier.size(60.dp),
                        onClick = {
                            viewModel.insert()
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            modifier = Modifier.fillMaxSize().padding(8.dp),
                            imageVector = Icons.Default.Check,
                            contentDescription = "Capture",
                            tint = Color.White,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CameraPreview(controller: LifecycleCameraController, modifier: Modifier){
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        factory = {
            PreviewView(it).apply {
                this.controller = controller
                controller.bindToLifecycle(lifecycleOwner)
            }
        },
        modifier = modifier
    )
}
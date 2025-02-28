package com.jop.jetpack.camera.view.previewPhoto.screen

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.navigation.NavHostController
import com.jop.jetpack.camera.data.model.ImageCapture
import com.jop.jetpack.camera.ui.component.CustomAlertDialog
import com.jop.jetpack.camera.ui.component.CustomToolbar
import kotlinx.coroutines.Job

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewPhotoScreen(navController: NavHostController, imageCapture: ImageCapture?, deleteImage: (ImageCapture) -> Job) {
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val showAlertDialog = remember { mutableStateOf(false) }
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    BackHandler {
        navController.previousBackStackEntry?.savedStateHandle?.remove<ImageCapture>("image")
        navController.popBackStack()
    }

    Scaffold(
        topBar = {
            CustomToolbar(
                title = "Preview Image",
                canNavigateBack = true,
                navigateUp = { onBackPressedDispatcher?.onBackPressed() },
                color = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = Color.Black,
                    titleContentColor = MaterialTheme.colorScheme.surface,
                    navigationIconContentColor = MaterialTheme.colorScheme.surface,
                    actionIconContentColor = MaterialTheme.colorScheme.surface,
                ),
                actions = {
                    IconButton(onClick = { showAlertDialog.value = true }) {
                        Icon(
                            imageVector = Icons.Default.DeleteOutline,
                            contentDescription = "Delete",
                        )
                    }
                }
            )
        },
    ) { padding ->
        if(showAlertDialog.value){
            CustomAlertDialog(
                title = "Hapus Gambar",
                message = "Apakah anda yakin ingin menghapus gambar ini?",
                btnConfirmText = "Hapus",
                btnCancelText = "Batal",
                btnConfirmAction = {
                    deleteImage(imageCapture!!)
                    onBackPressedDispatcher?.onBackPressed()
                },
                onDismiss = {
                    showAlertDialog.value = false
                }
            )
        }

        imageCapture?.imageBitmap?.asImageBitmap()?.let {
            val state = rememberTransformableState { zoomChange, panChange, rotationChange ->
                scale = (scale * zoomChange).coerceIn(1f, 4f)

                val extraWidth = (scale - 1) * imageCapture.imageBitmap.width
                val extraHeight = (scale - 1) * imageCapture.imageBitmap.height

                val maxX = extraWidth / 2
                val maxY = extraHeight / 2

                offset = Offset(
                    x = (offset.x + scale * panChange.x).coerceIn(-maxX, maxX),
                    y = (offset.y + scale * panChange.y).coerceIn(-maxY, maxY)
                )
            }

            Image(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxSize()
                    .consumeWindowInsets(padding)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onDoubleTap = {
                                scale = 1f
                                offset = Offset.Zero
                            }
                        )
                    }
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        translationX = offset.x
                        translationY = offset.y
                    }
                    .transformable(state),
                bitmap = it,
                contentDescription = "",
            )
        }
    }
}
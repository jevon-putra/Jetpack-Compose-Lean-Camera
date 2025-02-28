package com.jop.jetpack.camera.view.home.screen

import android.Manifest
import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import com.jop.jetpack.camera.data.model.ImageCapture
import com.jop.jetpack.camera.ui.component.CustomAlertDialog
import com.jop.jetpack.camera.ui.component.CustomToolbar
import com.jop.jetpack.camera.ui.component.PermissionDialog
import com.jop.jetpack.camera.ui.route.Route
import kotlinx.coroutines.Job

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, listState: State<List<ImageCapture>>, deleteAll: () -> Job) {
    val context = LocalContext.current
    val showAlertPermission = remember { mutableStateOf(false) }
    val showAlertDelete = remember { mutableStateOf(false) }
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGrant ->
            if(isGrant){
                navController.navigate(Route.CAMERA)
            } else {
                if(ActivityCompat.shouldShowRequestPermissionRationale(context as Activity , Manifest.permission.READ_MEDIA_IMAGES)){
                    Toast.makeText(context,"Izinkan aplikasi untuk mengakses kamera", Toast.LENGTH_SHORT).show()
                } else {
                    showAlertPermission.value = true
                }
            }
        }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomToolbar(
                title = "Learning Camera",
                color = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.surface,
                    actionIconContentColor = MaterialTheme.colorScheme.surface,
                ),
                actions = {
                    IconButton(onClick = { showAlertDelete.value = true }) {
                        Icon(
                            imageVector = Icons.Default.DeleteForever,
                            contentDescription = "Delete",
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton (
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(32.dp),
                onClick = {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                },
            ) {
                Icon(Icons.Default.CameraAlt,
                    contentDescription = "Capture Camera",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    ){ padding ->
        if(showAlertPermission.value){
            PermissionDialog(
                title = "Peringatan",
                message = "Izinkan aplikasi untuk mengakses kamera",
                onDismiss = { showAlertPermission.value = false }
            )
        }

        if(showAlertDelete.value){
            CustomAlertDialog(
                title = "Hapus Gambar",
                message = "Apakah anda yakin ingin menghapus semua gambar?",
                btnConfirmText = "Hapus",
                btnCancelText = "Batal",
                btnConfirmAction = { deleteAll() },
                onDismiss = { showAlertDelete.value = false }
            )
        }

        if(listState.value.isEmpty()){
            Column(
                modifier = Modifier.fillMaxSize().padding(padding),
                verticalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.CenterVertically
                )
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Belum ada foto yang tersimpan",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        textAlign = TextAlign.Center
                    )
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Tekan tombol kamera dan abadikan momen mu",
                    style = MaterialTheme.typography.bodySmall.copy(
                        textAlign = TextAlign.Center
                    )
                )
            }
        } else {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize().padding(padding),
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(listState.value) { image ->
                    Card(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clickable {
                                navController.currentBackStackEntry?.savedStateHandle?.set("image", image)
                                navController.navigate(Route.PREVIEW_PHOTO)
                            },
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Image(
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            contentScale = ContentScale.Crop,
                            bitmap = image.imageBitmap.asImageBitmap(),
                            contentDescription = "",
                        )
                    }
                }
            }
        }
    }
}
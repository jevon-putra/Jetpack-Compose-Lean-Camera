package com.jop.jetpack.camera.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomToolbar(title: String = "", canNavigateBack: Boolean = false, navigateUp: () -> Unit = {}, color: TopAppBarColors = TopAppBarDefaults.topAppBarColors(), actions: @Composable RowScope.() -> Unit = {}){
    TopAppBar(
        title = { if(title.isNotEmpty()) Text(text = title, style = MaterialTheme.typography.titleLarge) },
        actions = actions,
        colors = color,
        navigationIcon = {
            if(canNavigateBack){
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = color.navigationIconContentColor
                    )
                }
            }
        }
    )
}
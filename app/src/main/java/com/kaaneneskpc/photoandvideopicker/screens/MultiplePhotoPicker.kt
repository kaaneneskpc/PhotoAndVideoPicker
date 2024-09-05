package com.kaaneneskpc.photoandvideopicker.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kaaneneskpc.photoandvideopicker.util.StorageUtil

@Composable
fun MultiplePhotoPicker() {
    val context = LocalContext.current

    var imageUris by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }

    val multiplePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = {
            imageUris = it
        }
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 32.dp)) {
        Row {
            Button(onClick = {
                multiplePhotoPicker.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }) {
                Text("Pick Multiple Images")
            }

            Button(onClick = {
                imageUris.forEach { uri ->
                    uri?.let {
                        StorageUtil.uploadToStorage(uri = it, context = context, type = "image")
                    }
                }
            }) {
                Text("Upload")
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
            items(imageUris) { uri ->
                AsyncImage(model = uri, contentDescription = null, modifier = Modifier.size(248.dp))
            }
        }
    }
}
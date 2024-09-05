package com.kaaneneskpc.photoandvideopicker.screens

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.kaaneneskpc.photoandvideopicker.util.DispatchGroup

@Composable
fun Home() {
    var imageUris: MutableList<Uri> by remember { mutableStateOf(mutableListOf()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = Unit){
        val storage = Firebase.storage
        val listRef = storage.reference.child("images")
        val dispatchGroup = DispatchGroup()

        val tmpUris: MutableList<Uri> = mutableListOf()
        listRef.listAll()
            .addOnSuccessListener{results ->
                results.items.forEach { res ->
                    dispatchGroup.enter()
                    res.downloadUrl
                        .addOnSuccessListener { uri ->
                            tmpUris.add(uri)
                            dispatchGroup.leave()
                            isLoading = false
                        }
                        .addOnFailureListener {
                            isLoading = false
                        }

                    dispatchGroup.notify {
                        imageUris = tmpUris

                    }
                }
            }
            .addOnFailureListener {
               isLoading = false
            }
    }


    Column(modifier = Modifier.fillMaxSize().padding(top = 32.dp)) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            Text("Uploaded Images")
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .weight(0.5f)
            ) {
                items(imageUris) { uri ->
                    AsyncImage(
                        model = uri,
                        contentDescription = null,
                        modifier = Modifier.size(200.dp)
                    )
                }
            }
        }
    }
}
package dev.kiyo9w.kmpboilerplate.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import dev.kiyo9w.kmpboilerplate.R
import dev.kiyo9w.kmpboilerplate.feature.catalog.CatalogDetailViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogDetailScreen(itemId: Long, navigateBack: () -> Unit) {
    val viewModel: CatalogDetailViewModel = koinViewModel()
    val item by viewModel.item.collectAsStateWithLifecycle()

    LaunchedEffect(itemId) {
        viewModel.setId(itemId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(item?.breed.orEmpty()) },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.back))
                    }
                },
            )
        },
    ) { padding ->
        val current = item
        if (current == null) {
            EmptyScreenContent(
                message = stringResource(R.string.no_data_available),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
            )
        } else {
            Column(
                Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(padding),
            ) {
                AsyncImage(
                    model = current.imageUrl,
                    contentDescription = current.breed,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                )
                Column(Modifier.padding(16.dp)) {
                    Text(current.breed, style = MaterialTheme.typography.headlineMedium)
                    Spacer(Modifier.height(8.dp))
                    Text(stringResource(R.string.label_source, current.source))
                    Text(stringResource(R.string.label_id, current.id.toString()))
                }
            }
        }
    }
}

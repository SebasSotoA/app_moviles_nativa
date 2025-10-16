package com.app.episodic.ui.lists.create

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.episodic.ui.theme.EpisodicTheme
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateListScreen(
    viewModel: CreateListViewModel = hiltViewModel(),
    onClose: () -> Unit,
    onCreated: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val focusManager = LocalFocusManager.current

    EpisodicTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Crear lista") },
                    navigationIcon = {
                        IconButton(onClick = onClose) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Cerrar"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = state.name,
                    onValueChange = viewModel::onNameChange,
                    label = { Text("Nombre de la lista") },
                    placeholder = { Text("Terror") },
                    isError = state.nameError != null,
                    supportingText = state.nameError?.let { { Text(it) } },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { 
                            viewModel.onSubmit { name ->
                                focusManager.clearFocus()
                                onCreated(name)
                            }
                        }
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.weight(1f))
                
                Button(
                    onClick = { 
                        viewModel.onSubmit { name ->
                            focusManager.clearFocus()
                            onCreated(name)
                        }
                    },
                    enabled = !state.isSubmitting && state.name.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    if (state.isSubmitting) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text("Crear Lista")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateListScreen_Preview() {
    EpisodicTheme {
        CreateListScreen(
            onClose = {},
            onCreated = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CreateListScreen_ErrorPreview() {
    EpisodicTheme {
        CreateListScreenPreview(
            state = CreateListState(
                name = "",
                nameError = "Ingresa un nombre válido"
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateListScreenPreview(
    state: CreateListState,
    onClose: () -> Unit = {},
    onCreated: (String) -> Unit = {}
) {
    EpisodicTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Crear lista") },
                    navigationIcon = {
                        IconButton(onClick = onClose) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Cerrar"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = state.name,
                    onValueChange = {},
                    label = { Text("Nombre de la lista") },
                    placeholder = { Text("Terror") },
                    isError = state.nameError != null,
                    supportingText = state.nameError?.let { { Text(it) } },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.weight(1f))
                
                Button(
                    onClick = { onCreated(state.name) },
                    enabled = !state.isSubmitting && state.name.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    if (state.isSubmitting) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text("Crear Lista")
                    }
                }
            }
        }
    }
}

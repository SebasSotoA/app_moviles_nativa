package com.app.episodic.ui.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.app.episodic.R
import java.util.*

@Composable
fun FilterDialog(
    initialMinRating: Float = 0f,
    initialYear: String = "",
    initialSelectedGenres: List<String> = emptyList(),
    onDismiss: () -> Unit,
    onApplyFilter: (minRating: Float, genres: List<String>, year:Int? ) -> Unit,
    onClearFilters: () -> Unit = {}
) {
    val context = LocalContext.current

    // Cargar los géneros desde strings.xml
    val genres = listOf(
        context.getString(R.string.genre_action),
        context.getString(R.string.genre_adventure),
        context.getString(R.string.genre_animation),
        context.getString(R.string.genre_comedy),
        context.getString(R.string.genre_crime),
        context.getString(R.string.genre_documentary),
        context.getString(R.string.genre_drama),
        context.getString(R.string.genre_family),
        context.getString(R.string.genre_fantasy),
        context.getString(R.string.genre_history),
        context.getString(R.string.genre_horror),
        context.getString(R.string.genre_music),
        context.getString(R.string.genre_mystery),
        context.getString(R.string.genre_romance),
        context.getString(R.string.genre_scifi),
        context.getString(R.string.genre_tv_movie),
        context.getString(R.string.genre_thriller),
        context.getString(R.string.genre_war),
        context.getString(R.string.genre_western)
    )

    // Inicializar el diálogo con los valores pasados
    var minRating by remember { mutableStateOf(initialMinRating) }
    val selectedGenres = remember { mutableStateOf(initialSelectedGenres.toSet()) }
    var query by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var releaseYearText by remember { mutableStateOf(initialYear) }

    val yearError by remember(releaseYearText) {
        mutableStateOf(releaseYearText.isNotEmpty() && (releaseYearText.length != 4 || releaseYearText.any { !it.isDigit() }))
    }

    val hasFilters = (minRating > 0f) || selectedGenres.value.isNotEmpty() || releaseYearText.isNotEmpty()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.FilterList, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Filtrar contenido",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .heightIn(max = 480.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Calificación
                Text("Calificación mínima", fontWeight = FontWeight.Bold)
                RatingSection(minRating) { minRating = it }
                // Presets rápidos
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(0f, 6f, 7f, 8f).forEach { preset ->
                        AssistChip(
                            onClick = { minRating = preset },
                            label = { Text(if (preset == 0f) "Cualquiera" else ">= ${preset}") },
                            leadingIcon = { Icon(Icons.Default.Star, contentDescription = null) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = if (minRating == preset) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
                            )
                        )
                    }
                }

                // Año
                Text("Año", fontWeight = FontWeight.Bold)
                OutlinedTextField(
                    value = releaseYearText,
                    onValueChange = { input ->
                        if (input.length <= 4 && input.all { it.isDigit() }) releaseYearText = input
                    },
                    label = { Text("Año de lanzamiento (opcional)") },
                    placeholder = { Text("Ej. 2021") },
                    supportingText = { if (yearError) Text("Ingresa 4 dígitos", color = MaterialTheme.colorScheme.error) },
                    isError = yearError,
                    singleLine = true,
                    trailingIcon = {
                        if (releaseYearText.isNotEmpty()) {
                            IconButton(onClick = { releaseYearText = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Borrar año")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                // Géneros
                GenreHeader(selectedCount = selectedGenres.value.size) {
                    selectedGenres.value = emptySet()
                }
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    label = { Text("Buscar género") },
                    singleLine = true,
                    trailingIcon = {
                        if (query.isNotEmpty()) {
                            IconButton(onClick = { query = "" }) { Icon(Icons.Default.Clear, contentDescription = "Borrar búsqueda") }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                GenreSearchAndChips(
                    genres = genres,
                    query = query,
                    expanded = expanded,
                    onToggleExpanded = { expanded = !expanded },
                    selected = selectedGenres.value,
                    onToggleGenre = { genre ->
                        selectedGenres.value = if (genre in selectedGenres.value)
                            selectedGenres.value - genre else selectedGenres.value + genre
                    }
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Consejo: combina géneros y rating; podrás limpiar en cualquier momento.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val year = releaseYearText.toIntOrNull()
                    onApplyFilter(minRating, selectedGenres.value.toList(), year)
                    onDismiss()
                },
                enabled = !yearError,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Aplicar filtros")
            }
        },
        dismissButton = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = { onClearFilters(); onDismiss() }) { Text("Limpiar") }
                TextButton(onClick = onDismiss, enabled = true) { Text("Cancelar") }
            }
        }
    )
}

@Composable
private fun RatingSection(current: Float, onChange: (Float) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Slider(
            value = current,
            onValueChange = onChange,
            valueRange = 0f..10f,
            steps = 19,
            modifier = Modifier.weight(1f)
        )
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            shape = MaterialTheme.shapes.small
        ) {
            Text(
                text = String.format(Locale.getDefault(), "%.1f", current),
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
            )
        }
    }
}

@Composable
private fun GenreHeader(selectedCount: Int, onClear: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Géneros", fontWeight = FontWeight.Bold)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("$selectedCount seleccionados", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.width(8.dp))
            TextButton(onClick = onClear) { Text("Limpiar") }
        }
    }
}

@Composable
private fun GenreSearchAndChips(
    genres: List<String>,
    query: String,
    expanded: Boolean,
    onToggleExpanded: () -> Unit,
    selected: Set<String>,
    onToggleGenre: (String) -> Unit
) {
    val filtered = if (query.isBlank()) genres else genres.filter { it.contains(query, ignoreCase = true) }
    val limit = 8
    val displayed = if (!expanded && filtered.size > limit) filtered.take(limit) else filtered

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        displayed.forEach { genre ->
            FilterChip(
                selected = genre in selected,
                onClick = { onToggleGenre(genre) },
                label = { Text(genre) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = Color.White
                )
            )
        }
    }

    if (filtered.size > limit) {
        TextButton(onClick = onToggleExpanded, modifier = Modifier.fillMaxWidth()) {
            Text(if (expanded) "Ver menos" else "Ver más")
        }
    }
}

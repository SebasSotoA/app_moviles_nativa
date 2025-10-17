package com.app.episodic.ui.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.app.episodic.movie_detail.domain.models.Review
import com.app.episodic.ui.components.CollapsibleText
import com.app.episodic.ui.home.itemSpacing
import kotlin.math.round
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ReviewItem(
    modifier: Modifier = Modifier,
    review: Review
) {
    Column (modifier){
        val nameAnnotatedString = buildAnnotatedString {
            append(review.author)
            append(" • ")
            append(formatDate(review.createdAt))
        }
        val ratingAnnotatedString = buildAnnotatedString {
            // Apply bold style to "6/"
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append(round(review.rating).toString()) // round to nearest int for display
            pop() // End bold styling

            // Apply small font size to "10"
            pushStyle(SpanStyle(fontSize = 10.sp))
            append("10")
            pop() // End small styling
        }
        Text(
            text = nameAnnotatedString,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(itemSpacing))
        CollapsibleText(text = review.content, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(itemSpacing))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.Star, contentDescription = null)
            Text(text = ratingAnnotatedString, style = MaterialTheme.typography.bodySmall)
        }
    }

}

private fun formatDate(dateString: String): String {
    return try {
        // Parse the input date (assuming it's in ISO format like "2023-12-25T10:30:00.000Z")
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        outputFormat.format(date ?: Date())
    } catch (e: Exception) {
        // If parsing fails, try to extract just the date part
        if (dateString.contains("T")) {
            dateString.substring(0, dateString.indexOf("T"))
        } else {
            dateString
        }
    }
}
package com.zekiee.wearmet.ui.components.searchScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zekiee.wearmet.domain.model.Location
import com.zekiee.wearmet.ui.theme.WearMetTheme
import java.util.Locale

@Composable
fun LocationItem(
    location: Location,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val countryName = remember(location.countryCode) {
        runCatching {
            Locale.Builder().setRegion(location.countryCode).build()
                .getDisplayCountry(Locale.getDefault())
        }.getOrDefault("")
    }
    val title = listOfNotNull(location.flag, location.name).joinToString(" ")
    val subtitle = listOfNotNull(location.region, countryName.ifBlank { null })
        .joinToString(", ")

    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (subtitle.isNotBlank()) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Preview
@Composable
fun ShowLocationItem() {
    WearMetTheme {
        LocationItem(location = previewLocation, onClick = {})    }
}

val previewLocation = Location(
    id = 1,
    name = "Algeciras",
    latitude = 36.13,
    longitude = -5.45,
    countryCode = "ES",
    region = "Andalucía",

)
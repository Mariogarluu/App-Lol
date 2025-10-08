package com.example.app_lol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.app_lol.ui.theme.AppLolTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppLolTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ChampionListScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ChampionListScreen(
    modifier: Modifier = Modifier,
    championViewModel: ChampionViewModel = viewModel()
) {
    val champions by championViewModel.champions.collectAsState()

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(
            items = champions,
            key = { _, champion -> champion.id }
        ) { index, champion ->
            ChampionCard(
                champion = champion,
                imageOnLeft = index % 2 == 0
            )
        }
    }
}

@Composable
fun ChampionCard(
    champion: Champion,
    imageOnLeft: Boolean,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .animateContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (imageOnLeft) {
                ChampionImage(champion = champion)
                ChampionText(champion = champion, expanded = expanded, modifier = Modifier.weight(1f))
            } else {
                ChampionText(champion = champion, expanded = expanded, modifier = Modifier.weight(1f))
                ChampionImage(champion = champion)
            }
        }
    }
}

@Composable
fun ChampionImage(champion: Champion, modifier: Modifier = Modifier) {
    AsyncImage(
        model = champion.image,
        contentDescription = "Icon of " + stringResource(champion.name),
        modifier = modifier
            .size(80.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ChampionText(champion: Champion, expanded: Boolean, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = stringResource(id = champion.name),
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = stringResource(id = champion.title),
            style = MaterialTheme.typography.titleMedium,
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = stringResource(id = champion.description),
            style = MaterialTheme.typography.bodyMedium,
            maxLines = if (expanded) Int.MAX_VALUE else 2
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ChampionListScreenPreview() {
    AppLolTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            ChampionCard(
                champion = Champion(1, R.string.ziggs_name, R.string.ziggs_title, R.string.ziggs_description, R.drawable.ziggs),
                imageOnLeft = true
            )
            ChampionCard(
                champion = Champion(2, R.string.vi_name, R.string.vi_title, R.string.vi_description, R.drawable.vi),
                imageOnLeft = false
            )
        }
    }
}

package com.example.app_lol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.example.app_lol.ui.theme.AppLolTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppLolTheme {
                val navController = rememberNavController()

                Scaffold { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "champion_list", // La pantalla inicial
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("champion_list") {
                            ChampionListScreen(navController = navController)
                        }
                        composable(
                            route = "champion_detail/{championId}",
                            arguments = listOf(navArgument("championId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val championId = backStackEntry.arguments?.getInt("championId")
                            if (championId != null) {
                                ChampionDetailScreen(championId = championId, navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ChampionListScreen(
    navController: NavController,
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
                imageOnLeft = index % 2 == 0,
                onClick = {
                    navController.navigate("champion_detail/${champion.id}")
                }
            )
        }
    }
}

@Composable
fun ChampionCard(
    champion: Champion,
    imageOnLeft: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (imageOnLeft) {
                ChampionImage(champion = champion)
                ChampionText(champion = champion, modifier = Modifier.weight(1f))
            } else {
                ChampionText(champion = champion, modifier = Modifier.weight(1f))
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
        modifier = modifier.size(80.dp).clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ChampionText(champion: Champion, modifier: Modifier = Modifier) {
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
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChampionDetailScreen(
    championId: Int,
    navController: NavController,
    championViewModel: ChampionViewModel = viewModel()
) {
    val champion = championViewModel.getChampionById(championId)

    if (champion == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Campeón no encontrado")
            Button(onClick = { navController.popBackStack() }) {
                Text("Volver")
            }
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = champion.name)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = champion.image,
                contentDescription = "Splash art of " + stringResource(champion.name),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 9f)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(id = champion.title),
                style = MaterialTheme.typography.headlineSmall,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(id = champion.description),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChampionCardPreview() {
    AppLolTheme {
        ChampionCard(
            champion = Champion(1, R.string.ziggs_name, R.string.ziggs_title, R.string.ziggs_description, R.drawable.ziggs),
            imageOnLeft = true,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChampionDetailScreenPreview() {
    AppLolTheme {
        val navController = rememberNavController()
        ChampionDetailScreen(championId = 1, navController = navController)
    }
}

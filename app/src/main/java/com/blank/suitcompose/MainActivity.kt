package com.blank.suitcompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.blank.suitcompose.ui.theme.SuitComposeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuitComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting()
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun Toolbar() {
    val context = LocalContext.current
    TopAppBar(
        title = {
            Text(text = "Test", maxLines = 2)
        },
        navigationIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = ""
            )
        },
        actions = {
            IconButton(onClick = {
                val intent = Intent(context, PlayInGameActivity::class.java)
                context.startActivity(intent)
            }) {
                Icon(Icons.Filled.Favorite, contentDescription = null)
            }
        }
    )
}

@ExperimentalComposeUiApi
@Composable
fun Greeting() {
    Scaffold(
        topBar = {
            Toolbar()
        }
    ) {
        BodyContent(
            Modifier
                .padding(it)
                .padding(8.dp)
        )
    }
}

@Composable
fun TabButton(constraintsScope: CoroutineScope, state: LazyListState) {
    Row {
        Button(onClick = {
            constraintsScope.launch {
                state.animateScrollToItem(0)
            }
        }) {
            Text(text = "Top")
        }
        Button(onClick = {
            constraintsScope.launch {
                state.animateScrollToItem(99)
            }
        }) {
            Text(text = "End")
        }
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    MyOwnColum(modifier) {
        Text(text = "Hi there!")
        Text(text = "Thanks for goin through the layouts codelab")
        TabButton(coroutineScope, scrollState)
        List(scrollState)
    }
}

@Composable
fun ItemView(index: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = rememberImagePainter(data = "https://developer.android.com/images/brand/Android_Robot.png"),
            contentDescription = "",
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "Item $index", style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun List(state: LazyListState) {
    LazyColumn(state = state) {
        items(100) { item ->
            ItemView(index = item)
        }
    }
}

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SuitComposeTheme {
        Greeting()
    }
}
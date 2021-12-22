package com.blank.suitcompose

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blank.suitcompose.ui.theme.Blue
import com.blank.suitcompose.ui.theme.SuitComposeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayInGameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuitComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Content()
                }
            }
        }
    }
}

@Composable
private fun Content(controller: Controller = Controller()) {
    val context = LocalContext.current
    Scaffold(modifier = Modifier.padding(16.dp)) {
        val imageSuits = mutableListOf<ImageSuit>()
            .apply {
                add(ImageSuit(R.drawable.batu, "batu"))
                add(ImageSuit(R.drawable.gunting, "gunting"))
                add(ImageSuit(R.drawable.kertas, "kertas"))
                add(ImageSuit(R.drawable.close, "close"))
                add(ImageSuit(R.drawable.refresh, "refresh"))
            }

        val result by controller.result.observeAsState("VS")
        var indexStart by remember { mutableStateOf(-1) }
        var indexEnd by remember { mutableStateOf(-1) }
        val coroutineScope = rememberCoroutineScope()

        Column {
            TopSection(imageSuits[3])
            PlayerNameView(player1 = "Pemain 1", player2 = "COM")
            Row {
                ImageView(
                    list = imageSuits,
                    modifier = Modifier.padding(top = 30.dp),
                    indexStart
                ) {
                    indexStart = it
                    coroutineScope.launch {
                        repeat(imageSuits.size) {
                            indexEnd = (0..2).random()
                            delay(100)
                        }
                        controller.checkPemenang(
                            imageSuits[indexStart].des,
                            imageSuits[indexEnd].des
                        )
                    }
                }

                TextResult(result = result)

                ImageView(
                    list = imageSuits,
                    modifier = Modifier.padding(top = 30.dp),
                    indexEnd
                )
            }
            BottomSection(imageSuit = imageSuits[4]) {
                indexStart = -1
                indexEnd = -1
                controller.setResult("VS")
            }
        }
    }
}

@Composable
fun ColumnScope.BottomSection(imageSuit: ImageSuit, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = imageSuit.image),
            contentDescription = imageSuit.des,
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.BottomEnd)
                .clickable { onClick() },
        )
    }
}

@Composable
fun PlayerNameView(player1: String, player2: String) {
    Row {
        Text(
            text = player1,
            modifier = Modifier.wrapContentSize(),
            color = Blue,
            style = MaterialTheme.typography.h6
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Text(
            text = player2,
            modifier = Modifier.wrapContentSize(),
            color = Blue,
            style = MaterialTheme.typography.h6
        )
    }
}

@Composable
fun TopSection(close: ImageSuit) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.splash_screen1),
            contentDescription = "",
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.Center)
        )
        Image(
            painter = painterResource(id = close.image),
            contentDescription = close.des,
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.TopEnd)
                .clickable {
                    (context as Activity).finish()
                }
        )
    }
}

@Composable
fun RowScope.TextResult(result: String) {
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
            .padding(20.dp)
            .rotate(-15f)
            .background(setBgResult(resutl = result))
            .align(Alignment.CenterVertically),
    ) {
        Text(
            text = result,
            textAlign = TextAlign.Center,
            style = setStyleResult(resutl = result),
            color = setColorTextResult(resutl = result),
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

@Composable
fun ImageView(
    list: MutableList<ImageSuit>,
    modifier: Modifier = Modifier,
    clicked: Int,
    onClick: (Int) -> Unit = {}
) {
    LazyColumn {
        itemsIndexed(list) { index, item ->
            if (index < 3) {
                Image(
                    painter = painterResource(id = item.image),
                    contentDescription = item.des,
                    modifier = modifier
                        .size(80.dp)
                        .background(
                            if (clicked == index) Blue else Color.Transparent,
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { onClick(index) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    SuitComposeTheme {
        Content()
    }
}

class ImageSuit(val image: Int, val des: String)
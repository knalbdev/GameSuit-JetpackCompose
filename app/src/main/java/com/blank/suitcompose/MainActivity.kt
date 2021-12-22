package com.blank.suitcompose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.blank.suitcompose.ui.theme.SuitComposeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuitComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ContentMainActivity()
                }
            }
        }
    }
}

const val pvp =
    "https://raw.githubusercontent.com/JV1703/chapter5_asset/master/landing-page1.png"

const val pvc =
    "https://raw.githubusercontent.com/JV1703/chapter5_asset/master/landing-page2.png"

@Composable
fun ContentMainActivity() {
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Scaffold(scaffoldState = scaffoldState) {
        MyContent()
        coroutineScope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                "Selamat Datang Anu",
                "Tutup",
                SnackbarDuration.Indefinite
            )
        }
    }
}

private fun Context.goToGame() {
    val intent = Intent(this, PlayInGameActivity::class.java)
    startActivity(intent)
}

@Composable
private fun MyContent() {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "PILIH LAWANMU",
            modifier = Modifier.padding(top = 60.dp),
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.SemiBold
        )
        ImageTitleView(
            linkImage = pvp, title = "Pemain VS Pemain",
            modifier = Modifier.padding(25.dp)
        ) {
            context.goToGame()
        }
        ImageTitleView(
            linkImage = pvc, title = "Pemain VS CPU",
            modifier = Modifier.padding(16.dp)
        ) {
            context.goToGame()
        }
    }
}

@Composable
fun ImageTitleView(
    linkImage: String,
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier.clickable {
            onClick()
        }, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberImagePainter(data = linkImage),
            contentDescription = "",
            modifier = Modifier.size(200.dp)
        )
        Text(text = title, style = MaterialTheme.typography.h5)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SuitComposeTheme {
        ContentMainActivity()
    }
}
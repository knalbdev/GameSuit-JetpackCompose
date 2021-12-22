package com.blank.suitcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.blank.suitcompose.ui.theme.SuitComposeTheme

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

@Composable
fun ContentMainActivity() {
    Scaffold {
        MyContent()
    }
}

@Composable
private fun MyContent() {
    Column {
        Text(text = "PILIH LAWANMU")

    }
}

@Composable
fun ImageTitleView(idImg: Int, title: String, onClick: () -> Unit) {
    Column(Modifier.clickable {
        onClick()
    }) {
        Text(text = title, style = MaterialTheme.typography.h5)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SuitComposeTheme {
        MyContent()
    }
}
package com.example.movieapplicationv2.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

/**
 * Created by Alo-BambangHariantoSianturi on 05/12/23.
 */
@Composable
fun DetailScreen2(movieTitle: String, modifier: Modifier = Modifier) {


    Box(modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Text(text = movieTitle,
            textAlign = TextAlign.Center, fontSize = 50.sp)
    }

//    Box(modifier = modifier, contentAlignment = Alignment.Center) {
//        Text(
//            text = movieTitle,
//            textAlign = TextAlign.Center,
//            fontSize = 50.sp
//        )
//    }
//    Text(text = "Example",modifier = Modifier.width(100.dp).height(100.dp).background(color = Color.Cyan).wrapContentHeight(align = Alignment.CenterVertically),color = Color.Black,fontSize = 42.sp,textAlign = TextAlign.Right)


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailScreen2Preview() {
    DetailScreen2(movieTitle = "")
}
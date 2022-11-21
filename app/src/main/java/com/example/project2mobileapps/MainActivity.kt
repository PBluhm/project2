package com.example.project2mobileapps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.project2mobileapps.ui.theme.Project2MobileAppsTheme
import com.example.project2mobileapps.ui.theme.greenColor
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Project2MobileAppsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MyMainApp() {
    val pagerState = rememberPagerState(pageCount = 3)

    Column(modifier = Modifier.background(Color.White)) {
        TopAppBar(backgroundColor = greenColor) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "All My Todos",
                    modifier = Modifier
                        .padding(10.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun MyTabs(pagerState: PagerState) {

    val tabList = listOf(
        "Today" to Text("TODAY"),
        "Tomorrow" to Text("TOMORROW"),
        "This Week" to Text("THIS WEEK"),
    )

    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = greenColor,
        contentColor = Color.White,

        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 2.dp,
                color = Color.White,
            )
        }
    ) {

        tabList.forEachIndexed { index, _ ->

            Tab(

                text = {
                    Text(
                        tabList[index].first,
                        color = if (pagerState.currentPage == index) Color.White else Color.LightGray
                    )
                },

                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }

            )

        }

    }

}

@ExperimentalPagerApi
@Composable
fun MyDifferentDays(pagerState: PagerState) {
    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> MyDifferentDaysContent(data = "Test")
            1 -> MyDifferentDaysContent(data = "Test2")
            2 -> MyDifferentDaysContent(data = "Test3")
        }
    }
}

@Composable
fun MyDifferentDaysContent(data: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Project2MobileAppsTheme {

    }
}
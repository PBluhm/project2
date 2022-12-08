package com.example.project2mobileapps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
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
                    MyMainApp()
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
                    textAlign = TextAlign.Center,
                    color = Color.White,

                )
            }
        }
        MyTabs(pagerState = pagerState)
        MyDifferentDays(pagerState = pagerState)
    }
}

@ExperimentalPagerApi
@Composable
fun MyTabs(pagerState: PagerState) {

    val tabList = listOf(
        "Today" to Icons.Default.List,
        "Tomorrow" to Icons.Default.Face,
        "This Week" to Icons.Default.Person,
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
                icon = {
                    Icon(imageVector = tabList[index].second, contentDescription = null)
                },

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
            0 -> MyDifferentDaysContent(day = "Today")
            1 -> MyDifferentDaysContent(day = "Tomorrow")
            2 -> MyDifferentDaysContent(day = "This Week")
        }
    }
}

@Composable
fun MyDifferentDaysContent(day: String) {

    val myTextBox = remember {
        mutableStateOf(TextFieldValue())
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                value = myTextBox.value,
                onValueChange = {myTextBox.value = it},
            )

            Spacer(Modifier.width(10.dp))

            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = greenColor,
                    contentColor = Color.White)
            ) {
                Text(
                    text = "ADD",
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Project2MobileAppsTheme {

    }
}
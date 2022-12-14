package com.example.project2mobileapps

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                    MyMainApp(LocalContext.current)
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MyMainApp(context: Context) {
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
        MyDifferentDays(pagerState = pagerState, context)
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
fun MyDifferentDays(pagerState: PagerState, context: Context) {
    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> MyDifferentDaysContent(day = "Today", context)
            1 -> MyDifferentDaysContent(day = "Tomorrow", context)
            2 -> MyDifferentDaysContent(day = "This Week", context)
        }
    }
}

@Composable
fun MyDifferentDaysContent(day: String, context: Context) {

    val myTextBox = remember {
        mutableStateOf(TextFieldValue())
    }

    val dbHandler: DBHandler = DBHandler(context);
    val todoList: List<UserModel> = dbHandler.readItem(day)


    Column(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                value = myTextBox.value,
                onValueChange = { myTextBox.value = it },
            )

            Spacer(Modifier.width(10.dp))

            Button(
                onClick = {
                    dbHandler.addNewTodo(myTextBox.value.text, day)
                    myTextBox.value = TextFieldValue()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = greenColor,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "ADD",
                )
            }

        }

        Spacer(modifier = Modifier.height(5.dp))

        LazyColumn() {
            items(todoList.size) { index ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp, vertical = 8.dp)
                        .border(BorderStroke(1.dp, Color.Black))
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = {
                                    dbHandler.removeItem(todoList[index].Todo)
                                    myTextBox.value = TextFieldValue("${myTextBox.value.text} ")
                                }
                            )
                        }

                ) {
                    Text(
                        todoList[index].Todo,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                }
            }
        }
    }
}

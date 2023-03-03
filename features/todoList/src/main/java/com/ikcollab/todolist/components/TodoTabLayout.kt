package com.ikcollab.todolist.components

import android.annotation.SuppressLint
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun TodoTabLayout(
    tabData: List<String>,
  //  pagerState: PagerState,
    tabPage: Int,
    onPageChange: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    ScrollableTabRow(
        selectedTabIndex = tabPage,
        backgroundColor = Color.Red,
        indicator = { tabPositions ->
            HomeTabIndicator(tabPositions, tabPage, tabData[tabPage])
        },
        edgePadding = 12.dp
    ) {
        tabData.forEachIndexed { index, item ->
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White)
                    .clickable {
                        //scope.launch {
                            onPageChange(index)
                          //  pagerState.scrollToPage(index)
                      //  }
                    }) {
                Text(modifier = Modifier.padding(4.dp), text = item, fontSize = 18.sp)
            }
        }
    }
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
private fun HomeTabIndicator(
    tabPositions: List<TabPosition>,
    tabPage: Int,
    tabTitle: String,
) {
    val transition = updateTransition(
        tabPage,
        label = "Tab indicator"
    )
    val indicatorLeft by transition.animateDp(
        transitionSpec = {
            spring(stiffness = Spring.StiffnessVeryLow)
        },
        label = "Indicator left"
    ) { page ->
        tabPositions[page].left
    }
    val indicatorRight by transition.animateDp(
        transitionSpec = {
            spring(stiffness = Spring.StiffnessVeryLow,)
        },
        label = "Indicator right"

    ) { page ->
        tabPositions[page].right
    }
    val color by transition.animateColor(
        label = "Border color"
    ) { page ->
        Color.Yellow
    }
    Box(
        Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = indicatorLeft)
            .width(indicatorRight - indicatorLeft)
            .padding(4.dp)
            .fillMaxSize()
            .clip(
                RoundedCornerShape(4.dp)
            )
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Text(text = tabTitle, fontSize = 18.sp)
    }
}

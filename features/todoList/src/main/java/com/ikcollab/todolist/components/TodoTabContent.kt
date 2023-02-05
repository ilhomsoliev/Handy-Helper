package com.ikcollab.todolist.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState


@OptIn(ExperimentalPagerApi::class)
@Composable
fun TodoTabContent(
    tabData: List<String>,
    pagerState: PagerState,
    onPageChange: (Int) -> Unit
) {
    HorizontalPager(state = pagerState, count = tabData.size) { index ->
        /*LaunchedEffect(key1 = false, block = {
            onPageChange(index)
        })*/
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = tabData[index])
        }
    }
}
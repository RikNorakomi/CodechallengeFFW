package com.vanvelzen.codechallengeffw.android.ui

import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vanvelzen.codechallengeffw.android.dividerColor

@Composable
fun CustomDivider(
    modifier: Modifier = Modifier
) {
    Divider(modifier, MaterialTheme.colors.dividerColor)
}


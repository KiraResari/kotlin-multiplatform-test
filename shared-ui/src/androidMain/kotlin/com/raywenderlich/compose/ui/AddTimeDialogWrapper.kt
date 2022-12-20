package com.raywenderlich.compose.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import com.raywenderlich.compose.ui.onDismissType

@Composable
actual fun AddTimeDialogWrapper(onDismiss: onDismissType, content: @Composable () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss) {
        content()
    }
}
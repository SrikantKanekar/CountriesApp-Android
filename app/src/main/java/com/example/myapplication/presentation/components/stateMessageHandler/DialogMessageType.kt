package com.example.myapplication.presentation.components.stateMessageHandler

import androidx.compose.runtime.Composable
import com.example.myapplication.presentation.components.dialog.MyErrorDialog
import com.example.myapplication.presentation.components.dialog.MyInfoDialog
import com.example.myapplication.presentation.components.dialog.MySuccessDialog
import com.example.myapplication.utils.MessageType.*
import com.example.myapplication.utils.Response

@Composable
fun DialogMessageType(
    response: Response,
    removeStateMessage: () -> Unit
) {
    response.message?.let { message ->
        when(response.messageType){

            Success -> {
                MySuccessDialog(
                    message = message,
                    removeStateMessage = removeStateMessage
                )
            }

            Error -> {
                MyErrorDialog(
                    message = message,
                    removeStateMessage = removeStateMessage
                )
            }

            Info -> {
                MyInfoDialog(
                    message = message,
                    removeStateMessage = removeStateMessage
                )
            }
        }
    }
}
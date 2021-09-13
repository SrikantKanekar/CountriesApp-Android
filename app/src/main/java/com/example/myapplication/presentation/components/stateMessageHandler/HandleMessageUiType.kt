package com.example.myapplication.presentation.components.stateMessageHandler

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.utils.StateMessage
import com.example.myapplication.utils.UiType.*

@Composable
fun HandleMessageUiType(
    stateMessage: StateMessage?,
    scaffoldState: ScaffoldState,
    removeStateMessage: () -> Unit = {}
) {
    if (stateMessage?.response?.message != null) {
        stateMessage.response.let { response ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {

                when (response.uiType) {

                    Dialog -> {
                        DialogMessageType(
                            response = response,
                            removeStateMessage = removeStateMessage
                        )
                    }

                    SnackBar -> {
                        SnackbarMessageType(
                            response = response,
                            scaffoldState = scaffoldState,
                            removeStateMessage = removeStateMessage
                        )
                    }

                    AreYouSureDialog -> {
                        removeStateMessage()
                    }

                    None -> {
                        removeStateMessage()
                    }
                }
            }
        }
    } else {
        removeStateMessage()
    }
}
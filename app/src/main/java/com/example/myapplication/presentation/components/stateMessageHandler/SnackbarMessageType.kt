package com.example.myapplication.presentation.components.stateMessageHandler

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myapplication.presentation.components.snackbar.ErrorSnackbar
import com.example.myapplication.presentation.components.snackbar.InfoSnackbar
import com.example.myapplication.presentation.components.snackbar.SuccessSnackbar
import com.example.myapplication.presentation.theme.snackbarController
import com.example.myapplication.utils.MessageType.*
import com.example.myapplication.utils.Response
import kotlinx.coroutines.launch

@Composable
fun SnackbarMessageType(
    response: Response,
    scaffoldState: ScaffoldState,
    removeStateMessage: () -> Unit
) {
    response.message?.let {

        Box(modifier = Modifier.fillMaxSize()) {

            snackbarController.getScope().launch {
                snackbarController.showSnackbar(
                    scaffoldState = scaffoldState,
                    message = response.message,
                    actionLabel = "Ok",
                    removeStateMessage = removeStateMessage
                )
            }

            when(response.messageType){

                Success -> {
                    SuccessSnackbar(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        snackbarHostState = scaffoldState.snackbarHostState,
                        onDismiss = {
                            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                            removeStateMessage()
                        }
                    )
                }

                Error -> {
                    ErrorSnackbar(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        snackbarHostState = scaffoldState.snackbarHostState,
                        onDismiss = {
                            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                            removeStateMessage()
                        }
                    )
                }

                Info -> {
                    InfoSnackbar(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        snackbarHostState = scaffoldState.snackbarHostState,
                        onDismiss = {
                            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                            removeStateMessage()
                        }
                    )
                }
            }
        }
    }
}
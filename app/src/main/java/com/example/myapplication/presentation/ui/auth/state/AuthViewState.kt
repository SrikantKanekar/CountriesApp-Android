package com.example.myapplication.presentation.ui.auth.state

import com.example.myapplication.database.entity.Token

data class AuthViewState(
    var token: Token? = null,
    var previousUserCheck: Boolean? = null,
)
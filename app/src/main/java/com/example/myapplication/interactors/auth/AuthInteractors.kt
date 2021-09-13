package com.example.myapplication.interactors.auth

class AuthInteractors(
    val attemptLogin: AttemptLogin,
    val attemptRegistration: AttemptRegistration,
    val checkPreviousUser: CheckPreviousUser
)
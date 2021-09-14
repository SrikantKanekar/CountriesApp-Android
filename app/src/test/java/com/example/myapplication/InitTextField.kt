package com.example.myapplication

import com.example.myapplication.presentation.components.textField.TextFieldState

/**
 * refer presentation -> components -> textField -> TextFieldState class
 * [TextFieldState] class holds the textField value and corresponding error value(if any)
 * In order for the errors to be showed on the UI, the TextField must be focused at least once
 *
 * This function initializes the textField state so that errors can be displayed
 */
fun initTextField(state: TextFieldState) {
    state.onFocusChange(true)
    state.enableShowErrors()
}
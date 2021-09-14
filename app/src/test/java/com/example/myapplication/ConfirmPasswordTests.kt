package com.example.myapplication

import com.example.myapplication.presentation.components.textField.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ConfirmPasswordTests {

    private val password = "valid password"
    private val passwordState = PasswordState(password)

    @Test
    fun `should return error if password is empty`() {
        // Arrange
        val state = ConfirmPasswordState(passwordState)
        initTextField(state)

        // Act
        val error = state.getError()

        // Assert
        assertEquals(PASSWORD_EMPTY_ERROR, error)
    }

    @Test
    fun `should return error if password is less than 4`() {
        // Arrange
        val state = ConfirmPasswordState(passwordState)
        initTextField(state)

        // Act
        state.text = "A"
        val error = state.getError()

        // Assert
        assertEquals(PASSWORD_SMALL_ERROR, error)
    }

    @Test
    fun `should return error if password's don't match`() {
        // Arrange
        val state = ConfirmPasswordState(passwordState)
        initTextField(state)

        // Act
        state.text = "different password"
        val error = state.getError()

        // Assert
        assertEquals(PASSWORD_DO_NOT_MATCH_ERROR, error)
    }

    @Test
    fun `should return null if password is valid`() {
        // Arrange
        val state = ConfirmPasswordState(passwordState)
        initTextField(state)

        // Act
        state.text = password
        val error = state.getError()

        // Assert
        assertNull(error)
    }
}
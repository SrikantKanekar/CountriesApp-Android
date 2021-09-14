package com.example.myapplication

import com.example.myapplication.presentation.components.textField.*
import org.junit.Assert.*
import org.junit.Test

class PasswordTests {

    @Test
    fun `should return error if password is empty`() {
        // Arrange
        val state = PasswordState()
        initTextField(state)

        // Act
        val error = state.getError()

        // Assert
        assertEquals(PASSWORD_EMPTY_ERROR, error)
    }

    @Test
    fun `should return error if password is less than 4`() {
        // Arrange
        val state = PasswordState()
        initTextField(state)

        // Act
        state.text = "A"
        val error = state.getError()

        // Assert
        assertEquals(PASSWORD_SMALL_ERROR, error)
    }

    @Test
    fun `should return null if password is valid`() {
        // Arrange
        val state = PasswordState()
        initTextField(state)

        // Act
        state.text = "valid password"
        val error = state.getError()

        // Assert
        assertNull(error)
    }
}
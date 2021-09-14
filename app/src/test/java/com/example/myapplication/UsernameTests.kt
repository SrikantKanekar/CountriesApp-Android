package com.example.myapplication

import com.example.myapplication.presentation.components.textField.USERNAME_EMPTY_ERROR
import com.example.myapplication.presentation.components.textField.USERNAME_LARGE_ERROR
import com.example.myapplication.presentation.components.textField.UsernameState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class UsernameTests {

    @Test
    fun `should return error if username is empty`() {
        // Arrange
        val state = UsernameState()
        initTextField(state)

        // Act
        val error = state.getError()

        // Assert
        assertEquals(USERNAME_EMPTY_ERROR, error)
    }

    @Test
    fun `should return error if username is greater then 20`() {
        // Arrange
        val state = UsernameState()
        initTextField(state)

        // Act
        state.text = "A long username ..............."
        val error = state.getError()

        // Assert
        assertEquals(USERNAME_LARGE_ERROR, error)
    }

    @Test
    fun `should return null if username is valid`() {
        // Arrange
        val state = UsernameState()
        initTextField(state)

        // Act
        state.text = "username"
        val error = state.getError()

        // Assert
        assertNull(error)
    }
}
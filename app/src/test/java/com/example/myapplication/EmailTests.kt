package com.example.myapplication

import com.example.myapplication.presentation.components.textField.*
import org.junit.Assert.*
import org.junit.Test

class EmailTests {

    @Test
    fun `should return error if email is empty`() {
        // Arrange
        val state = EmailState()
        initTextField(state)

        // Act
        val error = state.getError()

        // Assert
        assertEquals(EMAIL_EMPTY_ERROR, error)
    }

    @Test
    fun `should return error if email is invalid`() {
        // Arrange
        val state = EmailState()
        initTextField(state)

        // Act
        state.text = "invalid email"
        val error = state.getError()

        // Assert
        assertTrue(error!!.contains(EMAIL_INVALID_ERROR))
    }

    @Test
    fun `should return null if email is valid`() {
        // Arrange
        val state = EmailState()
        initTextField(state)

        // Act
        state.text = "email@test.com"
        val error = state.getError()

        // Assert
        assertNull(error)
    }
}
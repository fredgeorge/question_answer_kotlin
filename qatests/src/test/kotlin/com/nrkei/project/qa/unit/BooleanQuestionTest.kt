package com.nrkei.project.qa.unit

import org.junit.jupiter.api.Test
import com.nrkei.project.qa.dsl.dialog
import com.nrkei.project.qa.model.BooleanQuestion
import com.nrkei.project.qa.model.DialogStatus.*
import org.junit.jupiter.api.Assertions.assertEquals

// Ensures that a true/false question works
class BooleanQuestionTest {
    private val TRUE_FALSE_Q = BooleanQuestion()

    @Test
    fun `test true`() {
        dialog {
            first ask TRUE_FALSE_Q answers {
                on answer true conclude SUCCEEDED
                on answer false conclude FAILED
            }
        }.also { dialog ->
            assertEquals(NOT_STARTED, dialog.status())
        }
    }
}

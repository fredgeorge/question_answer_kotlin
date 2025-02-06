/*
 * Copyright (c) 2025 by Fred George
 * @author: Fred George  fredgeorge@acm.org
 * Licensed under the MIT License; see LICENSE file in root.
 */

package com.nrkei.project.qa.unit

import com.nrkei.project.qa.dsl.dialog
import com.nrkei.project.qa.model.BooleanQuestion
import com.nrkei.project.qa.model.Choices
import com.nrkei.project.qa.model.DialogStatus.Companion.NOT_STARTED
import com.nrkei.project.qa.model.DialogStatus.DialogConclusion.Companion.FAILED
import com.nrkei.project.qa.model.DialogStatus.DialogConclusion.Companion.SUCCEEDED
import com.nrkei.project.qa.model.QuestionIdentifier
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

// Ensures that a true/false question works
class SimpleDialogTest {
    private val trueFalseQuestion1Id = QuestionIdentifier("trueFalseQuestion1")
    private val trueFalseQuestion2Id = QuestionIdentifier("trueFalseQuestion2")
    private val trueFalseQuestion1 = { choices: Choices -> BooleanQuestion(trueFalseQuestion1Id, choices) }
    private val trueFalseQuestion2 = { choices: Choices -> BooleanQuestion(trueFalseQuestion2Id, choices) }

    @Test
    fun `single question`() {
        dialog {
            first ask trueFalseQuestion1 answers {
                on answer true conclude SUCCEEDED
                on answer false conclude FAILED
            }
        }.also { dialog ->
            assertEquals(NOT_STARTED, dialog.status())
            assertEquals(trueFalseQuestion1Id, dialog.question(trueFalseQuestion1Id).id)
            assertThrows<IllegalArgumentException> { dialog.question(trueFalseQuestion2Id) }
            assertEquals(trueFalseQuestion1Id, dialog.nextQuestion().id)
        }
    }

    @Test
    fun `empty dialog`() {
        dialog {}.also { dialog ->
            assertEquals(NOT_STARTED, dialog.status())
            assertThrows<IllegalStateException> { dialog.nextQuestion() }
        }
    }

    @Test
    fun `two Boolean questions`() {
        dialog {
            first ask trueFalseQuestion1 answers {
                on answer true conclude SUCCEEDED
                on answer false conclude FAILED
            }
            then ask trueFalseQuestion2 answers {
                on answer true conclude SUCCEEDED
                on answer false conclude FAILED
            }
        }.also { dialog ->
            assertEquals(NOT_STARTED, dialog.status())
            assertEquals(trueFalseQuestion1Id, dialog.nextQuestion().id)
        }
    }

    @Test
    fun `first keyword required for first question`() {
        assertThrows<IllegalArgumentException> {
            dialog {
                then ask trueFalseQuestion1 answers {
                    on answer true conclude SUCCEEDED
                    on answer false conclude FAILED
                }
            }
        }
    }

    @Test
    fun `first keyword only valid for first question`() {
        assertThrows<IllegalArgumentException> {
            dialog {
                first ask trueFalseQuestion1 answers {
                    on answer true conclude SUCCEEDED
                    on answer false conclude FAILED
                }
                first ask trueFalseQuestion2 answers {
                    on answer true conclude SUCCEEDED
                    on answer false conclude FAILED
                }
            }
        }
    }

    @Test
    fun `two questions cannot have the same id`() {
        dialog {
            first ask trueFalseQuestion1 answers {
                on answer true conclude SUCCEEDED
                on answer false conclude FAILED
            }
            then ask trueFalseQuestion1 answers {
                on answer true conclude SUCCEEDED
                on answer false conclude FAILED
            }
        }.also { dialog ->
            assertThrows<IllegalStateException> { dialog.question(QuestionIdentifier("trueFalseQuestion1")) }
        }
    }
}

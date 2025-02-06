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
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

// Ensures that a true/false question works
class SimpleDialogTest {
    private val trueFalseQuestion1 = { choices: Choices -> BooleanQuestion(choices) }
    private val trueFalseQuestion2 = { choices: Choices -> BooleanQuestion(choices) }

    @Test
    fun `single Boolean question`() {
        dialog {
            first ask trueFalseQuestion1 answers {
                on answer true conclude SUCCEEDED
                on answer false conclude FAILED
            }
        }.also { dialog ->
            assertEquals(NOT_STARTED, dialog.status())
        }
    }

    @Test
    fun `empty dialog`() {
        dialog {}.also { dialog ->
            assertEquals(NOT_STARTED, dialog.status())
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
}

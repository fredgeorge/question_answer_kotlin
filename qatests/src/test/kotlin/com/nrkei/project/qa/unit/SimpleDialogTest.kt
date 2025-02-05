/*
 * Copyright (c) 2025 by Fred George
 * @author: Fred George  fredgeorge@acm.org
 * Licensed under the MIT License; see LICENSE file in root.
 */

package com.nrkei.project.qa.unit

import org.junit.jupiter.api.Test
import com.nrkei.project.qa.dsl.dialog
import com.nrkei.project.qa.model.BooleanQuestion
import com.nrkei.project.qa.model.Choices
import com.nrkei.project.qa.model.DialogConclusion.Companion.FAILED
import com.nrkei.project.qa.model.DialogConclusion.Companion.NOT_STARTED
import com.nrkei.project.qa.model.DialogConclusion.Companion.SUCCEEDED
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows

// Ensures that a true/false question works
class SimpleDialogTest {
    private val trueFalseQuestion = { choices:Choices -> BooleanQuestion(choices) }

    @Test
    fun `single Boolean question`() {
        dialog {
            first ask trueFalseQuestion answers {
                on answer true conclude SUCCEEDED
                on answer false conclude FAILED
            }
        }.also { dialog ->
            assertEquals(NOT_STARTED, dialog.conclusion())
        }
    }

    @Test
    fun `empty dialog`() {
        dialog {}.also { dialog ->
            assertEquals(NOT_STARTED, dialog.conclusion())
        }
    }

    @Test
    fun `two Boolean questions`() {
        dialog {
            first ask trueFalseQuestion answers {
                on answer true conclude SUCCEEDED
                on answer false conclude FAILED
            }
            then ask trueFalseQuestion answers {
                on answer true conclude SUCCEEDED
                on answer false conclude FAILED
            }
        }.also { dialog ->
            assertEquals(NOT_STARTED, dialog.conclusion())
        }
    }

    @Test
    fun `first keyword required for first question`() {
        assertThrows<IllegalArgumentException> {
            dialog {
                then ask trueFalseQuestion answers {
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
                first ask trueFalseQuestion answers {
                    on answer true conclude SUCCEEDED
                    on answer false conclude FAILED
                }
                first ask trueFalseQuestion answers {
                    on answer true conclude SUCCEEDED
                    on answer false conclude FAILED
                }
            }
        }
    }
}

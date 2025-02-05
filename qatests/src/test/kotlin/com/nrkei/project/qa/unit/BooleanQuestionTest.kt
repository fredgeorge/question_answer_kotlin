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
import com.nrkei.project.qa.model.DialogConclusion
import org.junit.jupiter.api.assertThrows

class BooleanQuestionTest {
    private val trueFalseQuestion = { choices: Choices -> BooleanQuestion(choices) }

    @Test
    fun `Must have two choices`() {
        assertThrows<IllegalArgumentException> {
            dialog {
                first ask trueFalseQuestion answers { }
            }
        }
        assertThrows<IllegalArgumentException> {
            dialog {
                first ask trueFalseQuestion answers {
                    on answer true conclude DialogConclusion.SUCCEEDED
                }
            }
        }
    }

    @Test
    fun `Cannot specify the same answer twice`() {
        assertThrows<IllegalArgumentException> {
            dialog {
                first ask trueFalseQuestion answers {
                    on answer true conclude DialogConclusion.SUCCEEDED
                    on answer false conclude DialogConclusion.FAILED
                    on answer true conclude DialogConclusion.SUCCEEDED
                }
            }
        }
    }

    @Test
    fun `Choices must be true or false`() {
        assertThrows<IllegalArgumentException> {
            dialog {
                first ask trueFalseQuestion answers {
                    on answer "whoops" conclude DialogConclusion.SUCCEEDED
                }
            }
        }
    }

    @Test
    fun `Choices cannot both be true or both be false`() {
        assertThrows<IllegalArgumentException> {
            dialog {
                first ask trueFalseQuestion answers {
                    on answer true conclude DialogConclusion.SUCCEEDED
                    on answer true conclude DialogConclusion.SUCCEEDED
                }
            }
        }
        assertThrows<IllegalArgumentException> {
            dialog {
                first ask trueFalseQuestion answers {
                    on answer false conclude DialogConclusion.FAILED
                    on answer false conclude DialogConclusion.FAILED
                }
            }
        }
    }
}
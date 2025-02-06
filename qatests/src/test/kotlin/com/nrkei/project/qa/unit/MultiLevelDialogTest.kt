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
import com.nrkei.project.qa.model.DialogStatus.Companion.STARTED
import com.nrkei.project.qa.model.DialogStatus.DialogConclusion.Companion.FAILED
import com.nrkei.project.qa.model.DialogStatus.DialogConclusion.Companion.SUCCEEDED
import com.nrkei.project.qa.model.QuestionIdentifier
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MultiLevelDialogTest {
    private val trueFalseId1 = QuestionIdentifier("trueFalse1")
    private val trueLegId1 = QuestionIdentifier("trueLeg1")
    private val falseLegId1 = QuestionIdentifier("falseLeg1")
    private val trueFalse1 = { choices: Choices -> BooleanQuestion(trueFalseId1, choices) }
    private val trueLeg1 = { choices: Choices -> BooleanQuestion(trueLegId1, choices) }
    private val falseLeg1 = { choices: Choices -> BooleanQuestion(falseLegId1, choices) }

    @Test
    fun `single question with children`() {
        dialog {
            first ask trueFalse1 answers {
                on answer true ask trueLeg1 answers {
                    on answer true conclude SUCCEEDED
                    on answer false conclude FAILED
                }
                on answer false ask falseLeg1 answers {
                    on answer true conclude SUCCEEDED
                    on answer false conclude FAILED
                }
            }
        }.also { dialog ->
            assertEquals(NOT_STARTED, dialog.status())
            assertEquals(trueFalseId1, dialog.nextQuestion().id)

            dialog.nextQuestion().be(true)
            assertEquals(STARTED, dialog.status())
            assertEquals(trueLegId1, dialog.nextQuestion().id)

            dialog.nextQuestion().be(false) // Question on true leg of first question
            assertEquals(FAILED, dialog.status())
            dialog.question(trueLegId1).be(true) // Change the answer on the true leg
            assertEquals(SUCCEEDED, dialog.status())
            assertThrows<IllegalStateException> { dialog.nextQuestion() }

            dialog.question(trueFalseId1).be(false) // Change the answer to the root question
            assertEquals(STARTED, dialog.status())
            assertEquals(falseLegId1, dialog.nextQuestion().id)

            dialog.nextQuestion().be(true) // Question on false leg of first question
            assertEquals(SUCCEEDED, dialog.status())
            dialog.question(falseLegId1).be(false) // Change the answer on the true leg
            assertEquals(FAILED, dialog.status())
            assertThrows<IllegalStateException> { dialog.nextQuestion() }

            // Change the root question back to true; previous answers retained
            dialog.question(trueFalseId1).be(true) // Again change the answer to the root question
            assertEquals(SUCCEEDED, dialog.status())
            assertThrows<IllegalStateException> { dialog.nextQuestion() }
        }
    }
}
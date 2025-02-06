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
        }
    }
}
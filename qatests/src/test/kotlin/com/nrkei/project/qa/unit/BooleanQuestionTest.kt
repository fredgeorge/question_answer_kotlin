/*
 * Copyright (c) 2025 by Fred George
 * @author: Fred George  fredgeorge@acm.org
 * Licensed under the MIT License; see LICENSE file in root.
 */

package com.nrkei.project.qa.unit

import com.nrkei.project.qa.dsl.DialogQuestion
import org.junit.jupiter.api.Test
import com.nrkei.project.qa.dsl.dialog
import com.nrkei.project.qa.model.BooleanQuestion
import com.nrkei.project.qa.model.Choices
import com.nrkei.project.qa.model.DialogConclusion.Companion.FAILED
import com.nrkei.project.qa.model.DialogConclusion.Companion.NOT_STARTED
import com.nrkei.project.qa.model.DialogConclusion.Companion.SUCCEEDED
import org.junit.jupiter.api.Assertions.assertEquals

// Ensures that a true/false question works
class BooleanQuestionTest {
    private val TRUE_FALSE_Q: DialogQuestion = { choices:Choices -> BooleanQuestion(choices) }

    @Test
    fun `test true`() {
        dialog {
            first ask TRUE_FALSE_Q answers {
                on answer true conclude SUCCEEDED
                on answer false conclude FAILED
            }
        }.also { dialog ->
            assertEquals(NOT_STARTED, dialog.conclusion())
        }
    }
}

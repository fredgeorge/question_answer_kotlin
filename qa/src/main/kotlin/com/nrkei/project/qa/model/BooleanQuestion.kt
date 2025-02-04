/*
 * Copyright (c) 2025 by Fred George
 * @author: Fred George  fredgeorge@acm.org
 * Licensed under the MIT License; see LICENSE file in root.
 */

package com.nrkei.project.qa.model

import com.nrkei.project.qa.model.DialogConclusion.Companion.NOT_STARTED

class BooleanQuestion(private val choices: Choices): Question {
    init {
        require(choices.keys.all { it in listOf(true, false) }) { "Invalid values for a true/false question"}
    }
    override fun conclusion() = NOT_STARTED
}
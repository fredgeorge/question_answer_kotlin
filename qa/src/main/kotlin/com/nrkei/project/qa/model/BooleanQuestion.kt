/*
 * Copyright (c) 2025 by Fred George
 * @author: Fred George  fredgeorge@acm.org
 * Licensed under the MIT License; see LICENSE file in root.
 */

package com.nrkei.project.qa.model

import com.nrkei.project.qa.model.DialogStatus.Companion.NOT_STARTED


class BooleanQuestion(override val id: QuestionIdentifier, private val choices: Choices): Question {
    init {
        require(choices.size == 2) { "Exactly two choices (true and false) are required for a true/false question"}
        require(choices.keys.all { it in listOf(true, false) }) { "Invalid values for a true/false question"}
    }
    constructor(idLabel: String, choices: Choices) : this(QuestionIdentifier(idLabel), choices)

    override fun status() = NOT_STARTED

    override fun questionOrNull(id: QuestionIdentifier) =
        if (this.id == id) this else choices.values.question(id)

    override fun nextQuestion() = this
}
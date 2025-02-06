/*
 * Copyright (c) 2025 by Fred George
 * @author: Fred George  fredgeorge@acm.org
 * Licensed under the MIT License; see LICENSE file in root.
 */

package com.nrkei.project.qa.dsl

import com.nrkei.project.qa.model.Choice
import com.nrkei.project.qa.model.Choice.Companion.map
import com.nrkei.project.qa.model.Choices
import com.nrkei.project.qa.model.DialogStatus
import com.nrkei.project.qa.model.DialogStatus.Companion.NOT_STARTED
import com.nrkei.project.qa.model.DialogStatus.Companion.STARTED
import com.nrkei.project.qa.model.DialogStatus.DialogConclusion
import com.nrkei.project.qa.model.DialogStatus.DialogConclusion.Companion.FAILED
import com.nrkei.project.qa.model.DialogStatus.DialogConclusion.Companion.SUCCEEDED
import com.nrkei.project.qa.model.Question

// DSL syntax to specify a series of questions
fun dialog(block: Dialog.() -> Unit) =
    Dialog().also { it.block() }

// A series of questions to ascertain a conclusion
class Dialog internal constructor() {
    private val questions = mutableListOf<Question>()

    // Syntax sugar
    val first get() = this.also { require(questions.isEmpty()) { "'then' keyword required for each question after the first in a dialog" } }
    val then get() = this.also { require(questions.isNotEmpty()) { "'first' keyword required for the first question in a dialog" }}

    infix fun ask(question: DialogQuestion) = QuestionBuilder(question)

    fun status() = questions
        .map { it.status() }
        .let { statuses: List<DialogStatus> ->
            when {
                statuses.isEmpty() -> NOT_STARTED
                statuses.all { it == NOT_STARTED } -> NOT_STARTED
                statuses.any { it == STARTED } -> STARTED
                statuses.all { it == SUCCEEDED } -> SUCCEEDED
                else -> FAILED
            }
        }

    inner class QuestionBuilder internal constructor(private val question: DialogQuestion) {

        infix fun answers(block: AnswersBuilder.() -> Unit) =
            AnswersBuilder()
                .also { it.block() }
                .let { questions.add(question(it.choices())) }
    }
}


class AnswersBuilder internal constructor() {
    private val choices = mutableListOf<Choice>()
    private lateinit var answerValue: Any

    // Syntax sugar
    val on = this

    infix fun answer(value: Any) = this.also { answerValue = value }

    infix fun conclude(status: DialogConclusion) {
        choices.add(Choice(answerValue, status))
    }

    internal fun choices(): Map<Any, Question> = choices.map()
}

typealias DialogQuestion = (Choices) -> Question

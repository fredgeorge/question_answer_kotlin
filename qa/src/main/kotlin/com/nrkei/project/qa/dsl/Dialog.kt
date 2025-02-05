/*
 * Copyright (c) 2025 by Fred George
 * @author: Fred George  fredgeorge@acm.org
 * Licensed under the MIT License; see LICENSE file in root.
 */

package com.nrkei.project.qa.dsl

import com.nrkei.project.qa.model.Choice
import com.nrkei.project.qa.model.DialogConclusion
import com.nrkei.project.qa.model.DialogConclusion.Companion.NOT_STARTED
import com.nrkei.project.qa.model.Question
import com.nrkei.project.qa.model.Choice.Companion.map
import com.nrkei.project.qa.model.Choices
import com.nrkei.project.qa.model.DialogConclusion.Companion.FAILED
import com.nrkei.project.qa.model.DialogConclusion.Companion.STARTED
import com.nrkei.project.qa.model.DialogConclusion.Companion.SUCCEEDED

// DSL syntax to specify a series of questions
fun dialog(block: Dialog.() -> Unit) =
    Dialog().also { it.block() }

// A series of questions to ascertain a conclusion
class Dialog internal constructor() {
    // Syntax sugar
    val first = this
    val then = this

    private val questions = mutableListOf<Question>()

    infix fun ask(question: DialogQuestion) = QuestionBuilder(question)

    fun conclusion() = questions
        .map { it.conclusion() }
        .let { conclusions ->
            when {
                conclusions.isEmpty() -> NOT_STARTED
                conclusions.all { it == NOT_STARTED } -> NOT_STARTED
                conclusions.any { it == STARTED } -> STARTED
                conclusions.all { it == SUCCEEDED } -> SUCCEEDED
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

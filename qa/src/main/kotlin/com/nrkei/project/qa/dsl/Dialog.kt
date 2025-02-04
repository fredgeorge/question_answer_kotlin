package com.nrkei.project.qa.dsl

import com.nrkei.project.qa.model.DialogStatus
import com.nrkei.project.qa.model.DialogStatus.NOT_STARTED
import com.nrkei.project.qa.model.Question

// DSL syntax to specify a series of questions
fun dialog(block: Dialog.() -> Unit) =
    Dialog()
        .also { it.block() }
//        .let {it.question()}

// A series of questions to ascertain a result
class Dialog internal constructor() {

    // Syntax sugar
    val first = this
    val then = this

    private val questionBuilders = mutableListOf<QuestionBuilder>()

    infix fun ask(question: Question) =
        QuestionBuilder(question).also {questionBuilders.add(it)}

    fun status() = NOT_STARTED
}

class QuestionBuilder internal constructor(private val question: Question) {

    infix fun answers(block: AnswersBuilder.() -> Unit) =
        AnswersBuilder()
            .also {it.block() }
}

class AnswersBuilder internal constructor() {

    // Syntax sugar
    val on = this

    infix fun answer(value: Any) = this

    infix fun conclude(status: DialogStatus) {
    }
}

/*
 * Copyright (c) 2025 by Fred George
 * @author: Fred George  fredgeorge@acm.org
 * Licensed under the MIT License; see LICENSE file in root.
 */

package com.nrkei.project.qa.model

// Understands a specific nugget of desired information
interface Question {
    val id: QuestionIdentifier
    fun status(): DialogStatus
    fun questionOrNull(id: QuestionIdentifier): Question?
    fun nextQuestion(): Question
}

internal fun Iterable<Question>.question(id: QuestionIdentifier): Question? = this
    .mapNotNull { it.questionOrNull(id) }
    .let {
        when {
            it.isEmpty() -> null
            it.size == 1 -> it.first()
            else -> throw IllegalStateException("Multiple questions found with id $id")
        }
    }

internal fun Iterable<Question>.nextQuestion(): Question? {
    forEach { question ->
        question.nextQuestion().also { next -> if (next !is DialogStatus.DialogConclusion) return next }}
    return null
}



/*
 * Copyright (c) 2025 by Fred George
 * @author: Fred George  fredgeorge@acm.org
 * Licensed under the MIT License; see LICENSE file in root.
 */

package com.nrkei.project.qa.model

// Understands a possible answer to a Question
class Choice internal constructor(value: Any, nextQuestion: Question) {
    private val nextQuestion = Pair(value, nextQuestion)
    companion object {
        internal fun List<Choice>.map() = this.map { it.nextQuestion }.toMap()
    }
}

typealias Choices = Map<Any, Question>

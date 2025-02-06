/*
 * Copyright (c) 2025 by Fred George
 * @author: Fred George  fredgeorge@acm.org
 * Licensed under the MIT License; see LICENSE file in root.
 */

package com.nrkei.project.qa.model

open class DialogStatus private constructor(idLabel: String) : Question {
    override val id = QuestionIdentifier("<$idLabel>")

    companion object {
        val NOT_STARTED = DialogStatus("NOT_STARTED")
        val STARTED = DialogStatus("STARTED")
    }

    override fun questionOrNull(id: QuestionIdentifier) =
        if (this.id == id) this else null

    override fun nextQuestion() = this

    override fun status() = this

    class DialogConclusion private constructor(idLabel: String) : DialogStatus(idLabel) {

        companion object {
            val SUCCEEDED = DialogConclusion("SUCCEEDED")
            val FAILED = DialogConclusion("FAILED")
        }
    }
}

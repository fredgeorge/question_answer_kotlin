/*
 * Copyright (c) 2025 by Fred George
 * @author: Fred George  fredgeorge@acm.org
 * Licensed under the MIT License; see LICENSE file in root.
 */

package com.nrkei.project.qa.model

open class DialogStatus private constructor() : Question {

    companion object {
        val NOT_STARTED = DialogStatus()
        val STARTED = DialogStatus()
    }

    override fun status() = this

    class DialogConclusion private constructor() : DialogStatus() {

        companion object {
            val SUCCEEDED = DialogConclusion()
            val FAILED = DialogConclusion()
        }
    }
}

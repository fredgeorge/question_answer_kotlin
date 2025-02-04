/*
 * Copyright (c) 2025 by Fred George
 * @author: Fred George  fredgeorge@acm.org
 * Licensed under the MIT License; see LICENSE file in root.
 */

package com.nrkei.project.qa.model

class DialogConclusion private constructor() : Question {

    companion object {
        val NOT_STARTED = DialogConclusion()
        val STARTED = DialogConclusion()
        val SUCCEEDED = DialogConclusion()
        val FAILED = DialogConclusion()
    }

    override fun conclusion() = this
}

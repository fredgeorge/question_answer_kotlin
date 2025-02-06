/*
 * Copyright (c) 2025 by Fred George
 * @author: Fred George  fredgeorge@acm.org
 * Licensed under the MIT License; see LICENSE file in root.
 */

package com.nrkei.project.qa.model

// Understands a specific nugget of desired information
interface Question {
    fun status(): DialogStatus
//    fun nextQuestion(): Question
}


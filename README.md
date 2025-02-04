### question_answer_kotlin

Copyright (c) 2025 by Fred George  
author: Fred George  fredgeorge@acm.org  
Licensed under the MIT License; see LICENSE file in root.


## Question / Answer Model in Kotlin

A model for building a series of questions and answers as a dialog
with various users. Based on Answers, the next relevant Question 
will be presented. Capabilities include:

- Various types of Question formats
  - Mutliple choice
  - Single text input
  - True/false
  - Integer value
  - Floating point value
- A DSL (domain specific language) to specify questions and answers
- Ability to change the Answer to a Question and pursue that path
- Ability to change the Answer back and keep the original Answers
in that chain
- Roles associated with various Questions, including
  - Who is allowed to answer
  - Who is allowed to see an answer
- Question/Answer blocks that can be plugged into other
Question/Answer chains
- Tenative Answers which can allow the flow to continue, but
are subject to review by a different Role
- Templates to generate multiple copies of a Question/Answer Block
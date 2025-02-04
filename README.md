### kotlin_template

Copyright (c) 2025 by Fred George  
author: Fred George  fredgeorge@acm.org  
Licensed under the MIT License; see LICENSE file in root.


## Starting template for Kotlin project using Gradle

Kotlin is relatively easy to set up with IntelliJ IDEA. 
Gradle is used for building and testing the project, and is a 
prerequisite. Install if necessary.
The following instructions are for installing the code 
in IntelliJ IDEA by JetBrains. 
Adapt as necessary for your environment.

Note: This implementation was set up to use:

- IntelliJ 2024.3.2.2 (Ultimate Edition)
- Kotlin 2.1.10
- Java SDK 23 (Oracle)
- Gradle 8.12.1
- JUnit 5.11.4 for testing

Open the reference code:

- Download the source code from github.com/fredgeorge
    - Clone, or pull and extract the zip
- Open IntelliJ
- Choose "Open" (it's a Gradle project)
- Navigate to the reference code root, and enter

Source and test directories should already be tagged as such,
with test directories in green.

Confirm that everything builds correctly (and necessary libraries exist).
There is a sample class, Rectangle, with a corresponding
test, RectangleTest. The test should run successfully
from the Gradle __test__ task.

Update the following:

- In settings.gradle.kts, change the rootProject.name
- In both engine and tests, choose your domain name for your code under kotlin directory
- Consider renaming the <engine> project to your domain specific label

# Twitter Calculator Demo Code
---

This is the code I used for the Reference during my live presentation. The code is fairly commented
now and the Library I wrote to hide Twitter and hack details is also included and viewable.

The Library Code (tclib) was NOT done during the presentation, but it's source code is available if
you are interested.

### Notes on Use:
---

- _**To use the code you will need to include the library in your source, or download the release.**_
- _**The release includes the library as a jar file and the twitter logo.**_
- _**The Twitter logo must be in the root of the working directory!**_


### Getting Started
---

This code manages dependencies using [Gradle](http://gradle.org/) though you can also setup the environment
manually fairly easily as well. Gradle will automatically handle building the project including making
the library a dependency of the main demo code.

Doing this manually is easy with an IDE. So only Gradle is discussed here. For IDEs just make sure you have
the twitter logo (twitter.png) in the project root directory (project working directory).gr

#### Using Gradle: 
1. Clone repository
2. Go to project directory in terminal
3. run 'gradlew build' and wait...
4. A 'build' directory should be made. Go to build -> libs
4a. Verify a jar file and a twitter logo is there
5. The jar file is executable, just execute it in java and go (java -jar in terminal)

### Plagiarism
---

- _**Please do not plagiarize, you only risk putting yourself in a bad position and potentially myself**_
- _**Leading form point one, do not simple copy and paste, contact me for help if you really need it**_
- _**This is meant to be a learning opportunity and tool! DO NOT ABUSE IT!**_

#### Copyright
---

Copyright (c) 2015 Matthew Crocco

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

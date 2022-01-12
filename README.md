# bug-buddy
Bug buddy is a simple bug tracking/help desk CLI program created using
Java and MongoDB. I created this program as a custom personal solution to 
track bugs and issues that I encounter while working on my projects.

Feel free to download and use the Maven project. Any constructive criticism is welcome.
## Features
The program is it's current form has most of the essential functionality to be 
useful for regular use. Bug buddy currently supports:

* Easily setting custom MongoDB database URI.
* Creating bug reports.
* Adding updates and progress reports to individual reports.
* Keeping track of report status as solved or in progress.
* Displaying bug report data in the console.
* Deleting bug reports.

I will be adding more features as I use the program and find shortcomings in it's current state.
## Instructions
* Simply create a MongoDB database locally or on a cloud provider of your choice.

* Create a database called bugs in which a collection called main should also be created.

* When prompted enter the database URI of the previously created database into the console.

* Once that's done you're off to the races!


**Side note:** If you wish to change the database URI simply edit the DatabaseURI.txt file, 
delete the current URI and add the new database link you wish to use.
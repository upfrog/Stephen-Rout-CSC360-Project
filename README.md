# Nexus
This a LinkedIn semi-clone, made for a class assignment.

It uses a Java backend, with data stored on REST server, and a JavaFX frontend. Both the back and frontend have unit tests.

It is basically functional, but has a wide variety of issues. This was an interesting project, but not one I'm passionate about, so I've decided that my time is better spent elsewhere.

Some features include:
- Making regular posts and job posts.
- Adding and removing editors for user pages.
- Editting user pages.
- Pushing posts to followers.


Just a few of the things that need improving include:
- Method and attribute privacy levles are not always thoughtfully chosen.
- Due to an early misunderstanding, the code which interacts with the REST server is a singleton.
- The password system is so horrible that it doesn't even deserve the title of "password system".
- The functionality to make a new account within the app is broken.


## Setup:
To run the app, clone this repo, https://github.com/michaelkbradshaw/SImpleRESTServer. The second repo contains the REST server code which this project relies on. To start the server, run SImpleRESTServer\src\main\java\simpleRESTServer\SimpleRESTServer.java. Once the server is running, the tests in Nexus\src\test should all run
The app can be started by running Nexus\src\main\java\main\Runner.java, but unless the tests have been run, there will be no account information to log in with.

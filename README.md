ExamSquirrel - InDev
==================================================

A tool for creating online practice tests with variable questions
--------------------------------------------------
Currently uses: Grunt, Maven, Jersey, a REST Api, MongoDB
Future dependencies: SQL, MathJax

Setting up the project for developers
--------------------------------------------------
The frontend/ folder is a Grunt project and the backend/ folder is a Maven project. To successfully build you do need to have both installed. As of right now you do need a Server running Tomcat and a MongoDB server running on port 27017 with the documents in the resources folder.

```
ExamSquirrel
    |
    |_frontend
    |   |_Gruntfile.js
    |   |_package.json
    |   |_src/
    |
    |_backend
    |   |_pom.xml
    |   |_src/
    |
    |_README.md
    |_.gitignore
```

Features
--------------
Planned URIs, other will be 404 or forwarded to root 
```
/
/create/
/exam/{examid}
/user/{userid}
```
Planned API URIs
```
/api/exam?examid=0&seed=0&length=0
/api/user?userid=0
```

Language mapping
------------------
For the most part only used in animations/pictures.
```
squirrel --> user
oak --> exam
acorn --> exam question
```

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

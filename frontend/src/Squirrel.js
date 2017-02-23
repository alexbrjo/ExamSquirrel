/**
 * The main controller for the frontend application. Manages the currently
 * loaded questions. Makes requests to the RESTful backend and updates the
 * web application. Java 2
 */
function Squirrel () {
    var thiz = this;
    this.questionList = [];
    this.currentQuestion = -1;
    var scriptStartTime = window.performance.now();
    var SEVLET_URL = 'api/exam?seed=1';
    
    /**
     * Loads a problem set from the API and uses it to start an exam
     * @param {type} user the owner of the problem set
     * @param {String} set the problem set to request 
     */
    this.exam = function(user, set) {
        new ApiRequest(SEVLET_URL, function(o) {
            console.log("loaded");
            
            console.log(o);
            thiz.questionList = o.questions;
        });
    }
    
    /**
     * Loads a problem set from the API and uses it for practice
     * @param {type} user the owner of the problem set
     * @param {String} set the problem set to request 
     */
    this.practice = function (user, set) {}
    
    this.loadSet = function (set) {}
    
    this.next = function () {
        thiz.currentQuestion++;
        var q = thiz.questionList[thiz.currentQuestion];
        var qroot = document.getElementById("question");
        qroot.children[0].innerHTML = q.content;

        var aroot = document.getElementById("answers");
        var kids = aroot.children;
        for (var i = 0; i < q.choices.length; i++) {
            kids[i].innerHTML = q.choices[i];
        }
    }
    
    var ans_buttons = document.getElementsByClassName("answer");
    for (var i = 0; i < ans_buttons.length; i++) {
        ans_buttons[i].onmouseup = thiz.next;
    }
}

var s = new Squirrel();
s.exam("base", "physics");

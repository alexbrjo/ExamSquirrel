/**
 * The main controller for the frontend application. Manages the currently
 * loaded questions. Makes requests to the RESTful backend and updates the
 * web application. Defensivily Hides data by having variables instead of 
 * object fields. 
 */
function Squirrel () {
    // data from exam request
    var questionList = [];
    var prime = 0; 
    
    // constants
    var scriptStartTime = window.performance.now();
    var SEVLET_URL = 'api/exam?seed=1';
    
    // front-end state data
    var correct = 0;
    var incorrect = 0;
    var currentQuestion = -1;
    
    /**
     * Very simple string hash so answers aren't stored as plain text. Same 
     * algorithm as the back end. This of course is replicable by the user, 
     * but that is alright. Just exists so asnwers aren't plain text and to 
     * create a disincentive to cheat. 
     */
    function hashCode (str) {
        var hash = 0;
        for (var i = 0; i < str.length; i++) {
            hash += str.charCodeAt(i) * prime;
        }
        return hash & hash;
    }
    
    /**
     * Compares hashes to check answer
     */
    var thiz = this;
    this.answer = function (e) {
        var a = e.srcElement.classList[0]; // should get correct letter 
        if (questionList[i].answer === hashCode(questionList[i].id + a)) {
            correct++;
            thiz.next();
        } else {
            incorrect++;
        }
        
        var percent;
        if (correct === 0 && incorrect === 0) {
            percent = 0;
        } else {
            percent = Math.floor( 80 * ( correct ) / ( correct + incorrect ) );
        }
        document.getElementById("done").style.width = percent + "%";
       
    }
    
    /**
     * Loads a problem set from the API and uses it to start an exam
     * @param {type} user the owner of the problem set
     * @param {String} set the problem set to request 
     */
    this.loadExam = function(user, set) {
        new ApiRequest(SEVLET_URL, function(o) {
            questionList = o.questions;
            prime = o.prime;
        });
    }
    
    /**
     * Loads a problem set from the API and uses it for practice
     * @param {type} user the owner of the problem set
     * @param {String} set the problem set to request 
     */
    this.practice = function (user, set) {alert("practice unsupported")}
    
    this.next = function () {
        currentQuestion++;
        var q = questionList[currentQuestion];
        var qroot = document.getElementById("question");
        qroot.children[0].innerHTML = q.content;

        var aroot = document.getElementById("answers");
        var kids = aroot.children;
        for (var i = 0; i < q.choices.length; i++) {
            kids[i].innerHTML = q.choices[i];
        }
    }
    
    // sets answer button mouse listeners 
    var ans_buttons = document.getElementsByClassName("answer");
    for (var i = 0; i < ans_buttons.length; i++) {
        ans_buttons[i].onmouseup = this.answer;
    }
}

var s;
window.onload = function () {
    s = new Squirrel();
    s.loadExam("base", "physics");
};

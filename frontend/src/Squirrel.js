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
    var SEVLET_URL = 'api/exam';
    
    // front-end state data
    var correct = 0;
    var incorrect = 0;
    var currentQuestion = -1;
    
    /**
     * Very simple string hash so answers aren't stored as plain text. Same 
     * algorithm as the back end. This of course is replicable by the user, 
     * but that is alright. Just exists to create a disincentive to cheat. 
     * @param {String} str the string of text to hash
     */
    var simpleHash = function (str) {
        var hash = 0;
        for (var i = 0; i < str.length; i++) {
            hash += str.charCodeAt(i) * prime;
        }
        return hash & hash;
    };
    
    /**
     * Compares hashes to check answer.
     * 
     * input -> check answer -[incorrect]-> negative animation
     *             |
     *             +-[correct]-> positive animation -> next question
     */
    var answer = function (e) {
        var a = e.srcElement.classList[0]; // should get correct letter 
        if (questionList[currentQuestion].answer === 
                simpleHash(questionList[currentQuestion].id + a)) {
            correct++;
            next();
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
       
    };
    
    /**
     * Loads a problem set from the API and uses it to start an exam
     * @param {type} user the owner of the problem set
     * @param {String} set the problem set to request 
     */
    var loadExam = function(user, set) {
        new ApiRequest(SEVLET_URL, function(o) {
            questionList = o.questions;
            prime = o.prime;
            next();
        });
    };
    
    /**
     * Loads a problem set from the API and uses it for practice
     * @param {type} user the owner of the problem set
     * @param {String} set the problem set to request 
     */
    var practice = function (user, set) {alert("practice unsupported");};
    
    var next = function () {
        currentQuestion++;
        var q = questionList[currentQuestion];
        var qroot = document.getElementById("question");
        qroot.children[0].innerHTML = q.content;

        var aroot = document.getElementById("answers");
        var kids = aroot.children;
        for (var i = 0; i < q.choices.length; i++) {
            kids[i].innerHTML = q.choices[i];
        }
    };
    
    // sets answer button mouse listeners 
    var ans_buttons = document.getElementsByClassName("answer");
    for (var i = 0; i < ans_buttons.length; i++) {
        ans_buttons[i].onmouseup = answer;
    }
    loadExam("base", "physics");
}

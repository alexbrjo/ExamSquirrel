/**
 * The main controller for the frontend application. Manages the currently
 * loaded questions. Makes requests to the RESTful backend and updates the
 * web application.
 */
function Squirrel () {
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
        var t = this;
        new ApiRequest(SEVLET_URL, function(q) {
            t.questionList = q;
            console.log(q);
        });
    }
    
    /**
     * Loads a problem set from the API and uses it for practice
     * @param {type} user the owner of the problem set
     * @param {String} set the problem set to request 
     */
    this.practice = function (user, set) {}
    
    this.loadSet = function (set) {
        
    }
    
    this.next = function () {
        this.currentQuestion++;
        var q = this.questionList[this.currentQuestion];
        var qroot = document.getElementById("question");
        qroot.children[0].innerHTML = q.content;

        var aroot = document.getElementById("answers");
        var kids = aroot.children;
        for (var i = 0; i < q.choices.length; i++) {
            kids[i].innerHTML = q.choices[i];
        }
    }
}

var s = new Squirrel();
s.exam("base", "physics");


var scriptStartTime = window.performance.now();
var SEVLET_URL = 'api/exam?seed=';

function clear() {
    document.getElementById("question").innerHTML = "";
}

var next;

function build (s) {
    scriptStartTime = window.performance.now();
    var exam = null;
    var questionArray = null;
    
    var seed = s || Math.floor(Math.random() * 1000);
    var jsonReq = new XMLHttpRequest();
    jsonReq.open('GET', SEVLET_URL + seed);

    jsonReq.onreadystatechange = function() {
        /*
         *  0	client created but open() not called
         *  1	open() called
         *  2	send() has been called and heads and status are available
         *  3	downloading, .responseText has some data
         *  4	download complete
         */
        if (jsonReq.readyState !== 4) return; 
        
        var genStartTime = window.performance.now();
        exam = JSON.parse(jsonReq.responseText);
        questionArray = exam.questions;
        
        console.log("Loaded " + questionArray.length + " questions in " + 
                Math.ceil(window.performance.now() - scriptStartTime)/1000 + 
                "s using seed: " + seed);
        
        var i = 0;
        insertQuestion(questionArray[i]);
        
        next = function () {
            i++;
            insertQuestion(questionArray[i]);
        }
        
        console.log("JSON successfully parsed.\n" + questionArray.length + "/" + 
                questionArray.length + " questions in " +  
                Math.ceil(window.performance.now() - genStartTime)/1000 + "s");
    };

    jsonReq.send();
}

function insertQuestion (q) {
    var qroot = document.getElementById("question");
    qroot.children[0].innerHTML = q.content;
    
    var aroot = document.getElementById("answers");
    var kids = aroot.children;
    for (var i = 0; i < q.choices.length; i++) {
        kids[i].innerHTML = q.choices[i];
    }
}

function buildQuestion (json, n) {
    // create question root
    var qroot = document.createElement("div");
    qroot.setAttribute("class", "question");
    
    /** CREATE QUESTION HEADER */
    // create question number
    var qbody = document.createElement("div");
    qbody.setAttribute("class", "question-body");
    qroot.appendChild(qbody);
    
    // create question number
    var qnum = document.createElement("div");
    qnum.setAttribute("class", "question-number");
    qnum.innerHTML = n + ".";
    qbody.appendChild(qnum);
    
    // create question content
    var qtext = document.createElement("div");
    qtext.setAttribute("class", "question-text");
    qtext.innerHTML = json.content;
    qbody.appendChild(qtext);
    
    var oroot = document.createElement("div");
    oroot.setAttribute("class", "option-wrapper");
    qroot.appendChild(oroot);
    
    /** CREATE OPTION DIVS */
    for (var i = 0; i < json.choices.length; i++) {
        var obutton = document.createElement("div");
        obutton.setAttribute("class", "option-button");
        oroot.appendChild(obutton);

        // create question number
        var onum = document.createElement("div");
        onum.setAttribute("class", "option-number");
        onum.innerHTML =  "(" + String.fromCharCode(65 + i) + ")";
        obutton.appendChild(onum);

        // create question content
        var otext = document.createElement("div");
        otext.setAttribute("class", "option-text");
        otext.innerHTML = json.choices[i];
        obutton.appendChild(otext);
    }

    return qroot;
}

build();

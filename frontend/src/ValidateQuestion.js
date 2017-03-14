
function formSubmit () {
    var question = { 
        id: document.getElementById("question-id").value,
        topic: document.getElementById("question-topic").value,
        content: document.getElementById("question-content").value,
        choices: null,
        tips: [""],
        variation: [[1, 2, 0.1]]
    };
    
    question.choices = [];
    var ans = document.getElementById("answers").children;
    for (var i = 0; i < ans.length; i++) {
        question.choices[i] = ans[i].value;
    }
        
    if (validateQuestion()) {
        ApiPut("api/exam", question, function(){ console.log("called"); })
    }
}

function validateQuestion () {
    return true;
}

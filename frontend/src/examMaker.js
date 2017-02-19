/**
 * Defines a request to the API. 
 */
function ApiRequest () {
    var exam = null;
    var questionArray = null;
    
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

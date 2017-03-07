/**
 * Defines a request to the API. 
 */
function ApiRequest (SEVLET_URL, callback) {
    var object = null;
    
    var jsonReq = new XMLHttpRequest();
    jsonReq.open('GET', SEVLET_URL);

    jsonReq.onreadystatechange = function() {
        /*
         *  0	client created but open() not called
         *  1	open() called
         *  2	send() has been called and heads and status are available
         *  3	downloading, .responseText has some data
         *  4	download complete
         *  
         *  Must have completed download with HTTP response OK
         */
        if (jsonReq.readyState !== 4 || jsonReq.status != 200) return; 
        
        object = JSON.parse(jsonReq.responseText);
        
        callback(object);
    };

    jsonReq.send();
}

/**
 * Defines a request to the API.
 * @param {type} SEVLET_URL
 * @param {type} content
 * @param {type} callback
 * @returns {undefined}
 */
function ApiPut (SEVLET_URL, content, callback) {
    var object = null;
    
    var jsonPut = new XMLHttpRequest();
    jsonPut.open('PUT', SEVLET_URL);
    jsonPut.setRequestHeader('Content-Type', 'application/json');
    
    jsonPut.onload = function() {
        if (jsonPut.status !== 200) return; 
        
        var genStartTime = window.performance.now();
        object = JSON.parse(jsonPut.responseText);
        
        callback(object);
    };

    
    jsonPut.send(JSON.stringify(content));
}

var a = ApiPut;

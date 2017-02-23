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
         */
        if (jsonReq.readyState !== 4) return; 
        
        var genStartTime = window.performance.now();
        object = JSON.parse(jsonReq.responseText);
        
        callback(object);
    };

    jsonReq.send();
}

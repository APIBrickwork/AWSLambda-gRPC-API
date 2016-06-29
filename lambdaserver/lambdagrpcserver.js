"use strict";
var AWS = require('aws-sdk');
var grpc = require("grpc");
var protoDescriptor = grpc.load("./lambda.proto");
//AWS.config.credentials= new AWS.SharedIniFileCredentials({profile: 'adminuser'});
AWS.config.update({
    region: 'eu-central-1'
});
var host = "0.0.0.0";
var port = process.env.LISTEN_PORT;
//var port = 8080;

var users = [];

// Check if it was called as required of as main
if (require.main === module) {
    var grpcServer = getServer();
    grpcServer.bind(host + ":" + port, grpc.ServerCredentials.createInsecure());
    console.log("Starting Server listening on " + host + ":" + port);
    grpcServer.start();
}

function getServer() {
    var server = new grpc.Server();
    server.addProtoService(protoDescriptor.LambdaOps.service, {
        addPersmission: addPersmission,
        createAlias: createAlias,
        createEventSourceMapping: createEventSourceMapping,
        createFunction: createFunction,
        deleteAlias: deleteAlias,
        deleteEventSourceMapping: deleteEventSourceMapping,
        deleteFunction: deleteFunction,
        getFunction: getFunction,
        listFunctions: listFunctions
    });
    return server;
}

/**
*Adds a permission to the resource policy associated with the specified AWS Lambda function.
*@param{json object} call
*/
function addPersmission(call, callback) {
    console.log("Received request for addPermission with values:\n" +
        JSON.stringify(call.request));
    var param = checkEmptyField(call.request);
    var lambda = new AWS.Lambda();
    var response;
    lambda.addPermission(param, function(err, data) {
        if (err) {
            console.log("Error Error Message\n");
            console.log(err, err.stack); // an error occurred
            response = err;
        } else {
            console.log("#Success Message#\n");
            console.log("string = " + JSON.stringify(data));
            console.log(data); // successful response
            response = data;
        }
        console.log("Sending response:\n" + JSON.stringify(response));
        callback(null, response);
    });
}

/**
*Creates an alias that points to the specified Lambda function version.
*@param{json object} call
*/
function createAlias(call, callback) {
    console.log("Received request for createAlias with values:\n" +
        JSON.stringify(call.request));
    var param = checkEmptyField(call.request);
    var lambda = new AWS.Lambda();
    var response;
    lambda.createAlias(param, function(err, data) {
        if (err) {
            console.log("Error Error Message\n");
            console.log(err, err.stack); // an error occurred
            response = err;
        } else {
            console.log("#Success Message#\n");
            console.log("string = " + JSON.stringify(data)); // successful response
          //  console.log(data);
            response = JSON.stringify(data);
        }
        console.log("Sending response:\n" + JSON.stringify(response));
        callback(null, response);
    });

}

/**
*Identifies a stream as an event source for a Lambda function.
*@param{json object} call
*/
function createEventSourceMapping(call, callback) {
    console.log("Received request for createEventSourceMapping with values:\n" +
        JSON.stringify(call.request));
    var param = checkEmptyField(call.request);
    var lambda = new AWS.Lambda();
    var response;
    lambda.createEventSourceMapping(param, function(err, data) {
        if (err) {
            console.log("Error Error Message\n");
            console.log(err, err.stack); // an error occurred
            response = err;
        } else {
            console.log("#Success Message#\n");
            console.log("string = " + JSON.stringify(data));
            console.log(data); // successful response
            response = data;
        }
        console.log("Sending response:\n" + JSON.stringify(response));
        callback(null, response);
    });
}

/**
*Creates a new Lambda function. The function metadata is created from the request parameters,
*and the code for the function is provided by a .zip file/ S3 bucket in the request body.
*@param{json object} call
*/
function createFunction(call, callback) {
    console.log("Received request for createFunction with values:\n" +
        JSON.stringify(call.request));
    var param = checkEmptyField(call.request);
    var lambda = new AWS.Lambda();
    var response;
    lambda.createFunction(param, function(err, data) {
        if (err) {
            console.log("Error Error Message\n");
            console.log(err, err.stack); // an error occurred
            response = err;
        } else {
            console.log("#Success Message#\n");
            //console.log("string = " + JSON.stringify(data));
            console.log(data); // successful response
          response = JSON.stringify(data);
        }
        console.log("Sending response:\n" + JSON.stringify(response));
        callback(null, response);
    });
}

/**
*Deletes the specified Lambda function alias.
*@param{json object} call
*/
function deleteAlias(call, callback) {
    console.log("Received request for deleteAlias with values:\n" +
        JSON.stringify(call.request));
    var param = checkEmptyField(call.request);
    var lambda = new AWS.Lambda();
    var response;
    lambda.deleteAlias(param, function(err, data) {
        if (err) {
            console.log("Error Error Message\n");
            console.log(err, err.stack); // an error occurred
            response = err;
        } else {
            console.log("#Success Message#\n");
            console.log("string = " + JSON.stringify(data));
            //console.log(data); // successful response
            response = "Alias deleted successfully";
        }
        console.log("Sending response:\n" + JSON.stringify(response));
        callback(null, response);
    });
}

/**
*Removes an event source mapping.
*This means AWS Lambda will no longer invoke the function for events in the associated source.
*@param{json object} call
*/
function deleteEventSourceMapping(call, callback) {
    console.log("Received request for deleteEventSourceMapping with values:\n" +
        JSON.stringify(call.request));
    var param = checkEmptyField(call.request);
    var lambda = new AWS.Lambda();
    var response;
    lambda.deleteEventSourceMapping(param, function(err, data) {
        if (err) {
            console.log("Error Error Message\n");
            console.log(err, err.stack); // an error occurred
            response = err;
        } else {
            console.log("#Success Message#\n");
            console.log("string = " + JSON.stringify(data));
            console.log(data); // successful response
            response = data;
        }
        console.log("Sending response:\n" + JSON.stringify(response));
        callback(null, response);
    });
}

/**
*Deletes the specified Lambda function code and configuration.
*@param{json object} call
*/
function deleteFunction(call, callback) {
    console.log("Received request for deleteFunction with values:\n" +
        JSON.stringify(call.request));
    var param = checkEmptyField(call.request);
    var lambda = new AWS.Lambda();
    var response;
    lambda.deleteFunction(param, function(err, data) {
        if (err) {
            console.log("Error Error Message\n");
            console.log(err, err.stack); // an error occurred
            response = err;
        } else {
            console.log("#Success Message#\n");
            console.log("string = " + JSON.stringify(data));
            console.log(data); // successful response
            response = "successfully deleted the function";
        }
        console.log("Sending response:\n" + JSON.stringify(response));
        callback(null, response);
    });
}
/****
*Returns a specific Lambda functions.
*@param{json object} call
*/
function getFunction(call, callback) {
    console.log("Received request for getFunction with values:\n" +
        JSON.stringify(call.request));
    var param = checkEmptyField(call.request);
    var lambda = new AWS.Lambda();
    var response;
    lambda.getFunction(param, function(err, data) {
        if (err) {
            console.log("Error Error Message\n");
            console.log(err, err.stack); // an error occurred
            response = err;
        } else {
            console.log("#Success Message#\n");
          //  console.log("string = " + JSON.stringify(data));
            console.log(data); // successful response
            response = JSON.stringify(data);
        }
        console.log("Sending response:\n" + JSON.stringify(response));
        callback(null, response);
    });
}

/****
*Returns a list of your Lambda functions. For each function,
*the response includes the function configuration information
*@param{json object} call
*/
function listFunctions(call, callback) {
    console.log("Received request for listFunctions with values:\n" +
        JSON.stringify(call.request));
    var param = checkEmptyField(call.request);
    var lambda = new AWS.Lambda();
    var response;
    lambda.listFunctions(param, function(err, data) {
        if (err) {
            console.log("Error Error Message\n");
            console.log(err, err.stack); // an error occurred
            response = err;
        } else {
            console.log("#Success Message#\n");
            //console.log("string = " + JSON.stringify(data));
            console.log(data); // successful response
            response = JSON.stringify(data);
        }
        console.log("Sending response:\n" + JSON.stringify(response));
        callback(null, response);
    });
}

/****
*Check empty field in the json parameter.
*@param{json object} call
*/
function checkEmptyField(request) {
    var paramname = [];
    for (var param in request) {

        console.log('type of ' + param + ' = ' + typeof request[param]);
        if (request[param] === ''||request[param] === 0) {
            paramname.push(param);
        }
        else if (typeof request[param]=== 'object') {
          console.log("recursion");
          checkEmptyField(request[param]);
        }

    }

    for (var i = 0; i < paramname.length; i++) {
        delete request[paramname[i]];
    }

    return request;
}

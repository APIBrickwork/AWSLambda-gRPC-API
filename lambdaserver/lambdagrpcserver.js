"use strict";
var AWS = require('aws-sdk');
var grpc = require("grpc");
var protoDescriptor = grpc.load("./lambda.proto");
//AWS.config.credentials= new AWS.SharedIniFileCredentials({profile: 'adminuser'});
AWS.config.update({
    region: 'eu-central-1'
});
var host = "0.0.0.0";
//var port = process.env.LISTEN_PORT;
var port = 8080;

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
function checkEmptyField(request) {
    var paramname = [];
    for (var param in request) {
        //console.log(param);
        //console.log(request[param]);
        console.log('type of ' + param + ' = ' + typeof request[param]);
        if (request[param] === ''||request[param] === 0) {
            paramname.push(param);
        }
        else if (typeof request[param]=== 'object') {
          console.log("recursion");
          checkEmptyField(request[param]);
        }

    }
    //console.log(paramname);
    for (var i = 0; i < paramname.length; i++) {
        delete request[paramname[i]];
    }
    //console.log(request);
    return request;
}

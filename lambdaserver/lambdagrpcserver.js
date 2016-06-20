"use strict";
var AWS = require('aws-sdk');
var grpc = require("grpc");
var protoDescriptor = grpc.load("./lambda.proto");

var host = "0.0.0.0";
var port = process.env.LISTEN_PORT;

var users = [];

// Check if it was called as required of as main
if(require.main === module){
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
    deleteFunction: deleteFunction
  });
  return server;
}

function addPersmission(call, callback){
  console.log("Received request for addPermission with values:\n"
  + JSON.stringify(call.request));
  var lambda = new AWS.Lambda();
  var response = lambda.addPermission(call.request, function (err, data) {
  if (err){ console.log(err, err.stack); // an error occurred
           return err.stack;}
  else     {console.log(data);           // successful response
           return data;}
  });
  console.log("Sending response:\n" + JSON.stringify(response));
  callback(null, response);
}


function createAlias(call, callback){
  console.log("Received request for createAlias with values:\n"
  + JSON.stringify(call.request));
  var lambda = new AWS.Lambda();
  var response = lambda.createAlias(call.request, function (err, data) {
    if (err){ console.log(err, err.stack); // an error occurred
             return err.stack;}
    else     {console.log(data);           // successful response
             return data;}
  });
  console.log("Sending response:\n" + JSON.stringify(response));
  callback(null, response);
}


function createEventSourceMapping(call, callback){
  console.log("Received request for createEventSourceMapping with values:\n"
  + JSON.stringify(call.request));
  var lambda = new AWS.Lambda();
  var response = lambda.createEventSourceMapping(call.request, function (err, data) {
    if (err){ console.log(err, err.stack); // an error occurred
             return err.stack;}
    else     {console.log(data);           // successful response
             return data;}
  });
  console.log("Sending response:\n" + JSON.stringify(response));
  callback(null, response);
}

function createFunction(call, callback){
  console.log("Received request for createFunction with values:\n"
  + JSON.stringify(call.request));
  var lambda = new AWS.Lambda();
  var response = lambda.createFunction(call.request, function (err, data) {
    if (err){ console.log(err, err.stack); // an error occurred
             return err.stack;}
    else     {console.log(data);           // successful response
             return data;}
  });
  console.log("Sending response:\n" + JSON.stringify(response));
  callback(null, response);
}
function deleteAlias(call, callback){
  console.log("Received request for deleteAlias with values:\n"
  + JSON.stringify(call.request));
  var lambda = new AWS.Lambda();
  var response = lambda.deleteAlias(call.request, function (err, data) {
    if (err){ console.log(err, err.stack); // an error occurred
             return err.stack;}
    else     {console.log(data);           // successful response
             return data;}
  });
  console.log("Sending response:\n" + JSON.stringify(response));
  callback(null, response);
}

function deleteEventSourceMapping(call, callback){
  console.log("Received request for deleteEventSourceMapping with values:\n"
  + JSON.stringify(call.request));
  var lambda = new AWS.Lambda();
  var response = lambda.deleteEventSourceMapping(call.request, function (err, data) {
    if (err){ console.log(err, err.stack); // an error occurred
             return err.stack;}
    else     {console.log(data);           // successful response
             return data;}
  });
  console.log("Sending response:\n" + JSON.stringify(response));
  callback(null, response);
}

function deleteFunction(call, callback){
  console.log("Received request for deleteFunction with values:\n"
  + JSON.stringify(call.request));
  var lambda = new AWS.Lambda();
  var response = lambda.deleteFunction(call.request, function (err, data) {
    if (err){ console.log(err, err.stack); // an error occurred
             return err.stack;}
    else     {console.log(data);           // successful response
             return data;}
  });
  console.log("Sending response:\n" + JSON.stringify(response));
  callback(null, response);
}
// function processCalculation(calcreq){
//   var response;
//   var values = calcreq.values_to_use;
//
//   if(calcreq.type === "ADDITION"){
//     var result = 0;
//     for(var i=0;i<values.length;i++){
//       result += parseInt(values[i]);
//     }
//     response =
//     {
//       map: {"ADDITION": result}
//     };
//   }
//   else if(calcreq.type === "MULTIPLICATION"){
//     var result = 1;
//     for(var i=0;i<values.length;i++){
//       result *= parseInt(values[i]);
//     }
//     response =
//     {
//       map: {"MULTIPLICATION": result}
//     };
//   }
//   else{
//     console.log("Wrong type: " + call.request.type);
//   }
//   return response;
// }

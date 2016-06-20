"use strict";
var grpc = require("grpc");
var async = require("async");
var protoDescriptor = grpc.load("./lambda.proto");

var grpcHost = "0.0.0.0";
var grpcPort = 8080;

var servicestub = new protoDescriptor.LambdaOps(grpcHost+":"+grpcPort,
  grpc.credentials.createInsecure());

function sendAddPersmissionRequest(){
  var request =
  {
     Action: 'STRING_VALUE', /* required */
     FunctionName: 'STRING_VALUE', /* required */
     Principal: 'STRING_VALUE', /* required */
     StatementId: 'STRING_VALUE', /* required */
     EventSourceToken: 'STRING_VALUE',
     Qualifier: 'STRING_VALUE',
     SourceAccount: 'STRING_VALUE',
     SourceArn: 'STRING_VALUE'
  };
  console.log("Using request:\n" + JSON.stringify(request));

  servicestub.addPersmission(request,
    function(err, response){
      if(err){
        console.log("Error when calling noStream: " + err);
      }
      else{
        console.log("Received response:\n" + JSON.stringify(response));
      }
    });
}

function sendCreateAliasRequest(){
  var request =
  {
    FunctionName: 'STRING_VALUE', /* required */
    FunctionVersion: 'STRING_VALUE', /* required */
    Name: 'STRING_VALUE', /* required */
    Description: 'STRING_VALUE'
  };
  console.log("Using request:\n" + JSON.stringify(request));

  servicestub.createAlias(request,
    function(err, response){
      if(err){
        console.log("Error when calling noStream: " + err);
      }
      else{
        console.log("Received response:\n" + JSON.stringify(response));
      }
    });
}

function sendCreateEventSourceMappingRequest(){
  var request =
  {
    EventSourceArn: 'STRING_VALUE', /* required */
    FunctionName: 'STRING_VALUE', /* required */
    StartingPosition: 'TRIM_HORIZON | LATEST', /* required */
    BatchSize: 0,
    Enabled: true || false
  };
  console.log("Using request:\n" + JSON.stringify(request));

  servicestub.createEventSourceMapping(request,
    function(err, response){
      if(err){
        console.log("Error when calling noStream: " + err);
      }
      else{
        console.log("Received response:\n" + JSON.stringify(response));
      }
    });
}

function sendcreateFunctionRequest(){
  var request =
  {
      Code: { /* required */
        S3Bucket: 'STRING_VALUE',
        S3Key: 'STRING_VALUE',
        S3ObjectVersion: 'STRING_VALUE',
        ZipFile: new Buffer('...') || 'STRING_VALUE'
      },
      FunctionName: 'STRING_VALUE', /* required */
      Handler: 'STRING_VALUE', /* required */
      Role: 'STRING_VALUE', /* required */
      Runtime: 'nodejs | nodejs4.3 | java8 | python2.7', /* required */
      Description: 'STRING_VALUE',
      MemorySize: 0,
      Publish: true || false,
      Timeout: 0,
      VpcConfig: {
        SecurityGroupIds: [
          'STRING_VALUE',
          /* more items */
        ],
        SubnetIds: [
          'STRING_VALUE',
          /* more items */
        ]
      }
  };
  console.log("Using request:\n" + JSON.stringify(request));

  servicestub.createFunction(request,
    function(err, response){
      if(err){
        console.log("Error when calling noStream: " + err);
      }
      else{
        console.log("Received response:\n" + JSON.stringify(response));
      }
    });
}

function sendDeleteAliasRequest(){
  var request =
  {
    FunctionName: 'STRING_VALUE', /* required */
    Name: 'STRING_VALUE' /* required */
  };
  console.log("Using request:\n" + JSON.stringify(request));

  servicestub.deleteAlias(request,
    function(err, response){
      if(err){
        console.log("Error when calling noStream: " + err);
      }
      else{
        console.log("Received response:\n" + JSON.stringify(response));
      }
    });
}

function sendDeleteEventSourceMappingRequest(){
  var request =
  {
    UUID: 'STRING_VALUE' /* required */
  };
  console.log("Using request:\n" + JSON.stringify(request));

  servicestub.deleteEventSourceMapping(request,
    function(err, response){
      if(err){
        console.log("Error when calling noStream: " + err);
      }
      else{
        console.log("Received response:\n" + JSON.stringify(response));
      }
    });
}

function sendDeleteFunctionRequest(){
  var request =
  {
    FunctionName: 'STRING_VALUE', /* required */
    Qualifier: 'STRING_VALUE'
  };
  console.log("Using request:\n" + JSON.stringify(request));

  servicestub.deleteFunction(request,
    function(err, response){
      if(err){
        console.log("Error when calling noStream: " + err);
      }
      else{
        console.log("Received response:\n" + JSON.stringify(response));
      }
    });
}

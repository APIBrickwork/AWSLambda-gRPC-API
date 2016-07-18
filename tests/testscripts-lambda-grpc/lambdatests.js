"use strict";
var grpc = require("grpc");
var testCase = require("nodeunit").testCase;
var async = require("async");
var protoDescriptor = grpc.load("../proto/lambda.proto");

var timeout = 3000;

var grpcHost = process.env.API_HOST;
var grpcPort = process.env.API_PORT;

var servicestub = new protoDescriptor.LambdaOps(grpcHost + ":" + grpcPort,
    grpc.credentials.createInsecure());


var functionName = "";
var deleteOutputMsg = "";

exports.sendCreateFunction = function(test) {
    async.series([
        // Create the function
        function(callback) {

            console.log("### Test sendCreateFunction.###\n\n");
            var request = {
                Code: { /* required */
                    S3Bucket: 'lambdaapi',
                    S3Key: 'LambdaFunctionOverHttps.zip',
                    S3ObjectVersion: 'JBm1hOQpBmTRm33wsUsOHSHOonO2qlHj'
                },
                FunctionName: 'LambdaFunctionOverHttps',
                /* required */
                Handler: 'LambdaFunctionOverHttps.handler',
                /* required */
                Role: 'arn:aws:iam::927707579246:role/lambda_basic_execution',
                /* required */
                Runtime: 'nodejs4.3', //'nodejs | nodejs4.3 | java8 | python2.7',/* required */
                Description: 'Nodejs testing ',
                MemorySize: 128,
                Publish: true
            };
            console.log("Using request:\n" + JSON.stringify(request));

            servicestub.createFunction(request,
                function(err, response) {
                    if (err) {
                        console.log("Error when calling createFunction: " + err);
                        callback();
                    } else {
                        console.log("Received response:\n" + JSON.stringify(response));
                        var outputLog = response['outputLog'];
                        var outputLogJson = JSON.parse(outputLog[0]);

                        functionName = outputLogJson['FunctionName'];
                        console.log("Found function name: " + functionName);
                        callback();

                    }
                });
        }],
        function(err) {
            console.log("checking test ");
            test.equals(functionName, 'LambdaFunctionOverHttps', "Expected " + functionName + " but was LambdaFunctionOverHttps.");
            test.done();
        }
    );
}

exports.sendGetFunction = function(test) {
    async.series([
        // Get the function
        function(callback) {

            console.log("### Test sendGetFunction.###\n\n");
            var request = {
                FunctionName: 'LambdaFunctionOverHttps' //'STRING_VALUE'/* required */
                    //  Qualifier: ''//'STRING_VALUE'
            };
            console.log("Using request:\n" + JSON.stringify(request));

            servicestub.getFunction(request,
                function(err, response) {
                    if (err) {
                        console.log("Error when calling getFunction: " + err);
                          callback();
                    } else {
                      console.log("Received response:\n" + JSON.stringify(response));
                      var outputLog = response['outputLog'];
                      var outputLogJson = JSON.parse(outputLog[0]);

                      var Config = outputLogJson['Configuration'];

                      functionName = Config['FunctionName'];
                      console.log("Found function name: " + functionName);
                      callback();
                    }
                });
        }],
        function(err) {
            console.log("checking test ");
            test.equals(functionName, 'LambdaFunctionOverHttps', "Expected " + functionName + " but was LambdaFunctionOverHttps.");
            test.done();
        }
    );
}

exports.sendDeleteFunctions = function(test) {
  async.series([
      // Get the function
      function(callback) {

          console.log("### Test sendDeleteFunctions.###\n\n");
          var request = {
              FunctionName: 'LambdaFunctionOverHttps' //'STRING_VALUE'/* required */
                  //  Qualifier: ''//'STRING_VALUE'
          };
          console.log("Using request:\n" + JSON.stringify(request));

          servicestub.deleteFunction(request,
              function(err, response) {
                  if (err) {
                      console.log("Error when calling deleteFunction: " + err);
                        callback();
                  } else {
                    console.log("Received response:\n" + JSON.stringify(response));
                    var outputLog = response['outputLog'];
                    console.log(outputLog);

                    var parsedOutput = JSON.stringify(outputLog[0]);
                    deleteOutputMsg = parsedOutput.replace(/"/g, '');
                    console.log("parsedOutput: '" + parsedOutput + "':" + (typeof parsedOutput));

                    callback();
                  }
              });
      }],
      function(err) {
          console.log("checking test ");
          var deleteMsg = 'successfully deleted the function';
          test.equals(deleteOutputMsg, deleteMsg, "Expected " + deleteMsg + " but was " + deleteOutputMsg);
          test.done();
      }
  );
}

function sleepFor(duration) {
    var now = new Date().getTime();
    while (new Date().getTime() < now + duration) {}
}

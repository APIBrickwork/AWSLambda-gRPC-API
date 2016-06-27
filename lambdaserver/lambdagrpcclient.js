"use strict";
var grpc = require("grpc");
var async = require("async");
var protoDescriptor = grpc.load("./lambda.proto");

var grpcHost = "0.0.0.0";
var grpcPort = 8080;

var servicestub = new protoDescriptor.LambdaOps(grpcHost + ":" + grpcPort,
    grpc.credentials.createInsecure());

function sendAddPersmissionRequest() {
    var request = {
        Action: 'STRING_VALUE',  /* required */
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
        function(err, response) {
            if (err) {
                console.log("Error when calling noStream: " + err);
            } else {
                console.log("Received response:\n" + JSON.stringify(response));
            }
        });
}

function sendCreateAliasRequest() {
    var request = {
        FunctionName: 'hello-world-python2', /* required */
        FunctionVersion: '$LATEST',/* required */
        Name: 'secondPythonApp',/* required */
        Description: 'Alias for python'
    };
    console.log("Using request:\n" + JSON.stringify(request));

    servicestub.createAlias(request,
        function(err, response) {
            if (err) {
                console.log("Error when calling noStream: " + err);
            } else {
                console.log("Received response:\n" + JSON.stringify(response));
            }
        });
}

function sendCreateEventSourceMappingRequest() {
    var request = {
        EventSourceArn: 'STRING_VALUE',
        /* required */
        FunctionName: 'STRING_VALUE',
        /* required */
        StartingPosition: 'TRIM_HORIZON | LATEST',
        /* required */
        BatchSize: 0,
        Enabled: true || false
    };
    console.log("Using request:\n" + JSON.stringify(request));

    servicestub.createEventSourceMapping(request,
        function(err, response) {
            if (err) {
                console.log("Error when calling noStream: " + err);
            } else {
                console.log("Received response:\n" + JSON.stringify(response));
            }
        });
}

function sendCreateFunctionRequest() {
    var request = {
        Code: { /* required */
            S3Bucket: 'lambdaapi',
            S3Key: 'LambdaFunctionOverHttps.zip',
            S3ObjectVersion: 'JBm1hOQpBmTRm33wsUsOHSHOonO2qlHj'
          //  ZipFile: new Buffer('...') || 'STRING_VALUE'
        },
        FunctionName: 'LambdaFunctionOverHttps',/* required */
        Handler: 'LambdaFunctionOverHttps.handler',/* required */
        Role: 'arn:aws:iam::927707579246:role/lambda_basic_execution',/* required */
        Runtime: 'nodejs4.3',//'nodejs | nodejs4.3 | java8 | python2.7',/* required */
        Description: 'Nodejs testing ',
        MemorySize: 128,
        Publish: true
        //Timeout: 0,
        // VpcConfig: {
        //     SecurityGroupIds: [
        //         'STRING_VALUE',
        //         /* more items */
        //     ],
        //     SubnetIds: [
        //         'STRING_VALUE',
        //         /* more items */
        //     ]
        // }
    };
    console.log("Using request:\n" + JSON.stringify(request));

    servicestub.createFunction(request,
        function(err, response) {
            if (err) {
                console.log("Error when calling noStream: " + err);
            } else {
                console.log("Received response:\n" + JSON.stringify(response));
            }
        });
}

function sendDeleteAliasRequest() {
    var request = {
        FunctionName: 'hello-world-nodejs1',/* required */
        Name: 'firstNodeJsApp' /* required */
    };
    console.log("Using request:\n" + JSON.stringify(request));

    servicestub.deleteAlias(request,
        function(err, response) {
            if (err) {
                console.log("Error when calling noStream: " + err);
            } else {
                console.log("Received response:\n" + JSON.stringify(response));
            }
        });
}

function sendDeleteEventSourceMappingRequest() {
    var request = {
        UUID: 'STRING_VALUE' /* required */
    };
    console.log("Using request:\n" + JSON.stringify(request));

    servicestub.deleteEventSourceMapping(request,
        function(err, response) {
            if (err) {
                console.log("Error when calling noStream: " + err);
            } else {
                console.log("Received response:\n" + JSON.stringify(response));
            }
        });
}

function sendDeleteFunctionRequest() {
    var request = {
        FunctionName: 'hello-world-nodejs', //'STRING_VALUE'/* required */
        //  Qualifier: ''//'STRING_VALUE'
    };
    console.log("Using request:\n" + JSON.stringify(request));

    servicestub.deleteFunction(request,
        function(err, response) {
            if (err) {
                console.log("Error when calling deleteFunction: " + err);
            } else {
                console.log("Received response:\n" + JSON.stringify(response));
            }
        });
}
function sendGetFunctionRequest() {
    var request = {
        FunctionName: 'hello-world-nodejs1' //'STRING_VALUE'/* required */
        //  Qualifier: ''//'STRING_VALUE'
    };
    console.log("Using request:\n" + JSON.stringify(request));

    servicestub.getFunction(request,
        function(err, response) {
            if (err) {
                console.log("Error when calling deleteFunction: " + err);
            } else {
                console.log("Received response:\n" + JSON.stringify(response));
            }
        });
}
function sendListFunctionsRequest() {
    var request = {
        //FunctionName: 'hello-world-nodejs', //'STRING_VALUE'/* required */
        //  Qualifier: ''//'STRING_VALUE'
    };
    console.log("Using request:\n" + JSON.stringify(request));

    servicestub.listFunctions(request,
        function(err, response) {
            if (err) {
                console.log("Error when calling deleteFunction: " + err);
            } else {
                console.log("Received response:\n" + JSON.stringify(response));
            }
        });
}

function main() {
    async.series([
        sendListFunctionsRequest
    ]);
}

if (require.main === module) {
    main();
}

exports.sendAddPersmissionRequest = sendAddPersmissionRequest;
exports.sendCreateAliasRequest = sendCreateAliasRequest;
exports.sendCreateEventSourceMappingRequest = sendCreateEventSourceMappingRequest;
exports.sendCreateFunctionRequest = sendCreateFunctionRequest;
exports.sendDeleteAliasRequest = sendDeleteAliasRequest;
exports.sendDeleteEventSourceMappingRequest = sendDeleteEventSourceMappingRequest;
exports.sendDeleteFunctionRequest = sendDeleteFunctionRequest;
exports.sendGetFunctionRequest = sendGetFunctionRequest;
exports.sendListFunctionsRequest = sendListFunctionsRequest;

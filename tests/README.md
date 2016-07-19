# tests
Tests-Bundles for evaluating this gRPC API.

## Test-Overview

__Possible states:__
* Outstanding: Not yet developed.
* Under dev: Is in development state.
* Finished: Development has finished and local execution worked. But not yet tested using a clean environment.
* Finalized: Final state. Also tested with a clean environment (fresh pull of repo, no preexisting docker images, no preexisting docker containers)

| Test-Bundle | Docker | Testscripts | Status | Execution-Info |
| ----------- | ------ | ----------- | ------ | ---- |
| (part4): Lambda gRPC API + test scripts | [docker-lambda-grpc](https://github.com/APIBrickwork/AWSLambda-gRPC-API/tree/master/tests/docker-lambda-grpc) | [testscripts-lambda-grpc](https://github.com/APIBrickwork/AWSLambda-gRPC-API/tree/master/tests/testscripts-lambda-grpc) | Finalized |See [Extended-Configfile](#extended-execution-config-credentials) |

## Extended Execution Config Credentials
__Those tests use AWS!__

It is a special execution routine where the user (in order to make the tests work) has to enter the AWS credentials. 
* Clone the repository
* Change directory to the subfolder stated in the column `Docker`
* Open the config.json file
* Insert your AWS credentials
* Insert or Modify the region
* Save the file
* Run `docker-compose up`

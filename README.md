# lambdagrpcserver

This is lambdagrpcserver offering AWS Lambda Functionality through a gRPC API.

## credentials setup for aws sdk
Edit the credentials in [config.json](https://github.com/APIBrickwork/AWSLambda-gRPC-API/blob/master/lambdaserver/config.json) and insert your AWS Access Key `accessKeyId` and AWS Secret Access Key `secretAccessKey` as well as optionally the region you want to use in `region` (default `eu-central-1`).

```
{ 
  "accessKeyId": "youraccesskey",
  "secretAccessKey": "yoursercretkey",
  "region": "eu-central-1" 
}
```

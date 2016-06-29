# lambdagrpcserver

A lambdagrpcserver written in Node.js

# credentials setup for aws sdk

[default]
aws_access_key_id = your_access_key
aws_secret_access_key = your_secret_key

[adminuser]
aws_access_key_id = your_access_key
aws_secret_access_key = your_secret_key

# config setup for aws sdk

[default]
output = json
region = eu-central-1
[profile adminuser]
output = json
region = eu-central-1

# AWS Kotlin Example

The first version of AWS Kotlin SDK was released on November 27th 2023. 
You can find the announcement [here](https://aws.amazon.com/es/about-aws/whats-new/2023/11/aws-sdk-kotlin/)

This includes important changes for the people using the java sdk. As most of my projects are using Kotlin I would like to 
start using the new SDK, but first, I need to see how the most common features in my projects should be implemented and tested,
also I thought it could be interesting to share the improvements in case it could help someone.

## Overview

The goal is to learn how implement common uses of the AWS SDK using the new Kotlin SDK 
and see how to test it using Localstack and Testcontainers.

For the moment the only way to try the project is executing test, 
to do it move to some example folder like `upload-s3-file` and execute

```shell
./gradlew test
```

## Features

Upload a file to s3 [upload-s3-file](upload-s3-file)

## Getting Started

### Prerequisites

Specify any prerequisites or dependencies that need to be installed before using the project.
You´ll need:
* Docker
* JDK 17 or greater
* Gradle

### Usage

For the moment the only way to try the project is executing test,
to do it move to some example folder like `upload-s3-file` and execute
```shell
./gradlew test
```

## Contributing

If you would like to contribute please submit an Issue or open a Pull Request, any help is welcome!
I´m working on the Github Actions Pipeline to automatically execute test on Pull Requests and main branch
to make it easier to contribute.

## License

The MIT License (MIT). Please see [License File](LICENSE.md) for more information.

## Acknowledgments

[AWS SDK Kotlin](https://github.com/awslabs/aws-sdk-kotlin)

[Localstack](https://github.com/localstack/localstack)

[TestContainers](https://github.com/testcontainers)

[Gradle](https://github.com/gradle/gradle)

[Kotlin](https://github.com/JetBrains/kotlin)

## Roadmap

A first draft of roadmap will include:

- Upload S3 file
- Download S3 file
- Obtain pre-signed url S3 file
- Publish message to SNS
- Receive message from SQS


## Contributors

You can check the contributors https://github.com/isamadrid90/aws-kotlin-examples/graphs/contributors

## Feedback

If you ideas to implement, find bugs or have any feedback in general 
I encourage you to open a new Issue, or better, open a pull request with a proposal to improve! 

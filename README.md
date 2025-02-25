# AWS Kotlin Examples

The first version of AWS Kotlin SDK was released on November 27th 2023. 
You can find the announcement [here](https://aws.amazon.com/es/about-aws/whats-new/2023/11/aws-sdk-kotlin/).

This project serves as a practical guide for developers transitioning from the Java SDK to the AWS Kotlin SDK. As most of my projects 
are using Kotlin, I wanted to explore how to implement common AWS operations using the new SDK and share these examples to help others 
in their transition journey.

## Overview

The project demonstrates real-world examples of common AWS operations implemented in Kotlin, showcasing best practices and modern 
testing approaches. Each example is thoroughly tested using Localstack and Testcontainers to ensure reliability and reproducibility.

### Project Goals
- Showcase practical implementations of common AWS operations using the Kotlin SDK
- Provide working examples with comprehensive tests using modern testing tools
- Help Kotlin developers transition from the Java SDK to the Kotlin SDK
- Serve as a learning resource for AWS service integration in Kotlin applications

### Technical Stack
- Kotlin
- AWS SDK for Kotlin
- Gradle (Build Tool)
- JDK 17+
- Testcontainers
- Localstack (AWS service emulation)
- JUnit

## Features

The project is organized into independent modules, each demonstrating a specific AWS service integration:

- Upload a file to S3: [upload-s3-file](upload-s3-file)
- Download a file from S3: [download-s3-file](download-s3-file)

Each module includes comprehensive tests and documentation to help you understand the implementation.

## Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:
* Docker (for running Localstack and tests)
* JDK 17 or greater
* Gradle (included via wrapper)

### Project Structure
The project follows a modular structure where each AWS service integration is contained in its own module. This makes it easy to:
- Focus on specific AWS services
- Run tests independently
- Understand implementations in isolation

### Usage

Currently, the best way to explore the project is through its tests. Each module contains integration tests that demonstrate the functionality:

1. Navigate to a specific module:
```shell
cd upload-s3-file  # or any other module
```

2. Run the tests:
```shell
./gradlew test
```

### Development Guidelines
- Follow Kotlin coding conventions
- Include comprehensive tests for new features
- Document public APIs
- Write self-documenting code with appropriate comments

## Testing Approach

The project emphasizes thorough testing using modern tools:

### Integration Testing
- Uses Testcontainers to manage Docker containers
- Leverages Localstack for AWS service emulation
- Ensures tests are reproducible and environment-independent

### Test Guidelines
- Tests should be independent and self-contained
- Clear in their purpose
- Using appropriate test doubles when needed
- Include proper error scenarios

## Contributing

Contributions are welcome! Here's how you can help:

1. Fork the repository
2. Create a feature branch
3. Write tests for new functionality
4. Implement the feature
5. Submit a Pull Request

I'm working on the Github Actions Pipeline to automatically execute tests on Pull Requests and main branch
to make it easier to contribute.

## Roadmap

Current progress and future plans:

✅ Completed:
- Upload S3 file
- Download S3 file

🚧 Planned:
- Obtain pre-signed URL for S3 files
- Publish messages to SNS
- Receive messages from SQS

## License

The MIT License (MIT). Please see [License File](LICENSE.md) for more information.

## Acknowledgments

Special thanks to these amazing projects that make this work possible:

- [AWS SDK Kotlin](https://github.com/awslabs/aws-sdk-kotlin)
- [Localstack](https://github.com/localstack/localstack)
- [TestContainers](https://github.com/testcontainers)
- [Gradle](https://github.com/gradle/gradle)
- [Kotlin](https://github.com/JetBrains/kotlin)

## Contributors

Check out all the amazing contributors [here](https://github.com/isamadrid90/aws-kotlin-examples/graphs/contributors)!

## Feedback and Support

Have ideas, found bugs, or want to contribute? I'd love to hear from you!
- Open an [Issue](https://github.com/isamadrid90/aws-kotlin-examples/issues) for bugs or feature requests
- Submit a [Pull Request](https://github.com/isamadrid90/aws-kotlin-examples/pulls) with your improvements
- Check the tests for usage examples
- Review the documentation for detailed information

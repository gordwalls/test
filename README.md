## Test Server
This project contains a small test server that implements a RESTful endpoint for messages. The server is written in Java using the Spring Framework and is built using Maven.

### Architecture
The server follows a standard 3 layer server architecture.  Each package contains a package-info.java file which documents the purpose of the package.  Each server layer is support by packages as follows:

##### API Layer
- com.example.controllers
- com.example.dtos

##### Application Layer
- com.example.applications

##### Data Layer
- com.example.entities
- com.example.repositories

##### Other Packages
- com.example.utils (Utiliies for logs and errors)
- com.example (Main and Database configuration beans)

The packages exist in a standard Maven file system structure that will be familiar to anyone who uses Eclipse.

### Building and Running

##### Prerequisites
In order to build and run this project, you will need the following:
- JDK 1.8
- Maven 3
- Git

Optionally, if you wish to view or manipulate the code with an IDE, **STS** is highly recommended.  STS (Spring Tools Suite) is an all-in-one IDE for Spring projects built on Eclipse.  You can download the IDE from [https://spring.io/tools/sts/all](https://spring.io/tools/sts/all).  This project was developed using STS 3.7.3 on top of Eclipse 4.5.2 on Windows 10.  The download packages contain everything you need.

##### Database considerations
Currently none as the server uses an in-memory noSQL database called Gemfire (for simplicity).  A future interation of this project may move to MongoDB.

##### Building

The project is a Maven project that contains a pom.xml at the root.  To build the project (completely to a self-contained jar), use the following command:
```
> mvn package
```

Note that this command will also run all the automated test cases. Since currently, the logs from the server are sent to the console, you will see logs from the executing server - including exceptions for those tests that exercise failure paths. Do not be concerned.

##### Running

Either of the following commands will run the server once it is built:
```
> mvn spring-boot:run
> java -jar target/test-0.0.1-SNAPSHOT.jar
```

Note that the first command will only work withing the scope of the pom.xml file.  If the jar is moved or renamed, only the second command will work.

##### Building and Running in STS
The project can be imported into STS using the following menu items:
```
File -> Import... -> Maven -> Existing Maven Projects -> <provide root directoy>
```
Once imported, the server can be run as an ordinary Java Application. Building is automatic, unless you have automatic builds turned off. The main is located in:
```
com.example.TestApplication.java
```

##### Using the server
The server can be exercised using any REST client tool. My favorite is POSTMAN which is a Google Plugin to Chrome.

### REST API

#### 1. Creating a message
**URL** - POST /message

**Headers** - Content-Type: application/json

**Request Body:**
```
{
  "message": <Message to create>
}
```

**Request Body Example:**
```json
{
  "message": "A man, a plan, a canal: Panama."
}
```

**Response Body:** The message that was just created
```
{
    "id": <Id of the new message>,
    "message": <message text>,
    "palindrome": <is this message a palindrome>
}
```

**Response Body Example:**
```json
{
    "id": 1,
    "message": "A man, a plan, a canal: Panama.",
    "palindrome": true
}
```

#### 2. Get all messages
**URL** - GET /message

**Headers** - Content-Type: application/json

**Request Body:** None

**Response Body:** All the messages
```
[
    {
        "id": <message id>,
        "message": <message text>,
        "palindrome": <is this message a palindrome>
    },
    ...
]
```

**Response Body Example:**
```json
[
    {
        "id": 2,
        "message": "Mary had a little lamb.",
        "palindrome": false
    },
    {
        "id": 1,
        "message": "A man, a plan, a canal: Panama.",
        "palindrome": true
    }
]
```

#### 3. Get one messages
**URL** - GET /message/{id}

**Headers** - Content-Type: application/json

**Request Body:** None

**Response Body:** The requested message
```
{
    "id": <message id>,
    "message": <message text>,
    "palindrome": <is this message a palindrome>
}
```

**Response Body Example:**
```json
{
    "id": 2,
    "message": "Mary had a little lamb.",
    "palindrome": false
}
```

#### 4. Delete one messages
**URL** - DELETE /message/{id}

**Headers** - Content-Type: application/json

**Request Body:** None

**Response Body:** The message that was just deleted
```
{
    "id": <deleted message id>,
    "message": <message text>,
    "palindrome": <is this message a palindrome>
}
```

**Response Body Example:**
```json
{
    "id": 2,
    "message": "Mary had a little lamb.",
    "palindrome": false
}
```
#### Errors
All errors that originate within the server respond with a common error body as follows:
```
{
    "errorId": <httpStatus>-<subCode>,
    "errorMessage": <error message>,
    "innerExceptionMessage": <see below>
}
```
All errors are identified using a unique error id which is a concatination of the http status code and a sequence number.  This allows all error conditions to be distinguished by the client if desired.  The http status code in the overall response will match the first part of the error id. 

If the error is due to an exception thrown by a subtending system (e.g. the database), then the message of that exception will be included in the innner exception message.  This is provided mainly for debugging.

An example error response body follows:
```json
{
    "errorId": "404-1",
    "errorMessage": "Message not found",
    "innerExceptionMessage": null
}
```

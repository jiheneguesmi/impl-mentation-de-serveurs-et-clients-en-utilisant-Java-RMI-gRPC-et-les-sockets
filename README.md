Java Services Implementation: gRPC, RMI, and Sockets
This repository contains the implementation of three different Java services using gRPC, RMI, and Sockets. Each service provides specific functionalities for managing tasks, messaging, and participating in chat conversations.

Overview

The project implements the following services:

gRPC Messaging Service: Allows sending messages to specific recipients and retrieving received messages for a given user.
Java RMI Task Management Service: Enables adding new tasks, removing existing tasks, and fetching the complete list of tasks.
Sockets Chat Service: Facilitates sending messages to a common chat room and retrieving messages sent by other users.
How to Use

gRPC Implementation
Setup

Navigate to the grpc directory.
Compile the Java files.
javac example/*.java
Generate gRPC code from the .proto file.
protoc --java_out=./src/main/java/ ./src/main/resources/messaging.proto

Run

Start the gRPC server.
java example.MessagingServer
Run the gRPC client.
java example.MessagingClient

RMI Implementation
Setup

Navigate to the rmi directory.
Compile the Java files.
javac example/*.java
Start the RMI registry.
start rmiregistry

Run

Run the RMI server.
java example.TaskServer
Run the RMI client.
java example.TaskClient

Sockets Implementation
Setup

Navigate to the socket directory.
Compile the Java files.
javac example/*.java

Run

Start the Socket server.
java example.Server
Run the Socket client.
java example.Client

Contributors

Jihene Guesmi
License

This project is licensed under the MIT License.

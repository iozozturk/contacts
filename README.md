# Contacts

Contacts is a seed project written in Java 8 for Play Framework 2.4.3 showcasing AngularJS, MongoDb with Morphia
client, Server Sent Events (SSE), Akka Actors and Akka Scheduler, Action composition
 in Play Framework for elapsed request time calculation and root level Exception handling
 usages on an XML file processing scenario.
 
## Features
- Async File upload at server side (See sample XML file and schema located at "samples" package)
- Validating XML file against defined schema
- Informing client when DB operations complete using SSE
- Saving file to directory defined in conf
- Mapping XML to Java models and saving to MongoDB merging duplicate entities
- Realtime Search at MongoDB on a key
- Updating search results on client utilizing SSE
- Uncaught Exception handling at root level for controllers
- Elapsed request time calculation, logging and putting at response headers

## Requirements
- Min. of Java 8
- MongoDb

## Instructions

- Check that your MongoDb instance is running and make changes on "application.conf" file in the conf package if necessary
- Run "./activator run" in the root directory for Mac, choose activator.bat for Windows
- It may take some time to resolve dependencies for the first run
- Navigate to localhost:9000 in your browser

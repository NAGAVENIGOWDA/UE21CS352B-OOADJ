# File-Sharing-System

## **Team Members:**
| **Name**              | **SRN**       |
| -----------------| -----------------|
| Chandan Kumar S  | PES2UG20CS804    |
| Chinmay Gowda     | PES2UG20CS902  |
| Suraj S Raju           | PES2UG20CS812   |
| Vijay J      | PES2UG20CS815    |

## Problem Statement
Secure File Sharing P2P application. Developed using SpringMVC framework, built using Maven.

 The project is an end to end secure file sharing system which mimics the P2P architecture. Files are shared via a room which is created by the client. The invite link of the sharing platform which in this case is a room is created for the peers who want to access and download files. The link has to be submitted by each peer to join the network to send or receive the files. The files being sent are encrypted with AES (Advanced Encryption Standard) encryption algorithm. On the sender side the file is encrypted using the secret key before sending the file.
      
  The encrypted files are decrypted on the receiving end using the secret key available in the room to validate the file. After the decryption the file is downloaded on the receiving end. In order to account for the file not being corrupted, the concept of checksum is used to verify the integrity of the file. The checksum algorithm used here is SHA-256. The encrypted file here is being sent to the peers in the room using different sockets in the local system presently. All the files being sent/transmitted in the room are stored in the mongodb database for future reference of the ongoing session. Each user is assigned a database to keep track of his files used in the session.
  
### To Run the Project 
 - Git Clone The Repository 
 ```
 git clone https://github.com/yoyozaemon/File-Sharing-System.git 
 ```
 
 - Change the Current Directory
 ```
 cd File-Sharing-System
 ```
 - Install the Maven Package for the Project
 ```
 mvn install
 &
 mvn clean install
 ```
 - To execute the code
 ```
 mvn spring-boot:run
 ```
 - Open the Any Browser and type localhost:{portnumber}
    - For PEER - 1{8001} & PEER - 2{8002}
````
 http://localhost:8001
 &&
 http://localhost:8002
````

> **Note**
>> Connect to MongoDB before running the project. 
>>> Build/Create a database with the names **FileDB1** and **FileDB2**
 
> OOADJ Project 2023

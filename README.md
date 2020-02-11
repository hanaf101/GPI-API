#Green Power Index - Metrics Fetch and Download

Follow below steps to run the application

1. Run `mvn clean install` to build the project
2. Choose the Spring Boot Application file, **IndexApiApplication**
3. Right Click on the file and Run as Java Application, You are all Set, Application will be running at `http://localhost:8080` 


Service exposes one REST API endpoint

  -GET  `/download/{pincode}` :- The response is a CSV file which contains Green Power index matric for the city corresponding to the  pincode for next 48 hours. 

*******************************************************************************
*                                    TASK                                     *
* *****************************************************************************
You got a task to create an internal tool to keep all employee's data of your company. Currently, the list of employees is exported to a CSV file.

As a first story, your target is to allow the HR team to upload this data from a file and display it in the web app.
Use Spring Boot, H2, and React.

Provide git repository with implementation and instructions on how to run the app.This question is required. *

*******************************************************************************
*                               WHAT WAS DONE                                 *
* *****************************************************************************
1. spring - as backend RESt  
   http://localhost:8080/hrapi/persons      - GET  : get list
   http://localhost:8080/hrapi/persons/{id} - GET : get by id 
   http://localhost:8080/hrapi/persons      - POST : add 
   http://localhost:8080/hrapi/persons/{id} - PUT : update
   http://localhost:8080/hrapi/persons/[id] - DELETE : delete

   Supports upload csv file with and without header (column names)
   Supports UTF8 
   H2 database auto created if not exists

2. frontend reactjs, bootstrap, axios CDN to have small application placed all in one jar
   additionaly implemented Add and Delete operations.

*******************************************************************************
*                               INSTRUCTIONS                                  *
* *****************************************************************************
To run aplication:
   
    download https://github.com/VytasGadliauskas/csv_uploader/APPLICATION/hr.jar file
    run from comand line: java -jar hr.jar
    open in browser: http://localhost:8080 



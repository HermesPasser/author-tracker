# author-tracker
comic artist api sample project made in spring boot for a bootcamp

## requeriments
- jdk 11
- maven

other dependencies can be found in pom.xml file 

## running
run the command `mvn spring-boot:run` and interact using the address http://localhost:8080/api/v1/author

## api

sample json (you can see full examples in post-example.md file)  
```json
{
  "id": 0,
  "penName": "",
  "altName": "",
  "profileImgUrl": "",
  "comics": [
    {
      "id": 0,
      "title": "",
      "description": "",
      "genre": "",
      "year": 2000,
      "status": "HIATUS"
    }
  ]
}
```
status values ara HIATUS, COMPLETED, and ONGOING  
do not include the _id_ if you are adding an author.  

*post*: create an author.  
*get /id*: get the author with the given id.  
*delete /id*: removes the author with the given id.  
*get*: get all authors.  
*put /id*: updates the author with the given id.  

## license

under MIT, see LICENSE file

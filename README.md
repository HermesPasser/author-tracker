# author-tracker
comic artist api sample project made in spring boot for a bootcamp

## requeriments
maven dependencies can be found in pom.xml file 
- jdk 11
- maven

## running
run the command `mvn spring-boot:run` and interact using the address http://localhost:8080/api/v1/author

## api

sample json
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
status values ara HIATUS, COMPLETE, and ONGOING  

*create*: create an author.  
*find/id*: get the author with the given id.
*delete/id*: removes the author with the given id.  
*list*: get all authors.  
*update/id*: updates the author with the given id.  

## license

under MIT, see LICENSE file

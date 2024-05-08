# Library API

### Project Technologies:
Java 21, Spring Boot 3.2.5, Spring Cache, Spring Validation, Spring AOP, Spring Data JPA, Swagger, Liquibase, H2 Database, Prometheus

### Run

> 1. Ensure that you have Java 21 installed on your system
> 2. Clone this repository
> 3. Go to project directory
> 4. `./gradlew bootRun`
> 5. Access `http://localhost:8080` 

---
### Swagger Documentation:
`http://localhost:8080/swagger-ui/index.html`

---
### User API:
List all users, get user by id, save, update and delete users 

![user_controller.PNG](resources%2Fuser_controller.PNG)

---
### Book API:

List all books, get book by id, save, update and delete books

![book_controller.PNG](resources%2Fbook_controller.PNG)

---
### Reservation API:

List all reservations, get reservation by id, save, update and delete reservations.
- `/booksAvailable` - Get available books on a period
- `/bookAvailable/{idBook}` - Verify if a book is available on period


- ![reservation_controller.PNG](resources%2Freservation_controller.PNG)

#### Validations:
> - idBook and idUser entered body request exists  
> - startDate and endDate are in the future.  
> - startDate is before endDate  
> - Already have a reservation on the dates entered

![reservation_body_validation.PNG](resources%2Freservation_body_validation.PNG) 
![reservation_conflict_validation.PNG](resources%2Freservation_conflict_validation.PNG)
---
### Review API:
#### Validations:  
> - idBook and idUser entered body request exists  
> - rating between 1 and 10

![review_controller.PNG](resources%2Freview_controller.PNG)

---
## Design choices
### ERD
> - Id's bigint type ensure size is not an issue in the future   
> - Reservation and Review table with FK to guarantee the relationship of the tables. Validation via code and database  
> - Reservation date control, so that there is no more than one reservation of the book in the same period

![Model databases.png](resources%2FModel%20databases.png)

### Spring Cache:
Used to speed up querying lists and items by ID.
When saving, updating or deleting a record, the LIST of this item cache is cleared.
To clean the caches, a scheduler was created that runs according to the cron configuration in the properties. Standard value every day at midnight. Spring Cache doesn't hava a TTL configuration.  

![cache_listAllBooks.PNG](resources%2Fcache_listAllBooks.PNG)

![cacheEvict_config.PNG](resources%2FcacheEvict_config.PNG)

>This solution was used due to its built-in use with spring and the simplicity of the application.
For more robust projects, using an external cache such as Redis is the most recommended.

### Spring AOP:
Used to display the processing time of each service method call.

### Spring Validation:
Used to validate whether the fields in the request body are filled in according to the rules

### H2 Database:
When project first run a new database will be created at "resources/db/database" and Liquibase will populate with basic data.

### Liquibase:
To create new migrations add new changeset to "resources/db/changelog" then add databaseChangeLog to "resources/db/changelog/db.changelog-master.yaml"

---
### Service Actuator:
> Health: `http://localhost:8080/actuator/health`
>
> Cache: `http://localhost:8080/actuator/caches`
>
> Liquibase: `http://localhost:8080/actuator/liquibase`
>
> Prometheus: `http://localhost:8080/actuator/prometheus`
>
> Metrics: `http://localhost:8080/actuator/metrics`

## Tech Service Store App
#### Spring Boot
This application is intended for a technical service to provide computer maintenance services by appointment and also to buy and sell second-hand computer parts. There is no requirement for features like inventory control in the project.

### Requirements
    - Java 21
    - Spring Boot 3.2.1
    - IntelliJ IDEA
---

### Dependencies
    - Spring Web
    - Spring Security
    - Spring Data JDBC
    - Spring Boot DevTools
    - PostgreSQL Driver

## Technical Services and Features

---

####  Login Operations 

* Users should be able to register with a username, email, and password in the system.
* Upon successful login, the system should return a JWT token.
* Services requiring authorization will operate with the JWT token

```
For testing purposes, the system should include the following pre-existing information:
(data.sql file)
2 roles (ADMIN and USER) to be inserted into the system via a script.
2 users (one with an admin role and one with a user role) to be inserted into the system via a script.
```

#### 1. Maintenance Services

The following procedures can be performed on customers' computers at the specified rates. These procedures are priced differently for desktops, laptops, and Mac devices.

|                                | Desktop | Laptop | MAC  | Duration (Hours) |
|:-------------------------------|:-------:|:------:|:----:|:----------------:|
| Formatting                     |   $50   |  $50   | $200 |        2         |
| Virus Cleaning                 |  $100   |  $100  | $100 |        4         |
| Data Recovery from Disk        |  $200   |  $200  | $400 |        10        |
| Fan and Thermal Paste Cleaning |   $30   |  $100  | $200 |        1         |

#### 2. Appointment Services – User Operations

After customers register in the system, they should be able to follow the flow below to schedule an appointment:

* The system must provide the first available day and time for an appointment.
* The customer should specify the desired operation (one of the four mentioned above) and include an optional note (up to 300 characters).
* The service should return the appointment ID upon successful scheduling.
* Appointment details should be retrievable from another endpoint using this ID.
* With the DeleteById operation, a customer should only be able to delete their own appointment.

A maximum of 10 hours of work should be accommodated in a single day. The system should automatically calculate the next appointment to ensure there is no more than 10 hours of work daily. For example, if there is a data recovery task taking 10 hours today, the next appointment should be scheduled for the following day, regardless of the type of service.

#### 3. Appointment Services – Admin Operations

All these services are exclusive to the admin user.

* The admin should be able to sort all appointments by date in ascending or descending order.
* Search functionality should be available to find appointments based on the registered user's name (using a 'like' query).
* When initially recorded, appointments should have a 'Pending' status.
* Using a PUT mapping endpoint, the status of an appointment can be updated to 'In Progress' by providing the appointment ID.
* Using another PUT mapping endpoint, the status of an appointment can be updated to 'Completed' by providing the appointment ID."

#### 4. Second-Hand Sales Operations – Admin Operations

After logging into the system, admin users will be able to record second-hand product sales (Sale table).

* This service will accept parameters for one of four products (CPU, GPU, RAM, Motherboard), along with relevant notes and pricing.
* All recorded sales information can be retrieved by everyone using the 'getall' method.
* Only admins can delete sales information using the 'deletebyid' method.

#### 5. Second-Hand Purchase Operations – User Operations

Users in the system can view second-hand products without logging in.

* With a 'getmapping' operation, users should be able to list CPU, GPU, RAM, and Motherboard sales.
* The response will include product prices, entered notes, and product information.
(Paging can be implemented as an extra feature in case of numerous products, but it is not mandatory.)
* Users should be able to search for items within sold products, such as only sold motherboards, graphics cards, or processors.
* A 'postmapping' operation will facilitate the sale by sending the sale ID and credit card information. Login is required for this process (to be simulated).
* Completed sales will be recorded in the 'sale_log' table in the database, including user ID, sale ID, date, time, and credit card information, to preserve the transaction history.
* After a successful sale, the entry will be directly removed from the 'sale' table.
* Multiple sales of the same product are possible, and for each sale, admins should create a new record.

#### 6. Second-Hand Sales Offer Operations – User Functions

Users can submit offers to the technical service to sell their second-hand products, i.e., for the technical service to purchase second-hand parts (Proposal table).

* If logged in, users can send information about one of the four computer parts (CPU, RAM, GPU, Motherboard), along with notes and the offer price, using a 'post mapping'.
* No appointment is required for the subsequent steps.
* The system should have endpoints for users to list their registered offers and delete them by ID.
* Admins can either reject or approve these offers. If approved, the customer can visit the service whenever convenient to complete the transaction.
* Users can check the status of their approved offers using the 'list my offers' endpoint

#### 7. Second-Hand Purchase Operations – Admin Functions

Admin users in the system will be able to view the offers submitted by customers (as described in the above process).

* In this service, all offers can be listed.
* Using the 'GetByID' method, information such as the part type, offer price, and notes can be retrieved.
* Through a 'postmapping' operation, admins can approve or reject offers by sending the offer ID. The service should return the offer details in response, reflecting the current status.
* This allows customers to see the status of their offers when listed. Offers cannot be deleted by admins.







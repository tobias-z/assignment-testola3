# Test assignment 3

## Usage

Start the database

```bash
docker compose up --build
```

### Running the tests

`The project requires java 13 or later`

```bash
mvn test -Dgroups=unit
mvn test -Dgroups=integration

# or just 'mvn test' for all the tests
```

## Task completion

Migration to add phone-number to user: [Migration script](src/main/resources/db/migration/V5__add_phonenumber_to_customer.sql)

### Booking

- [service-layer](src/main/java/servicelayer/booking)
- [datalayer](src/main/java/datalayer/booking)
- [unit tests](src/test/java/unit/servicelayer/booking)
- [integration of datalayer](src/test/java/integration/datalayer/booking)
- [integration of service-layer](src/test/java/integration/servicelayer/booking)

### Employee

- [service-layer](src/main/java/servicelayer/employee)
- [datalayer](src/main/java/datalayer/employee)
- [unit tests](src/test/java/unit/servicelayer/employee)
- [integration of datalayer](src/test/java/integration/datalayer/employee)

### Integration wrappers

When doing integration testing, I found it useful to create a base integration class which would handle running the migration
scripts as well as setting up all the fakes that may or may not be needed.

- [BookingIntegrationTest](src/test/java/integration/BookingIntegrationTest.java)
- [EmployeeIntegrationTest](src/test/java/integration/EmployeeIntegrationTest.java)
# Test assignment 3

## Usage

Start the database

```bash
docker compose up --build
```

### Running the tests

`The project requires java 13 or later` 

```bash
mvn test # to run all unit tests
mvn test -DtestGroups=integration # to run all integration tests
```
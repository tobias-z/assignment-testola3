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
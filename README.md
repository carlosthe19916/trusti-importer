# code-with-quarkus

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev -Dquarkus.args="-o=http://localhost:8080/advisories/csaf https://access.redhat.com/security/data/csaf/v2/advisories/"
```

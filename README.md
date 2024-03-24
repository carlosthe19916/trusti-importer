# code-with-quarkus

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev -Dquarkus.args="git -o=http://localhost:8080/advisories -wd=src/test/resources https://github.com/carlosthe19916/trusti.git"
```

```shell
./mvnw compile quarkus:dev -Dquarkus.args="http -o=http://localhost:8080/advisories https://access.redhat.com/security/data/csaf/v2/advisories/"
```

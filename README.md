# spring-boot-kafka-apicurio

This projects show example of how to integrate apicurio with kafka 


1. setup kafka and a topic "events"
2. extract server certificate and import it into a java key store as trust store. Configure application.yaml
3. install service registry and import the event avro schema
    ```mvn generate-sources -Pupload```

4. adjust application.yaml to use kafka and service registry endpoints and the create trust store
    ```
    bootstrap-servers: my-cluster-kafka-bootstrap-streams-playground-1.lab.redhat.com:443
    ...
    properties:
        apicurio:
            registry:
                url: example-apicurioregistry-kafkasql.strimzi.lab.redhat.com/apis/registry/v2
    ```
5. generate event class
    ```
    mvn generate-sources -Pavro
    ```
6. build and package:
    ```
    mvn clean package
    ```
7. send a rest request to produce and consume events 
    ```
    curl --location --request POST 'http://localhost:8080/kafka/publish' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "name": "some name",
        "description": "some description"
    }'
    ```

## Maven Profiles for apicurio

### build Event Class from event.avsc
```mvn generate-sources -DskipTests -Pavro```
### upload schema to registry
```mvn generate-sources -Pupload```

### download schema to src/main/
```mvn generate-sources -Pdownload```

## Extract Kafka secret and import it into a java keystore
```
oc extract secret/my-cluster-cluster-ca-cert  -n strimzi --keys=ca.crt --to=- > ca-crt-1.pem

keytool -import -file ca-crt-1.pem -keystore ./truststore-cluster-1.jks -storepass p@ssw0rd
```

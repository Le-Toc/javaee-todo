# Java EE Todo (Payara Micro + H2)

Minimal full‑stack starter: HTML/CSS/JS frontend + Java EE 8 (JAX‑RS + JPA) backend on Payara Micro, with H2 file DB.

## Run
```
mvn clean package
java -jar target/javaee-todo-microbundle.jar --noCluster
```

## Docker
```
docker build -t javaee-todo .
docker run -p 8080:8080 -v $(pwd):/app javaee-todo
```
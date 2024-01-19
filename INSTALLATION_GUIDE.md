# Installation Guide
## Prerequisites
* Docker
* Docker Compose (2.1.1 or higher required)
* Maven
* Java 21

## Installation
Use the following commands to install the project:
```bash
chmod +x build_and_run.sh
./build_and_run.sh
```

If `docker-compose version` returns a version lower than 2.1.1, the following commands can be used instead:

```bash
chmod +x build_and_run-legacy.sh
./build_and_run-legacy.sh
```

## Services
| Service   | Communication Type | Port |
|-----------|--------------------|------|
| account   | AMQP               |      |
| customer  | RESTful HTTP       | 8082 |
| payment   | AMQP               |      |
| token     | AMQP               |      |
| reporting | AMQP               |      |
| manager   | RESTful HTTP       | 8086 |
| merchant  | RESTful HTTP       | 8087 |

## Jenkins Credentials

**URL:** http://fm-08.compute.dtu.dk:8282/  
**Username:** `huba`  
**Password:** `hubert`


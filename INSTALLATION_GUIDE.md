# Installation Guide
## Prerequisites
* Docker
* Docker Compose
* Maven
* Java 21

## Installation
Use the following commands to install the project:
```bash
chmod +x build_and_run.sh
./build_and_run.sh
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


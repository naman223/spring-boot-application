It's simple spring boot application integrated with zipkin service.

mvn clean install

Commands:
curl -H "Content-Type: application/json" -X POST -d '{"id":1,"name":"xyz"}' http://localhost:8082/emp/create  | jq .

curl  http://localhost:8082/emp/1  | jq .

curl  http://localhost:8082/emps | jq .

curl -X DELETE http://localhost:8082/emp/1

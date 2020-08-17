# OAuth2-Sample

## ENG
A sample for authorization is taken from [medium](https://medium.com/@archnaaju/oauth2-and-spring-boot-2-a-complete-guide-to-setup-a-separate-authorization-server-resource-4d760cc0b1e1)

Installation instructions:
1) Load database structure from [sql file](resource-server/sql/customers-db.sql)
2) Set the following parameters for connection in [the configuration file](resource-server/src/main/resources/application.properties)
    + `spring.datasource.url`
    + `spring.datasource.username`
    + `spring.datasource.password`
3) Run the ["stub" module](auth-server) for authentication if you plan to use requests with limited access
4) Run [main module](resource-server) for provide resources
5) Use [requests for testing](resource-server/sampletest/ResourceServer.postman_collection.json) in [Postman program](https://www.postman.com/) format

## RUS
Образец для авторизации взят с [medium](https://medium.com/@archnaaju/oauth2-and-spring-boot-2-a-complete-guide-to-setup-a-separate-authorization-server-resource-4d760cc0b1e1)

Инструкция по установке:
1) Загрузить структуру базы данных из [sql файла](resource-server/sql/customers-db.sql)
2) Задать в [файле конфигурации](resource-server/src/main/resources/application.properties) следующие параметры для подключения
    + `spring.datasource.url`
    + `spring.datasource.username`
    + `spring.datasource.password`
3) Запустить [модуль "заглушку" для аутентификации](auth-server) если планируется использовать запросы с ограниченным доступом
4) Запустить [основной модуль](resource-server) для предоставления ресурсов
5) Использовать [запросы для тестирования в](resource-server/sampletest/ResourceServer.postman_collection.json) формате программы [Postman](https://www.postman.com/)



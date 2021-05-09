# family-auth
Authentication service for Family-app

Для запуска сервиса аутентификации требуется:
  1) Запустить бд Postgres на порту 5432
  2) Установить Maven, Java 14 
  3) Выполнить mvn clean install -DskipTests
  4) Запустить сервис
P.S. Стабильно интернет подклбчение требуется для регистрации в сервисе Kafka и дальшеней работы с ним.\ 


Сервис предоставляет услуги регистрации и авторизации пользователей в системе. Посмотреть доступные методы перейдя по ссылке <host>/swagger-ui.html/#
Методы можно исключительно просматривать, т.к. для доступа к сервису требуется специальный авторизационный хедер, недоступный в сваггере. Хедеры автоматически в запрос в сервисе gateway. По сути gateway является основной и единственной точкой доступа к сервису. Т.Е. для регистрации нового пользователя необходимо запустить gateway сервис и отправить запрос: <gateway.host>/auth/register.\
  
  
 Флоу регистрации: 
 POST <gateway.host>/auth/register
 \{
  "email": "boris200898@icloud.com",\
  "firstName": "boris",\
  "lastName": "kezikov",\
  "password": "12345",\
  "phone": "79308196302"\
}
заходим на почту - получаем код. Код необходимо ввести в течение 15 минут, иначе он будет удален. \
POST <gateway.host>/auth/verify2fa\
{
  "email": "boris200898@icloud.com",\
  "twoFaCode": "code"\
}
Аккаунт переходит в активное состояние.\

POST <gateway.host>/authenticate \
{
  "username": "boris200898@icloud.com",\
  "password": "12345"\
}
Возвращается JWT токен.  \

 
  
https://family-gateway.herokuapp.com/auth/register - один из инстансов сервиса крутится на нашем сервере heroku. 

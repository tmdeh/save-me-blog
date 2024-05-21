## application.yml
```
spring:
  jpa:
    show-sql: true
    properties:
      format_sql: true
      dialect: org.hibernate.dialect.MySQL8Dialect
    hibernate:
      ddl-auto: update
  datasource:
    url: jdbc:mysql://localhost:3306/blog?useSSL=false&useUnicode=true&allowPublicKeyRetrieval=true
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root
    password: 1234

jwt:
  secret: savemeyuseungdospringbootblogservice

file:
  path: posts
```
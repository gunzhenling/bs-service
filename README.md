# 服务

## 前置依赖
- MySQL 数据库 


## Quick start
### 获取源码并打包
```
git clone git@github.com:gunzhenling/bs-service.git
cd zc-service-pay

mvn clean compile package -DskipTests

# ls target/bc-service.jar
```


### 运行
```
cd target

# 以 dev 配置启动 (默认)
java -jar bc-service.jar --spring.profiles.active=dev

# 以 test 配置启动
java -jar bc-service.jar --spring.profiles.active=test

# 以 prod 配置启动
java -jar bc-service.jar --spring.profiles.active=prod
```



## 应用监测
```
curl http://localhost:8080/api/actuator/health
```
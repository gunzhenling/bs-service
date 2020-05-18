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
管理后台项目
  启动端口 5000 浏览器访问  http://localhost:5000
    cd frontend
    npm install (第一次启动)
    npm run dev
  启动端口 9998 图片服务端口 http://localhost:9998 获取图片
    node z-image.js

小程序请使用工具，导入wxapp项目


cd target

# 以 dev 配置启动 (默认)
java -jar bc-service.jar --spring.profiles.active=dev

java -jar D:\bc-service.jar --spring.profiles.active=szdev


# 以 test 配置启动
java -jar bc-service.jar --spring.profiles.active=test

# 以 prod 配置启动
java -jar bc-service.jar --spring.profiles.active=prod
```



## 应用监测
```
curl http://localhost:8080/api/actuator/health
```

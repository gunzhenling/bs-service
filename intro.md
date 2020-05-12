筑巢支付中心
=================================================
以智蜂项目的网络支付需求为基础, 提取常用的支付操作并加以封装.
对外, 适配常用的第三方支付平台.
对内, 提供统一简洁的访问接口.
避免重复劳动, 提高重用性

```
 +--------------+   +---------------+   +--------------+                               
 |  Alipay API  |   |  Weixin API   |   |  Unionpay API|  对外, 适配常用的第三方支付平台.  
 +--+-----------+   +-----+---------+   +-------+------+                              
    ^                     ^                     ^                                     
    |                     |                     |                                     
 +--+---------------------+---------------------+------+                            
 |                     zc-service-pay                  |                            
 |                     /refund                         |                            
 +--+---------------------+---------------------+------+                            
    ^                     ^                     ^                                     
    |                     |                     |         对内, 提供统一且简洁的访问接口.  
    |                     |                     |                                     
    |                     |                     |                                     
pay_type:alipay         pay_type:wx             pay_type:unionpay                      
refund_no:{refund_no}   refund_no:{refund_no}   refund_no:{refund_no}                 
order_no:{order_no}     order_no:{order_no}     order_no:{order_no}                   
```

## 工程启动
```
mvn spring-boot:run
```

## Rest 接口文档
http://localhost:8080/api/swagger-ui.html

## 其他文档
http://doc.smartfeng.com/docs/base

# 设计
## 内部技术
- Spring Boot
- Spring MVC
- MyBatis 数据访问
- MySQL 数据库
- Kafka 消息队列
- JWT 认证
- Swagger 接口管理
- Jackson

## 支付交易设计
交易接口: PaymentService, 提取常用的支付操作, 并在此接口定义操作方法, 比如:
- pay
- payQuery
- refund
- refundQuery
- payNotify

当前已有交易实现:
- AlipayServiceImpl 支付宝
- WxServiceImpl 微信

交易请求分发
- 应用启动, 从 ApplicationContext 获取所有 PaymentService 实现类的 Bean
- 根据 Bean 的 @PayChannel 注解, 确定其绑定的 payType
- 接收到请求, 根据请求参数 payType 将请求分发到对应的实现中

扩展新的支付类型, 仅需实现 PaymentService 并指定 PayChannel 的 payType, 注册为 Spring Bean.
*对账分发同理*


## 支付宝支付下单
### 扫码支付: alipay_qr
```
qrCode: https://qr.alipay.com/bax070917a8u34iydqdc0076
```
根据返回数据的 qrCode 字段, 将链接生成二维码, 供用户扫描支付.


### app支付: alipay_app
```
detail: "alipay_sdk=alipay-sdk-java-3.3.49.ALL&app_id=2016052401436400&biz_content=%7B%22out_trade_no%22%3A%22fanhangtest-124%22%2C%22product_code%22%3A%22INTBEE_APP_PAY%22%2C%22seller_id%22%3A%222088221861486600%22%2C%22subject%22%3A%22fanhang-notify%22%2C%22total_amount%22%3A%220.50%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F116.85.33.137%3A21999%2Fapi%2Ftrade%2Fpayment%2Falipay%2F101%2FpayNotify&sign=IWLvM4qkLot7NWxumfXhl8P%2FioKEgrAEcZ7tzIF6BUvLuixOJGcispJiD9%2F2rdTGf5qnOeNl87KVyqP0m1MfvVoR%2BMidmKm1DNwIprT1LuShnG3YXX0EIwgOw2n7vycqt%2Fi0JEVmY8ekaTm5FQzWGxDQeVSJsZ%2BSJMKH%2F7j0xc0%3D&sign_type=RSA&timestamp=2018-11-06+17%3A43%3A15&version=1.0"
```
detail 字符串包含订单相关信息以及签名数据, 根据此字符串, 使用sdk 唤起支付宝app
sdk 唤起支付宝: https://docs.open.alipay.com/204/105296/



### 手机网页支付: alipay_wap
```
<form name=\"punchout_form\" method=\"post\" action=\"https://openapi.alipay.com/gateway.do?charset=UTF-8&method=alipay.trade.wap.pay&sign=BzejHJybcX5MvMbXKUtKs+eifumtisB7Oc7kLfgDux+KlxqMN6mDmw1fabtnMFOxhdArCXibSnkVzVzXXCRlHulrEs0XC9Z8zq1kVK4K6d04tzdj9uw82Q4TMaasSjveEhz4W1rn+AVSe0dCOyh5AvnGarQcZtyyHxprfexgwS8=&return_url=http://localhost:9999/api/trade/payment/returnUrl&notify_url=http://116.85.33.137:21999/api/trade/payment/alipay/101/payNotify&version=1.0&app_id=2016052401436400&sign_type=RSA&timestamp=2018-11-07+09:56:22&alipay_sdk=alipay-sdk-java-3.3.49.ALL&format=json\">\n<input type=\"hidden\" name=\"biz_content\" value=\"{&quot;out_trade_no&quot;:&quot;fanhangtest-128&quot;,&quot;product_code&quot;:&quot;INTBEE_WAP_PAY&quot;,&quot;seller_id&quot;:&quot;2088221861486600&quot;,&quot;subject&quot;:&quot;fanhang-notify&quot;,&quot;total_amount&quot;:&quot;0.50&quot;}\">\n<input type=\"submit\" value=\"立即支付\" style=\"display:none\" >\n</form>\n<script>document.forms[0].submit();</script>
```
获取返回数据 detail 内容, 即为此表单, 提交跳转到"支付宝收银台".
待用户支付完毕, 支付宝根据 return_url 跳回.


## 微信支付下单
### 扫码支付: wx_qr
```
qrCode: weixin://wxpay/bizpayurl?pr=hyl0BSO
```
根据此链接生成二维码, 供用户扫描支付.


### app支付: wx_app
```
credential: {
    "appId": "wxf9206620f5f39aad",
    "noncestr": "V5NWMa7elH81x0r9p1SIkyvlcxDSpc3A",
    "packageValue": "Sign=WXPay",
    "partnerId": "1426644302",
    "prepayId": "wx07111838555458c72e45e56b2725406987",
    "sign": "2512ED82C7BCC6D5F078180D98CE4C2496EE97716C30C834654DBCF72071E398",
    "timestamp": "1541560718"
}
```
微信 sdk 唤起微信支付需要 credential 内的数据.
sdk 唤起微信支付: https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=8_5


### 手机网页支付: wx_wap
```
{
    "qrCode": null,
    "webUrl": "https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id=wx0710301796360734c46a28221665958085&package=394428219",
    "prepayId": "wx0710301796360734c46a28221665958085",
    "tradeNo": null,
    "credential": null,
    "signType": "HMAC-SHA256",
    "detail": {}
}
```
取得 webUrl 并重定, 用户到微信端支付


### 小程序支付: wx_lite
```
credential: {
    "appId": "wxf9206620f5f39aad",
    "noncestr": "V5NWMa7elH81x0r9p1SIkyvlcxDSpc3A",
    "packageValue": "Sign=WXPay",
    "partnerId": "1426644302",
    "prepayId": "wx07111838555458c72e45e56b2725406987",
    "sign": "2512ED82C7BCC6D5F078180D98CE4C2496EE97716C30C834654DBCF72071E398",
    "timestamp": "1541560718"
}
```
类似于 app 支付, 根据 credential 数据调用微信进行支付.
小程序微信支付: https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=7_7&index=5


### 公众号支付: wx_pub
TODO 


## 配置说明
```
payment:
  domain: http://116.85.33.137:21999         # 可外网访问的当前域名
  bill-download-folder: ./bill               # 对账单下载存放目录
  app:
    101:                                     # appId
      alipay:                                # 支付宝
        remark: 智蜂-支付宝
        app-id: 2016052401436400
        seller-id: 2088221861486600
        private-key: {private-key}
        public-key:  {public-key}
      wx:                                    # 微信
        remark: 智蜂-微信
        key: d195f111fd3bf436951b90973f0ccac8
        appid: wxf9206620f5f39aad
        mchid: 1426644302
        cert-path: classpath:cert/wx/apiclient_cert-101-app.p12
    501:
      wx-lite:
        remark: 小程序-微信
        key: d195f111fd3bf436951b90973f0ccac8
        appid: wx1e86ddc5b2485ab2
        mchid: 1429146602
        cert-path: classpath:cert/wx/apiclient_cert-501-lite.p12
```

## 关于回调
系统自动识别当前的回调处理接口:
```
{domain}/{context-path}/trade/payment/alipay/{appId}/payNotify

http://116.85.33.137:21999/api/trade/payment/alipay/101/payNotify
```
统一接收回调数据, 回调处理无误之后, 使用 kafka 广播通知业务系统.


## 对账
对账数据结构概览:
```
{
    "code": 0,
    "message": null,
    "result": {
        "payType": "alipay",
        "date": 1541347200000,
        "billCount": {                      # 对账单汇总记录
            "门店编号": "合计",
            "交易订单总笔数": "40",
            "退款订单总笔数": "9",
            "订单金额（元）": 3514.60,
            "商家实收（元）": 3514.60,
            "服务费（元）": -24.00,
            "实收净额（元）": 3490.60
        },
        "pays": [                                                  # 支付对比列表
            {
                "bill": {},                                        # 账单支付记录
                "request": {},                                     # 支付请求(支付中心)
                "orderNo": "fanhangtest-121",
                "tradeTime": 1541413470000,
                "billAmount": 20,                                  # 账单实收金额
                "serviceCharge": 0,                                # 账单手续费
                "income": 20,                                      # 实际到账收入(账单实收金额 - 账单手续费)
                "requestAmount": 20                                # 请求支付金额(支付中心)
            }
        ],
        "refunds": [                                                     # 退款对比列表
            {
                "tradeNo": "2018110522001411951013704743",
                "refundNo": "fanhangtest-120-r",
                "bill": {},                                              # 账单退款记录
                "request": {},                                           # 退款请求记录
                "billAmount": 20,                                        # 账单退款金额
                "serviceCharge": 0,                                      # 账单退款手续费
                "requestAmount": 20                                      # 请求退款金额(支付中心)
            }
        ],
        "payAmountTotal": 437459,           # 支付总额(账单)
        "payServiceChargeTotal": 2400,      # 支付手续费总额(账单)
        "refundAmountTotal": 85999,         # 退款总额(账单)
        "refundServiceChargeTotal": 0,      # 退款手续费总额(账单)
        "paysCount": 40,                    # 支付单数(账单)
        "refundsCount": 9,                  # 退款单数(账单)
        "incomeTotal": 349060               # 收入总计 = 支付总额 - 支付手续费总额 - 退款总额 - 退款手续费总额
    }
}
```

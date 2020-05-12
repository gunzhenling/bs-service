-- 支付请求记录
CREATE TABLE `pay_center3_pay_request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(20) NOT NULL,
  `pay_type` varchar(20) NOT NULL COMMENT '支付类别',
  `channel` varchar(20) NOT NULL COMMENT '支付渠道',
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `amount` int(11) NOT NULL COMMENT '金额',
  `subject` varchar(150) NOT NULL COMMENT '订单主题说明',
  `sign_type` varchar(20) NOT NULL COMMENT '签名加密类别',
  `client_ip` varchar(30) DEFAULT NULL COMMENT '客户端IP',
  `extra` varchar(500) DEFAULT NULL COMMENT '扩展数据',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `response` json DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_pay_type_order_no` (`pay_type`,`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- 退款请求记录
CREATE TABLE `pay_center3_refund_request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(20) NOT NULL,
  `pay_type` varchar(20) NOT NULL COMMENT '支付类别',
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `refund_no` varchar(50) NOT NULL COMMENT '退款单号',
  `total_amount` int(11) NOT NULL COMMENT '总金额',
  `refund_amount` int(11) NOT NULL COMMENT '退款金额',
  `client_ip` varchar(30) DEFAULT NULL COMMENT '客户端IP',
  `extra` varchar(500) DEFAULT NULL COMMENT '扩展数据',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `response` json DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_pay_type_refund_no` (`pay_type`,`refund_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 转账请求记录
CREATE TABLE `pay_center3_transfer_request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(20) NOT NULL,
  `pay_type` varchar(20) NOT NULL COMMENT '支付类别',
  `transfer_no` varchar(50) NOT NULL COMMENT '转账编号',
  `account` varchar(50) NOT NULL COMMENT '收款账户',
  `amount` int(11) NOT NULL COMMENT '金额',
  `real_name` int(11) NOT NULL COMMENT '收款人真实姓名',
  `client_ip` varchar(30) DEFAULT NULL COMMENT '客户端IP',
  `extra` varchar(500) DEFAULT NULL COMMENT '扩展数据',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_pay_type_transfer_no` (`pay_type`,`transfer_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 支付回调
CREATE TABLE `pay_center3_pay_notify` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `app_id` varchar(20) NOT NULL,
  `pay_type` varchar(20) NOT NULL COMMENT '支付类别',
  `trade_no` varchar(64) NOT NULL COMMENT '交易号',
  `fee_type` varchar(10) DEFAULT NULL COMMENT '货币类型',
  `cash_amount` int(11) NOT NULL COMMENT '收款金额',
  `total_amount` int(11) NOT NULL COMMENT '总金额',
  `extra` varchar(500) DEFAULT NULL COMMENT '扩展数据',
  `trade_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `details` json NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_pay_type_trade_no` (`pay_type`,`trade_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 退款回调
CREATE TABLE `pay_center3_refund_notify` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(20) NOT NULL,
  `pay_type` varchar(20) NOT NULL COMMENT '支付类别',
  `refund_no` varchar(50) NOT NULL COMMENT '退款单号',
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `trade_no` varchar(64) NOT NULL COMMENT '交易号',
  `refund_amount` int(11) NOT NULL COMMENT '退款金额',
  `total_amount` int(11) NOT NULL COMMENT '总金额',
  `refund_status` varchar(20) NOT NULL COMMENT '退款状态',
  `extra` varchar(500) DEFAULT NULL COMMENT '扩展数据',
  `trade_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `details` json NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_pay_type_refund_no` (`pay_type`,`refund_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- 退款查询结果
CREATE TABLE `pay_center3_refund_query_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(20) NOT NULL,
  `pay_type` varchar(20) NOT NULL COMMENT '支付类别',
  `refund_no` varchar(50) NOT NULL COMMENT '退款单号',
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `trade_no` varchar(64) NOT NULL COMMENT '交易号',
  `refund_amount` int(11) NOT NULL COMMENT '退款金额',
  `total_amount` int(11) NOT NULL COMMENT '总金额',
  `extra` varchar(500) DEFAULT NULL COMMENT '扩展数据',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `details` json NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_pay_type_refund_no` (`pay_type`,`refund_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 支付查询结果
CREATE TABLE `pay_center3_pay_query_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(20) NOT NULL,
  `pay_type` varchar(20) NOT NULL COMMENT '支付类别',
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `trade_no` varchar(64) NOT NULL COMMENT '交易号',
  `fee_type` varchar(10) DEFAULT NULL COMMENT '货币类型',
  `cash_amount` int(11) NOT NULL COMMENT '收款金额',
  `total_amount` int(11) NOT NULL COMMENT '总金额',
  `extra` varchar(500) DEFAULT NULL COMMENT '扩展数据',
  `trade_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `details` json NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_pay_type_order_no` (`pay_type`,`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;






-- 账单解析汇总数据
CREATE TABLE `pay_center3_bill_count_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(20) COLLATE utf8_bin NOT NULL,
  `pay_type` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '支付类别',
  `bill_date` varchar(8) COLLATE utf8_bin NOT NULL COMMENT '账单日期',
  `pay_count` int(11) NOT NULL COMMENT '支付笔数',
  `refund_count` int(11) NOT NULL COMMENT '退款笔数',
  `pay_total_amount` int(11) NOT NULL COMMENT '支付累计金额',
  `refund_total_amount` int(11) NOT NULL COMMENT '退款累计金额',
  `service_charge` int(11) NOT NULL COMMENT '服务费',
  `income` int(11) NOT NULL COMMENT '最终收入',
  `details` json NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_app_id_pay_type_bill_date` (`app_id`,`pay_type`,`bill_date`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



-- 账单解析日志
CREATE TABLE `pay_center3_bill_parse_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(20) NOT NULL,
  `pay_type` varchar(20) NOT NULL COMMENT '支付类别',
  `bill_date` varchar(8) NOT NULL COMMENT '账单日期',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_app_id_pay_type_bill_date` (`app_id`,`pay_type`,`bill_date`)
) ENGINE=InnoDB AUTO_INCREMENT=161 DEFAULT CHARSET=utf8;



-- 账单解析支付记录
CREATE TABLE `pay_center3_bill_pay_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(20) COLLATE utf8_bin NOT NULL,
  `pay_type` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '支付类别',
  `bill_date` varchar(8) COLLATE utf8_bin NOT NULL COMMENT '账单日期',
  `order_no` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '订单号',
  `trade_no` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '交易号',
  `pay_amount` int(11) NOT NULL COMMENT '支付金额',
  `service_charge` int(11) NOT NULL COMMENT '服务费',
  `trade_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '交易时间',
  `details` json NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_trade_no` (`trade_no`),
  UNIQUE KEY `idx_pay_type_order_no` (`pay_type`,`order_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1696 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



-- 账单解析退款记录
CREATE TABLE `pay_center3_bill_refund_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(20) COLLATE utf8_bin NOT NULL,
  `pay_type` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '支付类别',
  `bill_date` varchar(8) COLLATE utf8_bin NOT NULL COMMENT '账单日期',
  `order_no` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '订单号',
  `refund_no` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '退款单号',
  `trade_no` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '交易号',
  `refund_amount` int(11) NOT NULL COMMENT '退款金额',
  `service_charge` int(11) NOT NULL COMMENT '服务费',
  `trade_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '交易时间',
  `details` json NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_pay_type_refund_no` (`pay_type`,`refund_no`)
) ENGINE=InnoDB AUTO_INCREMENT=213 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



-- 账单比对批次
CREATE TABLE `pay_center3_bill_compare_batch` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8_bin NOT NULL,
  `pay_type` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '支付类别',
  `bill_date` varchar(8) COLLATE utf8_bin NOT NULL COMMENT '账单日期',
  `mistake_count` int(11) NOT NULL COMMENT '账目出错数',
  `unhandle_mistake_count` int(11) NOT NULL COMMENT '待处理账目出错数',
  `bill_pay_count` int(11) NOT NULL COMMENT '账单支付数',
  `request_pay_count` int(11) NOT NULL COMMENT '请求支付数目',
  `bill_refund_count` int(11) NOT NULL COMMENT '账单退款数',
  `request_refund_count` int(11) NOT NULL COMMENT '请求退款数目',
  `bill_pay_amount` int(11) NOT NULL COMMENT '账单支付金额',
  `request_pay_amount` int(11) NOT NULL COMMENT '请求支付金额',
  `bill_refund_amount` int(11) NOT NULL COMMENT '账单退款金额',
  `request_refund_amount` int(11) NOT NULL COMMENT '请求退款金额',
  `pay_service_charge` int(11) NOT NULL COMMENT '支付服务费',
  `refund_service_charge` int(11) NOT NULL COMMENT '退款服务费',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



-- 账单比对批次明细
CREATE TABLE `pay_center3_bill_compare_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `batch_id` int(11) NOT NULL,
  `pay_type` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '支付类别',
  `operate_type` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '支付退款类型',
  `order_no` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '订单号',
  `refund_no` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '退款单号',
  `bill_amount` int(11) NOT NULL COMMENT '账单金额',
  `request_amount` int(11) DEFAULT NULL COMMENT '请求金额',
  `trade_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '支付交易时间',
  `compare_status` int(5) NOT NULL COMMENT '比对状态',
  `compare_desc` varchar(500) COLLATE utf8_bin NOT NULL COMMENT '比对结果描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_batch_id_pay_type_order_no_refund_no` (`batch_id`,`pay_type`,`order_no`,`refund_no`)
) ENGINE=InnoDB AUTO_INCREMENT=608 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



create table rates (xid BIGINT AUTO_INCREMENT PRIMARY KEY, currency_pair CHAR(6), rate DECIMAL(8,4), effective_date DATE, timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP) ENGINE=INNODB;

create table requests (rid BIGINT AUTO_INCREMENT PRIMARY KEY, type INT, currency1 CHAR(3), currency2 CHAR(3), amount DECIMAL(16,4), timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP) ENGINE=INNODB;

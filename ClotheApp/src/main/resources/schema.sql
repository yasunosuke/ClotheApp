CREATE TABLE IF NOT EXISTS categories(
category_id CHAR(2) PRIMARY KEY,
category_name CHAR(30)
);

CREATE TABLE IF NOT EXISTS clothes(
clothe_image BINARY
,clothe_id CHAR(50) PRIMARY KEY
,clothe_name CHAR(50)
,category_id CHAR(2)
,storage CHAR(30)
,registered_date DATE
,FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

/*CREATE TABLE IF NOT EXISTS users(
user_id VARCHAR(50)
,user_name VARCHAR(50)
);*/
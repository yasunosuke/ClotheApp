DROP TABLE IF EXISTS clothes;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS storages;

CREATE TABLE IF NOT EXISTS categories(
category_id CHAR(2) PRIMARY KEY,
category_name CHAR(30) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS storages(
storage_code CHAR(2) PRIMARY KEY,
storage_name VARCHAR(50) NOT NULL UNIQUE);

CREATE TABLE IF NOT EXISTS clothes(
clothe_id CHAR(50) PRIMARY KEY
,clothe_image MEDIUMBLOB
,clothe_name VARCHAR(50) NOT NULL
,category_id CHAR(2) NOT NULL
,storage_code CHAR(2) NOT NULL
,registered_date DATE NOT NULL
,FOREIGN KEY (category_id) REFERENCES categories(category_id)
,FOREIGN KEY (storage_code) REFERENCES storages(storage_code)
);
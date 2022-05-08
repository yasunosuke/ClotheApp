DROP TABLE IF EXISTS clothes;
DROP TABLE IF EXISTS categories;

CREATE TABLE IF NOT EXISTS categories(
category_id CHAR(2) PRIMARY KEY,
category_name CHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS clothes(
clothe_image MEDIUMBLOB
,clothe_id CHAR(50) PRIMARY KEY
,clothe_name CHAR(50) NOT NULL
,category_id CHAR(2) NOT NULL
,storage CHAR(30) NOT NULL
,registered_date DATE NOT NULL
,FOREIGN KEY (category_id) REFERENCES categories(category_id)
);
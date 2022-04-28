INSERT INTO categories(category_id, category_name) VALUES('1', 'pants'),('2','t-shirts');

INSERT INTO clothes(
clothe_id,
clothe_name,
category_id,
storage,
registered_date
)VALUES
('1', 'Nike Pants', '1', 'closet', CURRENT_DATE()),
('2','Adidas T-shirts', '2', 'drawer', '2000-01-01');


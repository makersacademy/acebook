create table posts(
 post_id VARCHAR,
 content VARCHAR
);

--In db acebook,table Posts:
ALTER TABLE posts ADD COLUMN time timestamp;




--Create new user table in acebook db:
CREATE TABLE person(user_id SERIAL PRIMARY KEY, user_name VARCHAR(60), password VARCHAR);

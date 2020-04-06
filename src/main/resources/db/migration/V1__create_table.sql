create table posts(
 post_id VARCHAR,
 title VARCHAR,
 content VARCHAR
)

--In db acebook,table Posts:
ALTER TABLE posts ADD COLUMN date VARCHAR;
ALTER TABLE posts ADD COLUMN time timestamp;

--In db acebook-test,table posts:
ALTER TABLE posts ADD COLUMN date VARCHAR;
ALTER TABLE posts ADD COLUMN time timestamp;



--Create new user table in acebook db:
CREATE TABLE person(user_id SERIAL PRIMARY KEY, user_name VARCHAR(60), password VARCHAR);

--Create new user table in acebook-test db:
CREATE TABLE person(user_id SERIAL PRIMARY KEY, user_name VARCHAR(60), password VARCHAR);
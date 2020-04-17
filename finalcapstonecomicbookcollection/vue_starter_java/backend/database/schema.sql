BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id serial PRIMARY KEY,
  username varchar(255) NOT NULL UNIQUE,     -- Username
  email varchar(64),                   --E-mail address
  password varchar(32) NOT NULL,      -- Password
  salt varchar(256) NOT NULL,          -- Password Salt
  role varchar(255) NOT NULL default('user')
);

CREATE TABLE comic (

        comic_id serial PRIMARY KEY,
        title VARCHAR(64) NOT NULL,
        issue_title VARCHAR(128),
        issue_number INT NOT NULL,
        publisher VARCHAR(64) NOT NULL,
        publish_date DATE NOT NULL,
        hero VARCHAR(64),
        description VARCHAR(1000),
        image_link VARCHAR(255)

);

CREATE TABLE collection (
        
        collection_id serial PRIMARY KEY,
        user_id int,
        name VARCHAR (64) NOT NULL,
        description VARCHAR (64) NOT NULL,
        private boolean not null,
        
        CONSTRAINT fk_uc_user FOREIGN KEY (user_id) REFERENCES users (id)
            
);


CREATE TABLE user_comic (
        

        comic_id serial NOT NULL,
        user_id serial NOT NULL,
        collection_id serial NOT NULL,
        
        CONSTRAINT fk_uc_comic FOREIGN KEY (comic_id) REFERENCES comic (comic_id),
        CONSTRAINT fk_uc_user FOREIGN KEY (user_id) REFERENCES users (id),
        CONSTRAINT fk_uc_collection FOREIGN KEY (collection_id) REFERENCES collection (collection_id)
        
);


COMMIT TRANSACTION;
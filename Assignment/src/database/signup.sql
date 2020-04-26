BEGIN TRANSACTION;

DROP TABLE IF EXISTS registration;

CREATE TABLE registration (
  id serial PRIMARY KEY,
  email varchar(64) NOT NULL,
  hexa_id varchar (99) NOT NULL, 
  firstname varchar (64) NOT NULL,
  lastname varchar (64),
  phonenumber varchar (20)
               

);

COMMIT TRANSACTION;
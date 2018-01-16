DROP TABLE IF EXISTS spittle;
DROP TABLE IF EXISTS spitter;

create table spitter (
  id INT PRIMARY KEY,
  username VARCHAR(25) NOT NULL,
  password VARCHAR(25) NOT NULL,
  fullname VARCHAR(100) NOT NULL,
  email VARCHAR(50) NOT NULL,
  update_by_email BOOLEAN NOT NULL
);

create table spittle (
  id INT PRIMARY KEY,
  spitter_id INT NOT NULL,
  spittle_text VARCHAR(2000) NOT NULL,
  posted_time DATE NOT NULL,
  FOREIGN KEY (spitter_id) REFERENCES spitter(id)
);

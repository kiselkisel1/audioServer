create table artist (
  id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(200) NOT NULL,
  notes VARCHAR(2000),
  start_activity_year  INT NOT NULL ,
  end_activity_year  INT
);

 create TABLE genre (
  id   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL
);

 create TABLE artist_genre (
  artist_id INT NOT NULL,
  genre_id INT NOT NULL,

  FOREIGN KEY (artist_id) REFERENCES artist (id),
  FOREIGN KEY (genre_id) REFERENCES genre (id),

  UNIQUE (artist_id, genre_id)
);


 create table album (
  id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(200) NOT NULL,
  year  INT NOT NULL ,
  notes VARCHAR(2000)
  );



 create TABLE album_artist (
  album_id INT NOT NULL,
  artist_id INT NOT NULL,

 FOREIGN KEY (album_id) REFERENCES album (id),
  FOREIGN KEY (artist_id) REFERENCES artist (id),

  UNIQUE (album_id,artist_id)
);

insert into artist values (1,'artist1','note1',2000,2010);
insert into artist values (2,'artist2','note2',1980,1985);
insert into artist values (3,'artist3','note3',2011,2019);

insert into genre values (1, 'genre1');
insert into genre values (2, 'genre2');
insert into genre values (3, 'genre3');
insert into genre values (4, 'genre4');

insert into artist_genre values (2, 3);

insert into album values (1,'album1',2010,'album2');

select * from artist_genre ;



select * from genre ;

insert into album_artist values (1, 3);


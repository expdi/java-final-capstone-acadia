-- delete from artist_track;
-- delete from artist;
-- alter sequence artist_id_seq restart;
-- delete from track;
-- alter sequence track_id_seq restart;


insert into artist (name) values  ('Taylor Swift H2');
insert into artist (name) values  ('Madonna H2');
insert into artist (name) values  ('Lady Gaga H2');
insert into artist (name) values  ('Drake H2');
insert into artist (name) values  ('Nicki Minaj H2');
insert into artist (name) values  ('Britney Spears H2');
insert into artist (name) values  ('Demi Lovato H2');
insert into artist (name) values  ('Luis Miguel H2');
insert into artist (name) values  ('Natalia Lafourcade H2');
insert into artist (name) values  ('Marco Antonio Solís H2');

insert into track (duration,  issue_date, media_type, album, price, title)
values  (3,'2024-06-03', 1, '1986', '3.50', 'Cruel Summer');
insert into track (duration,  issue_date, media_type, album, price, title)
values  (4, '2024-06-01', 1, '2023', '2.10', 'Karma es un gato');

insert into artist_track (artist_id, track_id)
values  (1, 1);
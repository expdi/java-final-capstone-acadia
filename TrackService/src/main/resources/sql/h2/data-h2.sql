delete from artist_track;
delete from artist;
alter sequence artist_id_seq restart;
delete from track;
alter sequence track_id_seq restart;


insert into public.artist (id, name) values  (1, 'Taylor Swift H2');
insert into public.artist (id, name) values  (2, 'Madonna H2');
insert into public.artist (id, name) values  (3, 'Lady Gaga H2');
insert into public.artist (id, name) values  (4, 'Drake H2');
insert into public.artist (id, name) values  (5, 'Nicki Minaj H2');
insert into public.artist (id, name) values  (6, 'Britney Spears H2');
insert into public.artist (id, name) values  (7, 'Demi Lovato H2');
insert into public.artist (id, name) values  (8, 'Luis Miguel H2');
insert into public.artist (id, name) values  (9, 'Natalia Lafourcade H2');
insert into public.artist (id, name) values  (10, 'Marco Antonio Solís H2');

insert into public.track (duration, id, issue_date, media_type, album, price, title)
values  (3, 1, '2024-06-03', 1, '1986', '3.50', 'Cruel Summer');
insert into public.track (duration, id, issue_date, media_type, album, price, title)
values  (4:10, 2, '2024-06-01', 1, '2023', '2.10', 'Karma es un gato');

insert into public.artist_track (artist_id, track_id)
values  (1, 1);
delete from artist_track;
delete from artist;
alter sequence artist_id_seq restart;
delete from track;
alter sequence track_id_seq restart;


insert into public.artist (id, name)
values  (1, 'Taylor Swift');

insert into public.track (duration, id, issue_date, media_type, album, price, title)
values  (3, 1, '2024-06-03', 1, '1986', '3.50', 'Cruel Summer');

insert into public.artist_track (artist_id, track_id)
values  (1, 1);
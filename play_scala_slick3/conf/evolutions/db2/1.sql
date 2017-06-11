
# --- !Ups
CREATE TABLE "REQUEST_TEXT" (
  id bigint identity primary key,
  input_text char(256)
);
insert into REQUEST_TEXT(id, input_text) values ( null, 'slick db2');
insert into REQUEST_TEXT(id, input_text) values ( null, 'one');
insert into REQUEST_TEXT(id, input_text) values ( null, 'two');
insert into REQUEST_TEXT(id, input_text) values ( null, 'three');
insert into REQUEST_TEXT(id, input_text) values ( null, 'four');
insert into REQUEST_TEXT(id, input_text) values ( null, 'five');

# --- !Downs

DROP TABLE "REQUEST_TEXT";
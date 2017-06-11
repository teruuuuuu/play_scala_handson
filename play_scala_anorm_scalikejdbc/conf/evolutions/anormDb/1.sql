
# --- !Ups
CREATE TABLE "REQUEST_TEXT" (
  id bigint identity primary key,
  input_text char(256)
);
insert into REQUEST_TEXT(id, input_text) values ( null, 'one text');
insert into REQUEST_TEXT(id, input_text) values ( null, 'two text');
insert into REQUEST_TEXT(id, input_text) values ( null, 'three text');
insert into REQUEST_TEXT(id, input_text) values ( null, 'four text');

# --- !Downs

DROP TABLE "REQUEST_TEXT";
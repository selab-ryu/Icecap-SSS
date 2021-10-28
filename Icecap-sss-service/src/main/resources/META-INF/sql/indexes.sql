create index IX_42068C32 on SSS_Term (groupId, status);
create index IX_CE20FE6C on SSS_Term (groupId, userId, status);
create index IX_31B07E5D on SSS_Term (name[$COLUMN_LENGTH:75$]);
create index IX_68833E04 on SSS_Term (status);
create index IX_A97FBE3E on SSS_Term (userId, status);
create index IX_9C99B392 on SSS_Term (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_4DBDF694 on SSS_Term (uuid_[$COLUMN_LENGTH:75$], groupId);
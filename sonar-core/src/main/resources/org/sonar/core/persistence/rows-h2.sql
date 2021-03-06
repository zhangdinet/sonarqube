-- All the rows inserted during Rails migrations. Rows inserted during server startup tasks (Java) are excluded : rules, profiles, metrics, ...

INSERT INTO GROUPS(ID, NAME, DESCRIPTION, CREATED_AT, UPDATED_AT) VALUES (1, 'sonar-administrators', 'System administrators', '2011-09-26 22:27:51.0', '2011-09-26 22:27:51.0');
INSERT INTO GROUPS(ID, NAME, DESCRIPTION, CREATED_AT, UPDATED_AT) VALUES (2, 'sonar-users', 'Any new users created will automatically join this group', '2011-09-26 22:27:51.0', '2011-09-26 22:27:51.0');
ALTER TABLE GROUPS ALTER COLUMN ID RESTART WITH 3;

INSERT INTO GROUP_ROLES(ID, GROUP_ID, RESOURCE_ID, ROLE) VALUES (1, 1, null, 'admin');
INSERT INTO GROUP_ROLES(ID, GROUP_ID, RESOURCE_ID, ROLE) VALUES (2, 1, null, 'profileadmin');
INSERT INTO GROUP_ROLES(ID, GROUP_ID, RESOURCE_ID, ROLE) VALUES (3, 1, null, 'shareDashboard');
INSERT INTO GROUP_ROLES(ID, GROUP_ID, RESOURCE_ID, ROLE) VALUES (4, null, null, 'scan');
INSERT INTO GROUP_ROLES(ID, GROUP_ID, RESOURCE_ID, ROLE) VALUES (5, null, null, 'dryRunScan');
INSERT INTO GROUP_ROLES(ID, GROUP_ID, RESOURCE_ID, ROLE) VALUES (6, 1, null, 'provisioning');
ALTER TABLE GROUP_ROLES ALTER COLUMN ID RESTART WITH 7;

INSERT INTO GROUPS_USERS(USER_ID, GROUP_ID) VALUES (1, 1);
INSERT INTO GROUPS_USERS(USER_ID, GROUP_ID) VALUES (1, 2);


INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('1');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('2');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('10');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('11');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('14');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('35');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('48');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('49');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('53');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('54');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('59');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('61');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('62');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('79');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('80');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('86');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('87');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('88');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('93');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('95');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('111');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('115');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('118');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('119');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('120');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('131');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('132');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('133');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('134');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('135');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('136');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('137');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('138');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('139');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('140');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('141');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('142');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('151');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('160');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('162');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('163');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('165');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('166');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('167');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('168');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('169');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('170');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('180');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('190');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('191');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('200');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('201');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('202');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('203');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('210');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('211');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('212');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('213');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('214');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('215');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('216');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('217');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('220');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('221');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('222');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('230');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('231');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('232');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('233');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('234');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('235');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('236');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('237');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('238');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('239');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('240');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('241');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('250');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('251');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('252');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('254');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('255');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('256');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('257');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('258');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('259');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('260');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('261');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('262');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('263');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('280');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('281');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('282');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('283');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('284');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('285');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('286');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('287');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('300');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('301');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('302');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('303');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('304');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('305');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('306');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('320');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('321');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('330');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('331');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('332');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('333');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('334');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('335');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('350');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('351');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('352');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('353');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('354');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('355');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('356');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('358');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('359');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('360');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('361');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('362');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('363');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('370');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('379');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('380');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('381');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('382');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('383');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('387');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('388');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('391');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('392');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('394');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('397');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('398');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('399');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('400');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('401');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('402');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('403');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('404');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('405');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('406');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('410');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('411');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('412');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('413');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('414');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('415');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('416');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('417');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('418');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('419');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('430');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('431');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('432');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('433');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('434');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('440');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('441');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('442');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('443');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('444');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('460');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('461');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('462');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('463');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('464');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('465');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('466');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('467');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('480');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('481');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('482');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('483');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('484');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('485');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('486');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('488');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('489');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('490');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('492');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('493');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('494');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('495');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('496');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('497');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('498');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('510');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('511');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('512');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('513');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('514');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('515');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('516');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('517');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('518');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('519');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('520');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('521');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('522');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('523');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('524');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('525');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('526');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('530');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('531');
INSERT INTO SCHEMA_MIGRATIONS(VERSION) VALUES ('532');

INSERT INTO USERS(ID, LOGIN, NAME, EMAIL, CRYPTED_PASSWORD, SALT, CREATED_AT, UPDATED_AT, REMEMBER_TOKEN, REMEMBER_TOKEN_EXPIRES_AT) VALUES (1, 'admin', 'Administrator', '', 'a373a0e667abb2604c1fd571eb4ad47fe8cc0878', '48bc4b0d93179b5103fd3885ea9119498e9d161b', '2011-09-26 22:27:48.0', '2011-09-26 22:27:48.0', null, null);
ALTER TABLE USERS ALTER COLUMN ID RESTART WITH 2;

create schema kiimate collate utf8mb4_unicode_ci;

create table km_i_ins
(
	id varchar(64) not null,
	commit varchar(64) not null,
	owner_id varchar(64) not null,
	sub_id varchar(64) not null,
	ext_id varchar(64) not null,
	int_id varchar(64) not null,
	field varchar(64) not null,
	value varchar(64) not null,
	value_set varchar(64) not null,
	glimpse_id varchar(64) not null,
	operator_id varchar(64) not null,
	begin_time datetime not null,
	end_time datetime default '2100-01-01 00:00:00' null
);

create table km_m_ext
(
	id varchar(64) not null,
	commit varchar(64) not null,
	`group` varchar(64) not null,
	name varchar(64) not null,
	tree varchar(64) not null,
	owner_id varchar(64) not null,
	visibility varchar(64) not null,
	operator_id varchar(64) not null,
	begin_time datetime not null,
	end_time datetime default '2100-01-01 00:00:00' null
);

create table km_m_int
(
	id varchar(64) not null,
	commit varchar(64) not null,
	ext_id varchar(64) not null,
	field varchar(64) not null,
	is_single varchar(64) not null,
	structure varchar(64) not null,
	ref_m_pub_set varchar(64) not null,
	is_required varchar(64) not null,
	visibility varchar(64) not null,
	operator_id varchar(64) not null,
	begin_time datetime not null,
	end_time datetime default '2100-01-01 00:00:00' null
);

create table km_m_pub
(
	id varchar(64) not null,
	pub_set varchar(64) not null,
	provider_id varchar(64) not null,
	ext_id varchar(64) not null,
	int_id varchar(64) not null,
	version varchar(64) not null,
	stability varchar(64) not null,
	operator_id varchar(64) not null,
	begin_time datetime not null,
	end_time datetime default '2100-01-01 00:00:00' null
);

create table km_m_sub
(
	id varchar(64) not null,
	sub_set varchar(64) not null,
	subscriber_id varchar(64) not null,
	`group` varchar(64) not null,
	name varchar(64) not null,
	tree varchar(64) not null,
	operator_id varchar(64) not null,
	begin_time datetime not null,
	end_time datetime default '2100-01-01 00:00:00' null
);

create table km_s_pub
(
	id varchar(64) not null,
	pub_set varchar(64) not null,
	provider_id varchar(64) not null,
	m_sub_id varchar(64) not null,
	ins_id varchar(64) not null,
	version varchar(64) not null,
	visibility varchar(64) not null,
	stability varchar(64) not null,
	operator_id varchar(64) not null,
	begin_time datetime not null,
	end_time datetime default '2100-01-01 00:00:00' null
);

create table km_s_sub
(
	id varchar(64) not null,
	subscriber_id varchar(64) not null,
	operator_id varchar(64) not null,
	begin_time datetime not null,
	end_time datetime default '2100-01-01 00:00:00' null
);


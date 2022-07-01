use findprop^;

-- Drop helper procedures
drop procedure if exists get_nearest_postcodes^;
drop procedure if exists get_similar_local_authority_rent_prices^;

-- Drop price tables
drop table if exists postcode_area_rent_price^;
drop table if exists postcode_rent_price^;
drop table if exists local_authority_rent_price^;
drop table if exists local_authority_utility_price^;
drop table if exists region_rent_price^;

-- Drop geo tables
drop table if exists postcode^;
drop table if exists related_local_authority^;
drop table if exists local_authority^;
drop table if exists region^;

-- Create geo tables
create table region (
    id int not null auto_increment,
    code varchar(10) not null,
    name varchar(50) not null,
    primary key (id),
    index region_code_idx (code),
    index region_name_idx (name)
)^;

create table local_authority (
	id int not null auto_increment,
    code varchar(10) not null,
    name varchar(50) not null,
    region_id int,
    primary key(id),
    foreign key (region_id) references region (id),
    index local_authority_code_idx (code),
    index local_authority_name_idx (name)
)^;

create table related_local_authority (
    id int not null auto_increment,
	anchor_local_authority_id int not null, 
    related_local_authority_id int not null, 
    primary key (id),
    foreign key (anchor_local_authority_id) references local_authority (id),
    foreign key (related_local_authority_id) references local_authority (id)
)^;

create table postcode (
	id int not null auto_increment,
    code varchar(10) not null,
    latitude decimal(8, 6) not null,
    longitude decimal(8, 6) not null,
    location point not null srid 0,
    local_authority_id int not null,
    primary key (id),
    foreign key (local_authority_id) references local_authority (id),
    index postcode_code_idx (code),
    index postcode_lat_long_idx (latitude, longitude),
    spatial index postcode_location_idx (location)
)^;

-- Create price tables
create table region_rent_price (
	id int not null auto_increment,
    property_type enum('room', 'studio', 'flat') not null,
    bedrooms int,
    price_count int,
    price_mean int not null,
    price_low int,
    price_median int,
    price_high int,
    currency enum('gbp', 'usd', 'eur') not null,
    period enum('one_off', 'year', 'month', 'week', 'day') not null,
    source varchar(250) not null,
    published date not null,
    recorded_from date,
    recorded_to date,
    region_id int not null,
    primary key (id),
    foreign key (region_id) references region (id),
    index region_rent_price_per_period_idx (property_type, bedrooms, published)
)^;

create table local_authority_rent_price (
	id int not null auto_increment,
    property_type enum('room', 'studio', 'flat') not null,
    bedrooms int,
    price_count int,
    price_mean int not null,
    price_low int,
    price_median int,
    price_high int,
    currency enum('gbp', 'usd', 'eur') not null,
    period enum('one_off', 'year', 'month', 'week', 'day') not null,
    source varchar(250) not null,
    published date not null,
    recorded_from date,
    recorded_to date,
    local_authority_id int not null,
    primary key (id),
    foreign key (local_authority_id) references local_authority (id),
    index local_authority_rent_price_per_period_idx (property_type, bedrooms, published)
)^;

create table local_authority_utility_price (
	id int not null auto_increment,
    property_type enum('room', 'studio', 'flat') not null,
    bedrooms int,
    utility_type enum('tv_license', 'council_tax', 'internet', 'energy', 'water') not null,
    price_mean int not null,
    currency enum('gbp', 'usd', 'eur') not null,
    period enum('one_off', 'year', 'month', 'week', 'day') not null,
    source varchar(250) not null,
    published date not null,
    recorded_from date,
    recorded_to date,
    local_authority_id int not null,
    primary key (id),
    foreign key (local_authority_id) references local_authority (id),
    index local_authority_utility_price_per_period_idx (property_type, bedrooms, utility_type, published)
)^;

create table postcode_area_rent_price (
	id int not null auto_increment,
    property_type enum('room', 'studio', 'flat') not null,
    bedrooms int,
    price_count int,
    price_mean int not null,
    price_low int,
    price_median int,
    price_high int,
    currency enum('gbp', 'usd', 'eur') not null,
    period enum('one_off', 'year', 'month', 'week', 'day') not null,
    source varchar(250) not null,
    published date not null,
    recorded_from date,
    recorded_to date,
    postcode_area varchar(6) not null,
    primary key (id),
    index postcode_area_rent_price_idx (postcode_area),
    index postcode_area_rent_price_per_period_idx (property_type, bedrooms, published)
)^;

create table postcode_rent_price (
	id int not null auto_increment,
    property_type enum('room', 'studio', 'flat') not null,
    bedrooms int,
    price_count int,
    price_mean int not null,
    price_low int,
    price_median int,
    price_high int,
    currency enum('gbp', 'usd', 'eur') not null,
    period enum('one_off', 'year', 'month', 'week', 'day') not null,
    source varchar(250) not null,
    published date not null,
    recorded_from date,
    recorded_to date,
    postcode_id int not null,
    primary key (id),
    foreign key (postcode_id) references postcode (id),
    index local_authority_rent_price_per_period_idx (property_type, bedrooms, published)
)^;

create procedure get_nearest_postcodes(in center_lon decimal(8, 6), in center_lat decimal(8, 6), in max_range decimal(8, 2))
begin
	declare max_range_km decimal(8, 2);
    declare srid int;
	declare lat_degree_km decimal(6, 3);
    declare min_lat decimal(8, 6);
    declare max_lat decimal(8, 6);
    declare min_lon decimal(8, 6);
    declare max_lon decimal(8, 6);
    declare center_point point;
    declare search_area polygon;
    
	set max_range_km = max_range / 1000;
	set srid = 0;
	set lat_degree_km = 111.045;

	set min_lat = center_lat - (max_range_km / lat_degree_km);
	set max_lat = center_lat + (max_range_km / lat_degree_km);
	set min_lon = center_lon - (max_range_km / (lat_degree_km * COS(RADIANS(center_lat))));
	set max_lon = center_lon + (max_range_km / (lat_degree_km * COS(RADIANS(center_lat))));

	set center_point = ST_SRID(POINT(center_lon, center_lat), srid);
	set search_area = ST_MakeEnvelope (
	  POINT(max_lon, max_lat),
	  POINT(min_lon, min_lat)
	);

	select *
	from postcode
	where ST_Contains(search_area, location)
	and ST_Distance_Sphere(center_point, location) < max_range
	order by ST_Distance_Sphere(center_point, location) asc
    limit 100;
end^;

create procedure get_similar_local_authority_rent_prices(in la_id int, in p_type varchar(50), in b_count int)
begin
	declare p_mean int;
    declare la_id1 int;
    declare la_id2 int;
    
    select lpi.price_mean 
    into p_mean
	from local_authority_rent_price lpi 
	where lpi.bedrooms = b_count 
		and lpi.property_type = p_type 
		and lpi.local_authority_id = la_id;
    
	select la.id 
    into la_id1
	from local_authority_rent_price lp inner join local_authority la on lp.local_authority_id = la.id
	where lp.bedrooms = b_count 
		and lp.property_type = p_type 
		and la.id != la_id 
		and lp.price_mean < p_mean
	order by price_mean desc
	limit 1;
    
    select la.id 
    into la_id2
	from local_authority_rent_price lp inner join local_authority la on lp.local_authority_id = la.id
	where lp.bedrooms = b_count 
		and lp.property_type = p_type 
		and la.id != la_id 
		and lp.price_mean > p_mean
	order by price_mean asc
	limit 1;
    
    select lp.* 
    from local_authority_rent_price lp 
    where lp.bedrooms = b_count 
		and lp.property_type = p_type 
		and lp.local_authority_id in (la_id1, la_id2);
end^;

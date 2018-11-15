

drop table Product;
drop table Customer;
drop table SaleItem;
drop table Sale;


create table Product (
    Product_ID integer not null unique,
    Product_Name varchar(20) not null,
    Description varchar(20),
    Category varchar(20) not null,
    Price decimal(6, 3) not null check (Price >= 0),
    Quantity_In_Stock integer not null check (Quantity_In_Stock >= 0),
    constraint pk_product primary key (product_id)


create table Customer (
    Person_ID integer auto_increment,
    username  varchar(20) not null unique,
    First_Name varchar(20) not null,
    Surname varchar(20) not null,
    Password varchar(20) not null,
    Email_Address varchar(20) not null,
    Shipping_Address varchar(50) not null,
    Credit_Card_Details varchar(20) not null,
    constraint Person_pk primary key (Person_ID)

create table Sale (
    Sale_ID integer auto_increment, 
    Sale_Date date not null,
    Status varchar(50),
    Person_ID integer,
    constraint pk_Sale_ID primary key (Sale_ID),
    constraint Sale_Customer foreign key (Person_ID) references Customer

create table SaleItem (
    Quantity_Purchased integer not null check (Quantity_Purchased >= 0),
    Price decimal(6, 3) not null check (Price >= 0),
    Product_ID integer not null,
    Sale_ID integer,
    constraint SaleItem_PK primary key (Product_ID, Sale_ID),
    constraint SaleItem_Product foreign key (Product_ID) references Product,
    constraint SaleItem_Sale foreign key (Sale_ID) references Sale,
);


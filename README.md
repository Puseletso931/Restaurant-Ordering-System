Online Food Ordering System
SYSTEM DOCUMENTATION
10-06-2022


Abstract
The Online Food Ordering System depicted in this report has been outlined to fill a 
specific specialty within the market by providing restaurants with the ability to offer their 
customers an online ordering system without having to contribute huge sums of time and 
money in having custom software designed specifically for them.
Traditional ordering system had a lot of inconveniences. For example, waiting to get the 
food served, receiving incorrect bills and incorrect orders. All this inconvenience leaves 
the customers unsatisfied with the restaurant’s services and thus poor ratings for the 
restaurants as well.
The Ordering application is built dynamically based on the current state of the system, so 
any changes made are reflected in real time. Visitors to the site, once registered, are then 
able to easily navigate the menu, view orders, and rate their orders after collection with 
only a few clicks, greatly simplifying the ordering process. Back in the restaurant, placed 
orders are promptly retrieved and displayed in an easily readable format for efficient 
processing.
The purpose of this document is to provide in-depth descriptions of design and 
implementation details of the system, as well as descriptions of all available functionality 
and plans for evolution. In addition, tips have been included for all three components to 
give the reader a clear idea of intended typical use cases for the system.
Objective
• User-friendly system
• The system keeps customers up to date on the status of their takeout orders.
• The system caters to multiple takeaways. 
• Two users utilize the system namely, Staff and Customers.
• The Application has two interfaces for each user namely, Staff and Customers.


SYSTEM MODEL 
The structure of is divided into three main logical components. 
The first component must provide menu management
• Allows the restaurant to control what customers can order. 
The second component is the web ordering system
- Provides the functionality for customers to place their order and supply all 
necessary details. 
The third component is the order retrieval system
- Used by the restaurant to keep track of all orders which have been placed, this 
component takes care of retrieving and displaying order information, as well as 
updating orders which have already been processed
-
The menu management system will be available only to restaurant Staff
The functionalities for the STAFF:
• Log in to the system
• Register if they have no account
• Add a new/update order
• Mark status for an order as either pending, ready or collected
• Update default options for a given food item
• View average rating for orders made


The Web Ordering System Users of the web ordering system, namely restaurant 
customers, 
The functionalities for the CUSTOMER:
• Create an account
• Log in to the system
• Register if they have no account
• Navigate the restaurant’s menu
• Select an item from the menu
• Review their order status
• Place an order
• Receive confirmation in the form of an order number
• Provide feedback through ratings
The order retrieval system, utilized only by restaurant STAFF, and provides the following 
functions
The functionalities for the DATABASE:
• Retrieve new orders from the database
• Display the orders in an easily readable way
• Update an order status 
The Non-functional Requirements 
The Order System was developed using Android Studio; the application is cross-compiled 
to MySQL remote database and Java, along with a PHP backend, all of which are 
supported by any reasonably well maintained web server, although I would recommend
accessing the web server using Wits Wi-Fi. All of the application data is stored in a 
MySQL database


DATABASE DESIGN
Business Rules
• Each Restaurant employs many Staff members .Each Staff member is employed at 
one Restaurant. 1:M
• Each Staff member creates zero or many orders. Each order is created by one Staff 
member. 1:M
• A Customer can place zero or many orders. Each order is placed by one Customer. 
1:M
Entity Relation Diagram


IMPLEMENTED TABLES :


ISSUES RESOLVED
The ORDERS table 
Contains Order-rating attribute that would have contained null values if the customer 
decided not to order anything 
The solution to that is to place the value 0 as default indicating No thumbs up with a value 
of 1 and no thumbs down with the value of -1
Contains Ordered-at attribute which is a multivalued and composite attribute that is 
composed of the date which can be sub divided into day, month, year and time which can 
be sub divided into hours, minutes, seconds.
In the STAFF and CUSTOMERS tables, the attributes containing the names of the users 
would have been a composite attribute, causing difficulty of retrieving just the first names 
or just the last names. This issue was solved by separating those attributes into 
STAFF_LNAME and STAFF_FNAME, and CUSTOMER_LNAME and CUSTOMER_FNAME
(split into first name and last name). 


AUTHORISATION DESIGN
When a user first arrives at the site, they are presented with an Authorization Login form. 
The login forms and registration forms for both the customers and staff members check 
the user input. Error messages are sent whenever a required input filed is empty. There 
are also error messages for when invalid input is entered for fields requiring email 
addresses. During registration, the user has to enter a password and confirm it by entering 
it again. If the two passwords are not similar or are shorter than 6 characters,
corresponding error messages are sent. These error messages alert the user of what is 
wrong or what is needed of them.
The login forms check the user input by comparing it to the corresponding data in the 
database, thus the correct user can login with their own email and password, including 
having access to their own data only.
The registration forms insert new user input into the database, thereafter, they can use 
their email and password to login. 
After either logging in or, if they do not yet have an account, first registering and then 
logging in, the customer is then taken to a welcome page with the main menu. From here, 
they have five Restaurant option as the System allows multiple takeaways. At any time 
they can view their shopping cart and when their orders are made, they can proceed to 
rate their orders using a thumbs up or thumbs down button. The Staff members are taken 
to their main page where they can add orders, change the status of orders, and through 
the click of a button, view their average ratings. 
RESTAURANT DESIGN
It constitutes of an interface with five Restaurants and a shopping cart that sends the 
customer to view all their orders and rate them if they would like to. 
A staff member is able to add a new order for a customer by including their staff email and 
the customer’s email. This is added into the database while including a system generated 
date and time of the order. The default order status for a newly created order is “Pending”. 
The staff member is also able to set or change the status of an order to “pending”, “ready” 
or “collected”. Additionally, the staff member can view their average rating, the number of 
non-rated orders, and the total number of orders they have added to the database. 
CONCLUSION
Online Food Ordering system is done to help and solve one of the important problems of 
customer. Because large number of customer can use the internet and phone. Various 
issues will be solved by this system. Thus, implementation of Online Food Ordering 
system requires a database to function otherwise the application is worthless. It helps in 
tracking order easily and gives information about the order details to the customer. The 
Food website application made for restaurant massive one help to receiving orders.
 
 THANK YOU

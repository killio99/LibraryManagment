# LibraryManagment

Killian: My idea

Main file



Library class 
                
Contructor
- ability to mass upload on construction for users and books
- Ask what does this look like?

Variables

maxBookCheckout: default = 3, getters and setters
array of book objects
array of user objects
- able to update
Transaction history object

Methods
Removing a book
- reads to database
adding a book
- reads to database

Removing a user
- reads to database
Adding a user
- reads to database

Overload
findBook(ISBN) returns book object for all


findBook(author)
findBook(title)




Book Class

Variables
ISBN Number
String title
String author
int numAvailable
Maybe: Boolean if its available

Array of users that have reserved the book

Methods
isAvailable: Returns if there is a book available given the numAvailable, updated numAvailable


User class

Variables
Username
Password
array of checked out books
array of books reserved?
double fines

Methods
checkOutBook(Book obj): Checks if they have the max, checks if the book is available, checks if they have overdue fees, the changes variables and
adds to instance array. Add to transaction history

Reserve(user, book)
add to user's reserve list
add to book's reserve list
payBackFines(double cost): subtract fines

getters and setters




Class transaction history
reads to and from a file

two .add methods, one contains an alternate time thats not .today


Ray: Additional Ideas

Library Class:
- Search feature for books (by title, author, or ISBN).
- Allow book reservation if no copies are available.

User Class:
- Store overdue fines and add a method for users to pay fines before borrowing again.

Transaction History:
- Add a method to retrieve a user's full borrowing history.
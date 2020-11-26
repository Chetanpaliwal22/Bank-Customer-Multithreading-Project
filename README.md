Description:

This project was part of course COMP6411, Comparative study of programming languages, in this project we have created a Concurrent banking environment in Erlang.

Requirement:

A customer can contact multiple bank for the loan and the bank will approve/reject the loan application based on some pre-defined constraints.

The program execution should stop once all the customers get the money they require or banks are out of the fund, in second scenario customer will not be able to meet their loan objective.

In the end the application will display information about all the banking transactions, the amount remaining with Banks and the amount given to each customer.


* For Erlang Version:

	To run the code:

	In CMD: 
	1. Compile the three file using command: erlc filename.erl command.
	2. Run the application using command: erl -noshell -s money -s init stop

	In Eclipse: 
	1. Run the project.
	2. then start the function money:start().

	Note: 
	1. For error related to input read:
	Put the complete source path.
	2. For calculation purpose I am printing each amount in the end. For example even if customer 1 reach objective in first go his amount will be printed in the 		end with all the cusomter.


	Note:
	If the request amount is less then the random amount then the customer will request the remaining loan amount.

* For Java version: 
	1. Change the source path in the main method of money.java class.
	2. Run the money.java class.

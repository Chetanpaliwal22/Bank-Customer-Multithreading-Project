-module(money). 
-author("Student ID: 40083388").
-export([start/0,run/2, forLoopBank/4, forLoopCust/4, printFromCustomer/0]). 
-compile([export_all]).
-import(bank, [giveAmount/4]).
-import(customer, [takeAmount/7]).
%http://www.codecodex.com/wiki/Traverse_a_Map
%http://erlang.org/doc/man/maps.html#size-1


start() -> 
    %Master = spawn(money, printFromCustomer, []),
   	register(master,self()),
	%MasterPid = whereis(master),
	A = 10,
	%MasterPid ! {A},
   	%CFile = file:consult("C:\\Users\\Chetan Paliwal\\Desktop\\Summer\\COMP 6411\\Project\\erlang\\customers.txt"),
   	CFile = file:consult("customers.txt"),
	CustList =element(2,CFile),
   	FinalCustMap = maps:from_list(CustList),
   
  	%BFile = file:consult("C:\\Users\\Chetan Paliwal\\Desktop\\Summer\\COMP 6411\\Project\\erlang\\banks.txt"),
   	BFile = file:consult("banks.txt"),
   	BankList =element(2,BFile),
   
   	FinalBankMap = maps:from_list(BankList),
   
	NumberCustomer = maps:size(FinalCustMap),
	NumberBank = maps:size(FinalBankMap),
	
	io:fwrite("** Customers and loan objectives ** ~n"),
	KeyCust = maps:keys(FinalCustMap),
	ValuesCust = maps:values(FinalCustMap),
	
	CustKey = lists:reverse(KeyCust),
	CustValues = lists:reverse(ValuesCust),
	printData(CustKey,CustValues,NumberCustomer),
   
	io:fwrite("** Banks and financial resources ** ~n"),
	KeyBank = maps:keys(FinalBankMap),
	ValuesBank = maps:values(FinalBankMap),
	
	BankKey = lists:reverse(KeyBank),
	BankValues = lists:reverse(ValuesBank),
	printData(BankKey,BankValues,NumberBank),
   run(FinalCustMap,FinalBankMap),
	printFromCustomer().
   %timer:sleep(2500).
   
   
run(FinalCustMap,FinalBankMap) ->

	NumberCustomer = maps:size(FinalCustMap),
	NumberBank = maps:size(FinalBankMap),
	KeyBank = maps:keys(FinalBankMap),
	KeyCust = maps:keys(FinalCustMap),
	
	forLoopBank(FinalCustMap,FinalBankMap,KeyBank,NumberBank),
    forLoopCust(FinalCustMap,FinalBankMap,KeyCust,NumberCustomer).
	
forLoopBank(FinalCustMap,FinalBankMap,List, 0) ->
    done;
	
forLoopBank(FinalCustMap,FinalBankMap,List, I) ->
	Bank = lists:nth(I,List),
	PidMaster = whereis(master),
	BankAmount = maps:get(Bank,FinalBankMap),
	Pid = spawn(bank, giveAmount, [Bank,"jill",BankAmount,PidMaster]),
	register(Bank,Pid),
	Pid ! {Bank},
	forLoopBank(FinalCustMap,FinalBankMap,List, I - 1).
	
forLoopCust(FinalCustMap,FinalBankMap,List, 0) ->
    done;
forLoopCust(FinalCustMap,FinalBankMap,List, J) ->
	Customer = lists:nth(J,List),
	PidMaster = whereis(master),
	CustAmount = maps:get(Customer,FinalCustMap),
	Pid = spawn(customer, takeAmount, [FinalCustMap,FinalBankMap,Customer,"Bank",CustAmount,CustAmount,PidMaster]),
	PidMaster = whereis(master), 
	Pid ! {Customer},
	register(Customer,Pid),
    forLoopCust(FinalCustMap,FinalBankMap,List, J - 1).
	
printData(Key,Value, 0) ->
    done;
printData(Key,Value, I) ->
	Keyy= lists:nth(I,Key),
	Valuee = lists:nth(I,Value),
	io:fwrite("~p",[Keyy]),
	io:fwrite(": "),
	io:fwrite("~p ~n",[Valuee]),
    printData(Key, Value,I - 1).
	%printFromCustomer().
	
	
printFromCustomer() ->
	receive
			
		{Customer, Bank, Amount} ->
			io:fwrite("~p requests a loan of ~p dollar(s) from ~p. ~n",[Customer,Amount,Bank]),
			printFromCustomer();
			
		{"Approved",Customer,Bank,Amount}->
			io:format("~p approves a loan of ~p dollars from ~p.~n", [Bank,Amount,Customer]),
			printFromCustomer();
   
		{"Denied",Customer,Bank,Amount}->
			io:format("~p denies a loan of ~p dollars from ~p.~n", [Bank,Amount,Customer]),
			printFromCustomer();

		{"ReachedObjective",Customer,Bank,Amount}->
			io:format("~p has reached the objective of ~p dollar(s). Woo Hoo!~n",[Customer,Amount]),
			printFromCustomer();
				
		{"NotReachedObjective",Customer,Bank,Amount}->
			io:format("~p was only able to borrow ~p dollar(s). Boo Hoo!~n", [Customer,Amount]),
			printFromCustomer();

		{Bank,Amount}->
			io:format("~p has ~p dollar(s) remaining.~n",[Bank,Amount]),
			printFromCustomer()

		after 3000 ->
			exit(self(),ok)
	end.
		
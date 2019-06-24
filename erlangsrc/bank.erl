-module(bank). 
-author("Student ID: 40083388").
-export([giveAmount/4]). 
-compile([export_all]).
-import(money,[printFromCustomer/0]).
-import(customer,[takeAmount/6]).

giveAmount(Bank,Customer,BankAmount,PidMaster) ->

receive 
	{BankthreadName} ->
		giveAmount(Bank,Customer,BankAmount,PidMaster);

	{Cust,BankFromCust,AmountRequested} ->
										
			PiddMaster = whereis(master),
			CustomerThread = whereis(Cust),
			NewBankAmount = BankAmount - AmountRequested,
			
			if NewBankAmount < 0 ->
				  FinalBankAmount = BankAmount;
			true ->
				  FinalBankAmount = BankAmount - AmountRequested
			end,
			
			if BankAmount >= AmountRequested ->
				PiddMaster ! {"Approved",Cust,BankFromCust,AmountRequested},
				CustomerThread ! {"Approved",Cust,BankFromCust,AmountRequested};
		 	true ->
				PiddMaster ! {"Denied",Cust,BankFromCust,AmountRequested},
				CustomerThread ! {BankFromCust,Cust}			
			end,
								
			giveAmount(Bank,Customer,FinalBankAmount,PidMaster)
			
		after 2000 ->
			sendfinalamount(PidMaster,Bank,BankAmount),
			exit(self(),ok)
		end.
		
		sendfinalamount(PidMaster,Bank,BankAmount) ->
		PidMaster ! {Bank,BankAmount}.
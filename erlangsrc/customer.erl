-module(customer). 
-author("Student ID: 40083388").
-export([takeAmount/7]).
-import(money,[printFromCustomer/0]).
-import(bank,[giveAmount/4]).
-compile([export_all]).

takeAmount(FinalCustMap,FinalBankMap,Customer,Bank,CustAmount,ConstCustAmount,PidMaster) ->
	receive
		
		{Customer} ->
			
			BankSize = maps:size(FinalBankMap),
			RandomBankIndex = rand:uniform(BankSize),
			KeyList = maps:keys(FinalBankMap),
			NewBank = lists:nth(RandomBankIndex,KeyList),
			PidMaster = whereis(master),
			AmountRequested = rand:uniform(50),
					
			BankThread = whereis(NewBank),
			NewAmountRequested = CustAmount,

			if CustAmount == 0 ->
				ok;
			true ->
				if( BankSize > 0) ->
					if CustAmount >= AmountRequested ->
						PidMaster ! {Customer, NewBank, AmountRequested},
						BankThread ! {Customer,NewBank,AmountRequested};
					true ->
						PidMaster ! {Customer, NewBank, NewAmountRequested},
						BankThread ! {Customer,NewBank,NewAmountRequested}
					end;
				true ->
					ok
				end
			end,

			RandomSleepTime = rand:uniform(100),
			timer:sleep(RandomSleepTime),
			
			
			takeAmount(FinalCustMap,FinalBankMap,Customer,NewBank,CustAmount,ConstCustAmount,PidMaster);
			
		{"Approved",Cust,BankFromBank,AmountRequested} ->

			NewCustAmount = CustAmount - AmountRequested,
			
			NewFinalCustMap = maps:remove(Cust,FinalCustMap),
			NewFinalCustMap2 = maps:put(Cust,NewCustAmount,NewFinalCustMap),

			CustomerThread = whereis(Cust),
			CustomerThread ! {Cust},
				
			takeAmount(NewFinalCustMap2,FinalBankMap,Cust,Bank,NewCustAmount,ConstCustAmount,PidMaster);
			
		{Bank,Customer} ->

			
			NewFinalBM = maps:remove(Bank,FinalBankMap),
			BankMapSize = maps:size(NewFinalBM),

			if BankMapSize == 0 ->
				ok;
			true ->
				CustomerThread = whereis(Customer),
				CustomerThread ! {Customer}
			end,
				
			takeAmount(FinalCustMap,NewFinalBM,Customer,Bank,CustAmount,ConstCustAmount,PidMaster)
		
		after 1000 ->
			if CustAmount == 0 -> 
				PidMaster ! {"ReachedObjective",Customer,Bank,ConstCustAmount};
			true ->				
				PidMaster ! {"NotReachedObjective",Customer,Bank, (ConstCustAmount - CustAmount)}
			end,
			exit(self(),ok)
		end.
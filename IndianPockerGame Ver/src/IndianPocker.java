
public class IndianPocker {

	public static void main(String[] args)
	{
		Dealer dealer = new Dealer();
		Players player1 = new Players(1);
		Players player2 = new Players(2);
		int stage=0;
		int die=0;
		
		
		System.out.println("-----------인디언 포커-----------");
		System.out.println();
		
		while(true)
		{
			stage++;
			System.out.printf("<%d라운드를 시작합니다>\n",stage);
			if(stage%10==1)
				dealer.initCardDeck();
	
			dealer.distributeCard(player1, player2);
			dealer.initRound(player1, player2);
			
			while(true)
			{
				if(dealer.EndorNot()==0)
					dealer.betting(player1, player2);			
				else
					break;
				die=dealer.checkDie(player1,player2);
				
				if(die==1)
					break;
				
				System.out.println();
				if(dealer.EndorNot()==0)
					dealer.betting(player2, player1);
				else
					break;
				die=dealer.checkDie(player1,player2);
				
				if(die==1)
					break;
			}
			if(die==1)
				break;
			
		}
	}
}

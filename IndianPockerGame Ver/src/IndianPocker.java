
public class IndianPocker {

	public static void main(String[] args)
	{
		Dealer dealer = new Dealer();
		Players player1 = new Players(1);
		Players player2 = new Players(2);
		int stage=0;
		
		System.out.println("-----------�ε�� ��Ŀ-----------");
		System.out.println();
		
		while(true)
		{
			stage++;
			System.out.printf("<%d���带 �����մϴ�>\n",stage);
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
				System.out.println();
				if(dealer.EndorNot()==0)
					dealer.betting(player2, player1);
				else
					break;
			}
			
			if(player1.getChips()==0)
			{
				System.out.printf("%d�÷��̾ Ĩ�� ��� �����Ͽ����ϴ�. %d�÷��̾��� �¸��Դϴ�."
						, player1.getId(),player2.getId());
				break;
			}
			else if(player1.getChips()==0)
			{
				System.out.printf("%d�÷��̾ Ĩ�� ��� �����Ͽ����ϴ�. %d�÷��̾��� �¸��Դϴ�."
						, player2.getId(),player1.getId());
				break;
			}
		}
	}
}

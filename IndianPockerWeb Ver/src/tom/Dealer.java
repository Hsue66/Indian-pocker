package tom;

import java.util.Random;
import java.util.Scanner;

public class Dealer  {
	
	public static Integer card1=0;	// �÷��̾� 1�� ī��
	public static Integer card2=0;	// �÷��̾� 2�� ī��
	private Integer total=0;		// �� Ĩ�� 
	private Integer end=0;			// ����
	private Integer roundlose=0;	// ���忡�� ��
	private Integer same=0;			// ������ ���
	
	// �ڵ带 �ٶ���ĥ�� ��� -Ĩ�� �� ���� //
	private Integer tot=0;
	
	Random rand = new Random(); 
	
	// cardDeck ����
	private Integer[] cardDeck = new Integer[10];
	
	//**********ī�嵦 �ʱ�ȭ�Լ�**********//
	public void initCardDeck()
	{
		for(Integer i=0; i<10; i++)		// cardDeck 0���� �ʱ�ȭ
			cardDeck[i]=0;
	}
	
	
	//**********ī��̴� �Լ�**********//
	public Integer pickCard()
	{
		Integer overlap=0;	// ��ĥ ���
		Integer card=0;
		do
		{
			card = rand.nextInt(10);	// �����ϰ� ī�����
			overlap=0;	
			// ����ī�� ǥ�ÿ�
			for(Integer i=0; i<10; i++)
			{
				if(i==card)
				{
					if(cardDeck[i]==2)
						overlap++;	// ��ĥ ���
					else
						cardDeck[i]=cardDeck[i]+1;	// �ش�ī�� ���� Ƚ������
				}
			}
		}while(overlap!=0);	//��ĥ��� �ٽ� �̱�
		
		return card+1;	// ī�� ��ȯ
	}	
	
	//**********ī��й� �Լ�**********//
	public void distributeCard(Players player1, Players player2)
	{
		System.out.println("ī�带 �й��մϴ�.");	
		
		card1=pickCard();
		card2=pickCard();
		
		System.out.printf("P%d�� ī��� %d�Դϴ�.\n",player1.getId(),card1);
		System.out.printf("P%d�� ī��� %d�Դϴ�.\n\n",player2.getId(),card2);
	}
	
	//**********���� �ʱ� �Լ�**********//
	public void initRound(Players player1, Players player2)
	{
		if(same==0)			// ���� ���忡�� �ٸ� ī�忴�� ��� 
		{	
			System.out.println("�⺻������ �ص帳�ϴ�");
			player1.basicBetting();
			player2.basicBetting();
			accBetchips(player1,player2);
			System.out.println("�÷��̾�1 Ĩ :" +player1.getChips());
			System.out.println("�÷��̾�2 Ĩ :" +player2.getChips());
		}
		else				// ���� ���忡�� ���� ī�忴�� ���
			same=0;
	}
	
	//**********�÷��̾� Ĩ�����Լ�**********//
	public void accBetchips(Players player1, Players player2)
	{
		total=player1.getAccchip()+player2.getAccchip();
	}
	
	//**********Ĩ���� üũ�Լ�**********//
	public Integer checkBet(Players nowP, Players nextP)
	{
		Integer wrong=0;
		if(nowP.getAccchip()<nextP.getAccchip())
		{
			wrong++;
			System.out.println("�߸������ϼ̽��ϴ�. ������ ������ Ĩ���� ���ų� ũ�� �����ؾ��մϴ�.");
			nowP.wrongBetchips();
		}
		else
		{
			wrong=0;
			nowP.rightBetchips();
			
			// Ĩ����
			if(nowP.getId()==1)
				accBetchips(nowP, nextP);
			else
				accBetchips(nextP, nowP);
			
			if(nowP.getAccchip()==nextP.getAccchip())
			{
				System.out.println("���� ���� Ĩ�� �����Ͽ� ������ �����մϴ�.");
				end=1;
				if(nowP.getId()==1)
					RoundResult(nowP, nextP);
				else
					RoundResult(nextP, nowP);
			}
		}
		return wrong;
	}
	
	//**********Ĩ���� �����Լ�**********//
	public void betting(Players nowP, Players nextP)	// ���� �����ϴ� ���
	{
		Integer wrong=0;
		
		@SuppressWarnings("resource")
		Scanner yesorno = new Scanner(System.in);
		System.out.printf("%d�÷��̾� : ������ �����Ͻðڽ��ϱ�(y/n)? ",nowP.getId());
		char choice=yesorno.next().charAt(0);
		
		if(choice=='y'||choice=='Y')
		{
			do{
				wrong=0;
				System.out.printf("�󸶸� �����Ͻðڽ��ϱ�? ");
					
				wrong=checkBet(nowP,nextP);
				
			}while(wrong!=0);
		}
		
		else
		{
			System.out.printf("%d �÷��̾ ������ �����Ͽ����ϴ�.",nowP.getId());
			end=1;
			roundlose++;
			nowP.changeLose(0);
			
			if(nowP.getId()==1)
			{
				//accBetchips(nowP, nextP);
				RoundResult(nowP, nextP);
			}
			else
			{
				//accBetchips(nextP, nowP);
				RoundResult(nextP, nowP);
			}				
		}
	}
	
	
	//%%%%%%%%%% ���� üũ �Լ� %%%%%%%%%% //
	public Integer checkSame(Players nowP, Players nextP)
	{
		Integer roundEnd=0;		
		
		if(nowP.getAccchip() == nextP.getAccchip())
			roundEnd++;		// ���� ������ ���

		return roundEnd;
	}
	
	//%%%%%%%%%% ���� üũ �Լ� %%%%%%%%%% //
	public void checkRound(Players nowP, Players nextP, int Ncard)
	{
		tot = nowP.getAccchip() + nextP.getAccchip();	 // ���� Ĩ ��ġ��
		nowP.changeLose(1);		// �÷��̾� ���� ǥ�� �����
		
		if(Ncard==10)			// ������ �÷��̾��� ī�尡 10�� ���
		{
			nowP.penalty();		// ���Ƽ �ο�
			tot=tot+10;
		}
		
		nextP.increaseWin(tot);	// ��� Ĩ �� ����
		nowP.initBetting();		// ���� �ʱ�ȭ
		nextP.initBetting();	// ���� �ʱ�ȭ
		tot = 0;
	}

	//%%%%%%%%%% ���� Ĩ ���� �϶� ���� üũ �Լ� %%%%%%%%%% //
	public void checkSamechipRound(Players player1, Players player2)
	{
		tot = player1.getAccchip() + player2.getAccchip();// ���� Ĩ ��ġ��
		
		if(card1 > card2)		// �÷��̾� 1�� ī�尡 ū ���
		{
			player1.increaseWin(tot);	// 1�÷��̾� Ĩ �� ����
			player1.initBetting();		// ���� �ʱ�ȭ
			player2.initBetting();		// ���� �ʱ�ȭ
			tot=0;
		}
		else if(card1 < card2)	// �÷��̾� 2�� ī�尡 ū ���
		{
			player2.increaseWin(tot);	// 1�÷��̾� Ĩ �� ����
			player1.initBetting();		// ���� �ʱ�ȭ
			player2.initBetting();		// ���� �ʱ�ȭ
			tot=0;
		}
		else					// ī�尡 ���� ���
		{
			System.out.printf("�� �÷��̾��� ī�尡 �����ϹǷ� ���õ� Ĩ���� �����մϴ�.\n");
			System.out.printf("P1�� ���� Ĩ: %d\nP2�� ���� Ĩ: %d", player1.getAccchip(), player2.getAccchip());
			same++;		// ī�� ���Ҵٰ� ǥ��
		}
	}
	
	
	//**********���� ����Լ�**********//
	public void RoundResult(Players player1, Players player2)
	{
		if(roundlose!=0)	// ���÷��̾ ������ ���
		{
			if(roundlose==player1.getLose())
			{
				System.out.printf("%d �÷��̾  �¸��߽��ϴ�.\n", player2.getId());
				player1.changeLose(1);
				if(card1==10)
				{
					System.out.println("10ī���̹Ƿ� �г�Ƽ�� ����˴ϴ�. Ĩ10���� �߰��� �ҽ��ϴ�.");
					player1.penalty();
					total=total+10;
				}
				player2.increaseWin(total);
				player1.initBetting();
				player2.initBetting();
				total=0;
			}
			else if(roundlose==player2.getLose())
			{
				System.out.printf("%d �÷��̾  �¸��߽��ϴ�.\n", player1.getId());
				player2.changeLose(1);
				if(card2==10)
				{
					System.out.println("10ī���̹Ƿ� �г�Ƽ�� ����˴ϴ�. Ĩ10���� �߰��� �ҽ��ϴ�.");
					player2.penalty();
					total=total+10;
				}
				player1.increaseWin(total);
				player1.initBetting();
				player2.initBetting();
				total=0;
			}
			roundlose=0;
		}
		else	// �������� �������
		{
			// ī�� üũ�� ���� Ȯ��
			if(card1>card2)
			{
				System.out.printf("%d �÷��̾��� ī�尡 �� Ŀ�� �¸��߽��ϴ�.\n", player1.getId());
				player1.increaseWin(total);
				player1.initBetting();
				player2.initBetting();
				total=0;
			}
			else if(card1<card2)
			{
				System.out.printf("%d �÷��̾��� ī�尡 �� Ŀ�� �¸��߽��ϴ�.\n", player2.getId());
				player2.increaseWin(total);
				player1.initBetting();
				player2.initBetting();
				total=0;
			}
			else
			{
				System.out.printf("�� �÷��̾��� ī�尡 �����ϹǷ� ���õ� Ĩ���� �����մϴ�.\n");
				same++;
			}
		}
		System.out.printf("[ P1�� Ĩ��  %d, P2�� Ĩ��  %d ]\n"
				,player1.getChips(),player2.getChips(),total);
		System.out.printf("[ 1�÷��̾�: %d, 2�÷��̾�: %d ]\n\n",
				player1.getWin(),player2.getWin());
		System.out.println("----------------------------------------");
		System.out.println();
	}
	
	//**********���� Ȯ���Լ�**********//
	public Integer EndorNot()
	{
		Integer endornot=end;
		end=0;
		return endornot;
	}
	
	//**********���ߴ��� Ȯ���Լ�**********//
	public Integer checkDie(Players player1,Players player2)
	{
		if(player1.getChips()<=0)
		{
			System.out.printf("%d�÷��̾ Ĩ�� ��� �����Ͽ����ϴ�. %d�÷��̾��� �¸��Դϴ�."
					, player1.getId(),player2.getId());
			return 1;
		}
		else if(player2.getChips()<=0)
		{
			System.out.printf("%d�÷��̾ Ĩ�� ��� �����Ͽ����ϴ�. %d�÷��̾��� �¸��Դϴ�."
					, player2.getId(),player1.getId());
			return 1;
		}
		else
			return 0;
	}
}


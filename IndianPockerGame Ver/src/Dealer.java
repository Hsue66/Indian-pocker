import java.util.Random;
import java.util.Scanner;

public class Dealer  {
	
	private int card1=0;	// �÷��̾� 1�� ī��
	private int card2=0;	// �÷��̾� 2�� ī��
	private int total=0;
	private int end=0;			// ����
	private int roundlose=0;	// ���忡�� ��
	private int same=0;
	
	Random rand = new Random(); 
	
	// cardDeck ����
	private int[] cardDeck = new int[10];
	
	//**********ī�嵦 �ʱ�ȭ�Լ�**********//
	public void initCardDeck()
	{
		for(int i=0; i<10; i++)		// cardDeck 0���� �ʱ�ȭ
			cardDeck[i]=0;
	}
	
	
	//**********ī��̴� �Լ�**********//
	public int pickCard()
	{
		int overlap=0;	// ��ĥ ���
		int card=0;
		do
		{
			card = rand.nextInt(10);	// �����ϰ� ī�����
			overlap=0;	
			// ����ī�� ǥ�ÿ�
			for(int i=0; i<10; i++)
			{
				if(i==card)
				{
					if(cardDeck[i]==2)
						overlap++;	// ��ĥ ���
					else
						cardDeck[i]=cardDeck[i]+1;
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
		
		System.out.printf("P%d�� ī��� %d�Դϴ�.\n",player2.getId(),card2);
		System.out.printf("*P%d�� ī��� %d�Դϴ�.\n\n",player1.getId(),card1);
		
	}
	
	//**********���� �ʱ� �Լ�**********//
	public void initRound(Players player1, Players player2)
	{
		if(same==0)
		{	
			System.out.println("�⺻������ ���ֽʽÿ�.");
			player1.basicBetting();
			player2.basicBetting();
			accBetchips(player1,player2);
		}
		else
			same=0;
	}
	
	//**********�÷��̾� Ĩ�����Լ�**********//
	public void accBetchips(Players player1, Players player2)
	{
		
		total=player1.getAccchip()+player2.getAccchip();
		System.out.printf("P1�Ǵ���Ĩ  %d, P2�� ����Ĩ  %d, �� ������ Ĩ�� ����: %d\n\n"
				,player1.getAccchip(),player2.getAccchip(),total);
	
	}
	
	//**********Ĩ���� üũ�Լ�**********//
	public int checkBet(Players nowP, Players nextP)
	{
		int wrong=0;
		if(nowP.getAccchip()<nextP.getAccchip())
		{
			wrong++;
			System.out.println("�߸������ϼ̽��ϴ�. ������ ������ Ĩ���� ���ų� ũ�� �����ؾ��մϴ�.");
			nowP.wrongBetchips();
		}
		else
		{
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
		int wrong=0;
		
		Scanner yesorno = new Scanner(System.in);
		System.out.printf("%d�÷��̾� : ������ �����Ͻðڽ��ϱ�(y/n)? ",nowP.getId());
		char choice=yesorno.next().charAt(0);
		
		if(choice=='y'||choice=='Y')
		{
			do{
				wrong=0;
				System.out.printf("�󸶸� �����Ͻðڽ��ϱ�? ");
				nowP.betChips();
				
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
	public int EndorNot()
	{
		int endornot=end;
		end=0;
		return endornot;
	}
	
	//**********���ߴ��� Ȯ���Լ�**********//
	public int checkDie(Players player1,Players player2)
	{
		if(player1.getChips()<=0)
		{
			System.out.printf("%d�÷��̾ Ĩ�� ��� �����Ͽ����ϴ�. %d�÷��̾��� �¸��Դϴ�."
					, player1.getId(),player2.getId());
			return 1;
		}
		else if(player1.getChips()<=0)
		{
			System.out.printf("%d�÷��̾ Ĩ�� ��� �����Ͽ����ϴ�. %d�÷��̾��� �¸��Դϴ�."
					, player2.getId(),player1.getId());
			return 1;
		}
		else
			return 0;
	}
}

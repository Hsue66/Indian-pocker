import java.util.Random;
import java.util.Scanner;

public class Dealer  {
	
	private int card1=0;	// 플레이어 1의 카드
	private int card2=0;	// 플레이어 2의 카드
	private int total=0;
	private int end=0;			// 종료
	private int roundlose=0;	// 라운드에서 진
	private int same=0;
	
	Random rand = new Random(); 
	
	// cardDeck 생성
	private int[] cardDeck = new int[10];
	
	//**********카드덱 초기화함수**********//
	public void initCardDeck()
	{
		for(int i=0; i<10; i++)		// cardDeck 0으로 초기화
			cardDeck[i]=0;
	}
	
	
	//**********카드뽑는 함수**********//
	public int pickCard()
	{
		int overlap=0;	// 겹칠 경우
		int card=0;
		do
		{
			card = rand.nextInt(10);	// 랜덤하게 카드생성
			overlap=0;	
			// 나온카드 표시용
			for(int i=0; i<10; i++)
			{
				if(i==card)
				{
					if(cardDeck[i]==2)
						overlap++;	// 겹칠 경우
					else
						cardDeck[i]=cardDeck[i]+1;
				}
			}
		}while(overlap!=0);	//겹칠경우 다시 뽑기
		
		return card+1;	// 카드 반환
	}	
	
	//**********카드분배 함수**********//
	public void distributeCard(Players player1, Players player2)
	{
		System.out.println("카드를 분배합니다.");	
		
		card1=pickCard();
		card2=pickCard();
		
		System.out.printf("P%d의 카드는 %d입니다.\n",player2.getId(),card2);
		System.out.printf("*P%d의 카드는 %d입니다.\n\n",player1.getId(),card1);
		
	}
	
	//**********라운드 초기 함수**********//
	public void initRound(Players player1, Players player2)
	{
		if(same==0)
		{	
			System.out.println("기본배팅을 해주십시오.");
			player1.basicBetting();
			player2.basicBetting();
			accBetchips(player1,player2);
		}
		else
			same=0;
	}
	
	//**********플레이어 칩누적함수**********//
	public void accBetchips(Players player1, Players player2)
	{
		
		total=player1.getAccchip()+player2.getAccchip();
		System.out.printf("P1의누적칩  %d, P2의 누적칩  %d, 총 누적된 칩의 개수: %d\n\n"
				,player1.getAccchip(),player2.getAccchip(),total);
	
	}
	
	//**********칩배팅 체크함수**********//
	public int checkBet(Players nowP, Players nextP)
	{
		int wrong=0;
		if(nowP.getAccchip()<nextP.getAccchip())
		{
			wrong++;
			System.out.println("잘못배팅하셨습니다. 상대방의 누적된 칩수와 같거나 크게 배팅해야합니다.");
			nowP.wrongBetchips();
		}
		else
		{
			nowP.rightBetchips();
			
			// 칩누적
			if(nowP.getId()==1)
				accBetchips(nowP, nextP);
			else
				accBetchips(nextP, nowP);
			
			if(nowP.getAccchip()==nextP.getAccchip())
			{
				System.out.println("같은 수의 칩을 배팅하여 게임을 종료합니다.");
				end=1;
				if(nowP.getId()==1)
					RoundResult(nowP, nextP);
				else
					RoundResult(nextP, nowP);
			}
		}
		return wrong;
	}
	
	//**********칩배팅 진행함수**********//
	public void betting(Players nowP, Players nextP)	// 돈을 배팅하는 기능
	{
		int wrong=0;
		
		Scanner yesorno = new Scanner(System.in);
		System.out.printf("%d플레이어 : 게임을 진행하시겠습니까(y/n)? ",nowP.getId());
		char choice=yesorno.next().charAt(0);
		
		if(choice=='y'||choice=='Y')
		{
			do{
				wrong=0;
				System.out.printf("얼마를 배팅하시겠습니까? ");
				nowP.betChips();
				
				wrong=checkBet(nowP,nextP);
				
			}while(wrong!=0);
		}
		
		else
		{
			System.out.printf("%d 플레이어가 게임을 포기하였습니다.",nowP.getId());
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
	
	//**********라운드 결과함수**********//
	public void RoundResult(Players player1, Players player2)
	{
		if(roundlose!=0)	// 한플레이어가 포기한 경우
		{
			if(roundlose==player1.getLose())
			{
				System.out.printf("%d 플레이어가  승리했습니다.\n", player2.getId());
				player1.changeLose(1);
				if(card1==10)
				{
					System.out.println("10카드이므로 패널티가 적용됩니다. 칩10개를 추가로 잃습니다.");
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
				System.out.printf("%d 플레이어가  승리했습니다.\n", player1.getId());
				player2.changeLose(1);
				if(card2==10)
				{
					System.out.println("10카드이므로 패널티가 적용됩니다. 칩10개를 추가로 잃습니다.");
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
		else	// 동점으로 끝낸경우
		{
			// 카드 체크로 승자 확인
			if(card1>card2)
			{
				System.out.printf("%d 플레이어의 카드가 더 커서 승리했습니다.\n", player1.getId());
				player1.increaseWin(total);
				player1.initBetting();
				player2.initBetting();
				total=0;
			}
			else if(card1<card2)
			{
				System.out.printf("%d 플레이어의 카드가 더 커서 승리했습니다.\n", player2.getId());
				player2.increaseWin(total);
				player1.initBetting();
				player2.initBetting();
				total=0;
			}
			else
			{
				System.out.printf("두 플레이어의 카드가 동일하므로 배팅된 칩들을 유지합니다.\n");
				same++;
			}
		}
		System.out.printf("[ P1의 칩수  %d, P2의 칩수  %d ]\n"
				,player1.getChips(),player2.getChips(),total);
		System.out.printf("[ 1플레이어: %d, 2플레이어: %d ]\n\n",
				player1.getWin(),player2.getWin());
		System.out.println("----------------------------------------");
		System.out.println();
	}
	
	//**********종료 확인함수**********//
	public int EndorNot()
	{
		int endornot=end;
		end=0;
		return endornot;
	}
	
	//**********패했는지 확인함수**********//
	public int checkDie(Players player1,Players player2)
	{
		if(player1.getChips()<=0)
		{
			System.out.printf("%d플레이어가 칩을 모두 소진하였습니다. %d플레이어의 승리입니다."
					, player1.getId(),player2.getId());
			return 1;
		}
		else if(player1.getChips()<=0)
		{
			System.out.printf("%d플레이어가 칩을 모두 소진하였습니다. %d플레이어의 승리입니다."
					, player2.getId(),player1.getId());
			return 1;
		}
		else
			return 0;
	}
}

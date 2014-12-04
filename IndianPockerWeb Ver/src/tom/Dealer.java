package tom;

import java.util.Random;

public class Dealer  {
	
	public static Integer card1=0;	// 플레이어 1의 카드
	public static Integer card2=0;	// 플레이어 2의 카드
	private Integer total=0;		// 총 칩수 
	private Integer end=0;			// 종료
	private Integer roundlose=0;	// 라운드에서 진
	private Integer same=0;			// 동점인 경우
	
	// 코드를 다뜯어고칠수 없어서 -칩의 합 변수 //
	private Integer tot=0;
	
	Random rand = new Random(); 
	
	// cardDeck 생성
	private Integer[] cardDeck = new Integer[10];
	
	//%%%%%%%%%% 카드덱 초기화함수 %%%%%%%%%%//
	public void initCardDeck()
	{
		for(Integer i=0; i<10; i++)		// cardDeck 0으로 초기화
			cardDeck[i]=0;
	}
	
	
	//%%%%%%%%%% 카드뽑는 함수 %%%%%%%%%%//
	public Integer pickCard()
	{
		Integer overlap=0;	// 겹칠 경우
		Integer card=0;
		do
		{
			card = rand.nextInt(10);	// 랜덤하게 카드생성
			overlap=0;	
			// 나온카드 표시용
			for(Integer i=0; i<10; i++)
			{
				if(i==card)
				{
					if(cardDeck[i]==2)
						overlap++;	// 겹칠 경우
					else
						cardDeck[i]=cardDeck[i]+1;	// 해당카드 뽑힌 횟수증가
				}
			}
		}while(overlap!=0);	//겹칠경우 다시 뽑기
		
		return card+1;	// 카드 반환
	}	
	
	//%%%%%%%%%% 카드분배 함수 %%%%%%%%%%//
	public void distributeCard(Players player1, Players player2)
	{
		System.out.println("카드를 분배합니다.");	
		
		card1=pickCard();
		card2=pickCard();
		
		System.out.printf("P%d의 카드는 %d입니다.\n",player1.getId(),card1);
		System.out.printf("P%d의 카드는 %d입니다.\n\n",player2.getId(),card2);
	}
	
	//%%%%%%%%%% 라운드 초기 함수 %%%%%%%%%%//
	public void initRound(Players player1, Players player2)
	{
		if(same==0)			// 이전 라운드에서 다른 카드였던 경우 
		{	
			System.out.println("기본배팅을 해드립니다");
			player1.basicBetting();
			player2.basicBetting();
			accBetchips(player1,player2);
			System.out.println("플레이어1 칩 :" +player1.getChips());
			System.out.println("플레이어2 칩 :" +player2.getChips());
		}
		else				// 이전 라운드에서 같은 카드였던 경우
			same=0;
	}
	
	//%%%%%%%%%% 플레이어 칩누적함수 %%%%%%%%%%//
	public void accBetchips(Players player1, Players player2)
	{
		total=player1.getAccchip()+player2.getAccchip();
	}
	
	//%%%%%%%%%% 라운드 체크 함수 %%%%%%%%%%//
	public Integer checkSame(Players nowP, Players nextP)
	{
		Integer roundEnd=0;		
		
		if(nowP.getAccchip() == nextP.getAccchip())
			roundEnd++;		// 라운드 끝났을 경우

		return roundEnd;
	}
	
	//%%%%%%%%%% 라운드 체크 함수 %%%%%%%%%%//
	public void checkRound(Players nowP, Players nextP, int Ncard)
	{
		tot = nowP.getAccchip() + nextP.getAccchip();	 // 배팅 칩 합치기
		nowP.changeLose(1);		// 플레이어 포기 표시 지우기
		
		if(Ncard==10)			// 포기한 플레이어의 카드가 10인 경우
		{
			nowP.penalty();		// 페널티 부여
			tot=tot+10;
		}
		
		nextP.increaseWin(tot);	// 상대 칩 수 증가
		nowP.initBetting();		// 배팅 초기화
		nextP.initBetting();	// 배팅 초기화
		tot = 0;
	}

	//%%%%%%%%%% 같은 칩 배팅 일때 라운드 체크 함수 %%%%%%%%%% //
	public void checkSamechipRound(Players player1, Players player2)
	{
		tot = player1.getAccchip() + player2.getAccchip();// 배팅 칩 합치기
		
		if(card1 > card2)		// 플레이어 1의 카드가 큰 경우
		{
			player1.increaseWin(tot);	// 1플레이어 칩 수 증가
			player1.initBetting();		// 배팅 초기화
			player2.initBetting();		// 배팅 초기화
			tot=0;
		}
		else if(card1 < card2)	// 플레이어 2의 카드가 큰 경우
		{
			player2.increaseWin(tot);	// 1플레이어 칩 수 증가
			player1.initBetting();		// 배팅 초기화
			player2.initBetting();		// 배팅 초기화
			tot=0;
		}
		else					// 카드가 같은 경우
		{
			System.out.printf("두 플레이어의 카드가 동일하므로 배팅된 칩들을 유지합니다.\n");
			System.out.printf("P1의 누적 칩: %d\nP2의 누적 칩: %d", player1.getAccchip(), player2.getAccchip());
			same++;		// 카드 같았다고 표시
		}
	}
	
}


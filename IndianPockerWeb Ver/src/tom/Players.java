package tom;

public class Players {

	private Integer chips;		// 가지고 있는 칩의 개수
	private Integer betchip;	// 배팅한 칩의 개수
	private Integer accchip;	// 배팅된 칩들을 합친 개수
	private Integer id;			// 플레이어 구분
	private Integer nowlose;	// 지금라운드 진 경우
	private Integer win;		// 게임이긴 수
	
	//%%%%%%%%%% 플레이어 초기화 함수 %%%%%%%%%%//
	public void Init(int id)	
	{
		this.id=id;	
		chips=20;		// 초기 칩의 개수
		
		// 인자초기화
		betchip=0;	
		accchip=0;
		nowlose=0;
		win=0;
	}

	// 인자들 get함수
	public Integer getWin()
	{
		return win;
	}
	public Integer getLose()
	{
		return nowlose;
	}
	public Integer getId()
	{
		return id;
	}
	public int getAccchip()
	{
		return accchip;
	}
	public Integer getChips()
	{
		return chips;
	}
	public Integer getBetchip() {
		return betchip;
	}

	
	//%%%%%%%%%% 배팅한 칩 수 받아오는 함수 %%%%%%%%%%//
	public void setBetchip(String betinput) 
	{
		try{
			this.betchip = Integer.parseInt(betinput);
		}
		catch(NumberFormatException e)
		{
			this.betchip = -1;
		}
	}
	
	//%%%%%%%%%% 승리한 횟수함수 %%%%%%%%%%//
	public void increaseWin(Integer earn)
	{
		win++;
		chips=earn+chips;
	}
	
	//%%%%%%%%%% 라운드 포기 표시 함수 %%%%%%%%%%//
	public void changeLose(Integer n)
	{
		if(n==0)
			nowlose++;
		else
			nowlose--;
	}
	
	//%%%%%%%%%% 배팅초기화함수 %%%%%%%%%%//
	public void initBetting()
	{
		accchip=0;
		betchip=0;
	}
			
	//%%%%%%%%%% 기본배팅함수 %%%%%%%%%%//
	public void basicBetting()
	{
		// 기본 배팅
		accchip=accchip+1;
		chips=chips-1;
	}
	
	//%%%%%%%%%% 패널티함수 %%%%%%%%%%//
	public void penalty()
	{
		accchip=accchip+10;
		chips=chips-10;
	}
	
	//%%%%%%%%%% 칩베팅함수 %%%%%%%%%%//
	public int betChips()	// 돈을 베팅하는 기능
	{
		if(betchip<0) return -1;		// 숫자베팅이 아닐 경우
		else if(betchip>chips) return 1;// 본인칩보다 많이 베팅 했을 경우
		else if(betchip==0)	return 2;	// 베팅을 포기했을 경우
		else {							// 정상적인 베팅일 경우
			accchip = betchip+accchip;	// 칩 누적
			chips=chips-betchip;		// 소유 칩에서 빼기
			return 3;
		}
	}
	
	//%%%%%%%%%% 베팅 수정 함수 %%%%%%%%%%//
	public void wrongBetchips()
	{
		accchip = accchip-betchip;	// 칩 원상복귀
		chips=chips+betchip;
	}
	
	//%%%%%%%%%% 제대로 베했을때  함수 %%%%%%%%%%//
	public void rightBetchips()
	{
		System.out.printf("%d개 칩을 배팅했습니다.누적 %d \n",betchip,accchip);
	}
	
}

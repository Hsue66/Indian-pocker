import java.util.Scanner;

public class Players {

	private int chips;		// 가지고 있는 칩의 개수
	private int betchip;	// 배팅한 칩의 개수
	private int accchip;	// 배팅된 칩들을 합친 개수
	private int id;			// 플레이어 구분
	private int nowlose;	// 지금라운드 진 경우
	private int win;		// 게임이긴 수
	
	public Players(int id)	// 생성자
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
	public int getWin()
	{
		return win;
	}
	public int getLose()
	{
		return nowlose;
	}
	public int getId()
	{
		return id;
	}
	public int getAccchip()
	{
		return accchip;
	}
	public int getChips()
	{
		return chips;
	}
	
	
	//**********승리한 횟수함수**********//
	public void increaseWin(int earn)
	{
		win++;
		chips=earn+chips;
	}
	
	//**********라운드 패한횟수함수**********//
	public void changeLose(int n)
	{
		if(n==0)
			nowlose++;
		else
			nowlose--;
	}
	//**********배팅초기화함수**********//
	public void initBetting()
	{
		accchip=0;
		betchip=0;
	}
			
	//**********기본배팅함수**********//
	public void basicBetting()
	{
		// 기본 배팅
		accchip=accchip+1;
		chips=chips-1;
	}
	
	//**********패널티함수**********//
	public void penalty()
	{
		accchip=accchip+10;
		chips=chips-10;
	}
	
	//**********칩배팅함수**********//
	public void betChips()	// 돈을 배팅하는 기능
	{
		int wrong=0;
		
		do
		{
			wrong=0;
			Scanner betting = new Scanner(System.in);
			betchip = betting.nextInt();
				
			if(betchip>chips)	// 칩을 잘못배팅 했을 경우
			{
				System.out.printf("가진 칩 수보다  많습니다. 다시 입력해주십시오: ");
				wrong++;
			}
		}while(wrong!=0);	
		accchip = betchip+accchip;	// 칩 누적
		chips=chips-betchip;	// 소유 칩에서 빼기
	}
	
	//**********배팅 수정 함수**********//
	public void wrongBetchips()
	{
		accchip = accchip-betchip;	// 칩 원상복귀
		chips=chips+betchip;
	}
	
	//**********제대로 배팅했을때  함수**********//
	public void rightBetchips()
	{
		System.out.printf("%d개 칩을 배팅했습니다.누적 %d \n",betchip,accchip);
	}
}

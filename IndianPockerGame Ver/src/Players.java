import java.util.Scanner;

public class Players {

	private int chips;		// ������ �ִ� Ĩ�� ����
	private int betchip;	// ������ Ĩ�� ����
	private int accchip;	// ���õ� Ĩ���� ��ģ ����
	private int id;			// �÷��̾� ����
	private int nowlose;	// ���ݶ��� �� ���
	private int win;		// �����̱� ��
	
	public Players(int id)	// ������
	{
		this.id=id;	
		chips=20;		// �ʱ� Ĩ�� ����
		
		// �����ʱ�ȭ
		betchip=0;	
		accchip=0;
		nowlose=0;
		win=0;
	}
	
	// ���ڵ� get�Լ�
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
	
	
	//**********�¸��� Ƚ���Լ�**********//
	public void increaseWin(int earn)
	{
		win++;
		chips=earn+chips;
	}
	
	//**********���� ����Ƚ���Լ�**********//
	public void changeLose(int n)
	{
		if(n==0)
			nowlose++;
		else
			nowlose--;
	}
	//**********�����ʱ�ȭ�Լ�**********//
	public void initBetting()
	{
		accchip=0;
		betchip=0;
	}
			
	//**********�⺻�����Լ�**********//
	public void basicBetting()
	{
		// �⺻ ����
		accchip=accchip+1;
		chips=chips-1;
	}
	
	//**********�г�Ƽ�Լ�**********//
	public void penalty()
	{
		accchip=accchip+10;
		chips=chips-10;
	}
	
	//**********Ĩ�����Լ�**********//
	public void betChips()	// ���� �����ϴ� ���
	{
		int wrong=0;
		
		do
		{
			wrong=0;
			Scanner betting = new Scanner(System.in);
			betchip = betting.nextInt();
				
			if(betchip>chips)	// Ĩ�� �߸����� ���� ���
			{
				System.out.printf("���� Ĩ ������  �����ϴ�. �ٽ� �Է����ֽʽÿ�: ");
				wrong++;
			}
		}while(wrong!=0);	
		accchip = betchip+accchip;	// Ĩ ����
		chips=chips-betchip;	// ���� Ĩ���� ����
	}
	
	//**********���� ���� �Լ�**********//
	public void wrongBetchips()
	{
		accchip = accchip-betchip;	// Ĩ ���󺹱�
		chips=chips+betchip;
	}
	
	//**********����� ����������  �Լ�**********//
	public void rightBetchips()
	{
		System.out.printf("%d�� Ĩ�� �����߽��ϴ�.���� %d \n",betchip,accchip);
	}
}

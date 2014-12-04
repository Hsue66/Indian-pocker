package tom;

public class Players {

	private Integer chips;		// ������ �ִ� Ĩ�� ����
	private Integer betchip;	// ������ Ĩ�� ����
	private Integer accchip;	// ���õ� Ĩ���� ��ģ ����
	private Integer id;			// �÷��̾� ����
	private Integer nowlose;	// ���ݶ��� �� ���
	private Integer win;		// �����̱� ��
	
	//%%%%%%%%%% �÷��̾� �ʱ�ȭ �Լ� %%%%%%%%%%//
	public void Init(int id)	
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

	
	//%%%%%%%%%% ������ Ĩ �� �޾ƿ��� �Լ� %%%%%%%%%%//
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
	
	//%%%%%%%%%% �¸��� Ƚ���Լ� %%%%%%%%%%//
	public void increaseWin(Integer earn)
	{
		win++;
		chips=earn+chips;
	}
	
	//%%%%%%%%%% ���� ���� ǥ�� �Լ� %%%%%%%%%%//
	public void changeLose(Integer n)
	{
		if(n==0)
			nowlose++;
		else
			nowlose--;
	}
	
	//%%%%%%%%%% �����ʱ�ȭ�Լ� %%%%%%%%%%//
	public void initBetting()
	{
		accchip=0;
		betchip=0;
	}
			
	//%%%%%%%%%% �⺻�����Լ� %%%%%%%%%%//
	public void basicBetting()
	{
		// �⺻ ����
		accchip=accchip+1;
		chips=chips-1;
	}
	
	//%%%%%%%%%% �г�Ƽ�Լ� %%%%%%%%%%//
	public void penalty()
	{
		accchip=accchip+10;
		chips=chips-10;
	}
	
	//%%%%%%%%%% Ĩ�����Լ� %%%%%%%%%%//
	public int betChips()	// ���� �����ϴ� ���
	{
		if(betchip<0) return -1;		// ���ں����� �ƴ� ���
		else if(betchip>chips) return 1;// ����Ĩ���� ���� ���� ���� ���
		else if(betchip==0)	return 2;	// ������ �������� ���
		else {							// �������� ������ ���
			accchip = betchip+accchip;	// Ĩ ����
			chips=chips-betchip;		// ���� Ĩ���� ����
			return 3;
		}
	}
	
	//%%%%%%%%%% ���� ���� �Լ� %%%%%%%%%%//
	public void wrongBetchips()
	{
		accchip = accchip-betchip;	// Ĩ ���󺹱�
		chips=chips+betchip;
	}
	
	//%%%%%%%%%% ����� ��������  �Լ� %%%%%%%%%%//
	public void rightBetchips()
	{
		System.out.printf("%d�� Ĩ�� �����߽��ϴ�.���� %d \n",betchip,accchip);
	}
	
}

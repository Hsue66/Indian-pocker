package tom;

public class PlayerInfo {

	private String userid;			// ����� ID
	private Integer userchip;		// ����� Ĩ
	private Integer usercard;		// ����� ī��
	private Integer betchip;		// ����� ���� Ĩ
	private Integer useraccchips;	// ����� ���� Ĩ
	private Integer userorder;		// �÷���  ����
	
	// ����� ID getter & setter //
	public void setUserid(String userid)
	{
		this.userid = userid;
	}
	public String getUserid()
	{
		return userid;
	}

	// ����� Ĩ getter & setter //
	public void setUserchip(Integer userchip)
	{
		this.userchip = userchip;
	}
	public Integer getUserchip()
	{
		return userchip;
	}
	
	// ����� ī�� getter & setter //
	public void setUsercard(Integer usercard)
	{
		this.usercard = usercard;
	}
	public Integer getUsercard()
	{
		return usercard;
	}
	
	// ����� ���� Ĩ getter & setter //
	public void setBetchip(Integer betchip)
	{
		this.betchip = betchip;
	}
	public Integer getBetchip()
	{
		return betchip;
	}
	
	// ����� ���� Ĩ getter & setter //
	public Integer getUseraccchips() 
	{
		return useraccchips;
	}
	public void setUseraccchips(Integer useraccchips) 
	{
		this.useraccchips = useraccchips;
	}
	
	// �÷��� ���� getter & setter //
	public void setUserorder(Integer userorder)
	{
		this.userorder = userorder;
	}
	public Integer getUserorder()
	{
		return userorder;
	}

}

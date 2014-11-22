package tom;

public class PlayerInfo {

	private Integer userchip;
	private String userid;
	private Integer usercard;
	private Integer betchip;
	//**********************//
	private Integer userorder;
	private Integer useraccchips;
	
	public Integer getUseraccchips() 
	{
		return useraccchips;
	}
	
	public void setUseraccchips(Integer useraccchips) 
	{
		this.useraccchips = useraccchips;
	}
	
	public void setUserorder(Integer userorder)
	{
		this.userorder = userorder;
	}
	public Integer getUserorder()
	{
		return userorder;
	}
	//**********************//
	
	public void setUserid(String userid)
	{
		this.userid = userid;
	}
	public String getUserid()
	{
		return userid;
	}
	
	public void setUserchip(Integer userchip)
	{
		this.userchip = userchip;
	}
	
	public Integer getUserchip()
	{
		return userchip;
	}
	
	public void setUsercard(Integer usercard)
	{
		this.usercard = usercard;
	}
	public Integer getUsercard()
	{
		return usercard;
	}
	
	public void setBetchip(Integer betchip)
	{
		this.betchip = betchip;
	}
	
	public Integer getBetchip()
	{
		return betchip;
	}

}

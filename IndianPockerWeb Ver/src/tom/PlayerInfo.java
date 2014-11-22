package tom;

public class PlayerInfo {

	private String userid;			// 사용자 ID
	private Integer userchip;		// 사용자 칩
	private Integer usercard;		// 사용자 카드
	private Integer betchip;		// 사용자 배팅 칩
	private Integer useraccchips;	// 사용자 누적 칩
	private Integer userorder;		// 플레이  순서
	
	// 사용자 ID getter & setter //
	public void setUserid(String userid)
	{
		this.userid = userid;
	}
	public String getUserid()
	{
		return userid;
	}

	// 사용자 칩 getter & setter //
	public void setUserchip(Integer userchip)
	{
		this.userchip = userchip;
	}
	public Integer getUserchip()
	{
		return userchip;
	}
	
	// 사용자 카드 getter & setter //
	public void setUsercard(Integer usercard)
	{
		this.usercard = usercard;
	}
	public Integer getUsercard()
	{
		return usercard;
	}
	
	// 사용자 배팅 칩 getter & setter //
	public void setBetchip(Integer betchip)
	{
		this.betchip = betchip;
	}
	public Integer getBetchip()
	{
		return betchip;
	}
	
	// 사용자 누적 칩 getter & setter //
	public Integer getUseraccchips() 
	{
		return useraccchips;
	}
	public void setUseraccchips(Integer useraccchips) 
	{
		this.useraccchips = useraccchips;
	}
	
	// 플레이 순서 getter & setter //
	public void setUserorder(Integer userorder)
	{
		this.userorder = userorder;
	}
	public Integer getUserorder()
	{
		return userorder;
	}

}

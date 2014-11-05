package login;

public class Register {
	private String nickName = "";
	
	public boolean checkNickName(){
		boolean check;
		if(nickName == "")
			check = false;
		else
			check = true;
		
		return check;
	}
	public void setNickName(String nickName){
		this.nickName = nickName;
	}
	public String getNickName(){
		return nickName;
	}
}

package user;

public class UserVO {
	private String userid,userPwd,userName,userGender,userEmail;
	private int admin;
	public UserVO() {
		super();
	}

	public UserVO(String userid, String userPwd, String userName, String userGender, String userEmail,int admin) {
		super();
		this.userid = userid;
		this.userPwd = userPwd;
		this.userName = userName;
		this.userGender = userGender;
		this.userEmail = userEmail;
		this.admin = admin;
	}
	
	public UserVO(String userid,String userName,int admin) {
		super();
		this.userid = userid;
		this.userName = userName;
		this.admin = admin;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public int getAdmin() {
		return admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "UserVO [userid=" + userid + ", userPwd=" + userPwd + ", userName=" + userName + ", userGender="
				+ userGender + ", userEmail=" + userEmail + ", admin=" + admin + "]";
	}

	
	
}

package comment;

import java.sql.Date;

public class ScoreVO {
	
	private int sno, num;
	private String userid;
	private double score;
	private String cmt;
	private Date regdate;
	
	
	public ScoreVO() {
		super();
	}
	public ScoreVO(int sno, int num, String userid, double score, String cmt, Date regdate) {
		super();
		this.sno = sno;
		this.num = num;
		this.userid = userid;
		this.score = score;
		this.cmt = cmt;
		this.regdate = regdate;
	}
	public ScoreVO(String userid, double score, String cmt) {
		super();
		this.userid = userid;
		this.score = score;
		this.cmt = cmt;
	}
	public int getSno() {
		return sno;
	}
	public void setSno(int sno) {
		this.sno = sno;
	}
	public int getnum() {
		return num;
	}
	public void setnum(int num) {
		this.num = num;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getCmt() {
		return cmt;
	}
	public void setCmt(String cmt) {
		this.cmt = cmt;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	@Override
	public String toString() {
		return "ScoreVO [sno=" + sno + ", num=" + num + ", userid=" + userid + ", score=" + score + ", cmt=" + cmt
				+ ", regdate=" + regdate + "]";
	}
	
	
}

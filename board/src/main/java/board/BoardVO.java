package board;

import java.sql.Date;

public class BoardVO {
	private int num;
	private String writer, subject, email, content, password;
	private Date reg_date;
	private int readcount;
	private String ip;
	private int ref,re_step,re_level;
	public BoardVO() {
		super();
	}
	public BoardVO(int num, String writer, String subject, String email, String content, String password, Date reg_date,
			int readcount, String ip, int ref, int re_step, int re_level) {
		super();
		this.num = num;
		this.writer = writer;
		this.subject = subject;
		this.email = email;
		this.content = content;
		this.password = password;
		this.reg_date = reg_date;
		this.readcount = readcount;
		this.ip = ip;
		this.ref = ref;
		this.re_step = re_step;
		this.re_level = re_level;
	}
	
	public BoardVO(int num, String writer, String subject, String email, String content, String password, Date reg_date,
			int readcount) {
		super();
		this.num = num;
		this.writer = writer;
		this.subject = subject;
		this.email = email;
		this.content = content;
		this.password = password;
		this.reg_date = reg_date;
		this.readcount = readcount;
		
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public int getReadcount() {
		return readcount;
	}
	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getRef() {
		return ref;
	}
	public void setRef(int ref) {
		this.ref = ref;
	}
	public int getRe_step() {
		return re_step;
	}
	public void setRe_step(int re_step) {
		this.re_step = re_step;
	}
	public int getRe_level() {
		return re_level;
	}
	public void setRe_level(int re_level) {
		this.re_level = re_level;
	}
	@Override
	public String toString() {
		return "BoardVO [num=" + num + ", writer=" + writer + ", subject=" + subject + ", email=" + email + ", content="
				+ content + ", password=" + password + ", reg_date=" + reg_date + ", readcount=" + readcount + ", ip="
				+ ip + ", ref=" + ref + ", re_step=" + re_step + ", re_level=" + re_level + "]";
	}
	
	
	
	
}

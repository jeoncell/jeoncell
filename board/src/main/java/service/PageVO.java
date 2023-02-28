package service;

public class PageVO {
	//page 나누기 정보를 담을 객체
	//paging 을 수행하기위한 기본 변수 (설정)
	private int page; //현재 페이지 (get)
    private int totalCount; //row 전체의 수 (get)
    private int displayRow =10;  //한 페이지에 몇 개의 로우 (선택 set)
    private int displayPage =10;  //한 페이지에 몇 개의 페이지 (선택 set)
    //paging 메소드 수행 후 값이 설정되는 변수
    private int beginPage;  //출력 시작
    private int endPage;    //출력 끝
    boolean prev; //prev 버튼이 보일건지 안보일건지
    boolean next; //next 버튼이 보일건지 안보일건지
    //디폴트 생성자 x ;
    public PageVO(int page, int totalCount, int displayRow, int displayPage) {
		super();
		this.page = page;
		this.totalCount = totalCount;
		this.displayRow = displayRow;
		this.displayPage = displayPage;
	}
  //키워드 검색용
    private String columnName; //데이터베이스 컬럼명
	private String keyword; //입력한 키워드 값
    
    public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    public int getDisplayRow() {
        return displayRow;
    }
    public void setDisplayRow(int displayRow) {
        this.displayRow = displayRow;
    }
    public int getDisplayPage() {
        return displayPage;
    }
    public void setDisplayPage(int displayPage) {
        this.displayPage = displayPage;
    }
    public int getBeginPage() {
        return beginPage;
    }
    public int getEndPage() {
        return endPage;
    }
    public boolean isPrev() {
        return prev;
    }
    public boolean isNext() {
        return next;
    }
    
 // 도서점수 댓글관련하여 Ajax 아래로 출력하기 위한 설정값 : 더보기 버튼 활성/비활성화 결정
    public boolean pagingStar(){
        endPage = ((int)Math.ceil(page/(double)displayPage))*displayPage;
        int totalPage = (int)Math.ceil(totalCount/(double)displayRow);
        
        if(totalPage<endPage){
            endPage = totalPage;
            return false;
        }else{
            return true;
        }
    }
    public void paging(){
        // prev, next, beginPage, endPage를 계산해서 만든다.
        // 2+9 = 11, 11/10 = 1, 1*10 = 10
        // 10+9 = 19, 19/10 = 1, 1*10 = 10
        // 11+9 = 20, 20/10 = 2, 2*10 = 20
        // 20+9 = 29, 29/10 = 2, 2*10 = 20
        // 111+9 = 120 120/10 = 12, 12*10 = 120
        
        // (2+9)/10 * 10 (1번 방법)
        //endPage = ((page+(displayPage-1))/displayPage)*displayPage;
        
        // 1/10 0.1(올림) 1 (2번 방법) : 현재 페이지 수가 (1~10) 이면, 표시페이지 수 1~10 페이지
        endPage = ((int)Math.ceil(page/(double)displayPage))*displayPage;
        System.out.println("endPage : " + endPage);
        
        beginPage = endPage - (displayPage - 1);
        System.out.println("beginPage : " + beginPage);
        
        // 글 32개
        // 32/10 = 3.2 (올림) 4페이지
        // 2=?  2/10
        int totalPage = (int)Math.ceil(totalCount/(double)displayRow);
        
        if(totalPage<endPage){
            endPage = totalPage;
            next = false;
        }else{
            next = true;
        }
        prev = (beginPage==1)?false:true;//page가 11이상에만 나온다.
        System.out.println("endPage : " + endPage);
        System.out.println("totalPage : " + totalPage);       
    }
    
    
    
    
  
  	
    
    
    
    
    
    
    
}

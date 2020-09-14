package goru_firstDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class put_category {
	public static void category() {
		List p_cate = new ArrayList();
		String strQ = "";
		DB_MAN DBM = new DB_MAN(); //DB를 열고 실행시키고 닫아주는 class 객체 생성
		
		Document doc;
		try {
			doc = (Document) Jsoup.connect("https://www.gettyimagesgallery.com/collections/").get();
			//갤러리 홈페이지
			int cnt = 0;
			Elements elements = doc.select(".nav-item a");
			//카테고리 중 큰 분류를 가져옴
			System.out.println("큰 카테고리를 가져옵니다.---------------------------------------");
			for(Element e : elements){ //가져온 큰 분류만큼 리스트에 추가한다
				String text = e.text();
				text = text.replaceAll("'","''");
				p_cate.add(text);
				System.out.println(p_cate.get(cnt++));
			}System.out.println("-------------------------------------------------------\n");
			
			cnt=-1; //디비에 넣을 경우 (p_category-큰분류, c_category-작은 분류)순으로 넣을 것임
			//작은 분류의 첫시작은 All이라는 카테고리임 >> 작은분류에 있는 사진 모음 >> 따라서 필요가 없음
			//작은 분류에서 All은 만나면 큰분류의 시작임
			//따라서 All을 만나면 cnt를 1씩 증가 시키면 0부터 시작하게됨
			//이 0으로 해당하는 p_category를 처음부터 꺼내올 수 있음
			
			elements = doc.select(".row .col-md-3 a"); //큰 분류 밑 작은 분류를 가져옴 cnt=0; List c_cate
			System.out.println("큰 카테고리 밑 작은 카테고리를 가져옵니다.--------------------------------");
			DBM.dbOpen();
			strQ="delete from category;";
			DBM.DB_stmt.executeUpdate(strQ);
			for(Element e : elements){ 
				strQ = "Insert into category(p_category, c_category, link) values('"; //db에 넣을 sql문 작성
				
				if(e.text().substring(0, 3).equals("All")) { 
					cnt++; 
					continue; //all을 만나면 작은분류가 아니므로 큰분류 순서만 증가시키고 처음부터 시작 
				}
				String text = e.text();
				text = text.replaceAll("'","''"); //혹시 작은 분류 중 '이 있다면 sql문이라 앞에 ''로 치환해준다
				
				strQ += p_cate.get(cnt)+"', '"+text+"', '"+e.attr("href")+"');"; //sql문 완성
				DBM.DB_stmt.executeUpdate(strQ); //sql문 실행
				System.out.println("*********sql문 시작*********\n"+strQ+"*********sql문 끝*********\n"); //sql문이 제대로 만들어졌는지 확인하기 위함
			}
			//디비에 잘 들어갔는지 확인
			System.out.println("***************db 출력 시작******************");
			strQ="select * from category;"; 
			cnt = 1;
			DBM.DB_rs = DBM.DB_stmt.executeQuery(strQ);
			while(DBM.DB_rs.next()) {
				System.out.println((cnt++)+"번 출력!");
				System.out.println(DBM.DB_rs.getString("p_category"));
				System.out.println(DBM.DB_rs.getString("c_category"));
				System.out.println(DBM.DB_rs.getString("link"));
			} 
			DBM.dbClose();
			System.out.println("***************db 출력 끝******************");
			System.out.println("-------------------------------------------------------\n");
		} catch (IOException e1) { //혹시 모를 에러처리
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (Exception e) {
            System.out.println("SQLException : "+e.getMessage());
        }
	}
}
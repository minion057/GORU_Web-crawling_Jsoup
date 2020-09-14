package goru_firstDB;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class put_product {
	static ArrayList<ArrayList<String>> cate = new ArrayList<ArrayList<String>>();
	static String strQ = ""; //sql문
	static String name="", keyword="", link="", imglink=""; //디비에 넣을 것
	static DB_MAN DBM = new DB_MAN(); //DB를 열고 실행시키고 닫아주는 class 객체 생성
	
	public static void product() {
				
		Document doc;
        Elements elements;
		try {
			int cnt = 0;
			DBM.dbOpen();
			strQ="select * from category;";
			DBM.DB_rs = DBM.DB_stmt.executeQuery(strQ);
            while(DBM.DB_rs.next()){
            	ArrayList<String> ele = new ArrayList<String>();
                ele.add(DBM.DB_rs.getString("p_category"));
                ele.add(DBM.DB_rs.getString("c_category"));
                ele.add(DBM.DB_rs.getString("link"));    
                cate.add(ele);
                cnt++;
            } 
			strQ="delete from product;";
			DBM.DB_stmt.executeUpdate(strQ);
            DBM.dbClose();
            for(int c = 0; c < cate.size() ; c++) {
            	doc = (Document)Jsoup.connect(cate.get(c).get(2)).get();
            	elements = doc.select(".grid-item a"); //이미지 or 세부 카테고리 herf
            	
    			
            	if(doc.select(".collection-title").size()>1) { //타이틀 1개 : 작은 카테고리 / 그 이상은 세부 카테고리가 더 있는 상태
            		// 큰 카테고리 >  작은 카테고리 > 더 작은 카테고리(keyword)
            		int title = doc.select(".collection-title").size()-1;
            		cnt = 0;
            		ArrayList<String> lnks = new ArrayList<String>();
            		ArrayList<String> keys = new ArrayList<String>();
            		for(Element e : elements){
            			if(cnt >= title)	break;
            			//collection-title : 타이틀(c_category) 1개 + 세부타이틀(keyword)로 구성
            			//필요한 것은 세부타이틀 따라서 0부터 시작하니 1부터 시작하면 세부타이틀부터 가져옴
            			//따라서 사이즈도 1을 줄여야 함
        				keys.add("#"+doc.select(".collection-title").get(cnt+1).text().replace(" ", "#"));
        				lnks.add(e.attr("href"));
                       	cnt+=1;
            		}
            		setsql(doc, "", keys.size(), c);
            		for(int e = 0; e<keys.size();e++) {
                      	doc = (Document)Jsoup.connect(lnks.get(e)).get();
                      	setsql(doc, keys.get(e), 0, c);
            		}
            	}else {
            		// 큰 카테고리  > 작은 카테고리 > 갤러리
            		setsql(doc, "", 0, c);
            	}
            }
			
			
		} catch (IOException e1) { //혹시 모를 에러처리
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (Exception e) {
            System.out.println("SQLException : "+e.getMessage());
        }
	}

	private static void setsql(Document doc, String key, int sebu, int c) {
		Elements elements = doc.select(".item-wrapper img");
		int cnt = 0;
		System.out.println(cate.get(c).get(1)+" 실행중");
		for(Element e : elements){
			if(sebu != 0) {
				if(sebu > cnt) {
					cnt++;
					continue;
				}
			}
			keyword=key;
			String[] a = e.attr("alt").split(" ");
			for(int q = 0 ; q < a.length; q++) {
				if(a[q].equals("from")) break;
				keyword+="#"+a[q];
				if(q==0) {
					name = a[q];
				}else {
					name += " "+a[q];
				}
			}
			imglink = e.attr("data-src");
			link=doc.select(".grid-item a").get(cnt).attr("href");
			cnt++;
			
			try {
				DBM.dbOpen();
				
				strQ = "insert into product(name, c_category, keyword, link, img_link) values('";
				name = name.replaceAll("'","''"); //혹시 작은 분류 중 '이 있다면 sql문이라 앞에 ''로 치환해준다
				keyword = keyword.replaceAll("'","''"); //혹시 작은 분류 중 '이 있다면 sql문이라 앞에 ''로 치환해준다
				strQ+=name+"', '"+cate.get(c).get(1).replaceAll("'","''")+"', '"+keyword+"', '"+link+"', '"+imglink+"');";
				DBM.DB_stmt.executeUpdate(strQ); //sql문 실행
				
				DBM.dbClose();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
	}
}
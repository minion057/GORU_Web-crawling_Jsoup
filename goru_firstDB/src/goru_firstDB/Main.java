package goru_firstDB;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		for(;;) {
			int answer = menu();
			switch(answer) {
			case 1:
				put_category.category();
				System.out.println("실행완료.\n");
				continue; //다시 메뉴를 입력하게 한다.
			case 2:
				put_product.product();
				System.out.println("실행완료.\n");
				break;
			case 3:
				System.out.println("안녕히가세요.");
				System.exit(0); //메뉴 3번은 프로그램 종료이므로 종료한다.
			default:
				break;			
			}
		}
	}
	
	public static int menu() { //메뉴를 나타내는 메소드
		String menu = "1. 카테고리 테이블 생성 (주의! 기존 테이블이 사라지고 새로 생성합니다.) \n"+
				  "2. 이미지 테이블 생성 (주의! 기존 테이블이 사라지고 새로 생성합니다.)\n"+
				  "3. 프로그램 종료\n"+
				  "원하는 메뉴를 숫자로만 입력 후 엔터를 눌러주세요.\n";
		int answer = 0;
		Scanner scan = new Scanner(System.in);
		for(;;) {
			System.out.println(menu);
			answer = intvar(scan.nextLine());
			if(answer >=1 && answer <= 3) break;
			System.out.println(menu);
		} 
		return answer;
	}
	
	public static int intvar(String sc) { //메뉴입력한 것을 숫자로 바꿔주는 메소드
		int ans = 0; //1,2,3이여야 정상적으로 메뉴를 입력한 것
		try {
			ans =  Integer.parseInt(sc); //String 형을 Integer 형으로 형변환.
		} catch (NumberFormatException e) {
			return ans;
		}
		return ans;
	}
}
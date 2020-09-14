# GORU _ Web Image crawling(Jsoup)

GORU 애플리케이션에 Data로 활용할 이미지 정보를 크롤링하는 프로그램입니다.   
https://www.gettyimagesgallery.com/collections/   
위 사이트에서 사진, 사진 키워드, URL을 수집하였습니다.   

   
     
## DB 구조   
   
![00.DB 구조.png](/img/00.db%20structure.PNG)    
category - 사진의 카테고리 정보 (큰 카테고리와 세부 카테고리로 나뉘어진다)   
product - 사진의 정보 (시진 제목, 사진 키워드, 사진 URL 등)   
   
      
## 카테고리 수집      
> __<카테고리 - p_category>__  
>    
> ![01.카테고리 코드.png](/img/01.p_category.png)   
> 오른쪽 사이트는 이미지를 수집하는 사이트의 모습이다.    
> 사이트 메뉴 Collections은 이미지 카테고리를 뜻하는데, 여기서 모든 카테고리를 뜻하는 Image Library를 제외하고 수집한다.    
      
   
>  __<세부 카테고리 - c_category>__   
>    
> ![02.세부 카테고리 코드.png](/img/02.c_category.png)      
> 아래 사이트는 이미지를 수집하는 사이트의 모습이다.    
> 각 카테고리 밑에 세부 카테고리가 존재하는데, 여기서 모든 카테고리를 뜻하는 All를 제외하고 수집한다.    
      
   
> __<Jsoup으로 crawling한 결과>__  
>    
> ![03.카테고리 수집 실행 결과.png](/img/03.menu1_result.png)   
      
   
> __<Jsoup으로 crawling한 결과 DB에 들어간 데이터>__  
>    
> ![03.카테고리 수집 실행 결과 data.png](/img/03.menu1_result_DBdataExample.PNG)     
      
     
    
## 사진 수집      
> __<사진 정보>__   
>    
> ![04.이미지 수집 예시.png](/img/04.img_info.png)   
> 오른쪽 사이트는 이미지를 수집하는 사이트의 모습이다.    
> 사진에 녹색으로 표시한 부분만 정보를 수집한다.
> 단, 키워드는 구분을 위해 키워드가 끝나면 #으로 구분을 준다.
      
   
> __<Jsoup으로 crawling한 결과>__   
>    
> ![05.이미지 수집 실행 결과.png](/img/05.menu2_result.PNG)   
        
   
> __<Jsoup으로 crawling한 결과 DB에 들어간 데이터>__  
>    
> ![05.이미지 수집 실행 결과 data.png](/img/05.menu2_result_DBdataExample.PNG)   

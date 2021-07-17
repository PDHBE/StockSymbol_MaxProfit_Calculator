# Stock Symbol Max Profit Calculator

## 소개

<img width="576" alt="스크린샷 2021-06-18 오후 8 38 08" src="https://user-images.githubusercontent.com/82703938/122555855-ca606c80-d075-11eb-84b6-31ef78451a1e.png">

<img width="511" alt="스크린샷 2021-06-19 오후 1 06 23" src="https://user-images.githubusercontent.com/82703938/122630518-4ce13e80-d0ff-11eb-8a49-d09c155fd155.png">

US stock symbol(e.g. AAPL, GOOG) 입력시, 지난 180 일의 __max profit__ 을 계산하는 웹 서비스

---

## 기술 스택

- Java / Spring(Boot)

- HTML/CSS, BootStrap, Thymeleaf

- Maven

---

## 구조

<img width="413" alt="스크린샷 2021-07-06 오후 6 38 45" src="https://user-images.githubusercontent.com/82703938/124578965-ab414780-de89-11eb-8a18-4a9784258f28.png">
<img width="279" alt="스크린샷 2021-07-06 오후 6 39 40" src="https://user-images.githubusercontent.com/82703938/124579027-bb592700-de89-11eb-9d43-5eaca88a5474.png">

DDD(도메인 주도 설계)를 참고하여 ui(표현) 계층, application(응용) 계층, domain 계층, infrastructure 계층으로 분리하였습니다.

### ui(표현) 계층

- StockController : US Stock Symbol 에 대한 Max Profit 요청을 매핑하는 역할

- ExceptionController : StockController 에서 발생하는 모든 예외를 처리하는 역할

### application(응용) 계층

- StockService : 도메인 객체 리스트를 받아 도메인 서비스를 호출하는 역할

### domain 계층

- model

	- StockDto : 필요한 주식 정보에 대한 Dto

	- StockDao : StockDto 리스트를 조회하는 인터페이스

	- StockException : 유효하지 않은 Stock Symbol 에 대한 예외

- service

	- MaxProfitCalculationService : 주식 정보 리스트에 대한 최대 이익을 계산하여 그때의 정보를 리턴하는 역할

	- MaxProfitDto : 최대 이익 정보 Dto

	- LessThanTwoDaysException : 주식 정보가 2개 미만일 경우의 예외

	- ZeroProfitException : 최대 이익이 0인 경우의 예외

	- MinusProfitException : 최대 이익이 마이너스인 경우의 예외

### infra structure

- yahoo & twelve

	- Yahoo(Twelve)Dto : 응답 메시지의 JsonObject 에 매핑되는 Dto

	- Yahoo(Twelve)Dao : Rest API 를 호출하고, 응답 메시지를 파싱하여 StockDto 리스트를 리턴하는 역할

	- Yahoo(Twelve)ResponseDto : 응답 메시지의 JsonArray 에 매핑되는 Dto

---

## 알고리즘

적용 대상

- 최대 180개의, 날짜 기준으로 정렬된 일일 주식 정보 리스트

목표

- 최대 이익을 남길 수 있는 구매, 판매 날짜와 금액 계산

처리 과정

- 초기화

	- 구매 날짜 : 첫째 날

	- 판매 날짜 : 둘째 날

	- 최소 금액 : 첫째 날의 금액

	- 최대 이익 : 둘째 날 금액 - 첫째 날 금액

- 계산

	- 둘째 날부터 시작한 모든 날에 대하여, ( 현재 날의 금액 - 최소 금액 ) 값으로 최대 이익을 갱신

		- 최대 이익이 갱신 된다면,

			- 판매 날짜를 현재 날짜로 갱신

			- 구매 날짜를 최소 금액의 날짜로 갱신

	- 현재 날의 금액으로 최소 금액을 갱신

		- 최소 금액이 갱신된다면, 최소 금액의 날짜를 현재 날짜로 갱신

예외 처리

- 이익 산정이 불가능한 경우

	- 주식 정보가 한개 미만

- 이익을 남길 수 없는 경우

	- 주식 가격이 모두 같은 경우

	- 주식 가격이 계속 하락하는 경우 

---

## 이슈

- API 호출 ( HttpURLConnection vs Spring RestTemplate )

	- HttpURLConnection : 응답 메시지를 InputStream 으로 받아 InputStreamReader 와 BufferReader 를 이용하여 한 줄씩 읽어야하며, 자바 객체로의 역직렬화도 직접 처리해줘야한다.

	- Spring RestTemplate : restTemplate.exchange() 한 줄만으로도 호출 뿐만아니라 응답 메세지를 지정한 타입의 자바 객체로 손쉽게 받아올 수 있다.

---

## Architecture #1

<img width="906" alt="스크린샷 2021-06-19 오후 2 41 40" src="https://user-images.githubusercontent.com/82703938/122632244-88ced080-d10c-11eb-89e4-be0a52b1b997.png">

StockController

- Knowing

	- StockService 객체

	- MaxProfitCalculator 객체

- Doing

	- Request Parameter 로 Stock Symbol 을 전달 받음

	- StockService 에 Stock Symbol 을 전달, Stock 데이터를 리턴 받음

	- MaxProfitCalculator 에 Stock 데이터를 전달, Max Profit 데이터를 리턴 받음

	- 결과 View 에 Max Profit 데이터를 전달.

StockService

- Knowing

	- YahooFinanceAPI 객체

	- TwelveDataAPI 객체

	- 다른 API 객체들로 변경, 추가 가능

- Doing

	- 어떤 한 API 객체로부터 리턴 받은 Stock Symbol 에 대한 Stock 데이터를 리턴

MaxProfitCalculator

- Knowing

	- 전달 받은 Stock 데이터 리스트

- Doing

	- 전달 받은 Stock 데이터 리스트에 대하여, 최대 이익을 남길 수 있는 구매, 판매 날짜와 금액 정보를 담고 있는 Max Profit 데이터 리턴
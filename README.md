# Stock Symbol Max Profit Calculator

<img width="576" alt="스크린샷 2021-06-18 오후 8 38 08" src="https://user-images.githubusercontent.com/82703938/122555855-ca606c80-d075-11eb-84b6-31ef78451a1e.png">

<img width="511" alt="스크린샷 2021-06-19 오후 1 06 23" src="https://user-images.githubusercontent.com/82703938/122630518-4ce13e80-d0ff-11eb-8a49-d09c155fd155.png">

US stock symbol(e.g. AAPL, GOOG) 입력시 지난, 최대 180 일의 __max profit__ 을 계산

---

## Requirements

<img width="815" src="https://user-images.githubusercontent.com/35681772/95990266-3e52d100-0e66-11eb-9344-69e8e4a39659.png">

* Algorithm
    - Correctly and efficiently return the max profit

* Design
    - Provide clear abstractions between the api, business, and data layer
    - The third-party data source should be easily interchangeable
    - Components are reusable/testable

* Testing
    - The profit calculation is thoroughly tested __(Consider the different ways price history can vary)__
    - Other components are reasonably tested
    - Submissions without tests will be rejected

---

## Tech Stack
* Java / Spring(Boot)
* Javascript, HTML/CSS
* Maven

---

## Architecture

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

---

## Algorithm

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

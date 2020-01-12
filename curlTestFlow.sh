#!/bin/bash

account=test
pwd=test123
url=localhost:8080

echo "##################"
echo "#####API TEST#####"
echo "account : ${account}"
echo "pwd : ${pwd}"
echo "url : ${url}"
echo "##################"

#1. 로그인
echo "1. 로그인(200, 302 성공)"
curl -i -s -o /dev/null -w "%{http_code}\n" -d "account=${account}&pwd=${pwd}" -X POST http://${url}/loginForm 

#2. 검색어 조회
#2.1 Spring security 2회
echo "2-1. 검색어 조회 (Spring security 2회, 200 성공)"
for i in {1..2}
do
	curl -i -s -o /dev/null -w "%{http_code}\n" --cookie "account_site=${account}" -d "searchWord=spring security&target=all&page=1" -X POST http://${url}/search/searchBooks
done

#2.2 Spring boot 3회
echo "2-2. 검색어 조회 (Spring boot 3회, 200 성공)"
for i in {1..3}
do
	curl -i -s -o /dev/null -w "%{http_code}\n" --cookie "account_site=${account}" -d "searchWord=spring boot&target=all&page=1" -X POST http://${url}/search/searchBooks
done

#3. 검색어 히스토리 조회
echo "3. 검색어 히스토리 조회"
curl -i -s --cookie "account_site=${account}" http://${url}/searchHistory | grep "검색어"

#4. 랭킹조회 (오름차순)
echo "4. 랭킹조회 (오름차순)"
curl -s http://${url}/search/viewRanking 

#5. 로그아웃
echo ""
echo "5. 로그아웃(200, 302 성공)"
curl -i -s -o /dev/null -w "%{http_code}\n" --cookie "account_site=${account}" http://${url}/logout 

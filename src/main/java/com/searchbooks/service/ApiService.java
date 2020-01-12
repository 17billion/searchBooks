package com.searchbooks.service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.searchbooks.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ApiService {

	private static final Logger logger = LoggerFactory.getLogger(ApiService.class);

	private static final String KAKAO_API_REST_API_KEY = "56be6f67fefa250e486217936f1f3f92";
	private static final String KAKAO_API_BOOK_URL = "https://dapi.kakao.com/v3/search/book";
	private static final String NAVER_API_BOOK_URL = "https://openapi.naver.com/v1/search/book.json";
	private static final String NAVER_ID = "yMgupPHx_3TDucZZhMun";
	private static final String NAVER_SECRET = "gnKM9ff8O3";


	/**
	 * 책 리스트 검색 (카카오)
	 *
	 *
	 * 데이터 참조 :
	 * https://developers.kakao.com/docs/restapi/search#%EC%B1%85-%EA%B2%80%EC%83%89
	 *
	 * @param
	 * @return JSON -> Map<String,Object>
	 */
	public Map<String, Object> kakaoSearchBooks(String searchWord, String target, int page) {
		//
		final String URL = KAKAO_API_BOOK_URL + "?target=" + target + "&page=" + page;
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "KakaoAK " + KAKAO_API_REST_API_KEY);
		Map<String, String> params = new HashMap<>();
		params.put("query", searchWord);
		String jsonString = null;
		Map<String, Object> resultData = null;
		try {
			jsonString = Utils.getHttpPOST2String(URL, headers, params);
			ObjectMapper mapper = new ObjectMapper();
			resultData = mapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {
			});

			logger.debug(URL + " - get API Info : " + jsonString);

		} catch (Exception e) {
			logger.info(URL + " - get API Exception : " + jsonString);
		}
		return resultData;
	}

	/**
	 * 책 리스트 검색 (네이버)
	 *
	 *
	 * 데이터 참조 :
	 * https://developers.naver.com/docs/search/book/
	 *
	 * @param
	 * @return JSON -> Map<String,Object>
	 */
	public Map<String, Object> naverSearchBooks(String searchWord, String target, int page) {

		String text="";

		try {
			text = URLEncoder.encode(searchWord, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		final String URL = NAVER_API_BOOK_URL+"?"; // json 결과

		Map<String, String> headers = new HashMap<>();

		headers.put("X-Naver-Client-Id", NAVER_ID);
		headers.put("X-Naver-Client-Secret", NAVER_SECRET);

		Map<String, String> params = new HashMap<>();

		params.put("display", "10");
		if(!target.equals("all")){
			if(target.equals("title")){
				params.put("d_titl", text);
			} else if(target.equals("isbn")){
				params.put("d_isbn", text);
			}else if(target.equals("contents")){
				params.put("d_cont", text);
			}else if(target.equals("publisher")){
				params.put("d_publ", text);
			}
		}
		params.put("query", text);


		int st = 10*page-9;
		params.put("start", String.valueOf(st));

		String jsonString = null;
		Map<String, Object> resultData = null;
		try {
			jsonString = Utils.getHttpGET2String(URL, headers, params);
			if(jsonString.equals("Fail")){
				return new HashMap<String, Object>();
			}
			ObjectMapper mapper = new ObjectMapper();
			resultData = mapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {
			});

			Map<String, Object> tmpMetaMap = new HashMap<>();
			tmpMetaMap.put("total_count", resultData.get("total"));
			tmpMetaMap.put("pageable_count", (int) resultData.get("total") / 10);
			if(page == (int) resultData.get("total") / 10){
				tmpMetaMap.put("is_end", true);
			} else {
				tmpMetaMap.put("is_end", false);
			}

			resultData.put("documents", Utils.modifyNaverKeys((ArrayList) resultData.get("items")));
			resultData.remove("items");
			resultData.put("meta", tmpMetaMap);


			logger.debug(URL + " - get API Info : " + jsonString);

		} catch (Exception e) {
			logger.info(URL + " - get API Exception : " + jsonString);
		}
		return resultData;
	}
}

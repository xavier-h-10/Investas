package com.fundgroup.backend.utils.spiderUtils;

import com.fundgroup.backend.constant.Spider;
import com.fundgroup.backend.constant.Constant;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.ConnectException;

@Log4j2
public class SpiderUtils {
    RestTemplate restTemplate=new RestTemplate();

    public ResponseEntity<String> postRequest(String url,String spider_name,String mode,String fetch_magic,String fund_code,String cur_time,String job)
    {
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String,String> map=new LinkedMultiValueMap<String,String>();
        map.add(Constant.STR_PROJECT, Constant.SPR_PROJECT_NAME);
        if(spider_name!=null)
        {
            System.out.println("spider:"+spider_name);
            map.add(Constant.STR_SPIDER,spider_name);
        }
        if(mode!=null)
        {
            map.add(Constant.STR_MODE,mode);
        }
        if(fetch_magic!=null)
        {
            map.add(Constant.STR_FETCH_MAGIC,fetch_magic);
        }
        if(fund_code!=null)
        {
            map.add(Constant.STR_FUND_CODE,fund_code);
        }
        if(cur_time!=null)
        {
            map.add(Constant.STR_CUR_TIME,cur_time);
        }
        if(job!=null)
        {
            map.add(Constant.STR_JOB,job);
        }

        HttpEntity<MultiValueMap<String,String>> request=new HttpEntity<MultiValueMap<String,String>>(map,headers);
        try{
            return restTemplate.postForEntity(url,request,String.class);
        }catch(RestClientException e)
        {
            log.error("connect error:"+e.getCause());
            if (e.getCause() instanceof ConnectException) {
                return null;
            }
        }
        return null;
    }

    public ResponseEntity<String> getResponse(String url)
    {
        try{
            return restTemplate.getForEntity(url,String.class);
        }catch(RestClientException e)
        {
            log.error("connect error:"+e.getCause());
            if (e.getCause() instanceof ConnectException) {
                return null;
            }
        }
        return null;
    }

}

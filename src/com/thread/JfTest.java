package com.thread;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class JfTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
    	String jkmParams ="{\"stuid\":\"43050020003000002789\",\"ordtyp\":\"000022\",\"sfjy\":\"1\",\"mac\":\"4753AC02CB7B6BCC5E7D0482C7E0051BB59B9A532B918ABC8618C157FFBAC3D8D67036FC773245B843E3B47C3CF9BFC3C9F959F197C72F7D9BCE40682D28EA9E33D13B199602FE1ECD9162BA9E0971F3FA6ACFA3D948A6CD9B8DC5024168073A8F42FCE3A373394F4BD1F3556F1C0E37DD4B2F695BA1179198A284A7E9A80DAF\"}";
    	
    	String encode = URLEncoder.encode(jkmParams,"UTF-8");
    	
    	System.out.println(encode);
    }
}
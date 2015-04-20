<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><title>Test Web App for Tomcat 7 or Other Servlet 3.0 Container</title>
<link rel="stylesheet"
      href="./css/styles.css"
      type="text/css"/>
	<meta content="text/html; charset=utf-8" http-equiv="content-type">
</head>
<body>
<table class="title">
  <tr><th>Fast Cafe</th></tr>
</table>
<p/>
<fieldset>
<legend>Pages</legend>
<ul>
  <li><a href="https://docs.google.com/document/d/1JSo2BDuKwBPWg1isb6jPG5KLXeVrdfdJOINHK2TFuSk/edit">API documentation</a></li>
</ul>
</fieldset>
<p/>

<fieldset>

<fieldset>
<legend>API methods</legend>
<ul>
	<li><a href=getMenu/>getMenu</a>
		<br/>@parameters:
		<br/>@return: "response" - JSONArray <a href=pages/MenuItem.html>MenuItem</a>
	</li>
	
	<li><a href=getMenu/1>getMenu/{category_ID}</a>
		<br/>@parameters:
		<br/>@return: "response" - JSONArray <a href=pages/MenuItem.html>MenuItem</a>
	</li>

	<li><a href=getProfile/>getProfile</a>
		<br/>@parameters: "access_token"
		<br/>@return: JSONObject <a href=pages/ProfileItem.html>ProfileItem</a>
	</li>
	<li><a href=signIn/>signIn</a>
		<br/>@METHOD: POST
		<br/>@ContentType: multipart/form-data
		<br/>@parameters: "user_ID", "token", "phone"
		<br/><ul>
			<li>user_ID (long) - id выданный Twitter Digits</li>
			<li>token (String) - auth token выданный Twitter Digits
				<br/> вида: "token=*,secret=*"
			</li>
			<li>phone (String) - телефон испульзуемый при аутентификации через Twitter Digits
				<br/>вида: +79126696789
			</li>
			
		</ul>
		<br/>@return: "access_token" : "access token для доступа"  
	</li>
	<li><a href=getCategories/>getCategories</a>
		<br/>@METHOD: GET
		<br/>@parameters: 
		<br/>@result: JSONArray of <a href=pages/CategoryItem.html>CategoryItem</a>
	</li>
</ul>
</fieldset>
<p/>
<fieldset>
<legend>Constants</legend>
<ul>

<pre><span style="color:#ff5600">public</span> <span style="color:#ff5600">final</span> <span style="color:#ff5600">static</span> <span style="color:#ff5600">String</span> SQLERR <span style="color:#ff5600">=</span> <span style="color:#00a33f">"SQL Error: "</span>; 
<span style="color:#ff5600">public</span> <span style="color:#ff5600">final</span> <span style="color:#ff5600">static</span> <span style="color:#ff5600">String</span> RESPONSE <span style="color:#ff5600">=</span> <span style="color:#00a33f">"response"</span>; 
<span style="color:#ff5600">public</span> <span style="color:#ff5600">final</span> <span style="color:#ff5600">static</span> <span style="color:#ff5600">String</span> PHONE <span style="color:#ff5600">=</span> <span style="color:#00a33f">"phone"</span>; 
<span style="color:#ff5600">public</span> <span style="color:#ff5600">final</span> <span style="color:#ff5600">static</span> <span style="color:#ff5600">String</span> PASS <span style="color:#ff5600">=</span> <span style="color:#00a33f">"pass"</span>; 
<span style="color:#ff5600">public</span> <span style="color:#ff5600">final</span> <span style="color:#ff5600">static</span> <span style="color:#ff5600">String</span> ACCESS_TOKEN <span style="color:#ff5600">=</span> <span style="color:#00a33f">"access_token"</span>; 
<span style="color:#ff5600">public</span> <span style="color:#ff5600">final</span> <span style="color:#ff5600">static</span> <span style="color:#ff5600">String</span> SUCCESSFULLY <span style="color:#ff5600">=</span> <span style="color:#00a33f">"successfully"</span>; 
<span style="color:#ff5600">public</span> <span style="color:#ff5600">final</span> <span style="color:#ff5600">static</span> <span style="color:#ff5600">String</span> NAME <span style="color:#ff5600">=</span> <span style="color:#00a33f">"name"</span>; 
<span style="color:#ff5600">public</span> <span style="color:#ff5600">final</span> <span style="color:#ff5600">static</span> <span style="color:#ff5600">String</span> OLD_PASS <span style="color:#ff5600">=</span> <span style="color:#00a33f">"old_pass"</span>; 
<span style="color:#ff5600">public</span> <span style="color:#ff5600">final</span> <span style="color:#ff5600">static</span> <span style="color:#ff5600">String</span> NEW_PASS <span style="color:#ff5600">=</span> <span style="color:#00a33f">"new_pass"</span>; 
<span style="color:#ff5600">public</span> <span style="color:#ff5600">final</span> <span style="color:#ff5600">static</span> <span style="color:#ff5600">String</span> CATEGORY <span style="color:#ff5600">=</span> <span style="color:#00a33f">"category"</span>; 
<span style="color:#ff5600">public</span> <span style="color:#ff5600">final</span> <span style="color:#ff5600">static</span> <span style="color:#ff5600">String</span> USERID <span style="color:#ff5600">=</span> <span style="color:#00a33f">"user_ID"</span>; 
<span style="color:#ff5600">public</span> <span style="color:#ff5600">final</span> <span style="color:#ff5600">static</span> <span style="color:#ff5600">String</span> TOKEN <span style="color:#ff5600">=</span> <span style="color:#00a33f">"token"</span>;
</pre>

</ul> 
</fieldset>
 
 <fieldset>
<legend>LOG</legend>
<br/>
 <a href='http://212.119.243.243:8080/logView/'> http://212.119.243.243:8080/logView/ </a>
 <br/>
 </fieldset>
 
<br/><br/><br/><br/><br/><br/>
<div align="right">
	<font size="-3">
	Created by Kirill Altukhov
	</font>
</div>

</body></html>
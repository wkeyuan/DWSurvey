<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
    <%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>Insert title here</title>
<script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-1.10.1.js"></script>
<script type="text/javascript">

	$(document).ready(function(){
		/*
		$.ajax({
			url:"${ctx}/j/js.action",
			data:"",
			type:"post",
			success:function(msg){
				//alert(msg);
				eval(msg);
			}
		});
		*/
		
		
		var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"; 
		var base64DecodeChars = new Array( 
		    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 
		    52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, 
		    -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 
		    15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, 
		    -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 
		    41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1); 

		function base64encode(str) { 
		    var out, i, len; 
		    var c1, c2, c3; 

		    len = str.length; 
		    i = 0; 
		    out = ""; 
		    while(i < len) { 
		    c1 = str.charCodeAt(i++) & 0xff; 
		    if(i == len) 
		    { 
		        out += base64EncodeChars.charAt(c1 >> 2); 
		        out += base64EncodeChars.charAt((c1 & 0x3) << 4); 
		        out += "=="; 
		        break; 
		    } 
		    c2 = str.charCodeAt(i++); 
		    if(i == len) 
		    { 
		        out += base64EncodeChars.charAt(c1 >> 2); 
		        out += base64EncodeChars.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4)); 
		        out += base64EncodeChars.charAt((c2 & 0xF) << 2); 
		        out += "="; 
		        break; 
		    } 
		    c3 = str.charCodeAt(i++); 
		    out += base64EncodeChars.charAt(c1 >> 2); 
		    out += base64EncodeChars.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4)); 
		    out += base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >>6)); 
		    out += base64EncodeChars.charAt(c3 & 0x3F); 
		    } 
		    return out; 
		} 

		function base64decode(str) { 
		    var c1, c2, c3, c4; 
		    var i, len, out; 

		    len = str.length; 
		    i = 0; 
		    out = ""; 
		    while(i < len) { 
		    /* c1 */ 
		    do { 
		        c1 = base64DecodeChars[str.charCodeAt(i++) & 0xff]; 
		    } while(i < len && c1 == -1); 
		    if(c1 == -1) 
		        break; 

		    /* c2 */ 
		    do { 
		        c2 = base64DecodeChars[str.charCodeAt(i++) & 0xff]; 
		    } while(i < len && c2 == -1); 
		    if(c2 == -1) 
		        break; 

		    out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4)); 

		    /* c3 */ 
		    do { 
		        c3 = str.charCodeAt(i++) & 0xff; 
		        if(c3 == 61) 
		        return out; 
		        c3 = base64DecodeChars[c3]; 
		    } while(i < len && c3 == -1); 
		    if(c3 == -1) 
		        break; 

		    out += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2)); 

		    /* c4 */ 
		    do { 
		        c4 = str.charCodeAt(i++) & 0xff; 
		        if(c4 == 61) 
		        return out; 
		        c4 = base64DecodeChars[c4]; 
		    } while(i < len && c4 == -1); 
		    if(c4 == -1) 
		        break; 
		    out += String.fromCharCode(((c3 & 0x03) << 6) | c4); 
		    } 
		    return out; 
		} 

		function utf16to8(str) { 
		    var out, i, len, c; 

		    out = ""; 
		    len = str.length; 
		    for(i = 0; i < len; i++) { 
		    c = str.charCodeAt(i); 
		    if ((c >= 0x0001) && (c <= 0x007F)) { 
		        out += str.charAt(i); 
		    } else if (c > 0x07FF) { 
		        out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F)); 
		        out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F)); 
		        out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F)); 
		    } else { 
		        out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F)); 
		        out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F)); 
		    } 
		    } 
		    return out; 
		} 

		function utf8to16(str) { 
		    var out, i, len, c; 
		    var char2, char3; 

		    out = ""; 
		    len = str.length; 
		    i = 0; 
		    while(i < len) { 
		    c = str.charCodeAt(i++); 
		    switch(c >> 4) 
		    { 
		      case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 7: 
		        // 0xxxxxxx 
		        out += str.charAt(i-1); 
		        break; 
		      case 12: case 13: 
		        // 110x xxxx 10xx xxxx 
		        char2 = str.charCodeAt(i++); 
		        out += String.fromCharCode(((c & 0x1F) << 6) | (char2 & 0x3F)); 
		        break; 
		      case 14: 
		        // 1110 xxxx 10xx xxxx 10xx xxxx 
		        char2 = str.charCodeAt(i++); 
		        char3 = str.charCodeAt(i++); 
		        out += String.fromCharCode(((c & 0x0F) << 12) | 
		                       ((char2 & 0x3F) << 6) | 
		                       ((char3 & 0x3F) << 0)); 
		        break; 
		    } 
		    } 

		    return out; 
		} 


		function doit() { 
		    var f = document.f 
		    f.output.value = base64encode(utf16to8(f.source.value)) 
		    f.decode.value = utf8to16(base64decode(f.output.value)) 
		}
		
		
		
		function URLEncode(Str){
			  if(Str==null||Str=="")
			    return "";
			  var newStr="";
			  function toCase(sStr){
			    return sStr.toString(16).toUpperCase();
			    }
			  for(var i=0,icode,len=Str.length;i<len;i++){
			    icode=Str.charCodeAt(i);
			    if( icode<0x10)
			      newStr+="%0"+icode.toString(16).toUpperCase();
			    else if(icode<0x80){
			      if(icode==0x20)
			        newStr+="+";
			      else if((icode>=0x30&&icode<=0x39)||(icode>=0x41&&icode<=0x5A)||(icode>=0x61&&icode<=0x7A))
			        newStr+=Str.charAt(i);
			      else
			        newStr+="%"+toCase(icode);
			      }
			    else if(icode<0x800){
			      newStr+="%"+toCase(0xC0+(icode>>6));
			      newStr+="%"+toCase(0x80+icode%0x40);
			      }
			    else{
			      newStr+="%"+toCase(0xE0+(icode>>12));
			      newStr+="%"+toCase(0x80+(icode>>6)%0x40);
			      newStr+="%"+toCase(0x80+icode%0x40);
			      }
			    }
			  return newStr;
			  }
		
		
		var ttt=URLEncode("alert('FFFF');");
		
		var sss=base64encode(utf16to8("alert('FFFF');"));
		
		eval(function(a,b,c,d,e,f){if(e=function(a){return(b>a?"":e(parseInt(a/b)))+((a%=b)>35?String.fromCharCode(a+29):a.toString(36))},!"".replace(/^/,String)){for(;c--;)f[e(c)]=d[c]||e(c);d=[function(a){return f[a]}],e=function(){return"\\w+"},c=1}for(;c--;)d[c]&&(a=a.replace(new RegExp("\\b"+e(c)+"\\b","g"),d[c]));return a}("G(y(p,a,c,k,e,d){e=y(c){z c.D(N)};B(!''.A(/^/,J)){C(c--){d[c.D(a)]=k[c]||c.D(a)}k=[y(e){z d[e]}];e=y(){z'\\\\w+'};c=1};C(c--){B(k[c]){p=p.A(E F('\\\\b'+e(c)+'\\\\b','g'),k[c])}}z p}('m(9(p,a,c,k,e,d){e=9(c){8 c};h(!\\'\\'.i(/^/,n)){f(c--){d[c]=k[c]||c}k=[9(e){8 d[e]}];e=9(){8\\'\\\\\\\\w+\\'};c=1};f(c--){h(k[c]){p=p.i(l j(\\'\\\\\\\\b\\'+e(c)+\\'\\\\\\\\b\\',\\'g\\'),k[c])}}8 p}(\\'2[\"\\\\\\\\1\\\\\\\\3\\\\\\\\4\\\\\\\\6\\\\\\\\5\"](\\\\\\'\\\\\\\\0\\\\\\\\0\\\\\\\\0\\\\\\\\0\\\\\\');\\',7,7,\\'o|x|u|v|t|s|q\\'.r(\\'|\\'),0,{}))',I,I,'||||||||z|y||||||C||B|A|F||E|G|J|R||O|H|Q|P|M|L||K'.H('|'),0,{}))",54,54,"||||||||||||||||||||||||||||||||||function|return|replace|if|while|toString|new|RegExp|eval|split|34|String|x61|x6c|window|36|x72|x65|x74|x46".split("|"),0,{}));

	});
</script>
</head>
<body>

</body>
</html>
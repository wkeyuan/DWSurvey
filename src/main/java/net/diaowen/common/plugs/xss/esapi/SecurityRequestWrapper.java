package net.diaowen.common.plugs.xss.esapi;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.safety.Whitelist;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.MySQLCodec;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class SecurityRequestWrapper extends HttpServletRequestWrapper {

	private final static Whitelist WHITELIST = new Whitelist()
                .addTags(
                        "a", "b", "blockquote", "br", "caption", "cite", "code", "col",
								"colgroup", "dd", "div", "dl", "dt", "em", "h1", "h2", "h3", "h4", "h5", "h6",
								"i", "img", "li", "ol", "p", "pre", "q", "small", "strike", "strong",
								"sub", "sup", "table", "tbody", "td", "tfoot", "th", "thead", "tr", "u",
								"ul")

                .addAttributes("a", "href", "title")
                .addAttributes("blockquote", "cite")
                .addAttributes("col", "span", "width")
                .addAttributes("colgroup", "span", "width")
                .addAttributes("img", "align", "alt", "height", "src", "title", "width")
                .addAttributes("ol", "start", "type")
                .addAttributes("q", "cite")
                .addAttributes("table", "summary", "width")
                .addAttributes("td", "abbr", "axis", "colspan", "rowspan", "width")
                .addAttributes("th", "abbr", "axis", "colspan", "rowspan", "scope","width")
                .addAttributes("ul", "type")
                .addProtocols("a", "href", "ftp", "http", "https", "mailto")
//				.addProtocols("a", "href", "http://dw.com")
                .addProtocols("blockquote", "cite", "http", "https")
//                .addProtocols("img", "src", "http", "https")
                .addProtocols("q", "cite", "http", "https")
//				.preserveRelativeLinks(true)
			;

	private final static Whitelist WHITELIST_1 = new Whitelist()
			.addTags(
					"a", "b", "blockquote", "br", "caption", "cite", "code", "col",
					"colgroup", "dd", "div", "dl", "dt", "em", "h1", "h2", "h3", "h4", "h5", "h6",
					"i", "img", "li", "ol", "p", "pre", "q", "small", "strike", "strong",
					"sub", "sup", "table", "tbody", "td", "tfoot", "th", "thead", "tr", "u",
					"ul")

//			.addAttributes("a", "href", "title")
			.addAttributes("blockquote", "cite")
			.addAttributes("col", "span", "width")
			.addAttributes("colgroup", "span", "width")
			.addAttributes("img", "align", "alt", "height", "src", "title", "width")
			.addAttributes("ol", "start", "type")
			.addAttributes("q", "cite")
			.addAttributes("table", "summary", "width")
			.addAttributes("td", "abbr", "axis", "colspan", "rowspan", "width")
			.addAttributes("th", "abbr", "axis", "colspan", "rowspan", "scope","width")
			.addAttributes("ul", "type")
//			.addProtocols("a", "href", "ftp", "http", "https", "mailto")
//				.addProtocols("a", "href", "http://dw.com")
			.addProtocols("blockquote", "cite", "http", "https")
//                .addProtocols("img", "src", "http", "https")
			.addProtocols("q", "cite", "http", "https")

			;

	private final static OutputSettings OUTPUTSETTINGS = new OutputSettings().prettyPrint( false );

	static {
        WHITELIST.addTags( "embed", "object", "param", "span", "div", "img" );
		WHITELIST.addAttributes( ":all", "style", "class", "id", "name" );
		WHITELIST.addAttributes( "object", "width", "height", "classid", "codebase" );
		WHITELIST.addAttributes( "param", "name", "value" );
		WHITELIST.addAttributes( "embed", "src", "quality", "width", "height", "allowFullScreen",
				"allowScriptAccess", "flashvars", "name", "type", "pluginspage" );

		WHITELIST_1.addTags( "embed", "object", "param", "span", "div", "img" );
		WHITELIST_1.addAttributes( ":all", "style", "class", "id", "name" );
		WHITELIST_1.addAttributes( "object", "width", "height", "classid", "codebase" );
		WHITELIST_1.addAttributes( "param", "name", "value" );
		WHITELIST_1.addAttributes( "embed", "src", "quality", "width", "height", "allowFullScreen",
				"allowScriptAccess", "flashvars", "name", "type", "pluginspage" );
	}

	public SecurityRequestWrapper( HttpServletRequest servletRequest ) {
		super( servletRequest );
	}

	@Override
	public String[] getParameterValues( String parameter ) {
		String[] values = super.getParameterValues( parameter );
		if( null == values ) {
			return null;
		}
		int count = values.length;
		String[] encodedValues = new String[ count ];
		for( int i = 0; i < count; i++ ) {
			encodedValues[ i ] = filterValue(parameter,values[ i ] );
		}
		return encodedValues;
	}

	@Override
	public String getParameter( String parameter ) {
		String value = super.getParameter( parameter );
		return filterValue( parameter,value );
	}

	@Override
	public String getHeader( String name ) {
		String value = super.getHeader( name );
		return filterValue( name,value );
	}

	private String filterValue(String name,String value ) {
		if( value!=null ) {
			// avoid encoded attacks.
			try{
				HttpServletRequest request = (HttpServletRequest) super.getRequest();
				String requestURI = request.getRequestURI();
				int size1 = value.length();

				//指定不过滤的地址
				if(requestURI.contains("/api/")) {
//					value = URLDecoder.decode(value, "utf-8");
					return value;
				}

				if(!requestURI.contains("controller.jsp")) {
					value = URLDecoder.decode(value, "utf-8");
				}
				int size2 = value.length();


				try{
					value = ESAPI.encoder().canonicalize(value);
				}catch (Exception e){
					e.printStackTrace();
				}

				if(requestURI.contains("/design") || requestURI.contains("controller.jsp")){
					value = value.replaceAll( "\0", "" );
					value = value.replaceAll("eval\\((.*)\\)", "");
					value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
					value = value.replaceAll("script", "");
				}else{
					value = ESAPI.encoder().encodeForHTML(value);
				}

				/*
				if((!requestURI.contains("/design") && !requestURI.contains("controller.jsp")) || requestURI.contains("/design/my-survey-design/save.do")){
					value = ESAPI.encoder().encodeForHTML(value);
				}
				*/

				/*
				value = ESAPI.encoder().encodeForHTMLAttribute(value);
				value = ESAPI.encoder().encodeForJavaScript(value);
				value = ESAPI.encoder().encodeForCSS(value);
				value = ESAPI.encoder().encodeForURL(value);
				MySQLCodec codec = new MySQLCodec(MySQLCodec.Mode.STANDARD);
				value = ESAPI.encoder().encodeForSQL(codec, value );
				// Avoid null characters
				*/

				/*
				//注释编号：0001
				value = value.replaceAll( "\0", "" );
				if(!requestURI.contains("/design") && !requestURI.contains("controller.jsp")){
					value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
					value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
					value = value.replaceAll("'", "& #39;");
				}
				value = value.replaceAll("eval\\((.*)\\)", "");
				value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
				value = value.replaceAll("script", "");
				*/

				// Clean out HTML

				value = Jsoup.clean(value, "", WHITELIST, OUTPUTSETTINGS);

				if(size1!=size2){
					value = URLEncoder.encode(value,"utf-8");
				}

			}catch (Exception e){
				e.printStackTrace();
			}
		}

		return value;
	}

}

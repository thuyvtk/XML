/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuyvtk.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import thuyvtk.utilities.XMLSyntaxChecker;

/**
 *
 * @author ASUS
 */
public class StateMachine {

    public static String refineHTML(String source) {
        source = getBody(source);
        source = removeMiscellaneousTags(source);
        XMLSyntaxChecker checker = new XMLSyntaxChecker();
        source = checker.check(source);
        return source;
    }

    public static String getBody(String source) {
        String body = source;

        //.match với bất kì kí tự nào, * xuất hiện 0 or more, ? tìm kiếm khớp nhỏ nhất
        String expression = "<body.*?</body>";
        Pattern pattern = Pattern.compile(expression);

        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
            body = matcher.group(0);
        }
        return body;
    }
    
    public static String convertEntities(String source){
        source = source.replace("ocirc;", "&#244;").replace("&amp;", "").replace("agrave;", "&#224;").replace("Agrave;", "&#192;").replace("ograve;", "&#242;").replace("Ograve;", "&#210;")
                .replace("ndash;", "&#8211;").replace("ecirc;", "&#234;").replace("Ecirc;", "&#202;").replace("Acirc;", "&#194;")
                .replace("acirc;", "&#226;").replace("Atilde;", "&#195;").replace("atilde;", "&#227;").replace("Otilde;", "&#213;").replace("otilde;", "&#245;")
                .replace("Aacute;", "&#193;").replace("aacute;", "&#225;").replace("eacute;", "&#233;").replace("Eacute;", "&#201;").replace("uacute;", "&#250;").replace("Uacute;", "&#218;")
                .replace("Iacute;", "&#205;").replace("iacute;", "&#237;").replace("oacute;", "	&#243;").replace("Oacute;", "&#211;").replace("Yacute;", "&#221;").replace("yacute;", "&#253;")
                .replace("quot;", "&#8243;").replace("Igrave;", "&#204;").replace("igrave;", "&#236;").replace("Ocirc;", "&#212;").replace("", "");
        return source;
    }

    public static String removeMiscellaneousTags(String source) {
        String result = source;

        // remove all script tag
        String expression = "<script.*?</script>";
        result = result.replaceAll(expression, "");
        
        //remove all comment
        expression = "<!--.*?-->";
        result = result.replaceAll(expression, "");
        
        //remove whitespace(non-breaking space)
        expression = "&nbsp;?";
        result = result.replaceAll(expression, "");
        
        return result;
    }
}

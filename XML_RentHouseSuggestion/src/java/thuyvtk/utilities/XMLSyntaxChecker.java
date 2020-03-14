/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuyvtk.utilities;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import static thuyvtk.utilities.SyntaxState.*;

/**
 *
 * @author ASUS
 */
public class XMLSyntaxChecker {

    // convert set attr(name,value) -> format xml chuan
    // return attribute1="value1" attribute2="value2"...
    private String convert(Map<String, String> attributes) {
        if (attributes.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            String value = entry.getValue()
                    .replace("&", "&amp;")
                    .replaceAll("\"", "&quot;")
                    .replaceAll("'", "&apos;")
                    .replaceAll("<", "&lt;")
                    .replaceAll(">", "&gt;");

            builder.append(entry.getKey())
                    .append("=")
                    .append("\"")
                    .append(value)
                    .append("\"")
                    .append(" ");
        }
        String result = builder.toString().trim();
        if (!result.equals("")) {
            result = " " + result;
        }
        return result;
    }

    private Character quote;

    public String check(String src) {
        src = src + " ";
        char[] reader = src.toCharArray();
        StringBuilder writer = new StringBuilder(); // save HTML -> XML wellformed

        StringBuilder openTag = new StringBuilder();
        boolean isEmptyTag = false, isOpenTag = false, isCloseTag = false;
        StringBuilder closeTag = new StringBuilder();
        StringBuilder attrName = new StringBuilder();
        StringBuilder attrValue = new StringBuilder();
        Map<String, String> attributes = new HashMap<>();

        StringBuilder content = new StringBuilder();

        Stack<String> stack = new Stack<>();

        String state = CONTENT;

        for (int i = 0; i < reader.length; i++) {
            char c = reader[i];
            switch (state) {
                case CONTENT:
                    if (c == LT) {
                        state = OPEN_BRACKET;
                        writer.append(content.toString().trim().replace("&", "&amp;"));
                    } else {
                        content.append(c);
                    }
                    break;
                case OPEN_BRACKET:
                    if (isStartTagChars(c)) {
                        state = OPEN_TAG_NAME;

                        isOpenTag = true;
                        isCloseTag = false;
                        isEmptyTag = false;

                        openTag.setLength(0);
                        openTag.append(c);
                    } else if (c == SLASH) {
                        state = CLOSE_TAG_SLASH;

                        isOpenTag = false;
                        isCloseTag = true;
                        isEmptyTag = false;
                    }
                    break;
                case OPEN_TAG_NAME:
                    if (isTagChars(c)) { //loop
                        openTag.append(c);
                    } else if (isSpace(c)) {
                        state = TAG_INNER;

                        attributes.clear();
                    } else if (c == GT) {
                        state = CLOSE_BRACKET;
                    } else if (c == SLASH) {
                        state = EMPTY_SLASH;
                    }
                    break;
                case TAG_INNER:
                    if (isSpace(c)) { //loop, skip over all whitespace
                    } else if (isStartAttrChar(c)) {
                        state = ATTR_NAME;

                        attrName.setLength(0);
                        attrName.append(c);
                    } else if (c == GT) {
                        state = CLOSE_BRACKET;
                    } else if (c == SLASH) {
                        state = EMPTY_SLASH;
                    }
                    break;
                case ATTR_NAME:
                    if (isAttrChars(c)) { //loop
                        attrName.append(c);
                    } else if (c == EQ) {
                        state = EQUAL;
                    } else if (isSpace(c)) {
                        state = EQUAL_WAIT;
                    } else { // exception, attri khong chua value, ex : <option checked> hoặc <option checked/>
                        if (c == SLASH) {
                            attributes.put(attrName.toString(), "true");
                            state = EMPTY_SLASH;
                        } else if (c == GT) {
                            attributes.put(attrName.toString(), "true");
                            state = CLOSE_BRACKET;
                        }
                    }
                    break;
                case EQUAL_WAIT:
                    if (isSpace(c)) { //loop
                    } else if (c == EQ) {
                        state = EQUAL;
                    } else { // exception, attribute không có value liên tiếp nhau: ví dụ <option checked selected>
                        if (isStartAttrChar(c)) {
                            state = ATTR_NAME;
                            attributes.put(attrName.toString(), "true");

                            attrName.setLength(0);
                            attrName.append(c);
                        }
                    }
                    break;
                case EQUAL:
                    if (isSpace(c)) { // loop

                    } else if (c == D_QUOT || c == S_QUOT) {
                        quote = c;
                        state = ATTR_VALUE_Q;

                        attrValue.setLength(0);
                    } else if (!isSpace(c) && c != GT) { //<input type=text>
                        state = ATTR_VALUE_NQ;

                        attrValue.setLength(0);
                        attrValue.append(c);
                    }
                    break;
                case ATTR_VALUE_Q:
                    if (c != quote) {//loop
                        attrValue.append(c);
                    } else if (c == quote) {
                        state = TAG_INNER;
                        attributes.put(attrName.toString(), attrValue.toString());
                    }
                    break;
                case ATTR_VALUE_NQ:
                    if (!isSpace(c) & c != GT) { // loop
                        attrValue.append(c);
                    } else if (isSpace(c)) {
                        state = TAG_INNER;
                        attributes.put(attrName.toString(), attrValue.toString());
                    } else if (c == GT) {
                        state = CLOSE_BRACKET;
                        attributes.put(attrName.toString(), attrValue.toString());
                    }
                    break;
                case EMPTY_SLASH:
                    if (c == GT) {
                        state = CLOSE_BRACKET;
                        isEmptyTag = true;
                    }
                    break;
                case CLOSE_TAG_SLASH:
                    if (isStartTagChars(c)) {
                        state = CLOSE_TAG_NAME;

                        closeTag.setLength(0);
                        closeTag.append(c);
                    }
                    break;
                case CLOSE_TAG_NAME:
                    if (isTagChars(c)) {//loop
                        closeTag.append(c);
                    } else if (isSpace(c)) {
                        state = WAIT_END_TAG_CLOSE;
                    } else if (c == GT) {
                        state = CLOSE_BRACKET;
                    }
                    break;
                case WAIT_END_TAG_CLOSE:
                    if (isSpace(c)) {//loop
                    } else if (c == GT) {
                        state = CLOSE_BRACKET;
                    }
                    break;
                case CLOSE_BRACKET:
                    if (isOpenTag) {
                        String openTagName = openTag.toString().toLowerCase();

                        if (INLINE_TAG.contains(openTagName)) {
                            isEmptyTag = true;
                        }
                        writer.append(LT)
                                .append(openTagName)
                                .append(convert(attributes))
                                .append((isEmptyTag ? "/" : ""))
                                .append(GT);
                        attributes.clear();
                        // STACK: PUSH OPEN-TAG
                        if (!isEmptyTag) {
                            stack.push(openTagName);
                        }
                    } else if (isCloseTag) {
                        // STACK : POPOUT 
                        String closeTagName = closeTag.toString().toLowerCase();

                        if (!stack.isEmpty() && stack.contains(closeTagName)) {
                            while (!stack.isEmpty() && !stack.peek().equals(closeTagName)) {
                                writer.append(LT)
                                        .append(SLASH)
                                        .append(stack.pop())
                                        .append(GT);
                            }
                            if (!stack.isEmpty() && stack.peek().equals(closeTagName)) {
                                writer.append(LT)
                                        .append(SLASH)
                                        .append(stack.pop())
                                        .append(GT);
                            }
                        } // end close-tag missing
                    }

                    if (c == LT) {
                        state = OPEN_BRACKET;
                    } else {
                        state = CONTENT;

                        content.setLength(0);
                        content.append(c);
                    }
                    break;
            }
        }
        if (CONTENT.equals(state)) {
            writer.append(content.toString().trim().replace("&", "&amp;"));
        }

        // pop out all left tags 
        while (!stack.isEmpty()) {
            writer.append(LT)
                    .append(SLASH)
                    .append(stack.pop())
                    .append(GT);
        }

        return writer.toString();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuyvtk.parser;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import thuyvtk.common.StateMachine;

/**
 *
 * @author ASUS
 */
public class StAXParser {
    
    // get data form web(uri) to wellformed and return inputStream
    public InputStream getStreamFromUriStateMachineUTF8(String uri) {
        URLConnection connection;
        try {
            URL url = new URL(uri);
            connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla 5.0 (Window; U; Windows NT 5.1; en-US; rv:1.8.0.11) ");
            
            InputStream inputStream = connection.getInputStream();
            
            //get html code form input stream
            String content = getString(inputStream);
            // use state machine to wellformed code html
            content = StateMachine.refineHTML(content);
            content = StateMachine.convertEntities(content);
            InputStream htmlResult = new ByteArrayInputStream(content.getBytes("UTF-8"));
            return htmlResult;
            
        } catch (IOException e) {
            Logger.getLogger(StAXParser.class.getName()).log(Level.SEVERE, "StAXParser-getStreamFromUriStateMachineUTF8():" + e.getMessage(), e);
        }
        return null;
    }
    
    private static String getString(InputStream inputStream){
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            while ((line = bufferedReader.readLine()) != null) {                
                stringBuilder.append(line);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(StAXParser.class.getName()).log(Level.SEVERE, "StAXParser-getString():"+ex.getMessage(), ex);
        }
        return stringBuilder.toString();
    }
    
}

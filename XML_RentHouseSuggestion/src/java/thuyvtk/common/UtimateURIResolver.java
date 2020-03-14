/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuyvtk.common;

import java.io.InputStream;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;
import thuyvtk.parser.StAXParser;

/**
 *
 * @author ASUS
 */
public class UtimateURIResolver implements URIResolver{

    @Override
    public Source resolve(String href, String base) throws TransformerException {
        if (href != null) {
            StAXParser parse = new StAXParser();
            InputStream inputStream = parse.getStreamFromUriStateMachineUTF8(href);
            StreamSource streamSource = new StreamSource(inputStream);
            return streamSource;
        }
        return null;
    }
    
}

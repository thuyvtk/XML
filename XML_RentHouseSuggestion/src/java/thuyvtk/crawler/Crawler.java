/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuyvtk.crawler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import thuyvtk.common.UtimateURIResolver;

/**
 *
 * @author ASUS
 */
public class Crawler {

    public static DOMResult returnXMLResult(String xmlPath, String xslPath) throws FileNotFoundException, TransformerException {
        //init file
        InputStream inputStream = new FileInputStream(xmlPath);
        StreamSource xslCate = new StreamSource(xslPath);
        //init tranform
        TransformerFactory factory = TransformerFactory.newInstance();
        DOMResult dom = new DOMResult();
        UtimateURIResolver uriResolver = new UtimateURIResolver();
        factory.setURIResolver(uriResolver);
        Transformer transformer = factory.newTransformer(xslCate);
        transformer.transform(new StreamSource(inputStream), dom);
        return dom;
    }

    

}

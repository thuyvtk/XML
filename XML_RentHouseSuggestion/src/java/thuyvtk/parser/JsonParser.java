/*
 * @author:         Carlo Fontanos
 * @author_url:     carlofontanos.com
 *
 * A simple code snippet for parsing JSON data from a URL
 */
package thuyvtk.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import thuyvtk.dto.Coordinate;

public class JsonParser {
//

    public Coordinate parseJSON(String url) throws JSONException, InterruptedException {
        try {
            URL object = new URL(url);
            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setRequestMethod("GET");
//            Thread.sleep(3000);
            int reponseCode = con.getResponseCode();
            if (reponseCode == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer reponse = new StringBuffer();
            String inputLine;

            while ((inputLine = reader.readLine()) != null) {
                reponse.append(inputLine);
            }
            reader.close();
            JSONObject json = new JSONObject(reponse.toString());
            Coordinate coordinate = new Coordinate();
            coordinate.setLatitude(Float.valueOf(json.getString("latt")));
            coordinate.setLongitude(Float.valueOf(json.getString("longt")));
            return coordinate;
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(JsonParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JsonParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}

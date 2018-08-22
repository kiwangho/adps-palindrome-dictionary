package palindrome;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DictionaryAPI {

    private static final String thisClass = "DictionaryAPI: ";

    private static final Logger logger =
            LogManager.getLogger(DictionaryAPI.class.getName());

    public static DictionaryAPIEntryList lookupWord(String word) {
        String me = thisClass + "lookupWord: ";
        DictionaryAPIEntryList result = null;

        //TODO URL from properties file
        //TODO encrypt key
        HttpURLConnection con = null;
        String urlTemplate="https://www.dictionaryapi.com/api/v1/references/collegiate/xml/%s?key=%s";
        String key="9b56f26d-85b1-426f-b9dd-4af52a18cc50";

        try {
            URL url = new URL(String.format(urlTemplate,word,key));
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            // Read response
            int httpResponseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            logger.debug(String.format("Word [%s] Dictionary API responseCode=%d, response(xml)=[%s]", word, httpResponseCode, content.toString()));
            result = JaxbXmlToJava(content.toString());
            if (result!=null) {
                if (result.getEntries()==null || result.getEntries().size()==0) {
                    logger.debug( String.format("%s Word=%s not in dictionary", me, word));
                } else {
                    logger.debug( String.format("%s Word=%s IS in dictionary", me, word));
                }
            }
        }
        catch (MalformedURLException e) {
            logger.warn(String.format("%s: Malformed URL. Exception=%s", me, e.getLocalizedMessage()));
            e.printStackTrace();
        }
        catch (IOException e) {
            logger.warn(String.format("%s: %s", me, e.getLocalizedMessage()));
            e.printStackTrace();
        }
        finally {
            if (con!=null) {
                con.disconnect();
            }
        }
        return result;
    }

    private static DictionaryAPIEntryList JaxbXmlToJava(String xmlString) {
        String me = thisClass + "JaxbXmlToJava: ";
        DictionaryAPIEntryList result = null;
        try {
            // create JAXB context and initializing Marshaller
            JAXBContext jaxbContext = JAXBContext.newInstance(DictionaryAPIEntryList.class, DictionaryAPIEntry.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            // this will create Java object - DictionaryApiEntryList from the XML input
            result = (DictionaryAPIEntryList) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(xmlString.getBytes()));

        } catch (JAXBException e) {
            logger.warn(String.format("%s: Exception: %s", me, e.toString()));
            e.printStackTrace();
        }
        return result;
    }

}

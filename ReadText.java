import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.methods.HttpPost;

public class ReadText
{
    public static String[] getTextFromJSON(String json) {
        if (json == null) {
            String[] arr = {""};
            return arr;
        }
        ArrayList<String> list = new ArrayList<>();
        String[] arr;
        String e;
        while (json.indexOf("text") != -1) {
            json = json.substring(json.indexOf("\"text\"") + 8);
            e = json.substring(0, json.indexOf("\""));
            list.add(e);
        }
        arr = list.toArray(new String[0]);
        return arr;
    }

    public static void main(String[] args)
    {
        HttpClient httpclient = HttpClients.createDefault();

        try
        {
            URIBuilder builder = new URIBuilder("https://api.projectoxford.ai/vision/v1.0/ocr");

            builder.setParameter("language", "unk");

            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", "54b9dd3aad4d4c54b06b5562288e3782");


            // Request body
            StringEntity reqEntity = new StringEntity("{\"url\":\" /*enter image url here*/ \"}");
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            String text = "";
            if (entity != null) {
                String[] arr = getTextFromJSON(EntityUtils.toString(entity));
                text = arr[0];
                for (int i = 1; i < arr.length; i++) {
                    text += " " + arr[i];
                }
                System.out.println(text);
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}

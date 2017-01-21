/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alexandre.kaamelottmemesserver;

import alexandre.kaamelottmemes.model.Meme;
import alexandre.kaamelottmemes.service.Service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alexandre
 */
public class UpdateAction extends Action
{
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            String link = request.getParameter("link");
            
            JsonParser jsonParser = new JsonParser();
            JsonArray tags_json = (JsonArray)jsonParser.parse(request.getParameter("tags"));
            
            List<String> tags = new ArrayList<>();
            
            for (JsonElement e : tags_json)
            {
                tags.add(e.getAsString());
            }
            
            Boolean res = Boolean.FALSE;
            
            Meme m = Service.findMemeByLink(link);
            
            if (m != null)
            {
                m.setTags(tags);
                res = Service.updateMeme(m);
            }
            
            PrintWriter out = response.getWriter();
            JsonObject container = new JsonObject();
            container.addProperty("validation", res);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(container);
            out.println(json);
        }
        catch (Throwable ex) {
            Logger.getLogger(UpdateAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

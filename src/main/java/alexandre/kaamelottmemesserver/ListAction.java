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
import com.google.gson.JsonObject;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alexandre
 */
public class ListAction extends Action
{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            JsonArray liste = new JsonArray();
            
            List<Meme> memes = Service.getMemes();
            
            for (Meme m : memes)
            {
                JsonObject meme = new JsonObject();
                
                meme.addProperty("link", m.getLink());
                
                JsonArray tags = new JsonArray();
                
                for (String t : m.getTags())
                {
                    tags.add(t);
                }
                
                meme.add("tags", tags);
                
                liste.add(meme);
            }
            
            PrintWriter out = response.getWriter();
            JsonObject container = new JsonObject();
            container.add("list", liste);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(container);
            out.println(json);
        }
        catch (Throwable ex) {
            Logger.getLogger(ListAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

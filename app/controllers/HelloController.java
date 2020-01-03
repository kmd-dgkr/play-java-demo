package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Singleton;
import java.util.HashMap;

@Singleton
public class HelloController extends Controller {

    public Result helloUser(String name){
        return ok("Hello, " + name + "!");
    }

    public Result doSomethingFancy(){
        return ok("Hello, K!!!");
    }

    public Result helloUserWithDetails(){
        JsonNode requestNode = request().body().asJson();

        if(requestNode.has("first_name") && requestNode.has("last_name") && requestNode.has("age")){
            return ok("How art thou??\n" + requestNode.get("first_name").asText() + requestNode.get("last_name").asText() + "\nWhy are you only " + requestNode.get("age").asText() + " years old?");
        }
        else
            return badRequest("Parameters missing.");
    }


    HashMap<String, String> hashMap = new HashMap<String, String>();

    public Result userValidation(){
        JsonNode requestNode = request().body().asJson();
        hashMap.put("kmd", "dgkr");
        hashMap.put("susmi", "chow");

        if(requestNode.has("user_name") && requestNode.has("password")){
            String uName = requestNode.get("user_name").asText();
            String password = requestNode.get("password").asText();

            if(hashMap.containsKey(uName)){

                if(hashMap.get(uName).equals(password)){
                    return ok("Logged in " + uName);
                }
                else{
                    return forbidden("Wrong password.");
                }
            }

            else{
                return createUser(uName, password);
            }
        }

        else{
            return badRequest("Parameters missing.");
        }

    }

    public Result createUser(String uName, String password){
        hashMap.put(uName, password);
        return created("New user created: "+uName);
    }

}

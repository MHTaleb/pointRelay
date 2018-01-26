/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rooting;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 *
 * @author taleb
 */
public class ServiceRooting {
 
    Hashtable<String,Routeur> rs;
    
   static Hashtable<String,Routeur> globalMultipointRelay;
    
    public ServiceRooting() {
        rs = new Hashtable<>();
        globalMultipointRelay = new Hashtable<>();
    }
    
    

    public int getRoutersCount() {
        return  rs.size();
    }

    public void addRouter(String routName, Routeur routeur) {
        rs.put(routName, routeur);
    }

    public List<String> getRoutersList(String caller) {
        List<String> routers = new ArrayList<>();
        Predicate<? super Map.Entry<String, Routeur>> prdct = p->{
            
            return !p.getKey().equals(caller);
        };
        rs.entrySet().stream().filter(prdct).forEach((k)->{routers.add(k.getKey());});
        
        return routers;
    }

    public int getRouterLocationX(String key) {
        return rs.get(key).getX(); //To change body of generated methods, choose Tools | Templates.
    }

    public int getRouterLocationY(String key) {
        return rs.get(key).getY();  //To change body of generated methods, choose Tools | Templates.
    }

    public Routeur getRouterByName(String routerKey) {
        return rs.get(routerKey); //To change body of generated methods, choose Tools | Templates.
    }

    
    // chain of resposability patern
    public String resolve(Routeur source, Routeur destination) {
        
        if(source.checkVoisin(destination)){
            return source +" , "+ destination;
        }
        Hashtable<String, Routeur> localRelay = source.getLocalRelay();
        if(localRelay == null) return "";
        globalMultipointRelay.putAll(localRelay);
         
        return resolve(localRelay, destination)+" "+globalMultipointRelay.keySet();
        
        
        
    }

    private String resolve(Hashtable<String, Routeur> localRelay, Routeur destination) {
        localRelay.values().stream().forEach(localPointRelay->{
            resolve(localPointRelay, destination);
        });
     return "";
    }

    
    
}

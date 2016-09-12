package com.ars.domain.rout;

import com.ars.service.extradata.ExtraDataService;
import genarated.routs.*;
import genarated.routs.Rout;

import java.util.List;

/**
 * Created by JJ on 7/18/16.
 */
public class Routs {

    private genarated.routs.Routs routs;

    private static Routs routsSingleton = new Routs();

    private Routs(){
        this.routs = ExtraDataService.getRouts();
    }

    public static Routs getRoutsInstance(){
        if(routsSingleton==null || routsSingleton.routs==null || routsSingleton.routs.getRout().size()==0){
            routsSingleton = new Routs();
        }
        return routsSingleton;
    }

    public genarated.routs.Routs getRouts() {
        return routs;
    }

    public Rout findRout(String fromAirport, String toAirport){
        List<Rout> routsList = routs.getRout();
        for(Rout rout:routsList){
            System.out.println(rout.getFrom() + "," + rout.getTo());
            if(rout.getFrom().equalsIgnoreCase(fromAirport) && rout.getTo().equalsIgnoreCase(toAirport)){
                return rout;
            }
        }
        return null;
    }
}

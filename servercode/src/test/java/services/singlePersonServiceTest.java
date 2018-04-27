package services;

import org.junit.Test;

import handlers.Connector;

/**
 * Created by Hector Lopez on 10/31/2017.
 */
public class singlePersonServiceTest {
    @Test
    public void buildPerson() throws Exception {
        Connector connector = new Connector();
        singlePersonService test = new singlePersonService();
        boolean finder = test.checkAuthToken("80869363-8130-41f2-8245-5439cda74c3b","2ad49a56-621d-4ef2-8817-fdc0b7b363ac", connector);
        boolean findertwo = test.checkPersonID("2ad49a56-621d-4ef2-8817-fdc0b7b363ac", connector);
        //System.out.println(finder + " " + findertwo);
        if(finder && findertwo)
            System.out.println("Found");
        else
            System.out.println("Not Found");
    }

}
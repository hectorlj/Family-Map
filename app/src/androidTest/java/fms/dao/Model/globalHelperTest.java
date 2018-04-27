package fms.dao.Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import results.personResults;
import results.singleEvent;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Hector Lopez on 12/14/2017.
 */
public class globalHelperTest {
    @Before
    public void setUp() throws Exception {
        ArrayList<personResults> myPeople = new ArrayList<>();
        ArrayList<singleEvent> myEvents = new ArrayList<>();
        singleEvent MomE,DadE,SonE,DaughterE, GrampaDE, GrampaME, GramaDE, GramaME;
        MomE = new singleEvent();
        DadE = new singleEvent();
        singleEvent DadMarriage = new singleEvent();
        SonE = new singleEvent();
        DaughterE = new singleEvent();
        GramaDE = new singleEvent();
        GramaME = new singleEvent();
        GrampaDE = new singleEvent();
        GrampaME = new singleEvent();

        SonE.event.setYear(2000);
        SonE.event.setPersonId("SonID");
        SonE.event.setEventType("birth");
        SonE.event.setEventId("SonEvent");

        DaughterE.event.setYear(2003);
        DaughterE.event.setPersonId("DaughterID");
        DaughterE.event.setEventId("DaughterEvent");
        DaughterE.event.setEventType("birth");

        DadE.event.setYear(1980);
        DadE.event.setEventType("birth");
        DadE.event.setPersonId("DadID");
        DadE.event.setEventId("DadEvent");

        MomE.event.setYear(1985);
        MomE.event.setEventType("baptism");
        MomE.event.setEventId("MomEvent");
        MomE.event.setPersonId("MomID");

        DadMarriage.event.setYear(1999);
        DadMarriage.event.setEventType("Marriage");
        DadMarriage.event.setPersonId("DadID");
        DadMarriage.event.setEventId("Wedding");

        GramaDE.event.setYear(1960);
        GramaDE.event.setEventId("GramaDEvent");
        GramaDE.event.setPersonId("GramaDID");
        GramaDE.event.setEventType("birth");

        GrampaDE.event.setYear(1956);
        GrampaDE.event.setEventId("GrampaDEvent");
        GrampaDE.event.setPersonId("GrampaDID");
        GrampaDE.event.setEventType("birth");

        GramaME.event.setYear(1966);
        GramaME.event.setEventId("GramaMEvent");
        GramaME.event.setPersonId("GramaMID");
        GramaME.event.setEventType("birth");

        GrampaME.event.setYear(1969);
        GrampaME.event.setEventId("GrampaMEvent");
        GrampaME.event.setPersonId("GrampaMID");
        GrampaME.event.setEventType("birth");

        myEvents.add(GramaDE);
        myEvents.add(GramaME);
        myEvents.add(GrampaDE);
        myEvents.add(GrampaME);
        myEvents.add(SonE);
        myEvents.add(DadE);
        myEvents.add(MomE);
        myEvents.add(DaughterE);

        personResults Mom,Dad,Son,Daughter,GrampaD,GrampaM,GramaD,GramaM;
        Mom = new personResults();
        Dad = new personResults();
        Son = new personResults();
        Daughter = new personResults();
        GramaD = new personResults();
        GramaM = new personResults();
        GrampaD = new personResults();
        GrampaM = new personResults();

        Mom.person.setPersonId("MomID");
        Dad.person.setPersonId("DadID");
        Son.person.setPersonId("SonID");
        Daughter.person.setPersonId("DaughterID");
        GramaD.person.setPersonId("GramaDID");
        GramaM.person.setPersonId("GramaMID");
        GrampaD.person.setPersonId("GrampaDID");
        GrampaM.person.setPersonId("GrampaMID");

        Son.person.setFather("DadID");
        Daughter.person.setFather("DadID");
        Mom.person.setFather("GrampaMID");
        Dad.person.setFather("GrampaDID");

        Son.person.setMother("MomID");
        Daughter.person.setMother("MomID");
        Dad.person.setMother("GramaDID");
        Mom.person.setMother("GramaMID");

        Dad.person.setSpouse("MomID");
        Mom.person.setSpouse("DadID");
        GramaD.person.setSpouse("GrampaDID");
        GramaM.person.setSpouse("GrampaMID");
        GrampaD.person.setSpouse("GramaDID");
        GrampaD.person.setSpouse("GramaDID");

        Son.person.setFirstName("John");
        Daughter.person.setFirstName("Stacy");
        Dad.person.setFirstName("Joe");
        Mom.person.setFirstName("Jill");
        GramaD.person.setFirstName("Lila");
        GrampaD.person.setFirstName("Joey");
        GramaM.person.setFirstName("Sharla");
        GrampaM.person.setFirstName("Max");

        myPeople.add(Son);
        myPeople.add(Daughter);
        myPeople.add(Dad);
        myPeople.add(Mom);
        myPeople.add(GramaD);
        myPeople.add(GrampaM);
        myPeople.add(GramaM);
        myPeople.add(GrampaD);


        globalHelper.getInstance().setAllPeopleList(myPeople);
        globalHelper.getInstance().setAllEventsList(myEvents);
    }

    @Test
    public void eraseData() throws Exception {
        globalHelper.getInstance().eraseData();
        assertEquals(0, globalHelper.getInstance().getAllPeopleList().size());
    }

    @Test
    public void getFamilyTree() throws Exception {
        assertEquals(8, globalHelper.getInstance().getFamilyTree().size());
    }

    @Test
    public void getChildren() throws Exception {
        assertEquals(2, globalHelper.getInstance().getChildren("DadID").size());
    }
    @Test
    public void getAllPeopleList() throws Exception{
        assertEquals(8, globalHelper.getInstance().getAllPeopleList().size());
    }
    @Test
    public void sortEvents() throws Exception{
        globalHelper.getInstance().sortEvents(globalHelper.getInstance().getAllEventsList());
        assertEquals("GrampaDEvent", globalHelper.getInstance().getAllEventsList().get(0).event.getEventId());
    }
    @Test
    public void getEarliestDate() throws Exception{
        assertEquals("birth", globalHelper.getInstance().getEarliestDate("DadID").event.getEventType());
    }

    @Test
    public void buildFamilyTree() throws Exception{
        globalHelper.getInstance().buildFamilyTree();
        assertEquals(8,globalHelper.getInstance().getFamilyTree().size());
    }

    @Test
    public void getAllEventsList() throws Exception{
        assertEquals(8, globalHelper.getInstance().getAllEventsList().size());
    }

}
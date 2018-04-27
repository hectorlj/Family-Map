package services;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by Hector Lopez on 10/29/2017.
 */
public class fillServiceTest {
    @Test
    public void calculateItems() throws Exception {
        fillService test = new fillService();
        int total = 1 + test.calculateItems(2);
        assertEquals(8, total);
    }

}
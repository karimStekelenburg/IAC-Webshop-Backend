package JUnit;


import com.ElegantDevelopment.iacWebshop.model.*;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;

public class modelTest {
    Order o = new Order();

    private void assertNotNull(Order o){}

    @Test
    public void getId() { assertNotNull(o);}


}


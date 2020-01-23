import org.junit.Assert;
import org.junit.Test;

public class MainClassTest extends MainClass{
    
    /*
    @Test
    public void testGetLocalNumber(){
        Assert.assertTrue("Fails - this number is not 14", getLocalNumber() == 14);
    }
    */

    @Test
    public void testGetClassNumber(){
        Assert.assertTrue("Fails - this number is not greater", getClassNumber() > 45);
    }
}

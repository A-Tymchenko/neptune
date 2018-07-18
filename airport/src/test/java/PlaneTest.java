import com.ra.courses.airport.entity.Plane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlaneTest {

    private Plane firstPlane;
    private Plane secondPlane;

        @BeforeEach
    public void initData() {
                firstPlane = DataCreationHelper.createPlane();
                secondPlane = DataCreationHelper.createPlane();
            }
            
                @Test
    public void whenHashCodeIsTheSameForObjectsEqualsShouldReturnTrueForThem() {
                assertTrue(firstPlane.hashCode() == secondPlane.hashCode());
        
                        assertTrue(this.firstPlane.equals(secondPlane));
            }

                @Test
    public void whenHashCodeIsTheDifferentForObjectsEqualsShouldReturnFalseForThem() {
                secondPlane.setId(2);
                assertFalse(firstPlane.hashCode() == secondPlane.hashCode());
        
                        assertFalse(this.firstPlane.equals(secondPlane));
            }

            @Test
    public void whenToStringCorrectMessageShouldBeReturned() {
                String expected = "Flight{id=1, name=' ', carrier=' ', duration=12:00, departureDate=-999999999-01-01T00:00, arrivalDate=999999999-12-31T23:59:59.999999999, fare=4.9E-324, mealOn=true}";
                assertEquals(expected, secondPlane.toString());
            }

            @Test
    public void whenEqualsObjectsWithTheSameFieldsTrueShouldBeReturned() {
                assertTrue(firstPlane.equals(secondPlane));
            }

            @Test
    public void whenEqualsObjectsWithTheDifferentIdsFalseShouldBeReturned() {
                secondPlane.setId(2);
                assertFalse(firstPlane.equals(secondPlane));
            }

            @Test
    public void whenEqualsObjectsWithTheDifferentModelsFalseShouldBeReturned() {
                secondPlane.setModel("");
                assertFalse(firstPlane.equals(secondPlane));
            }

    public void whenEqualsObjectsWithTheDifferentTypesFalseShouldBeReturned() {
        secondPlane.setType("");
        assertFalse(firstPlane.equals(secondPlane));
    }

            @Test
    public void whenEqualsObjectsWithTheDifferentOwnersFalseShouldBeReturned() {
                secondPlane.setOwner("");
        
                        assertFalse(firstPlane.equals(secondPlane));
            }


            @Test
    public void whenEqualsObjectWithNullThenShouldBeReturned() {
                assertFalse(firstPlane.equals(null));
            }

            @Test
    public void whenEqualsObjectsWithTheDifferentClassesFalseShouldBeReturned() {
                assertFalse(firstPlane.equals(new Object()));
            }
}



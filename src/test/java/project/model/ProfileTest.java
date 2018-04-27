package project.model;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class ProfileTest {


    /**
     * GIVEN a string
     * WHEN we call the method contains
     * THEN we get a boolean TRUE result
     */
    @Test
    public void shouldContain() {

        String unassigned = "UNASSIGNED";

        assertTrue(Profile.contains(unassigned));
    }

    /**
     * GIVEN a string
     * WHEN we call the method contains
     * THEN we get a boolean FALSE
     */
    @Test
    public void shouldNotContain() {

        String unassigned = "NOTHING";

        assertFalse(Profile.contains(unassigned));
    }
}

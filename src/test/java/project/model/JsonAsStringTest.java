package project.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class JsonAsStringTest {

    @Test
    public void equalsTest() {
        JsonAsString jasA = new JsonAsString("Test1");
        JsonAsString jasB = new JsonAsString("Test1");
        JsonAsString jasC = new JsonAsString("Test2");
        JsonAsString jasD = null;
        Project p1 = new Project("name3", "description4", new User());

        assertTrue(jasA.equals(jasA));// same object
        assertFalse(jasA.equals(jasD));// null object

        assertFalse(jasA.equals(p1));// different classes
        assertFalse(jasA.equals(jasC));// different string
        assertTrue(jasA.equals(jasB));// same string
    }

    @Test
    public void hashCodeTest() {
        JsonAsString jasA = new JsonAsString("Test1");
        assertEquals(80699807, jasA.hashCode());
    }
}
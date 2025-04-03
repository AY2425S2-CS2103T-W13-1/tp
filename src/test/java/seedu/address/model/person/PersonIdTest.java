package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PersonIdTest {
    @Test
    public void constructor_validPersonId() {
        PersonId personId = new PersonId("1");
        assertEquals("1", personId.value);
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PersonId(null));
    }

    @Test
    public void constructor_invalidPersonId_throwsIllegalArgumentException() {
        String invalidPersonId_empty = "";
        assertThrows(IllegalArgumentException.class, () -> new PersonId(invalidPersonId_empty));

        String invalidPersonId_string = "test";
        assertThrows(IllegalArgumentException.class, () -> new PersonId(invalidPersonId_string));
    }

    @Test
    public void isValidId() {
        // null person id
        assertThrows(NullPointerException.class, () -> PersonId.isValidId(null));

        // blank person id
        assertFalse(PersonId.isValidId("")); // empty string
        assertFalse(PersonId.isValidId(" ")); // spaces only

        // invalid person id
        assertFalse(PersonId.isValidId("test")); // non-integer
        assertFalse(PersonId.isValidId("123.45")); // non-integer
        assertFalse(PersonId.isValidId("123-45")); // non-integer
        assertFalse(PersonId.isValidId("123 45")); // non-integer
        assertFalse(PersonId .isValidId("-1")); // negative integer

        // valid person id
        assertTrue(PersonId.isValidId("0")); // zero
        assertTrue(PersonId.isValidId("1")); // positive integer
        assertTrue(PersonId.isValidId("1234567890")); // long integer
    }

    @Test
    public void reset() {
        assertEquals(0, PersonId.reset());
    }

    @Test
    public void setCounter() {
        // valid input
        assertEquals(5, PersonId.setCounter(5));
        assertEquals(0, PersonId.setCounter(0));
        assertEquals(Integer.MAX_VALUE, PersonId.setCounter(Integer.MAX_VALUE));

        // invalid input
        assertThrows(IllegalArgumentException.class, () -> PersonId.setCounter(-1));
        assertThrows(IllegalArgumentException.class, () -> PersonId.setCounter(Integer.MIN_VALUE));
    }

    @Test
    public void equals() {
        PersonId personId1 = new PersonId("1");
        PersonId personId2 = new PersonId("2");
        PersonId personId1Copy = new PersonId("1");

        // same object -> returns true
        assertTrue(personId1.equals(personId1));

        // same values -> returns true
        assertTrue(personId1.equals(personId1Copy));

        // different types -> returns false
        assertFalse(personId1.equals(1));

        // null -> returns false
        assertFalse(personId1.equals(null));

        // different person id -> returns false
        assertFalse(personId1.equals(personId2));
    }
}

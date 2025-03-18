package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class TagNamesContainsTagsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateTagList = Collections.singletonList("first");
        List<String> secondPredicateTagList = Arrays.asList("first", "second");

        TagNamesContainsTagsPredicate firstPredicate = new TagNamesContainsTagsPredicate(firstPredicateTagList);
        TagNamesContainsTagsPredicate secondPredicate = new TagNamesContainsTagsPredicate(secondPredicateTagList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagNamesContainsTagsPredicate firstPredicateCopy = new TagNamesContainsTagsPredicate(firstPredicateTagList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different tag list -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personHasAllTags_returnsTrue() {
        // Person with exact tag match
        TagNamesContainsTagsPredicate predicate =
                new TagNamesContainsTagsPredicate(Collections.singletonList("friends"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("friends").build()));

        // Person with all requested tags (multiple tags)
        predicate = new TagNamesContainsTagsPredicate(Arrays.asList("friends", "neighbor"));
        assertTrue(predicate.test(
                new PersonBuilder().withName("Alice Bob").withTags("friends", "neighbor", "family").build()));

        // Case-insensitive matching
        predicate = new TagNamesContainsTagsPredicate(List.of("FRIENDS"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("friends").build()));
    }

    @Test
    public void test_personDoesNotHaveAllTags_returnsFalse() {
        // Person missing some of the requested tags
        TagNamesContainsTagsPredicate predicate =
                new TagNamesContainsTagsPredicate(Arrays.asList("friends", "colleague"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("friends").build()));

        // Person has none of the requested tags
        predicate = new TagNamesContainsTagsPredicate(List.of("colleague"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("friends", "family").build()));

        // Person has no tags
        predicate = new TagNamesContainsTagsPredicate(List.of("friends"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withTags().build()));
    }

    @Test
    public void toStringMethod() {
        List<String> tags = Arrays.asList("tag1", "tag2");
        TagNamesContainsTagsPredicate predicate = new TagNamesContainsTagsPredicate(tags);

        String expected = TagNamesContainsTagsPredicate.class.getCanonicalName() + "{tags=" + tags + "}";
        assertEquals(expected, predicate.toString());
    }
}

package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s tags contain all the tag(s) given.
 * Tag matching is case-insensitive.
 */
public class TagNamesContainsTagsPredicate implements Predicate<Person> {
    private final List<String> tags;

    public TagNamesContainsTagsPredicate(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(Person person) {
        return tags.stream()
                .allMatch(tagName -> person.getTags().stream()
                        .anyMatch(tag -> tag.tagName.equalsIgnoreCase(tagName)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagNamesContainsTagsPredicate)) {
            return false;
        }

        TagNamesContainsTagsPredicate otherTagNamesContainsTagsPredicate = (TagNamesContainsTagsPredicate) other;
        return tags.equals(otherTagNamesContainsTagsPredicate.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("tags", tags).toString();
    }
}

package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's ID in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidId(String)}
 * For internal use and reference only.
 */
public class PersonId {

    public static final String MESSAGE_CONSTRAINTS =
            "PersonId should be an integer"; // The person id must be a valid integer
    public static final String VALIDATION_REGEX = "^0|[1-9]\\d*$";

    private static int counter = 0;
    public final String value;

    /**
     * Constructs a {@code PersonId}.
     * When a new {@code Person} is created, the {@code Person} is assigned a unique ID.
     */
    public PersonId() {
        PersonId.counter++;
        this.value = String.valueOf(PersonId.counter);
    }

    /**
     * Constructs a {@code PersonId} with a specified ID.
     * Used when loading data from storage.
     */
    public PersonId(String id) {
        requireNonNull(id);
        checkArgument(isValidId(id), MESSAGE_CONSTRAINTS);
        value = id;
    }

    /**
     * Returns true if a given string is a valid person id.
     */
    public static boolean isValidId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Resets the counter to 0.
     * Called in conjunction with {@code ClearCommand#execute()}.
     */
    public static int reset() {
        PersonId.counter = 0;
        return PersonId.counter;
    }

    /**
     * Sets the counter to the specified value.
     * Called in conjunction with {@code Storage#readAddressBook()}.
     */
    public static int setCounter(int maxId) {
        checkArgument(isValidId(String.valueOf(maxId)), MESSAGE_CONSTRAINTS);
        PersonId.counter = maxId;
        return PersonId.counter;
    }

    /**
     * Getter to obtain person ID
     * @return the person ID in integer
     */
    public int getIntId() {
        return Integer.parseInt(this.value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonId)) {
            return false;
        }

        PersonId otherId = (PersonId) other;
        return value.equals(otherId.value);
    }
}

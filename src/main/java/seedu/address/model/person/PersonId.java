package seedu.address.model.person;

/**
 * Represents a Person's ID in the address book.
 * Guarantees: immutable
 * For internal use and reference only.
 */
public class PersonId {

    public static int counter = 0;
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
        this.value = id;
    }

    /**
     * Resets the counter to 0.
     * Called in conjunction with {@code ClearCommand#execute()}.
     */
    public static void reset() {
        PersonId.counter = 0;
    }

    /**
     * Sets the counter to the specified value.
     * Called in conjunction with {@code }.
     */
    public static void setCounter(int maxId) {
        PersonId.counter = maxId;
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

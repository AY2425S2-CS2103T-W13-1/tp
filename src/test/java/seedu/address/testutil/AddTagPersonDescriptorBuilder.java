package seedu.address.testutil;

import seedu.address.logic.commands.PersonDescriptor;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building AddTagPersonDescriptor objects.
 */
public class AddTagPersonDescriptorBuilder {
    private PersonDescriptor descriptor;
    public AddTagPersonDescriptorBuilder() {
        descriptor = new PersonDescriptor();
    }

    public AddTagPersonDescriptorBuilder(PersonDescriptor descriptor) {
        this.descriptor = new PersonDescriptor(descriptor);
    }
    /**
     * Returns an {@code AddTagPersonDescriptor} with fields containing {@code person}'s details
     */
    public AddTagPersonDescriptorBuilder(Person person) {
        descriptor = new PersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setTags(person.getTags());
    }
    public PersonDescriptor build() {
        return descriptor;
    }


}

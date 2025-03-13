package seedu.address.testutil;

import seedu.address.logic.commands.AddTagCommand.AddTagPersonDescriptor;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building AddTagPersonDescriptor objects.
 */
public class AddTagPersonDescriptorBuilder {
    private AddTagPersonDescriptor descriptor;
    public AddTagPersonDescriptorBuilder() {
        descriptor = new AddTagPersonDescriptor();
    }

    public AddTagPersonDescriptorBuilder(AddTagPersonDescriptor descriptor) {
        this.descriptor = new AddTagPersonDescriptor(descriptor);
    }
    /**
     * Returns an {@code AddTagPersonDescriptor} with fields containing {@code person}'s details
     */
    public AddTagPersonDescriptorBuilder(Person person) {
        descriptor = new AddTagPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setTags(person.getTags());
    }
    public AddTagPersonDescriptor build() {
        return descriptor;
    }


}

package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddTagCommand.AddTagPersonDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

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

    /**
     * Sets the {@code Name} of the {@code AddTagPersonDescriptor} that we are building.
     */
    public AddTagPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code AddTagPersonDescriptor} that we are building.
     */
    public AddTagPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code AddTagPersonDescriptor} that we are building.
     */
    public AddTagPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code AddTagPersonDescriptor} that we are building.
     */
    public AddTagPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code AddTagPersonDescriptor}
     * that we are building.
     */
    public AddTagPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }
    public AddTagPersonDescriptor build() {
        return descriptor;
    }


}

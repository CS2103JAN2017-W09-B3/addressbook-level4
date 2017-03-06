package seedu.task.model.task;

import java.util.Objects;

import seedu.task.commons.util.CollectionUtil;
import seedu.task.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Person implements ReadOnlyTask {

    private Name name;
    private StartDate phone;
    private EndDate email;
    private CompletionStatus address;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, StartDate phone, EndDate email, CompletionStatus address, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyTask source) {
        this(source.getName(), source.getStartDate(), source.getEmail(), source.getCompletionStatus(), source.getTags());
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setPhone(StartDate phone) {
        assert phone != null;
        this.phone = phone;
    }

    @Override
    public StartDate getStartDate() {
        return phone;
    }

    public void setEmail(EndDate email) {
        assert email != null;
        this.email = email;
    }

    @Override
    public EndDate getEmail() {
        return email;
    }

    public void setAddress(CompletionStatus address) {
        assert address != null;
        this.address = address;
    }

    @Override
    public CompletionStatus getCompletionStatus() {
        return address;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this person with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setPhone(replacement.getStartDate());
        this.setEmail(replacement.getEmail());
        this.setAddress(replacement.getCompletionStatus());
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}

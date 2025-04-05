# How to use ScoopBook?

This is an overview of the commands available in ScoopBook. For more details, visit our user guide by copying the link provided.

## `add` command

Adds a contact to the address book.

```
add n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]
```

Example:

```
add n/John Doe p/98765432
```
Note:
- Tags can still be added later using the addtag command.

2. Listing all contacts: list

Shows a list of all contacts in the address book.

Format: list

3. Editing a contact: edit

Edits an existing contact in the address book.

Format: edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]

Note:
- Edits the contact at the specified INDEX. The index refers to the index number shown in the displayed contact list.
  The index must be a positive integer (1, 2, 3, …).
- At least one of the optional fields must be provided.
- Existing values will be updated to the input values.
- When editing tags, the existing tags of the person will be removed (i.e., adding of tags is not cumulative).
- You can remove all the contact’s tags by typing t/ without specifying any tags after it.

Examples:
- edit 1 p/91234567 e/johndoe@example.com edits the phone number and email address of the 1st person to 91234567 and
  johndoe@example.com.
- edit 2 n/Betsy Crower t/ edits the name of the 2nd person to Betsy Crower and clears all existing tags.

4. Locating contacts by name: find

Finds persons whose names contain any of the given keywords.

Format: find KEYWORD [MORE_KEYWORDS]

Note:
- The search is case-insensitive. For example, hans will match Hans.
- The order of the keywords does not matter. For example, Hans Bo will match Bo Hans.
- Only the name is searched.
- Only full words will be matched. For example, Han will not match Hans.
- Persons matching at least one keyword will be returned (OR search). For example, Hans Bo will return Hans Gruber and
  Bo Yang.

5. Deleting a person: delete

Deletes the specified person from the address book.

Format: delete INDEX

Note:
- Deletes the person at the specified INDEX.
- The index refers to the index number shown in the displayed person list.
- The index must be a positive integer (1, 2, 3, …).

Examples:
- list followed by delete 2 deletes the 2nd person in the address book.
- find Betsy followed by delete 1 deletes the 1st person in the results of the find command.

6. Clearing all entries: clear

Warning: this clears all entries from the address book.

Format: clear

7. Exiting the program: exit

Exits the program.

Format: exit

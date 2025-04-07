---
layout: page
title: User Guide
---

# ScoopBook

## :trophy: Our Goal

**ScoopBook** is built to help journalists efficiently manage the contacts of their sources, witnesses, and other key individuals they interact with on the job.

## :dart: Problems We’re Solving

- Traditional contact apps (like those on mobile phones) often have clunky interfaces that make adding and organizing contacts a hassle.
- Journalists frequently juggle multiple tools just to do simple tasks (like saving a contact or jotting down notes) wasting valuable time.
## :mag: How ScoopBook works?

**ScoopBook** is a **desktop contact management app** designed with journalists in mind. It combines the speed and precision of a **Command Line Interface (CLI)** with the ease of a **Graphical User Interface (GUI)**—so if you can type fast, you can work fast.

With ScoopBook, you get:
- **Blazing-fast** contact entry
- **Smart categorization** of contacts (e.g. sources, leads, officials)
- **Powerful, instant search** to find the right person, fast

ScoopBook helps you stay organized without breaking your workflow.
* Table of Contents
  {:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest `.jar` file from [here](https://github.com/AY2425S2-CS2103T-W13-1/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your ScoopBook.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar [CS2103-W13-1][ScoopBook].jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    * `list` : Lists all contacts.

    * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the address book.

    * `delete 3` : Deletes the 3rd contact shown in the current list.

    * `clear` : Deletes all contacts.

    * `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Commands are case-sensitive. <br>
  e.g. `LIST`command will not work.

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows the user guide, containing instructions on how to use the command.

```dtd
help
```

### Adding a person: `add`

Adds a person to the address book.

```dtd
add n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​
```
* The add command **must** have a name, and one of the following fields: phone number, email, address.
  i.e. `add n/Johnny Appleseed` does not work because there is no phone number, email or address.
* A person can have any number of tags (including 0).
* A person's name can only contain alphanumeric characters (numbers or letters only), whitespaces, and the following special characters: `,`, `(`, `)`, `@`, `.`, `-`, `'`.
* A person's tags can only contain alphanumeric characters (numbers or letters only, no special characters).
* If a contact is added with the following values, they will not be displayed in the contact list, as they are used as internal placeholders:
    - Phone: `000`
    - Email: `unknown@example.com`
    - Address: `Unknown address`  
      This ensures that every contact has a placeholder value for these fields if left empty.

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Listing all persons : `list`

Shows a list of all persons in the address book.

```
list
```

### Editing a person : `edit`

Edits an existing person in the address book at specified index.

```dtd
edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​
```

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* :bulb: TIP: You can remove all the person’s tags by typing `t/` without specifying any tags after it.
* Similar to the `add` command, the aforementioned placeholder values will not be displayed in the contact list.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 1 t/friends t/colleagues` Removes all existing tags of the 1st person, and sets the 1st person's tag to `friends` and `colleagues` only.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

```dtd
find KEYWORD [MORE_KEYWORDS]
```
* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `delete`

Deletes the specified person from the address book.

```dtd
delete INDEX
```

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Adding tags to a contact: `addtag`

Adds the tags typed in to the specified person.

```dtd
addtag INDEX t/TAG1 [t/MORETAGS]
```

* Adds the specified tags to the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* Multiple tags in a single `addtag` command is supported.
  i.e. `addtag 1 t/friend t/neighbour` will tag the 1st person with both "friend" and "neighbour".
* Tags can only contain alphanumeric characters (numbers or letters only, no special characters or spaces).
* Tags are case-sensitive.
  i.e. `addtag 1 t/friend` will add the tag "friend" while `addtag 1 t/Friend` will add the tag "Friend".

Examples:
* `list` followed by `addtag 2 t/friend` tags the 2nd person with "friends" in the address book.
* `find Betsy` followed by `addtag 1 t/friend` tags the 1st person in the results of the `find` command with "friends".

### Removing tag from a contact: `removetag`

Removes the specified tag from the person.

```dtd
removetag INDEX t/TAG1 [t/MORETAGS]
```

* Removes the specified tags from the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* Multiple tags in a single removetag command is supported.
  i.e. `removetag 1 t/friend t/neighbour` will remove both the "friend" and "neighbour" tag for the 1st person.
* To remove all tags from a person, type `edit INDEX t/` instead.
* Tags are case sensitive. The typed tag must match the tag on the person exactly.
  i.e. `removetag 1 t/friend` will not remove the tag "Friend".

Examples:
* `list` followed by `removetag 2 t/friend` removes the "friend" tag from the 2nd person in the address book.
* `find Betsy` followed by `removetag 1 t/friend` removes the "friend" tag from the 1st person in the results of the `find` command.

### Finding people with tags: `findtag`

Find persons who have all of the specified tags.

```dtd
findtag t/TAG1 [t/MORETAGS]
```

* The searching of tags is case-insensitive. e.g `friends` will match `Friends`
* The order of the tags does not matter. i.e. As long as the person has the listed tags, they will be shown.
* Only the tags are searched.
* Only full words will be matched e.g. `Friend` will not match `Friends`
* Only persons matching all the tags will be returned (i.e. `AND` search).

Examples:
* `findtag t/friends` returns people with tag `friends`, `Friends`, `FriEndS` (due to case insensitivity).
* `findtag t/friends t/neighbours` returns people with tag `friends` **and** `neighbours` only.

### Opening Note for Person: `note`

Open a window for the user to add notes to.
If the person at the specified `INDEX` already has a note, the note will be displayed and the user can edit it in the window.

If no note exists for the person, a new note will be created and displayed in the window for editing. <br> 

```dtd
note INDEX
```
* Opens a window for the user to add notes to the person at the specified `INDEX`.
  * Please use only this opened window to edit the note (see [#Known issues](#known-issues) section below)
* The index refers to the index number shown in the displayed person list.
* The index must be a positive integer 1, 2, 3, …
* The note will be saved when the window is closed.

Examples:
* `list` followed by `note 2` opens a note window for the 2nd person in the address book.
* `find Betsy` followed by `note 1` opens a note window for the 1st person in the results of the `find` command.

### Deleting Note from Person: `deletenote`

Deletes the note from the person.

```dtd
deletenote INDEX
```

* Deletes note for the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `deletenote 2` deletes the note for the 2nd person in the address book, if the note exists.
* `find Betsy` followed by `deletenote 1` deletes the note for the 1st person in the results of the `find` command, if the note exists.

### Clearing all entries : `clear`

Clears all entries from the address book.

- :warning: WARNING: This clears all contacts and notes from the address book.

```dtd
clear
```

### Exporting your contacts: `export`

Exports the contacts in a .json file to the target path.

```dtd
export TARGET_PATH
```

- The `export` command only exports your contacts. It does not export the notes tagged to them.
- Before executing the `export` command, add at least 1 contact using the `add` command.
- `export` command is case-insensitive. If `sAmPle.json` already exists (in the folder the `[CS2103-W13-1][ScoopBook].jar` is located at), `export sample.json` will overwrite `sAmPle.json`.
- Ensure that there are no special characters (E.g. `*!<>`) or spaces in the `TARGET_PATH`.
- * :bulb: TIP: If you are running into issues with TARGET_PATH, use `export sample.json` to export it directly to the root folder with of the [CS2103-W13-1][ScoopBook].jar file. Then, move the .json file to wherever you want it to be.

Examples:
* For Windows: `export C:/Users/username/Desktop/MyContacts.json`
  * saves the json file as `MyContacts.json` in the `Users/username/Desktop` folder. <br>


* For macOS: `export /Users/username/Desktop/MyContacts.json`
  * saves the json file as `MyContacts.json` in the `Users/username/Desktop` folder. <br>


* For Linux: `export /home/user/desktop/MyContacts.json` 
  * saves the json file as `MyContacts.json` in the `home/user/desktop` folder. <br>


* For all OS: `export Contacts.json`
  * saves the json file as `Contacts.json` in the root folder of where [CS2103-W13-1][ScoopBook].jar is located at.

### Importing your contacts: `import`

Imports contacts from the external .json file located at the specified path into the application.

```dtd
import TARGET_PATH
```

- :warning: WARNING: This command overwrites existing contacts and remove all notes.
- Only import .json files exported using the `export` command.
- Ensure that there are no special characters (E.g. `*!<>`) or spaces in the `TARGET_PATH`.

Examples:
* For Windows: `import C:/Users/username/Desktop/MyContacts.json` imports the json file from `MyContacts.json` in the `Users/username/Desktop` folder.
* For macOS: `import /Users/username/Desktop/MyContacts.json` imports the json file from `MyContacts.json` in the `Users/username/Desktop` folder.
* For Linux: `import /home/user/desktop/MyContacts.json` imports the json file from `MyContacts.json` in the `home/user/desktop` folder.
* `import Contacts.json` imports the json file named `Contacts.json` from the root folder of where [CS2103-W13-1][ScoopBook].jar is located at.

### Exiting the program : `exit`

Exits the program.

```dtd
exit
```

### Saving the data

ScoopBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

ScoopBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

**Unsure where to find the JSON file? No worries! Follow these instructions:**

1. In ScoopBook, type the following command: `export temp.json`
2. `temp.json` will be saved in your JAR file location. Open it in an editor of your choice.
3. Edit the fields while adhering to the format of the file. Save the JSON file.
4. In ScoopBook, type the following command: `import temp.json`
5. Done!

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, ScoopBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the ScoopBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous ScoopBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.
3. **If you open the Note Window**, and then run the `note` command again, the original Note Window will remain minimized, and no new Note Window will appear. The remedy is to manually restore the minimized Note Window.
4. **If you use any other means apart from the note window that ScoopBook opens to edit a note**, (eg. notepad) we cannot guarantee that your edits will be saved. This may be because of an encoding incompatibility between your text editor and ScoopBook's. Please use the note window that ScoopBook opens to edit the note.
5. **Text fields in the GUI**: Currently, text fields that are too long may be cut off in the GUI. We will introduce scrolling as a feature to enable viewing these fields in full in future releases.
6. **Adding a contact with placeholder values**: Currently, we do not prevent the user from adding a contact with placeholder values. This is because we want to allow the user to add a contact with only a name and one other field, and we chose these placeholder values as unlikely values that would be used for a contact.
    1. Regardless, we acknowledge that this may lead to confusion as these contact fields deliberately added with placeholder values will not be displayed in the contact list. We will fix this in future releases.
8. **Finding a contact**: Currently, the `find` command performs an `OR` search. While all contacts matching at least one keyword will be returned, they are not sorted according to the highest similarity or match. We will improve this in future releases.
--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Add Tag** | `addtag INDEX t/TAG1 [t/MORETAGS]…​` <br> e.g., `addtag 2 t/friend`
**Find Tag** | `findtag t/TAG1 [t/MORETAGS]…​` <br> e.g., `findtag t/friend`
**Remove Tag** | `removetag INDEX t/TAG1 [t/MORETAGS]…​` <br> e.g., `removetag 2 t/friend`
**Note** | `note INDEX` <br> e.g., `note 2`
**Delete Note** | `deletenote INDEX` <br> e.g., `deletenote 3`
**Export Contacts** | `export TARGET_PATH` <br> e.g., `export backup.json`
**Import Contacts** | `import TARGET_PATH` <br> e.g., `import previousVer.json`
**List** | `list`
**Help** | `help`

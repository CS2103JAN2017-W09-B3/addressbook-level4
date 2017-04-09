# Suru - Manual Test Script

By : `W09-B3`

---


## Introduction

This test script is to assist testers during their testing with `Suru` by providing them with sample test cases which covers the full range of functionality of `Suru`.

## Setting Up

1) Download the zip file [W09-B3][Suru].zip <br>
2) Extract all contents of the zip file into the same folder. The following files should be in the same folder if the extraction is successful.

> * [W09-B3][Suru].jar
> * data
	* taskmanager.xml
> * sampledata.xml
> * testscript.md

3) Launch [W09-B3][Suru].jar

## Manual Tests

### Loading sample file

* Type `load` `SampleData.xml`

> * You should see the task list being replaced with sample tasks
> * Suru should also give you the message "Load location has been changed."

### Adding Tasks

#### 1. Add a floating task
* Type `add` `Watch Fast and Furious 8`

> * The new task will be added into the Task Panel on the left
> * Newly added task is selected on the task list panel


#### 2. Add a deadline task
* Type `add` `Complete history assignment by 30/4/17`

> * The new task will be added with an end date of 30 April 2017
> * Newly added task is selected on the task list panel

#### 3. Add an event
* Type `add` `Attend Alice birthday party from next tuesday 8pm to next tuesday 10pm`

> * The new task will be added next Tuesday with a start time from 8pm to 10pm
> * Newly added task is selected on the task list panel 

#### 4. Add an event with time only
* Type `add` `Dinner with girlfriend at Marche from 8pm to 10pm`

> * The new task will be added with a start time from 8pm to 10pm on the same day
> * Newly added task is selected on the task list panel

#### 5. Add an event with tags
* Type `add` `Submit club event proposal by tomorrow #club #urgent`

> * The new task will be added with 'club' and 'urgent' tagged to the task
> * Newly added task is selected on the task list panel

### Undo / Redo
* Type `undo`

> *  The previous task added will be deleted
> * Suru should give you a message "Undo Command Successful. Deleted task: [submit club event proposal task details]"
* Type `redo`
> *  The deleted task will be added again
> * Suru should give you a message "Redo Command Successful. Restored previously added task: [submit club event proposal task details]"

### Edit Tasks
* Type `edit` `1` `Ask prof about Q10`

> * The existing task name `Ask prof about Q3`  will be changed to `Ask prof about Q10`
> * Edited task will be selected on the task list panel
> * Suru should give you a message "Edited task: [task details]"

### Delete Tasks
* Type `delete` `7`

> * Remove the task listed in index 7
> * Suru should give you a message "Deleted task: [task details]

### Add / Delete Tag
* Type `addtag` `1` `#revision #work`

> * The added tag will be added to the task
> * Suru should give you a message "Added new tags into task: [task details]"
* Type `deltag` `9` `#school`
> * The tag `#school` will be removed from from the task
> * Suru should give you a message "Deleted tags from task: [task details]"

### List Tasks
#### 1. List all tasks
* Type `list`

> * All tasks will be listed on Suru Task Panel, sorted by due date and completion status
> * Suru should give you a message "Listed all tasks"


#### 2. List completed tasks
* Type `list` `checked`

> * Only completed tasks will be listed on Suru Task Panel
> * Suru should give you a message "Listed all checked tasks"

#### 3. List incomplete tasks
* Type `list` `unchecked`

> * Only incomplete tasks will be listed on Suru Task Panel
> * Suru should give you a message "Listed all unchecked tasks"

#### 4. List overdue tasks
* Type `list` `overdue`

> * Only overdue tasks will be listed on Suru Task Panel
> * Suru should give you a message "Listed all overdue tasks"

#### 5. List upcoming tasks
* Type `list` `upcoming`

> * All upcoming task that are due in the next 3 days will be listed on Suru Task Panel
> * Suru should give you a message "Listed all upcoming tasks"

#### 6. List floating tasks
* Type `list` `someday`

> * All floatings task will be listed on Suru Task Panel
> * Suru should give you a message "Listed all someday tasks"

### Select Task
* `select` `3`

> * The task at index 3 is highlighted on Suru Task Manager
> * Suru should give you a message "Selected Task : 3 [taskdetails]"

### Find Tasks
* Type `find` `school`

> * All tasks with the word `school` in the name or the tags will be shown
> * Suru should give you a message "16 tasks listed!"

### Check / Uncheck Tasks
* Type `list` <br>
 Type `check` `1`

> * Lists all task in Suru
> * Checks off the 1st task in Suru Task Panel
> * Newly checked task will be highlighted and added below with the other completed tasks
> * Suru should also give you the message "Task `Ask prof about Q10` checked/completed!"

* Type `find` `proposal` <br>
Type `uncheck` `2`

> * Search for task with name 'proposal'
> * Unchecks off the 1st task in the Task Panel.
> * Newly unchecked task will be highlighted and added into the correct position sorted by due date
> * Suru should also give you the message "Task `Ask boss for feedback on proposal` unchecked/incomplete!"

### Reminder
#### 1. Set preferred email
* Type `email` `test@gmail.com`

> * Suru sets the email address to receive email reminders to `test@gmail.com`.
> * Suru should also give you the message "New email set successfully!"

#### 2. Set time before event to remind
* Type `remindme` `5`

> * Suru changes the number of minutes before an event before sending out a reminder for the user.
> * Suru should also give you the message "New reminder time set successfully! Will remind 5 minutes before the event!"

#### 3. Enable reminders
* Type `reminders` `enable`

> * Suru enables email reminders to be sent out for all task which have a start time by syncing the tasks data to a server on the cloud.
> * Suru should also give you the message "Reminders have been enabled and will be synced to the cloud!"
 
#### 4. Disable reminders
* Type `reminders` `disable`

> * Suru disables all email reminders for the email address
> * Suru should also give you the message "Reminders have been disabled."

### Save To
* Type `saveto` `myData.xml`

> * Suru changes its default save location to the unzipped directory with the file name "myData.xml"
> * Suru should display the new save location in the status bar as `Saving to: myData.xml`
> * Suru should also give you the message "Save location has been changed."

### Help
* Type `help`

> * Suru help window should display as a popup
> * Click on 'X' to close the window

### Clear Task Manager
* Type `clear`

> * Deletes all task data from Suru Task Manager

### Exit Task Manager
* Type `exit`

> * Exits Suru Task Manager
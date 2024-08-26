# ClosetApp

## a closet tracking application

This application is designed 
to allow the user to keep track of their clothes, especially
those who have extensive closets or patchy memories _(or both)_. In short
this is for people like me, who think of clothes as *"out of sight out of mind"*
and perpetually have to dig through their physical closets in order to find something,
potentially creating a mess and wasting time. This project is of interest to me 
because lately I have recently **greatly** downsized my own closet but still find myself having to 
physically go through it all to try to find something green or something 
corduroy, *and in this day and age I really ought to be able to do that in bed on an app.* 


## USER STORIES: *as a user, I want to be able to*
- add an article of clothing to my closet 
- remove an article of clothing from my closet
- wear an item in my closet
- wash all of my clothes
- wash a specific article of clothing
- save my closet to file 
- load my closet to file 

## Instructions for Grader

### How to add clothing to the closet (1st required event): 
- click the "add to closet" button 
- fill out the information in the text-boxes provided (for example, write the colour of the clothing you want to add in the text box with "colour" already in the box. Remember to empty the box before writing in!)
- press the submit button
- choose the fit of your clothing, if it is fitted, relaxed, or baggy

### How to filter your closet by colour or type (2nd required event):
- click one of the "filter closet" buttons, depending on what you want to filter your closet by
- type out the in provided text box what kind of clothing, or what colour clothing you want to see from your closet
- press the submit button

### How to empty your closet
- click on the "clear closet" button
- there is a weird bug in this button. You have to adjust the frame size to see that the closet has been cleared 

### Where to find the visual component of the Closet
- after clicking on the "save closet" button, a JOptionPane will pop up with a thumbs up picture and "you have saved your closet" as a message

### How to save your closet to file
- click on the "save closet" button

### How to load your closet from file
- click on the "load closet" button


### Phase 4: Task 2
### sample of events:
note: adding, loading, and filtering are the only user actions 
that are logged.

- Event log cleared. 
- loaded closet from file 
- Added to closet. 
- Closet filtered by type. 
- Closet filtered by colour.


### Phase 4: Task 3

I would have refactored my ClosetAppGUI class.
- either into classes separating all the different windows/frames or:
- added different classes for the different functions of the app
  - new class to hold all the methods for adding a piece of clothing
  - new class for saving and loading the closet using Json

- the reason why the ClosetAppGUI class would be the focus for refactoring is because while 
making it I found that I kept scrolling through 400 lines of code to look for a method I needed, 
and there ended up being some methods that had confusingly similar names (such as filterByColour 
and createOneTypeCloset) and some names like secondAddingStep, which without my context of creating this code would 
not be very informative. Of course, it was also not very cohesive as a class.
It has many responsibilities, as opposed to the one that it should have.
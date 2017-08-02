# Ex Tessera

https://play.google.com/store/apps/details?id=ny.gelato.extessera

### 'Deus ex tessera' - God from the dice

That's the main idea behind the creation of this app: No matter who's the master of the dungeon, the real power & fun is in a roll of the dice.

Ex Tessera is meant to facilitate this fun by replacing a 5th edition paper character sheet. No more writing and constantly re-writing information. Easy to use, like this:


![ ](https://media.giphy.com/media/xT39D5cbo5VGmk14uQ/giphy.gif)


Searching for spells and weapons is easy, too:


![ ](https://media.giphy.com/media/3oEhmPluqSfhQZRCgw/giphy.gif)


### Summary

The app is written in Kotlin and primarily uses a Model-View-ViewModel architecture. With the Android Databinding library, a lot of the interaction between View and ViewModel happens in the .xml file. I found this convenient as a developer wearing a designer's hat - I spent a lot of time in xml designing functional views that look nice and coherent.

More complex Views that display mulitple ViewModels implement an MVP-like architecture with a bit of MVI where each click on a ViewModel may signal its Intent with a BaseViewModel.Action. The presenter would then route that BaseViewModel/Action combo to the appropriate place, whether telling the View to display relevant user input, telling the Manager to perform some CRUD function, etc.

I've tried to comment each feature so that someone reading might more easily understand what I was trying to accomplish. Usually this comment is found in the feature package's View. I also encourage you to look at the base package to see a lot of classes/interfaces that are essential in most features - these are commented as well.

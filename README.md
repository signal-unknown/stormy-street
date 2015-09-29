#Stormy Street
Android app as part of DAT255 at Chalmers University of Technology.

##Important
Run following commands to make sure that API keys don't get pushed to GitHub
```
git update-index --assume-unchanged app/src/main/res/values/api_keys.xml
git update-index --assume-unchanged app/src/main/java/dat255/chalmers/stormystreet/APIConstants.java
```
You must add the API keys for ElectriCity and Google Maps manually to the following files
```
app/src/main/res/values/api_keys.xmlapp/src/main/java/dat255/chalmers/stormystreet/APIConstants.java
```
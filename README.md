#Stormy Street
An Android application called 'Bus Battle' (Stormy Street is the project name)
made as part of the DAT255 Software Engineering Project at Chalmers University
of Technology.

##Documentation
All documentation for the project can be found in the documents folder.

We also made two videos describing our concept and showing off our prototype:

[Concept video - Swedish](https://www.youtube.com/watch?v=GyUcD81qmlM)

[Prototype video](https://www.youtube.com/watch?v=jGopp9TR8vA)

##Usage
A signed APK for the app can be found at `app/app-release.apk`. The app is also
currently available as an open beta on Google Play at the following link:
[Google Play Beta](https://play.google.com/apps/testing/dat255.chalmers.stormystreet)

Note that for the Facebook integration to properly work you need to be using
the signed APK or install the app from Google Play - this is for security
purposes. Your facebook account also needs to be added as a beta tester
of the app, please contact the developers for this. There is also
a test account that can be used, you can get the login details for this 
from the developers

##Building
Before building you need to grab your own API key for both Google Maps and for
the ElectriCity API. These should then be inserted in the following files:
```
app/src/main/res/values/api_keys.xml
app/src/main/java/dat255/chalmers/stormystreet/APIConstants.java
```
The app will still build without them but will not work as expected. If you don't have the keys available but still want to try the app just use one of
the ways described under **Usage**.

##Development
###Important
To make sure that your own private API keys are not pushed to GitHub, please run
the following commands:
```
git update-index --assume-unchanged app/src/main/res/values/api_keys.xml
git update-index --assume-unchanged app/src/main/java/dat255/chalmers/stormystreet/APIConstants.java
```

###Contributors
CrashIT14 -- Alexander Håkansson  

Criticalstone -- Kevin Hoogendijk  

Sweshiman -- Alexander Karlsson  

davve94 -- David Fogelberg  

Mxiim -- Maxim Goretskyy  

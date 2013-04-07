# CallTag 
Simple Web App that utilizes Twitter and Twillio Api.

[CallTag Demo](http://calltag.herokuapp.com/index.htm)

App allows to make phone calls and send text message through tweet, by tweeting with hashtag **#call** or **#text** with the target phone numbers.


### Members Group 20 

* Bekhzod Khaitbaev
* Alexandros Vrakas
* Rachael Daminabo



### Requirement
Requires twitter4j.properties file in src/main/resources/twitter4j.properties containing 

	debug=true
	oauth.consumerKey=*********************
	oauth.consumerSecret=******************************************
	oauth.accessToken=**************************************************
	oauth.accessTokenSecret=******************************************



[More info](http://twitter4j.org/en/configuration.html)

##Used Libraries

1. [Twitter4j](https://github.com/yusuke/twitter4j)
2. [Twillio-Java](https://github.com/twilio/twilio-java)
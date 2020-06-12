# TweetList application made for BetVictor recruitment purposes
# Task
Display tweets which will be deliveried by twitter stream API (https://stream.twitter.com/1.1/statuses/filter.json), give user possibility to search to filter out data stream. Every displayed Tweet should have own time life span which means it should dissapear from list after some amount of time. That time counter should be stopped when user will be disconnected from internet and that item should stay in list until connection will be established again.  
# Approach
 I've used clean architecture aproach using DI pattern and ModelViewViewModel dessign pattern, application it's splitted into modules:
 - Data - module for dto classes which comes from Twitter API,
  - Common - module for some shared utilities/tools,
  - TweetsRepository - repository module to access Twitter API,
  - TweetsList - functionality module with list functionality and things related to it,
  - App - android app module.
# Screenshot
![](application.gif)
# Build Variants
Application has 2 builds flavours:
- apiData - that module allow you to use MockedTwitterRepository as data source,
- mockedData -  that module allow you to use repository which is connecting to real Api

# API configuration
For API configuration e.g. change Token Key, Consumer Key or Base URL you have to edit gradle.properties file.
# TweetList application made for BetVictor's recruitment purposes
# Task
Given task is to present an application which allows its users to display tweets which will be delivered by twitter stream API (https://stream.twitter.com/1.1/statuses/filter.json), that functionality give users possibility for searching through data stream. Every displayed Tweet should have own time life span which means it should disappear from list after some amount of time. That time counter should be stopped when user will be disconnected with internet and that item should stay in list as long as internet connection resumed.
# Approach
 I've used clean architecture approach using DI pattern and ModelViewViewModel design pattern, application is splitted into modules:
 - Data - module for dto classes which comes from Twitter API,
  - Common - module for some shared utilities/tools,
  - TweetsRepository - repository module to access Twitter API,
  - TweetsList - functionality module with list functionality and things related to it,
  - App - android app module.
# Screenshot
![](application.gif) ![](applicationLive.gif)
# Build Variants
Application has 2 builds flavours:
- apiData - that module allows you to use MockedTwitterRepository as data source,
- mockedData -  that module allows you to use repository which is connecting to real Api

# API configuration
For API configuration e.g. change Token Key, Consumer Key or Base URL - to do that you have to edit gradle.properties file.
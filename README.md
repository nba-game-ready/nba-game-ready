# 🏀 NBA Game Ready 🏀

## Table of Contents
1. [Overview](#Overview)
2. [Product Spec](#Product-Spec)
3. [Wireframes](#Wireframes)
4. [Networking](#Networking)
5. [Architecture](#Architecture)
6. [Responsibilities](#Responsibilities)

## Overview
### Description

Displays all live and upcoming NBA games, including live scores and television networks, in a format that's quick and easy to read.

### App Evaluation

- **Category:** Sports
- **Mobile:** This app would be primarily developed for mobile. Functionality wouldn't be limited to mobile devices, however mobile version could potentially have more features.
- **Story:** Pulls all NBA game information to display in a list on the main screen.
- **Market:** Ages 4+
- **Habit:** This app could be used as often as needed during the NBA season.
- **Scope:** First, we are displaying live game updates along with the television networks for televised games.  Eventually the app could evolve to displaying team and player stats from a user search.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User launches app to see a list of all NBA games for the current day
* User wants to know which games are televised and what channels those games are on
* User clicks on the network of the televised games and is taken to the network website
* User sees live updates on scores for each game
* User can click on a game and go to website to purchase tickets

**Optional Nice-to-have Stories**

* User clicks on a game listed and can see team and player stats
* User can see if they are close to a stadium in map view
* User can search for a team or player and add them to their favorites list
* User can set reminders and notifications for games
* User has an option to create an account and profile to have quick access to favorites, saved games, and setup notifications
* User has accessibility options
* User can change text to multiple different languages
* Push notifications affiliate marketing

### 2. Screen Archetypes

* Live Games Screen
   * lists of game times, scores, quarters, and television networks
   * buy ticket icon/button that connects to ticketing website
   * networks/television link that connects to the network website

* Yesterday Games Screen
   * lists of games and final scores 
   
* Tomorrow Games Screen
   * lists of game times and television networks
   * buy ticket icon/button that connects to ticketing website
   * networks/television link that connects to the network website
   
  

### 3. Navigation

**Flow Navigation** (Screen to Screen to Browser)

* Today screen -> <- Yesterday screen -> <- Tomorrow screen
* Buy ticket button -> Opens browser to ticketing website
* Networks/Television link -> Opens browser to Network's website

## Wireframes

<img src="wireframe.jpeg" width=400>


## Networking

##### API-NBA
- Base URL - [https://api-nba-v1.p.rapidapi.com/](https://api-nba-v1.p.rapidapi.com/)

   HTTP Verb | Endpoint | Description
   ----------|----------|------------
    `GET`    | /games/live/ | get all available games in play
    `GET`    | /games/date/ | get all games by a specific date
    
    
## Architecture
MVVM Architecture: Model-View-ViewModel
<img src="MVVM3.png" height=400>

## Responsibilities

| Name | Packages |
| - | :-: |
| `Kieran` | Recyclerview data & ViewModel | 
| `Raven`  | ListAdapter       |
| `Yunis`  | Networking - Games Data |
| `Claire` | Recyclerview UI |
| `Shikeya` | Main UI |
| `Sabur` | Networking - Images |


Shikeya - Team Swish.
Raven test

Claire-Team Swish
    
Kieran - Team Swish
    
Yunis Khamis

Added network
 User_Interface
Shikeya & Claire
=======
RecyclerView branch added

ViewModel brand added 

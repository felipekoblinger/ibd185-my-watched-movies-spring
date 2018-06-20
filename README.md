# Project - Development Laboratory V - IBD185
[![Build Status](https://travis-ci.com/felipekoblinger/ibd185-my-watched-movies-spring.svg?branch=master)](https://travis-ci.com/felipekoblinger/ibd185-my-watched-movies-spring)
[![Sonnar Status](https://sonarcloud.io/api/project_badges/measure?project=br.gov.sp.fatec%3AmyWatchedMovies&metric=coverage)](https://sonarcloud.io/dashboard?id=br.gov.sp.fatec%3AmyWatchedMovies)

This repository refers to final project of discipline `Development Laboratory in Database V` taught by Me. Emanuel Mineda (@Mineda) of Faculdade de Tecnologia Jessen Vidal de São José dos Campos (FATEC-SJC)

## My Watched Movies (back-end)
Is an application to collect watched movies in an easy way.

### Technologies
The following technologies used in this project:

#### Language
Java 1.8

#### Database
PostgreSQL 10

#### Framework
Spring 5.0.6

#### Dependecy Management
Maven

#### Dependencies
- Lombok (https://projectlombok.org)
- ModelMapper (http://modelmapper.org)
- Java JWT (https://github.com/jwtk/jjwt)
- Hibernate (http://hibernate.org)
- DBUnit (http://www.dbunit.org)
- JSON Path (https://github.com/json-path/JsonPath)

### API
The application API routes are:

#### Accounts
- [**POST**] /accounts/ (**No Authentication Required**)
  * Create an account with mandatory parameters: `username, email, password, name, birthday and gender`
  ```ruby
  { "username":"marionakani", "email":"marionakani@test.com", "name":"Mario Nakani", "gender":"MALE", "birthday":"20-01-1990" }
  ```
- [**GET**] /accounts/me/
  * Show logged account data
  ```ruby
  { "username":"marionakani", "email":"marionakani@test.com", "name":"Mario Nakani", "gender":"MALE", "birthday":"20-01-1990" }
  ```
- [**GET**] /accounts/exists-username-or-email/ (**No Authentication Required**)
  * Return if an username or e-mail exists
  ```ruby
  { "username":true, "email":true }
  ```
- [**PUT**] /accounts/change-password/
  * Change an account password with mandatory parameters: `currentPassword, newPassword`

#### Auth
- [**POST**] /auth/ (**No Authentication Required**)
  * Login and create a JWT Token
  ```ruby
  { "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYXJpb25ha2FuaSIsImV4cCI6MTUzMDA3MjkyMSwiaWF0IjoxNTI5NDY4MTIxfQ.D6l3fzwWOwNqj5FdHz4X21GXGFdkZ_RdTclAhkQezdoLutKimk9Cx3GVuNYxJ1QVj3Ue7Pq5He-AI3qCj-YVEw" }
  ```

#### Movies
- [**POST**] /movies/
  * Create a movie for with following parameters: `title, date (DD-MM-YYYY), rating (0-5), type, posterPath (optional), theMovieDatabaseId, genres, overview (optional) and releaseDate (DD-MM-YYYY and optional)`
  ```ruby
  { "uuid":"1529468984481-df4b7064-1408-423a-b3a3-bc8ccc4c15df", "title":"Fight Club", "date":"24-05-2017", "rating":5, "type":"SUBTITLED", "posterPath":"/anything.jpg", "theMovieDatabaseId":"550", "genres":"18", "overview":"A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \\\"fight clubs\\\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.", "releaseDate":"15-10-1999", "createdAt":"20-06-2018 01:29:44", "updatedAt":"20-06-2018 01:29:44" }
  ```
- [**PUT**] /movies/{uuid}/
  * Update a registered movie with the optional parameters: `date (DD-MM-YYYY), rating and type`
  ```ruby
  { "uuid":"1529468984481-df4b7064-1408-423a-b3a3-bc8ccc4c15df", "title":"Fight Club", "date":"25-05-2017", "rating":5, "type":"ORIGINAL", "posterPath":"/anything.jpg", "theMovieDatabaseId":"550", "genres":"18", "overview":"A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \\\"fight clubs\\\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.", "releaseDate":"15-10-1999", "createdAt":"20-06-2018 01:29:44", "updatedAt":"20-06-2018 01:35:43" }
  ```
- [**GET**] /movies/
  * List all registered movies
  ```ruby
  [{ "uuid":"1529468984481-df4b7064-1408-423a-b3a3-bc8ccc4c15df", "title":"Fight Club", "date":"24-05-2017", "rating":5, "type":"SUBTITLED", "posterPath":"/anything.jpg", "theMovieDatabaseId":"550", "genres":"18", "overview":"A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \\\"fight clubs\\\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.", "releaseDate":"15-10-1999", "createdAt":"20-06-2018 01:29:44", "updatedAt":"20-06-2018 01:29:44" }]
  ```
- [**GET**] /movies/{uuid}/
  * Show information about a specific movie
  ```ruby
  { "uuid":"1529468984481-df4b7064-1408-423a-b3a3-bc8ccc4c15df", "title":"Fight Club", "date":"24-05-2017", "rating":5, "type":"SUBTITLED", "posterPath":"/anything.jpg", "theMovieDatabaseId":"550", "genres":"18", "overview":"A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \\\"fight clubs\\\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.", "releaseDate":"15-10-1999", "createdAt":"20-06-2018 01:29:44", "updatedAt":"20-06-2018 01:29:44" }
  ```
- [**DELETE**] /movies/{uuid}/
  * Remove a movie
  ```ruby
  Resource deleted successfully.
  ```

#### Movies Statistics
- [**GET**] /movies-statistics/overall/ (**Paid Account only**)
  * Show statistics information about account's movies
  ```ruby
  { "total":2, "monthly":[{ "yearMonth":"March 2018","total":1 },{ "yearMonth":"April 2018","total":1 }], "type":{ "SUBTITLED":1,"DUBBED":1 } }
  ```

#### The Movie DB
- [**GET**] /the-movie-database/?term={term}
  * List all movies by term
  ```ruby
  {"page":1,"total_results":49,"total_pages":3,"results":[{"vote_count":756,"id":427641,"video":false,"vote_average":5.9,"title":"Rampage","popularity":71.626311,"poster_path":"/3gIO6mCd4Q4PF1tuwcyI3sjFrtI.jpg","original_language":"en","original_title":"Rampage","genre_ids":[28,12,878],"backdrop_path":"/wrqUiMXttHE4UBFMhLHlN601MZh.jpg","adult":false,"overview":"Primatologist Davis Okoye shares an unshakable bond with George, the extraordinarily intelligent, silverback gorilla who has been in his care since birth.  But a rogue genetic experiment gone awry mutates this gentle ape into a raging creature of enormous size.  To make matters worse, it’s soon discovered there are other similarly altered animals.  As these newly created alpha predators tear across North America, destroying everything in their path, Okoye teams with a discredited genetic engineer to secure an antidote, fighting his way through an ever-changing battlefield, not only to halt a global catastrophe but to save the fearsome creature that was once his friend.","release_date":"2018-04-12"},{"vote_count":165,"id":38780,"video":false,"vote_average":5.9,"title":"Rampage","popularity":12.240357,"poster_path":"/xl1q07RVwYKbkngd4eL145cBaUP.jpg","original_language":"en","original_title":"Rampage","genre_ids":[28,18,80,53,9648],"backdrop_path":"/hhbRfecY2qrqhSNuLtMgASAkRPT.jpg","adult":false,"overview":"The boredom of small town life is eating Bill Williamson alive. Feeling constrained and claustrophobic in the meaningless drudgery of everyday life and helpless against overwhelming global dissolution, Bill begins a descent into madness. His shockingly violent plan will shake the very foundations of society by painting the streets red with blood.","release_date":"2009-08-14"},{"vote_count":15,"id":99749,"video":false,"vote_average":6.4,"title":"Rampage","popularity":3.234653,"poster_path":"/8o0WyZOxZl04jk1hMXsqgMilfGT.jpg","original_language":"en","original_title":"Rampage","genre_ids":[53,18],"backdrop_path":"/7rzGNVf0nVo14XiBYwkBW4wldPW.jpg","adult":false,"overview":"Rampage delves into the subject of legal insanity, so often the default defense in modern-time gruesome crime trials. Alex McArthur plays an outwardly normal guy who goes on incredible killing and mutilating sprees until (and even after, when he escapes for a short time) he's captured. When he comes to trial, the liberal DA (Michael Biehn) is torn between his own leftist leanings and the reality of the heinous crimes for which the accused is being tried. He must argue for the death penalty.","release_date":"1987-09-01"},{"vote_count":2,"id":17288,"video":false,"vote_average":6,"title":"Rampage","popularity":1.770459,"poster_path":"/zpfWNW5CHbPHHSPb5uuMqopYfuf.jpg","original_language":"tr","original_title":"Korkusuz","genre_ids":[12,28],"backdrop_path":"/usZyWiogdUOmMTdsS7n6MRiHbZ4.jpg","adult":false,"overview":"A Turkish commando must infiltrate and capture a group of terrorists living in the mountains.","release_date":"1986-06-12"},{"vote_count":3,"id":104658,"video":false,"vote_average":5.3,"title":"Rampage","popularity":1.754968,"poster_path":"/53ywROZtzxWznTfeiOtzAAAJijS.jpg","original_language":"en","original_title":"Rampage","genre_ids":[12],"backdrop_path":"/fY2s1I0Jk4cvmqoiAqI0KL9JDaL.jpg","adult":false,"overview":"A German zoo hires two hunters to catch a rare breed of panther in Malaysia. The girlfriend of one of the hunters accompanies them on their hunt.","release_date":"1963-10-09"},{"vote_count":49,"id":395925,"video":false,"vote_average":4.8,"title":"Rampage: President Down","popularity":7.311732,"poster_path":"/vSw97KTyxUQWCyGvnrc7t73L15l.jpg","original_language":"en","original_title":"Rampage: President Down","genre_ids":[53,28,80],"backdrop_path":"/zRbVu2wAWy46H85TxayTBPKB2AR.jpg","adult":false,"overview":"Bill Williamson is back, alive and well and doing a recon mission around D.C. This time he wants to cause a major population disruption within the USA which result in devastating consequences reverberating throughout the world. His new mission this time to bring down The President of the United States and his Secret Service detail. Bill brings with him all the freak-in havoc and acidity of the previous 2 movies.","release_date":"2016-08-26"},{"vote_count":105,"id":250388,"video":false,"vote_average":5.4,"title":"Rampage: Capital Punishment","popularity":7.990752,"poster_path":"/pSpChn2UgYNzRFA0eR3YyBuoOAo.jpg","original_language":"en","original_title":"Rampage: Capital Punishment","genre_ids":[28,80,53],"backdrop_path":"/mvfXbtJJwtuy0YzbnMhShE2dikR.jpg","adult":false,"overview":"A man takes over a TV station and holds a number of hostages as a political platform to awaken humanity, instead of money.","release_date":"2014-07-19"},{"vote_count":5,"id":102167,"video":false,"vote_average":8.2,"title":"Rabbit Rampage","popularity":2.062573,"poster_path":"/c2I1jYiNltPCIPS4EQl79OkIyF1.jpg","original_language":"en","original_title":"Rabbit Rampage","genre_ids":[16],"backdrop_path":"/ogj8h3ynjJga9gFJu4jNkIOLzj3.jpg","adult":false,"overview":"Bugs Bunny is tormented by his own animator, in this successor to the 1953 cartoon \"Duck Amuck.\"","release_date":"1955-06-11"},{"vote_count":3,"id":122353,"video":false,"vote_average":6,"title":"Rampage","popularity":1.115031,"poster_path":"/x9ssGbWuWQfLXthav4sfALpL5uj.jpg","original_language":"en","original_title":"Rampage","genre_ids":[99],"backdrop_path":"/2VAz8EulRoQoSmA2CJUqBrzkNbW.jpg","adult":false,"overview":"Following his film about music and war in Iraq, Soundtrack to War , Producer/ DirectorGeorge Gittoes slices into the mirky underbelly of the Giant Land of the Free.  RAMPAGE is another Gittoes journey into the forbidden zones; – America’s war in Iraq, and in it’s own backyard – life in a Miami ‘hood – an exploration of hiphop’s musical innovations, as important as the field s the field hollers, the blue, the blues, and jazz, which also began in the black ghettos, and went on to evolve as major music styles.","release_date":"2006-11-30"},{"vote_count":2,"id":72704,"video":false,"vote_average":5.3,"title":"Rampage","popularity":1.106074,"poster_path":"/8h3ZnAPAbJiG4fzEmS1Ru9tQiMu.jpg","original_language":"en","original_title":"Rampage","genre_ids":[12,99],"backdrop_path":null,"adult":false,"overview":"The classic American bouldering movie, that helped launch the bouldering revolution. Follow Chris Sharma, Obe Carrion, and friends on a two month road trip across the west. Check out the country's best bouldering areas, and witness first ascents of dozens of the now legendary \"Sharma Problems.\" Features Castle Rock, LakeTahoe, Priest Draw, Black Mountain, The Tramway, Squamish, Humboldt, and The X-Games.","release_date":"1999-01-01"},{"vote_count":0,"id":501804,"video":false,"vote_average":0,"title":"Rampage","popularity":1.001256,"poster_path":"/vhZ3gEDHaV92Con277jzJFFQTci.jpg","original_language":"en","original_title":"Rampage","genre_ids":[28],"backdrop_path":"/hly0T5HS5DZY4eDvZyf4bltPpH6.jpg","adult":false,"overview":"An evil attempt to avenge a drug lords death ends in the murder of a DEA operative´s brother. Now it´s payback time as sergeant Jim Stone pursues his brother´s murderers as they proceed on a reign of terror and a relentless assault on the population of Washington D.C.","release_date":"1997-05-18"},{"vote_count":0,"id":199608,"video":false,"vote_average":0,"title":"Tornado Rampage","popularity":1.008043,"poster_path":"/oremTUwMbJljBCQnUyEWkwAcdIh.jpg","original_language":"cs","original_title":"Tornado Rampage","genre_ids":[99],"backdrop_path":null,"adult":false,"overview":"Tornado Rampage 2011 finds the people who were pulled into the raging tornadoes and tells their stories first-hand, with remarkable and terrifying video footage they shot in the heat of the storm. Discovery Channel's 'Stormchaser' Reed Timmer joins the hunt on the 27 April, tracking down the twisters as they form - with exclusive pictures of their progress across Alabama and Mississippi states.","release_date":"2011-05-22"},{"vote_count":1,"id":124322,"video":false,"vote_average":2,"title":"Zombie Rampage","popularity":1.222215,"poster_path":"/yhTJhjv16aEtpXQ8O2IS7FmS5gv.jpg","original_language":"en","original_title":"Zombie Rampage","genre_ids":[27],"backdrop_path":null,"adult":false,"overview":"A young man on the way to meet friends at a train station is derailed into a world full of zombies, homicidal gangs and serial killers in this Niagra of gore.","release_date":"1989-01-01"},{"vote_count":8,"id":423414,"video":false,"vote_average":6,"title":"Last Rampage: The Escape of Gary Tison","popularity":3.034239,"poster_path":"/kZDF2GDto5WCPW9wbRqrqf9yRWM.jpg","original_language":"en","original_title":"Last Rampage: The Escape of Gary Tison","genre_ids":[18],"backdrop_path":"/1RSoHYKttOBQeRTBwFuDxxXOXgM.jpg","adult":false,"overview":"The true story of the infamous prison break of Gary Tison and Randy Greenwalt from the Arizona State prison in Florence, in the summer of 1978.","release_date":"2017-09-22"},{"vote_count":0,"id":404336,"video":false,"vote_average":0,"title":"Woman Killer's Rampage","popularity":1.000137,"poster_path":"/8PCQenSMLWrzUUO01B39bvIBYMM.jpg","original_language":"cn","original_title":"Yan jiang","genre_ids":[53,18],"backdrop_path":null,"adult":false,"overview":"One man's infidelity leads to a wife's scorn and finally a woman's killer.","release_date":"1993-12-16"},{"vote_count":1,"id":426191,"video":false,"vote_average":10,"title":"London Rampage","popularity":1.062846,"poster_path":"/lxWTPFDWteqTfy5U1tWXGz8Fq0x.jpg","original_language":"en","original_title":"London Rampage","genre_ids":[28],"backdrop_path":null,"adult":false,"overview":"One man's fight to free his brother from a rough estate leads to a tournament to secure the new leader of the estate.","release_date":"2016-02-29"},{"vote_count":0,"id":13050,"video":false,"vote_average":0,"title":"Rampage Unchained","popularity":1.005894,"poster_path":null,"original_language":"en","original_title":"Rampage Unchained","genre_ids":[],"backdrop_path":"/vSYwxBUpl9wlyGIa2ZbCuq1MfU4.jpg","adult":false,"overview":"Behind the scenes footage of Quinton \"Rampage\" Jackson during his days in Pride.","release_date":"1900-01-01"},{"vote_count":0,"id":380536,"video":false,"vote_average":0,"title":"Spiritual Rampage","popularity":1.002357,"poster_path":null,"original_language":"en","original_title":"Spiritual Rampage","genre_ids":[],"backdrop_path":null,"adult":false,"overview":"Two boys go to a park to look for girls. Unusually, one of them is doing it for Lord Krishna.","release_date":"2003-01-31"},{"vote_count":0,"id":452099,"video":false,"vote_average":0,"title":"Strange Rampage","popularity":1.001687,"poster_path":"/wzKWJ1dCwdc191dtoqOX76Bz3RA.jpg","original_language":"en","original_title":"Strange Rampage","genre_ids":[35],"backdrop_path":null,"adult":false,"overview":"A psychoanalyst discusses a new phenomenon in urban America: women driven mad by loneliness. He gives us four case studies.","release_date":"1967-07-06"},{"vote_count":0,"id":28094,"video":false,"vote_average":0,"title":"American Rampage","popularity":1.000057,"poster_path":"/6QnRbywqrTv70dScp5dVikdUdGh.jpg","original_language":"en","original_title":"American Rampage","genre_ids":[28,53],"backdrop_path":null,"adult":false,"overview":"No overview found.","release_date":"1989-01-23"}]}
  ```

### Continous Code Quality
For the quality of the code, the project uses `SonarQube` in sonarcloud.io (https://sonarcloud.io/dashboard?id=br.gov.sp.fatec%3AmyWatchedMovies)

![SonarQube Screenshot](/assets/images/sonarqube-screenshot.png?raw=true "SonarQube")


### Continous Integration
In this project Travis C.I is in use (https://travis-ci.com/felipekoblinger/ibd185-my-watched-movies-spring)

![TravisCI Screenshot](/assets/images/travisci-screenshot.png?raw=true "TravisCI")

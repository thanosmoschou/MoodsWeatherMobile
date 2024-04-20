## Moods Weather
Did you know that weather has its own mood? <br>
Enter your city to find it!! <br>

### Screenshots
<img src="screenshots/introScreen.png" height=700 width=400> <br>
<img src="screenshots/resultSun.png" height=700 width=400> <br>
<img src="screenshots/resultClouds.png" height=700 width=400> <br>
<img src="screenshots/resultRain.png" height=700 width=400> <br>


### How it works
Type the name of your city, press the search button and let the app show you the weather forecast! <br>
The app uses 2 apis to get the job done. <br>
The first api is the Geocoding API of https://www.open-meteo.com <br>
App sends a request to this api by providing a city's name and it gets back the longitude and the latitude of the city <br>
(First it receives a JSON response and it manipulates the JSON to get the longitude and the latitude). <br>
The second api is the Weather Forecast API of Open-Meteo. <br>
App makes a request providing the longitude and the latitude of a city and it gets back a JSON response. Finally by handling this JSON response<br>
it gets the weather info.
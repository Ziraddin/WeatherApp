Weather App
Weather App is a mobile application that provides users with up-to-date weather information based on their location and allows them to add and view weather data for multiple cities. The app uses fingerprint authentication for security and utilizes the Retrofit library to fetch weather data from the OpenWeatherAPI.

Table of Contents
Features
Screenshots
Installation
Usage
Contributing
License
Features
Fingerprint Authentication: Securely access the app using fingerprint authentication.
Location-based Weather: Automatically fetches weather data based on the user's current location.
City Search and Addition: Allows users to search for cities and add them to their list of tracked locations.
City Viewing: Displays weather information for added cities using a ViewPager with an indicator.
OpenWeatherAPI Integration: Retrieves weather data from the OpenWeatherAPI.
View Pager Indicator: Provides a visual indicator for the ViewPager to show the current city being viewed.
Screenshots
Include some screenshots or GIFs of your Weather App here to give users a visual preview of the application.

Installation
To use the Weather App, follow these steps:

Clone the repository to your local machine:

bash
Copy code
git clone https://github.com/Ziraddin/WeatherApp.git
Open the project in Android Studio.

Build and run the app on your Android device or emulator.

Usage
When you open the app, you will be prompted to authenticate using your fingerprint.

After successfully authenticating, the main screen will ask for location access and display weather data for your current location.

To add a new city, click the plus or add button in the top-left corner of the main screen. A new screen will open for city search and addition.

Search for a city and add it to your list of tracked locations.

Return to the main screen, and you can swipe through the added cities using the ViewPager. The ViewPager indicator will show the current city being viewed.

Contributing
If you would like to contribute to the development of the Weather App, please follow these steps:

Fork the repository.

Create a new branch for your feature or bug fix:

css
Copy code
git checkout -b master
Make your changes and commit them:

sql
Copy code
git commit -m "Description of your changes"
Push your changes to your forked repository:

perl
Copy code
git push origin master
Create a pull request on the original repository.

License
This project is licensed under the MIT License, which means you are free to use, modify, and distribute the code for both personal and commercial purposes. See the LICENSE file for more details.

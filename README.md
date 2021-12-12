What does this utility do?
    
    1. Given latitude and longitude of two locations the utility interpolates "real" points on the road that connect A & B, 
       such that consecutive points are at a distance of 50 meters from each other. The input data for interpolation comes from
       external calls to the Google Directions API https://developers.google.com/maps/documentation/directions/overview
    2. The main interpolation logic resides in com.locus.internal.interpolator.LatLonInterpolator
    2. The interpolation distance and api key is read from application.properties file, thereby making it configurable.
    3. The external call to Google Girections API is implemented from the RouteRequestService.
    4. An external library "geographiclib" is used for geodesic computations. The DirectionsPolyline field returned
       by the Google Girections API is used to come with an interpolation logic.


How to run the application?

    1. Install maven and set path accordingly
    2. Navigate to the root of the extracted folder - "plotdirections"
    3. Run "mvn install" - this will install all the required 3rd party libraries
    4. Run "mvn spring-boot:run" - this will start the application at the default port 8080(unless configured otherwise)
    5. Open any browser and fire a request with the below parameters - output will be set of LatLngs at constant distance of 50m
        Request format: http://localhost:8080/query?origin=lat1,lng1&destination=lat2,lng2
        Example: http://localhost:8080/query?origin=12.93175,77.62872&destination=12.92662,77.63696


Sample Request: 
    
    http://localhost:8080/query?origin=12.93175,77.62872&destination=12.92662,77.63696
    
Sample Response:

    12.93175,77.62872
    12.931248168187702,77.62870980136852
    12.930836336224424,77.62889960211453
    12.930422656496583,77.62908493746374
    12.930008041894089,77.62925861584242
    12.929906026685156,77.62970749003179
    12.929804010703595,77.63015636385651
    12.92970199394941,77.63060523731662
    12.92959997642261,77.63105411041208
    12.929503256238887,77.63150395052097
    12.929446122725702,77.63196101859049
    12.929551580493674,77.63240421439369
    12.929720607639771,77.63283144291916
    12.929816686878619,77.63318791414072
    12.929366597495804,77.63322966775068
    12.928916450473151,77.63327086791917
    12.928466303428502,77.63331206793998
    12.928016156361858,77.63335326781312
    12.927566009273217,77.63339446753862
    12.927115862162585,77.63343566711646
    12.926665871291874,77.63347855568911
    12.926216105832404,77.63352388097684
    12.9257663403496,77.63356920610228
    12.925603622854752,77.63383537671317
    12.925684222198337,77.63428874825057
    12.92576450217322,77.63474217872556
    12.925845859662905,77.6351954092867
    12.925927536423485,77.63564858081469
    12.926009212397105,77.63610175263732
    12.92609098125071,77.63655490624869
    12.92601423077305,77.63699888894017
    12.926401019429532,77.63712296947627
    12.92662,77.63696

Plotting the output:

    The output can be plotted on https://www.mapcustomizer.com/#

![Alt text](src/main/resources/coordinates.png?raw=true "LatLng output")

![Alt text](src/main/resources/plot.png?raw=true "LatLng Plot")


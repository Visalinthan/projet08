package tourGuide;

import java.util.ArrayList;
import java.util.List;

import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsoniter.output.JsonStream;

import gpsUtil.location.VisitedLocation;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.user.User;
import tripPricer.Provider;

@RestController
public class TourGuideController {

	@Autowired
	TourGuideService tourGuideService;

    @Autowired
    RewardsService rewardsService;
	
    @RequestMapping("/")
    public String index() {
        return "Greetings from TourGuide!";
    }
    
    @RequestMapping("/getLocation") 
    public String getLocation(@RequestParam String userName) {
    	VisitedLocation visitedLocation = tourGuideService.getUserLocation(getUser(userName));
		return JsonStream.serialize(visitedLocation.location);
    }
    
    //  TODO: Change this method to no longer return a List of Attractions.
 	//  Instead: Get the closest five tourist attractions to the user - no matter how far away they are.
 	//  Return a new JSON object that contains:
    	// Name of Tourist attraction,
        // Tourist attractions lat/long,
        // The user's location lat/long,
        // The distance in miles between the user's location and each of the attractions.
        // The reward points for visiting each Attraction.
        //    Note: Attraction reward points can be gathered from RewardsCentral
    @RequestMapping("/getNearbyAttractions") 
    public String getNearbyAttractions(@RequestParam String userName) {

    	VisitedLocation visitedLocation = tourGuideService.getUserLocation(getUser(userName));
        List<Attraction> attractions = tourGuideService.getFiveNearByAttractions(visitedLocation);

        List<Object> result = new ArrayList<>();
        for (Attraction a : attractions){
            result.add("Name of Tourist attraction :" + a.attractionName);
            result.add("Tourist attractions lat/long :" + a.latitude +"/"+ a.longitude );
            result.add("User location lat/long :" + getUser(userName).getLastVisitedLocation().location.latitude +" / " + getUser(userName).getLastVisitedLocation().location.longitude  );
            result.add("Distance between user and attraction :" + this.rewardsService.getDistance(getUser(userName).getLastVisitedLocation().location, getUser(userName).getLastVisitedLocation().location));
            result.add("The reward points for visiting ;" + this.rewardsService.getRewardPoints(a,getUser(userName)) );
        }

    	return JsonStream.serialize(result);
    }
    
    @RequestMapping("/getRewards") 
    public String getRewards(@RequestParam String userName) {
    	return JsonStream.serialize(tourGuideService.getUserRewards(getUser(userName)));
    }
    
    @RequestMapping("/getAllCurrentLocations")
    public String getAllCurrentLocations() {
    	// TODO: Get a list of every user's most recent location as JSON
    	//- Note: does not use gpsUtil to query for their current location, 
    	//        but rather gathers the user's current location from their stored location history.
    	//
    	// Return object should be the just a JSON mapping of userId to Locations similar to:
    	//     {
    	//        "019b04a9-067a-4c76-8817-ee75088c3822": {"longitude":-48.188821,"latitude":74.84371} 
    	//        ...
    	//     }

        List<Object> result = new ArrayList<>();
        for (User u : getAllUsers()) {
           Location loc = getUser(u.getUserName()).getLastVisitedLocation().location;
            result.add(u.getUserId() + " : " + " longitude : " + loc.longitude +" , latitude : " + loc.latitude );
        }
    	
    	return JsonStream.serialize(result);
    }
    
    @RequestMapping("/getTripDeals")
    public String getTripDeals(@RequestParam String userName) {
    	List<Provider> providers = tourGuideService.getTripDeals(getUser(userName));
    	return JsonStream.serialize(providers);
    }
    
    private User getUser(String userName) {
    	return tourGuideService.getUser(userName);
    }

    @RequestMapping("/getAllUsers")
    private List<User> getAllUsers() {
        return tourGuideService.getAllUsers();
    }
   

}
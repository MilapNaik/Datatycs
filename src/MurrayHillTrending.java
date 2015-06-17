 
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import java.lang.String;

 
public class MurrayHillTrending {
	
	private final String USER_AGENT = "Mozilla/5.0";
 
	public static void main(String[] args) throws Exception {
 
		MurrayHillTrending http = new MurrayHillTrending();
 
		http.Foursquare();
		http.Yahoo();
	}
 
	// HTTP GET request for Foursquare
	private void Foursquare() throws Exception {	
		long total=0;
		
		//Foursquare api url for venues that are trending, with oauth_token added
		String url = "https://api.foursquare.com/v2/venues/trending?ll=" +
				/*LiveRamp = 37.7867,-122.401193
				 *Murray Hill = 40.747879200000000000,-73.975656700000000000 */
				"40.747879200000000000,-73.975656700000000000&" + 
				"oauth_token=FPYYELOUX1GUN0GQAT3UDAVDS0WZS4C0VRGGIMNNUZ2N50VH&v=20140614";
 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

 
		BufferedReader in = new BufferedReader(
		new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer responses = new StringBuffer();
 
		//Capture entire JSON response
		while ((inputLine = in.readLine()) != null) {
			responses.append(inputLine);
			System.out.println("\n");
		}
		in.close();

		//Parse JSON response
		String list = responses.toString();
		try {
			 
	             JSONParser jsonParser = new JSONParser();
	 
	             //break down Foursquare's JSON Object to get to the data
	             JSONObject jsonObject = (JSONObject) jsonParser.parse(list);
	             JSONObject response = (JSONObject) jsonObject.get("response");
	             JSONArray venues = (JSONArray) response.get("venues");
	             
	             // Print all values of the JSONArray
	             for(int i=0; i<venues.size(); i++){
	            	 //All venues nearby
	            	 JSONObject venuenames = (JSONObject) venues.get(i);
	            	 String name= (String) venuenames.get("name");
	            	 JSONObject Now = (JSONObject) venuenames.get("hereNow");
	            	 String summary = (String) Now.get("summary");
	            	 long count = (Long) Now.get("count");

	            	 /*To show all venues nearby*/
	            	  System.out.println(name + ": " + summary);   	  
	            	 
	            	 /*To narrow down to bars, clubs, or loungs nearby
	            	  
	            	 JSONArray categories = (JSONArray) venuenames.get("categories");
	            	 JSONObject categories1 = (JSONObject) categories.get(0);
	            	 
	            	 String catname = (String) categories1.get("pluralName");
	            	 if (catname.equals("Rock Clubs") || catname.equals("Bars") || 
        			 	 catname.equals("Sports Bars") || catname.equals("Lounges")){
        		 			System.out.println(name + " have " + count + " " + catname);
        	 		 }
	            	 */
	            	 
	            	 total= total +count;
	             }
	             
	             
	     } catch (ParseException ex) {
	             ex.printStackTrace();
	 
	     } catch (NullPointerException ex) {
	             ex.printStackTrace();
	     }
		
		System.out.println("\n" + "Total checkins: " + total);
    }

	
	private void Yahoo() throws Exception {	
		//Foursquare api url for venues that are trending, with oauth_token added
		String url = "http://dev.markitondemand.com/Api/v2/Quote/json?" +
		"json&Symbol=AAPL";
 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		/*int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
 		*/
		
		BufferedReader in = new BufferedReader(
		new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer responses = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			responses.append(inputLine);
			System.out.println("\n");
		}
		in.close();
		
		//Parse JSON response
				String list = responses.toString();
				System.out.println(list);
				try {
					
		            JSONParser jsonParser = new JSONParser();
			 
		            //Break down Markit OnDemand's JSON Object to get to the data
		            JSONObject jsonObject = (JSONObject) jsonParser.parse(list);
		            String name = (String) jsonObject.get("Name");
		            Double LastPrice = (Double) jsonObject.get("LastPrice");
		            
		            System.out.println(name + ": " + LastPrice);
		            //JSONArray venues = (JSONArray) response.get("Symbol");
		             
		            // Print all values of the JSONArray
		            /*for(int i=0; i<venues.size(); i++){
		           	    //All venues nearby
		                JSONObject venuenames = (JSONObject) venues.get(i);
		            	String name= (String) venuenames.get("name");
		            	JSONObject Now = (JSONObject) venuenames.get("hereNow");
		            	String summary = (String) Now.get("summary");
		            	 
		             }*/
			             
			     } catch (ParseException ex) {
			             ex.printStackTrace();
			 
			     } catch (NullPointerException ex) {
			             ex.printStackTrace();
			     }
	
    }
}
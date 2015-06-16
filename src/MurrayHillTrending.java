 
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
 
		System.out.println("Testing 1 - Send Http GET request");
		http.sendGet();
	}
 
	// HTTP GET request
	private void sendGet() throws Exception {	
		//Foursquare api url for venues that are trending, with oauth_token added
		String url = "https://api.foursquare.com/v2/venues/trending?ll=40.747879200000000000,-73.975656700000000000&" +
				"oauth_token=FPYYELOUX1GUN0GQAT3UDAVDS0WZS4C0VRGGIMNNUZ2N50VH&v=20140614";
 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer responses = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			responses.append(inputLine);
			System.out.println("\n");
		}
		in.close();

		String list = responses.toString();
		try {
			 
	             JSONParser jsonParser = new JSONParser();
	 
	             //break down Foursquare's JSON Object to get to the data
	             JSONObject jsonObject = (JSONObject) jsonParser.parse(list);
	             JSONObject response = (JSONObject) jsonObject.get("response");
	             JSONArray venues = (JSONArray) response.get("venues");
	             
	             // Print all values of the JSONArray
	             for(int i=0; i<venues.size(); i++){
	            	 //narrow down search to where the category = bars, clubs, or lounges
	            	 JSONObject venuenames = (JSONObject) venues.get(i);
	            	 String name= (String) venuenames.get("name");
	            	 JSONObject Now = (JSONObject) venuenames.get("hereNow");
	            	 String count = (String) Now.get("summary");
	            	 JSONArray categories = (JSONArray) venuenames.get("categories");
	            	 JSONObject categories1 = (JSONObject) categories.get(0);

	            	 String catname = (String) categories1.get("pluralName");

	            	 /*if (catname.equals("Rock Clubs") || catname.equals("Bars") || 
	            			 catname.equals("Sports Bars") || catname.equals("Lounges")){

	            		 System.out.println(name + " have " + count + " " + catname);
	            	 }*/
	            	 System.out.println(name + " has " + count);
	             }
	 
	     } catch (ParseException ex) {
	             ex.printStackTrace();
	 
	     } catch (NullPointerException ex) {
	             ex.printStackTrace();
	     }
    }

}
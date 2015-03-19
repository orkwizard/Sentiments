package com.sunset.sentiments.core;
/**
 * 
 */

/**
 * @author EdgarOsorio
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import com.google.gson.*;

import org.apache.commons.io.IOUtils;

public class Sentiments {

	/**
	 * @param args
	 * 
	 * 0 Very Negative
	 * 1 Negative
	 * 2 Neutral
	 * 3 Positive
	 * 4 Very Positive
	 * 
	 */
	
	private final String key="1563a11a8f5a431db0d26645b167e032";
	private final String uri="https://api.algorithmia.com/api/nlp/SentimentAnalysis";
	private URL url;
	private URLConnection urlcon;
	private boolean initialized;
	//String input ="I've traveled and stayed at many different resorts throughout the Caribbean and have experienced the ups and downs. I am realistic and do not feel some of the petty comments made on this site warrant attention. First off, the grounds here are beautiful and clean, the layout is efficient and everything you need is a short walk away. The staff and service is FIVE STARS! It starts with Veronica, who subtly patrols the grounds continuously. She observes and where needed, takes action. She will listen, follow up take ownership of any issue presented to her. She obviously takes great pride in her work and it shows among the staff she oversees. The food was excellent; the Italian restaurant was exceptional, the Asian restaurant surprisingly good (we're generally not big fans) , and the Mexican restaurant was excellent---although we would have liked to see more traditional Mexican foods on the menu. The rooms were clean and kept well, the showers hot and powerful and the refrigerators re-stocked daily. The A/C was a bit weak---a common complaint among the guests we spoke with. On the not so great side---the liquor was very cheap; we learned on day 3 that they kept some higher quality stuff (Stoli and Bacardi) hidden and available only by request. Not good!!!! The beer was warmish and the bars ran out regularly. Was rather humorous to see a wait staffer running from the kitched with a 5 gallon water jug filled with beer! After a few days the staff---shout outs to Ishmael, Fermin, Gregorio and Pablo----knew what you wanted and ensured no Blat Vodka! The pool was clean and enjoyable; lounge chairs were always available; but it would be nice if the hard wicker chairs had a cushion for comfort. Note: we visited Fisherman's Village, the lesser quality sister-resort, and they had thick cushions on their lounge chairs!. Now for the real downer and potential dealbreaker------the beach. Unkempt, unattractive and unusable!! The water had a consistent murky brown sludgy look and although there were staff members removing the vegetation, you could not go ankle deep without being swallowed up by seaweed and other kale lookalike organisms. That is why we trekked to Fisherman's---beach is a beauty. Hacienda management need to seriously commit resources ($$$) to get beach on par with rest of resort. A more Bahamian like beach, with better booze and cushioned lounge chairs would make this a 5 STAR resort. That being said, I would definitely return; great value for money and great welcoming staff and amenities.";
	//String input = "At first look the resort is quite lovely and the rooms are spacious and well appointed. Things go down hill from there. We were awoken at 5:30 am with the first of many carts rolling past our room. Then the backup warnings from large trucks started. Additionally you can hear the television and conversations quite clearly from adjoining rooms.";
	//String input ="I want to take this chance to recognize one of the often overlooked people at these resorts and that is the role of the luggage handlers or Bell boys if you prefer. Upon our arrival we were greeted by a gentleman named Jamie. We were greeted with a big big smile and a huge welcome as he helped my children out of the van. He had our luggage curb side before I could find my way to the back of the van and assured us it would be safe with him while we checked in. We were lucky enough to run in to Jamie several times when we left the lobby for various activities and excursions and he was always pleasant and helpful. Upon our departure the parking lot in front of the hotel was a very busy place and all the luggage handlers were very busy helping people find the correct shuttle and get their luggage to the appropriate vehicles. Standing out in this hub of activity was once again Jamie, this guy is capable of creating calm in the most chaotic of circumstances! He once again swooped in and made all of us feel like we mattered in an atmosphere that we could very well have been overlooked and left to fend for ourselves. And best of all when I thanked him for all his help he somehow turned it around and made me the recipient of his gratitude! He is an outstanding young man that even in his modest role is an asset to Hacienda Tres Rios";
	//String input ="This is not working. Its a bad NPL software and says bullshit";
	//String input ="Fabulous Time";
	//String input ="A very beautiful resort well groomed and kept up to par Service is very good from everyone working there Some individuals show extra care and give excellent service here are a few to mention Enriche Sanchez all restaurants Arely Paris cafe entertainer Michel and Tony Josefina at the Spa Limberg at Restaurant Alebrige and Capitan Rodrigo at Restaurante Mexicano";
	//String input ="Never write reviews. This resort well worth the time for review.Alex the Segway tour guide, entertaining and great English. Sonia activities coordinator very friendly and volley ball recruiter. Rosalina and Danni spa area, outstanding!";
	//private String input = "Really spend some amazing days, we were greeted with a huge smile and a cocktail welcome all guests, all the front desk staff is very friendly, Mr Jorge Nolazco and his team recommended sensocial my activity, my son and I enjoyed it much, I recommend this tour, it is incredibly renovator, only let yourself go, enjoy the awakening of all";
	
	public Sentiments(){
		try {
			start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void start() throws IOException{
		url = new URL(uri);
		urlcon = url.openConnection();
		urlcon.setDoInput(true);
		urlcon.setDoOutput(true);
		urlcon.setRequestProperty("Content-Type", "application/json");
		urlcon.setRequestProperty("Authorization",key);
		urlcon.setRequestProperty("Accept", "application/json");
		urlcon.connect();
		initialized=true;
	}
	
	public void process(String input) throws IOException{
		if(initialized){
			OutputStreamWriter output = new OutputStreamWriter(urlcon.getOutputStream());
			JsonPrimitive prim = new JsonPrimitive(input);
			output.write(new Gson().toJson(prim));
			output.flush();
			output.close();
			HttpURLConnection httpConn = (HttpURLConnection) urlcon;
			InputStream is;
			if (httpConn.getResponseCode() != 200) {
			    is = httpConn.getErrorStream();
			} else {
			    is = httpConn.getInputStream();
			}
			
			List<String> list = IOUtils.readLines(is,"UTF-8");
			StringBuilder sb = new StringBuilder();
			
			for(String s : list){
			    sb.append(s + "\n");
			}
			System.out.println(sb.toString());
			
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Sentiments sen;
		sen = new Sentiments();
		
		String input="";
		
		try{
			while(!input.equals("exit")){
				System.out.print("Tell me your sentence: ");
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				input = br.readLine();
				sen.process(input);
				}
		}catch(IOException ioe){
			System.out.println("IO error trying to read your sentiments!");
			System.out.println(ioe.getMessage());
	         System.exit(1);
		}
		
		
		
	}

}

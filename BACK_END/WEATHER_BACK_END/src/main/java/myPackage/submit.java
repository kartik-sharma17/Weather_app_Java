package myPackage;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class submit
 */
@WebServlet("/submit")
public class submit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public submit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
//		getting the required data from front end 
		String city_name = request.getParameter("city_name");
		
//		setup the variable for api
		String key = "1662c7c76e2780a3eb7c6004a4c5f43d";
		
		
		String url = "https://api.openweathermap.org/data/2.5/weather?q="+city_name+"&appid="+ key;
		
//		coverting the string url into url form 
		URL main_url = new URL(url);
		
//		we open the connection
		HttpURLConnection con = (HttpURLConnection) main_url.openConnection();
		con.setRequestMethod("GET");
		
		InputStream input_get = null;
		try {
//		now getting the data 
		input_get = con.getInputStream();
		}
		catch(Exception e) {
			request.setAttribute("error",e);
			
			request.getRequestDispatcher("error.jsp").forward(request,response);
			
		}
		
//		 convert the format into readable form 
		InputStreamReader read_data = new InputStreamReader(input_get);
		
//		to store the data 
		StringBuilder content = new StringBuilder();		

//		to scanner the data from read data we make the scanner 
		Scanner sc = new Scanner(read_data);
		
//		using loop to read all the data and save in content
		while(sc.hasNext()) {
			content.append(sc.nextLine());
		}
		

//		new typecasting the data in json  from string 
//		have to include jar file in lib or dependency in pom.xml
		Gson json_data = new Gson();
		JsonObject	data_in_json = json_data.fromJson(content.toString(),JsonObject.class);
		
//		now seperating the data from json object to use
		
//		getting the temp
		float temp = data_in_json.getAsJsonObject("main").get("temp").getAsFloat();
		
//		converting into cel -
		Double temp2 =  temp- 273.15;
		
//		converting the value into Float
		float temp3 = temp2.floatValue();
		
//		now decreasing the length to 1 , import decimal format from java.text
		DecimalFormat df = new DecimalFormat("#.#");

		String final_temp = df.format(temp3);
		
//		retriving the feel like temperature 
		
		float feel_like1 = data_in_json.getAsJsonObject("main").get("feels_like").getAsFloat();
		
//		converting into cel -
		float feel_like2 = (float) (feel_like1- 273.15);
		
		
//		converting the fomrat of feel like 
		String final_feel_like = df.format(feel_like2);
		
//		min temp
		
		int min_temp = data_in_json.getAsJsonObject("main").get("temp_min").getAsInt();
		
//		converting to cel
		
		int final_min_temp = (int) (min_temp - 273.15);
		
		
//		max temp
		
		int max_temp = data_in_json.getAsJsonObject("main").get("temp_max").getAsInt();
		
//		converting to cel
		
		int final_max_temp = (int) (max_temp - 273.15);
		
		
//		getting the humidity
		int humidity = data_in_json.getAsJsonObject("main").get("humidity").getAsInt();
		
		
//		getting the visibility 
		int visibility = data_in_json.get("visibility").getAsInt();
		
//		getting the wind speed 
		float wind_speed = data_in_json.getAsJsonObject("wind").get("speed").getAsFloat();
		
//		name of the location 
		String location  = data_in_json.get("name").getAsString();
		
//		getting the description of the weather 
		String des = data_in_json.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
		
		
//		now we have all the data from api now we have to send to jsp .
		
//		now we are setting the attribute 
		request.setAttribute("temperature",final_temp);
		request.setAttribute("feel_like",final_feel_like);
		request.setAttribute("min_temp",final_min_temp);
		request.setAttribute("max_temp",final_max_temp);
		request.setAttribute("humidity",humidity);
		request.setAttribute("visibility",visibility);
		request.setAttribute("wind_speed",wind_speed);
		request.setAttribute("location",location);
		request.setAttribute("description",des);
		
		
//		disconnecting the connection
		con.disconnect();
		
//		now sending the data to jsp 
		
		request.getRequestDispatcher("common.jsp").forward(request,response);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	}

}










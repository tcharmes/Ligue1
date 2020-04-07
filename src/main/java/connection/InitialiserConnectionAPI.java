package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;

import twitter4j.internal.org.json.JSONException;

public class InitialiserConnectionAPI {

	private static HttpURLConnection connection;
	public static StringBuffer responseContent;
	public static StringBuffer reponse;

	public static void initializeConnection() {

		BufferedReader reader;
		String line;
		responseContent = new StringBuffer();

		try {
			URL url = new URL("https://my-json-server.typicode.com/tcharmes/dataBaseL1/clubs");
			connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);

			int status = connection.getResponseCode();

			System.out.println("Le code réponse renvoyé par le serveur est : " + status + "\n");

			if (status > 299) {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			} else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}

	}
	
	private static void write(JSONArray jsonArray) {
		
		BufferedReader reader;
		String line;
		responseContent = new StringBuffer();

		try {
			URL url = new URL("https://my-json-server.typicode.com/tcharmes/dataBaseL1/clubs");
			connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("PUT");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);

			int status = connection.getResponseCode();

			System.out.println("Le code réponse renvoyé par le serveur est : " + status + "\n");

			if (status > 299) {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			} else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
		
	}

	/**
	 * GETTERS POUR RECUPERER TOUTES LES VALEURS
	 */

	/**
	 * @param surname
	 * @return le nombre de victoires ou -1 si le paramètre n'est pas le surnom
	 *         d'une équipe de Ligue 1
	 */
	public static int getNombreMatchsGagnes(String surname) {

		JSONArray clubs = new JSONArray(responseContent.toString());
		int nombreV = -1;
		for (int i = 0; i < clubs.length(); i++) {
			org.json.JSONObject club = clubs.getJSONObject(i);
			String clubTestedSurname = club.getString("surnom");
			if (surname.contentEquals(clubTestedSurname)) {
				int nombreVictoires = club.getInt("nombreMatchsGagnés");
				nombreV = nombreVictoires;
			}
		}
		return nombreV;
	}

	/**
	 * SETTERS
	 */

	public static void setNombreMatchsGagnes(String surname, int nouveauNombreVictoires) {

		JSONArray clubs = new JSONArray(responseContent.toString());
		int nouveau = -1;
		for (int i = 0; i < clubs.length(); i++) {
			org.json.JSONObject club = clubs.getJSONObject(i);
			String clubTestedSurname = club.getString("surnom");
			if (surname.contentEquals(clubTestedSurname)) {
				club.put("nombreMatchsGagnés", nouveauNombreVictoires);
				nouveau = club.getInt("nombreMatchsGagnés");
			}
		}
		System.out.println("Nouveau nombre de victoires de l'équipe "+surname+" : "+nouveau);
	}
	
	public static JSONArray setNombreMatchsGagnesPlusUn(String surname) {

		JSONArray clubs = new JSONArray(responseContent.toString()); // {Json:valeurs...}
		int nouveau = -1;
		for (int i = 0; i < clubs.length(); i++) {
			org.json.JSONObject club = clubs.getJSONObject(i);
			String clubTestedSurname = club.getString("surnom");
			if (surname.contentEquals(clubTestedSurname)) {
				club.put("nombreMatchsGagnés", club.getInt("nombreMatchsGagnés")+1);
				clubs.put(i, club);
				nouveau = club.getInt("nombreMatchsGagnés");
				System.out.println(responseContent.toString());
			}
		}
		
		System.out.println("Nouveau nombre de victoires de l'équipe "+surname+" : "+nouveau);
		
		return clubs;
	}

	/**
	 * 
	 * @param args
	 * @throws JSONException 
	 * @throws IOException 
	 */

	public static void main(String[] args) throws JSONException, IOException {

		initializeConnection();

		System.out.println(getNombreMatchsGagnes("FCGB"));
		System.out.println(getNombreMatchsGagnes("MHSC"));
		JSONArray setNombreMatchsGagnesPlusUn = setNombreMatchsGagnesPlusUn("MHSC");
		
		write(setNombreMatchsGagnesPlusUn);
		// Ecrite le Json
		
		
		setNombreMatchsGagnes("FCGB", 19);
	}
}

package com.bridzelabz.qa.Api_Automation;

import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
 
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RestAssured_ApiAutoation {
 // String uris1 = "spotify:track:1cpaDNciPGlC39qPs4RkMU";
 // String uris2 = "spotify:track:4sakBPqSpuj6FJti1rPme7";
 // String uris3 = "spotify:track:7n5Jcqw85WSfJHrMXsk5N2"; 
	String userId;
    String playlistid;
	String token;
	
	@BeforeTest
	
	public void getToken() {
      token = "Bearer BQD7IL-yXraO06qbYef6MsBslP9dLPTIc1yxQOfVo7aHWSyEBN3UZDioDoWf0ZJkRHuAsO4KxnpakiqNEU0MkPKkrqv5xkKwYu0f4ejxSigircOWaP1DnSOzeIpDFYjUv66V1PU4EPqs-vb9Il8X6azUq9FPF0iEeq60_klQGbFjcDm9ZZRkthvh004CYujIqP3jCkZ48xhcMyZt9JAlpG3TvLiMfV5JfL2wAaVOcCyURyQ7vxCGbND-u79MSIDzRkCYXsdZR5JGpw0";
	}
	
	@Test(priority=11)
	public void Get_Current_Users_Profile() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/me");
		response.prettyPrint();
		userId = response.path("id");
		System.out.println("my user id is : " +userId );
		response.then().assertThat().statusCode(200);
	//	Assert.assertEquals(userId, "icfsot7nfn2vmbku7jq7n2bkt");

	}
	@Test(priority=12)
	public void Get_Users_Profile() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/users/icfsot7nfn2vmbku7jq7n2bkt");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	//--------Playlists-----------
	@Test(priority=9)
	public void Get_Current_Users_Playlists() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("	https://api.spotify.com/v1/me/playlists");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test(priority=8)
	public void Get_Playlist() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/playlists/"+playlistid+"");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test(priority=7)
	public void Get_Users_Playlists() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/users/"+userId+"/playlists");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test(priority=1)
	public void Create_Playlist() {
		Response response = RestAssured.given()
				.header("Accept", "application/json")
				.header("contentType","application/json")
				.header("Authorization", token)
				.body("{\r\n"
						+ "  \"name\": \"playlist 3\",\r\n"
						+ "  \"description\": \"New playlist description\",\r\n"
						+ "  \"public\": false\r\n"
						+ "}")
				.when()
				.post("https://api.spotify.com/v1/users/icfsot7nfn2vmbku7jq7n2bkt/playlists");
		playlistid = response.path("id");
		System.out.println("my playlist id is : " +playlistid);
		response.prettyPrint();
		response.then().assertThat().statusCode(201);
	}
	@Test (priority=2)
	public void add_Items_To_Playlist() {
		Response response = RestAssured.given()
				.header("Accept", "application/json")
				.header("contentType","application/json")
				.header("Authorization", token)
				.queryParam("uris", "spotify:track:1cpaDNciPGlC39qPs4RkMU,spotify:track:4sakBPqSpuj6FJti1rPme7,spotify:track:7n5Jcqw85WSfJHrMXsk5N2")
				.when()
				.post("	https://api.spotify.com/v1/playlists/"+playlistid+"/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(201);
	}
	@Test (priority=3)
	public void upate_Playlist_Items() {
		Response response = RestAssured.given()
				.header("Accept", "application/json")
				.header("contentType","application/json")
				.header("Authorization", token)
				.body("{\r\n"
						+ "  \"range_start\": 1,\r\n"
						+ "  \"insert_before\": 3,\r\n"
						+ "  \"range_length\": 2\r\n" 
						+ "}")
				.when()
				.put("https://api.spotify.com/v1/playlists/"+playlistid+"/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test(priority=10)
	public void remove_Playlist_Items() {
		Response response = RestAssured.given()
				.header("Accept", "application/json")
				.header("contentType","application/json")
				.header("Authorization", token)
				.body("{ \"tracks\": [{ \"uri\": \"spotify:track:4sakBPqSpuj6FJti1rPme7\" }] }")
				.when()
				.delete("https://api.spotify.com/v1/playlists/"+playlistid+"/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority=4)
	public void change_Playlist_Detais() {
		Response response = RestAssured.given()
				.header("Accept", "application/json")
				.header("contentType","application/json")
				.header("Authorization", token)
				.body("{\r\n"
						+ "  \"name\": \"Updated Playlist2\",\r\n"
						+ "  \"description\": \"Updated playlist description\",\r\n"
						+ "  \"public\": false\r\n"
						+ "}")
				.when()
				.put("https://api.spotify.com/v1/playlists/"+playlistid+"");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	

	@Test (priority=5)
	public void Get_Playlist_Items() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/playlists/6Y16mISmxbkeD9boSvJCqv/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
}
	@Test(priority=6)
	public void Get_Playlist_Coverimage() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("	https://api.spotify.com/v1/playlists/6Y16mISmxbkeD9boSvJCqv/images");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
}
	//------------------search------------------
	
	@Test(priority=14)
	public void search_For_Item() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.queryParam("q", "thaman")
				.queryParam("type", "track")
				.when()
				.get("https://api.spotify.com/v1/search");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
}
	
	//------------------Tracks------------------
	@Test(priority=13)
	public void Get_Tracks_Audio_Analysis() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("	https://api.spotify.com/v1/audio-analysis/1cpaDNciPGlC39qPs4RkMU");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
}
	@Test(priority=15)
	public void Get_Tracks_Audio_Features_() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("	https://api.spotify.com/v1/audio-features");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
}
	@Test(priority=16)
	public void Get_Tracks_Audio_Features() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("	https://api.spotify.com/v1/audio-features/7n5Jcqw85WSfJHrMXsk5N2");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
}
	@Test(priority=17)
	public void Get_Track() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("	https://api.spotify.com/v1/audio-features/7n5Jcqw85WSfJHrMXsk5N2");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
}
	@Test(priority=18)
	public void Get_Several_Tracks() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.queryParam("ids", "4sakBPqSpuj6FJti1rPme7")
				.when()
				.get("https://api.spotify.com/v1/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
}
}
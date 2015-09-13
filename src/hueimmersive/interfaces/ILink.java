package hueimmersive.interfaces;

import com.google.gson.JsonObject;

import java.net.HttpURLConnection;


public interface ILink
{
	enum ResponseType
	{
		DATA,
		SUCCESS,
		ERROR
	}

	String getBaseAPIurl();

	JsonObject GET(String APIurl) throws Exception;

	JsonObject PUT(String APIurl, JsonObject data) throws Exception;

	JsonObject POST(String APIurl, JsonObject data) throws Exception;

	JsonObject DELETE() throws Exception;

	boolean canConnect(HttpURLConnection connection);

	JsonObject extractJsonObject(String response);

	ResponseType getResponseType(JsonObject response);
}
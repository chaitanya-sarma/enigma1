package org.icrisat.genomicSelection.util;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

public class UtilWebService {

	private static Client client = ClientBuilder.newClient();
	private static WebTarget webTarget;
	private static Response response;

	public static Response authenticate(String uri, String userName, String password) {
		webTarget = client.target(uri);
		Form formData = new Form();
		formData.param("username", userName);
		formData.param("password", password);
		response = webTarget.request().post(Entity.form(formData));
		return response;
	}
	
	public static Response makeCall(String uri) {
		webTarget = client.target(uri);
		response = webTarget.request().get();
		return response;
	}
}

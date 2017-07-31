package org.icrisat.genomicSelection.util;

import java.util.ArrayList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import javax.ws.rs.core.Form;

public class RESTCallsGobii {

	private static Client client = ClientBuilder.newClient();
	private static WebTarget webTarget;
	private static Response response;
	private static JsonParser parser = new JsonParser();

	public static ArrayList<AlleleMatrices> getAlleleMatricesList() {
		int status = 0;
		webTarget = client.target("http://gobiilab03.bti.cornell.edu:8080/gobii_test/brapi/v1/allelematrices");
		response = webTarget.request().get();
		status = response.getStatus();
		ArrayList<AlleleMatrices> alleleMatricesList = new ArrayList<AlleleMatrices>();
		if (status == 200) {
			String res = response.readEntity(String.class);
			JsonObject json = (JsonObject) parser.parse(res);
			JsonArray result = json.get("result").getAsJsonObject().get("data").getAsJsonArray();
			for (int i = 0; i < result.size(); i++) {
				AlleleMatrices am = new AlleleMatrices();
				JsonObject ele = result.get(i).getAsJsonObject();
				am.setName(ele.get("name").toString());
				am.setMatrixDbId(ele.get("matrixDbId").toString());
				am.setDescription(ele.get("description").toString());
				am.setLastUpdated(ele.get("lastUpdated").toString());
				am.setStudyDbId(ele.get("studyDbId").toString());
				alleleMatricesList.add(am);
			}

			// Just for multiple entries
			for (int a = 2; a < 15; a++) {
				for (int i = 0; i < result.size(); i++) {
					AlleleMatrices am = new AlleleMatrices();
					JsonObject ele = result.get(i).getAsJsonObject();
					am.setName(ele.get("name").toString());
					am.setMatrixDbId(Integer.toString(a));
					am.setDescription(ele.get("description").toString());
					am.setLastUpdated(ele.get("lastUpdated").toString());
					am.setStudyDbId(Integer.toString(a));
					alleleMatricesList.add(am);
				}
			}
		}
		return alleleMatricesList;
	}

	public static void postAlleleMatricesSearch(String matrixDbId) {
		int status = 0;
		String baseURI = "http://gobiilab03.bti.cornell.edu:8080/gobii_test/brapi/v1/allelematrix-search";
		webTarget = client.target(baseURI);
		matrixDbId = matrixDbId.replaceAll("\"", "");
		Form formData = new Form();
		formData.param("matrixDbId", matrixDbId);

		response = webTarget.request().post(Entity.form(formData));
		status = response.getStatus();
		ArrayList<AlleleMatrices> alleleMatricesList = new ArrayList();
		if (status == 200) {
			String res = response.readEntity(String.class);
			JsonObject json = (JsonObject) parser.parse(res);
			JsonArray result = json.get("metadata").getAsJsonObject().get("status").getAsJsonArray();
			GobiiRequestStatus requestStatus = new GobiiRequestStatus();

			for (int i = 0; i < result.size(); i++) {
				JsonObject ele = result.get(i).getAsJsonObject();
				requestStatus.setCode(ele.get("code").getAsString());
				requestStatus.setMessage(ele.get("message").getAsString());
			}
		}
	}

	public static void main(String[] args) {
		postAlleleMatricesSearch("1");
	}
}

// Helper class to store the async id.
class GobiiRequestStatus {

	String code;
	String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
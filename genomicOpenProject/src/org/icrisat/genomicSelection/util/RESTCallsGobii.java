package org.icrisat.genomicSelection.util;

import java.util.ArrayList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

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
			for (int a = 0; a < 10; a++) {
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
			}
		}
		return alleleMatricesList;
	}
}

package ar.com.restba;

import java.util.List;


import ar.com.restba.json.JsonObject;
import ar.com.restba.types.ObrasRegistradas;

public interface RestBAClient {

	public List<ObrasRegistradas> fetchObrasRegistradas();

	public JsonObject fetchDataset(String dataset);

}

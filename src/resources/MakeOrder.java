package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/makeOrder/")
public class MakeOrder extends BaseResource{

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MEDIA_TYPE_JSON)
	public String makeOrder() {
		return "";
	}
}

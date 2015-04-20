package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.view.Viewable;


@Path("/")
public class IndexPage {

	@GET
	@Produces(MediaType.TEXT_HTML)
	public Viewable getIndexPage() {
		return new Viewable("/index");
	}
}

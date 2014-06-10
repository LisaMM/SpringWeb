package be.vdab.rest;

import java.net.URI;
import java.util.*;

import javax.xml.bind.annotation.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import be.vdab.entities.Filiaal;

@XmlRootElement(name = "filialen")
@XmlAccessorType(XmlAccessType.FIELD)
public class FiliaalListREST {
	private static final String URI_TEMPLATE = "/filialen";
	@XmlElement(name = "filiaal")
	private List<FiliaalListItemREST> filialen = new ArrayList<>();
	private Link link;

	public static String getUriTemplate() {
		return URI_TEMPLATE;
	}

	public List<FiliaalListItemREST> getFilialen() {
		return filialen;
	}

	public Link getLink() {
		return link;
	}

	protected FiliaalListREST() {
	}

	public FiliaalListREST(Iterable<Filiaal> filialen) {
		for (Filiaal filiaal : filialen) {
			this.filialen.add(new FiliaalListItemREST(filiaal));
		}
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path(URI_TEMPLATE).build().toUri();
		this.link = new Link("self", uri);
	}
}

package Items;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OdrerItem {
	public long article;
	public int[] toppingsIds;
	public int size;

	public OdrerItem(){}
}


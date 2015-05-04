package Items;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class A {

	public int A;

	public String B;
	@Override
	public String toString() {
		return "A: " + A + " B: " + B;
	}
}

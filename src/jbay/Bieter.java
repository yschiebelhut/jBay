package jbay;

/**
 * @author Matrikel-Nr. 3354235
 */
public class Bieter {

	private String vorname;
	private String nachname;

	public Bieter(String vorname, String nachname) {
		this.vorname = vorname;
		this.nachname = nachname;
	}

	public String getFullName() {
		return vorname + " " + nachname;
	}
}

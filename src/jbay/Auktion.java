package jbay;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

/**
 * @author Matrikel-Nr. 3354235
 */
public class Auktion {

	public static final double increment = 1.0;
	private Ware ware;
	private Gebot hoechstes = null;
	private double preis = 0;
	private Calendar ende;

	public Auktion(Ware ware, int dauer) {
		this.ware = ware;
		this.ende = Calendar.getInstance();
		this.ende.setTimeInMillis(System.currentTimeMillis() + 60000 * dauer);
	}

	public boolean gebotAbgeben(Gebot g) {
		try (PrintWriter p = new PrintWriter(new FileWriter("auktionen.txt", true))) {
			StringBuilder line = new StringBuilder();
			line.append("[");
			line.append(Calendar.getInstance().getTime().toString());
			line.append("] Gebot von ");
			line.append(g.getBieter().getFullName());
			line.append(" für ");
			line.append(this.ware.getTitle());
			line.append(": ");
			line.append(g.getMaxBetrag());
			line.append(" Euro.");

			p.println(line.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (g.getMaxBetrag() < this.preis + increment) {
			return false;
		}
		if (this.hoechstes == null) {
			this.preis = increment;
			this.hoechstes = g;
			return true;
		}
		if (this.hoechstes.getBieter().equals(g.getBieter())) {
			if (g.getMaxBetrag() > this.hoechstes.getMaxBetrag()) {
				this.hoechstes = g;
				return true;
			}
		}
		if (g.getMaxBetrag() >= this.preis + increment) {
			if (g.getMaxBetrag() <= this.hoechstes.getMaxBetrag()) {
				this.preis = Math.min(g.getMaxBetrag() + increment, this.hoechstes.getMaxBetrag());
				return false;
			} else {
				this.preis = Math.min(g.getMaxBetrag(), this.hoechstes.getMaxBetrag() + increment);
				this.hoechstes = g;
				return true;
			}
		}
		return false;
	}

	public Ware getWare() {
		return ware;
	}

	public Gebot getHoechstes() {
		return hoechstes;
	}

	public double getPreis() {
		return preis;
	}

	public Calendar getEnde() {
		return ende;
	}
}

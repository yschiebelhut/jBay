package jbay;

/**
 * @author Matrikel-Nr. 3354235
 */
public class jBay {
	public static void main(String[] args) {
		Auktionshaus jbay = new Auktionshaus();
		jbay.addAuktion(new Auktion(
				new Ware("Trunschuhe", "Tolle Turnschuhe, kaum getragen"), 2
		));
		jbay.addAuktion(new Auktion(
				new Ware("iPad", "Nagelneues iPad 3"), 4
		));
		jbay.addAuktion(new Auktion(
				new Ware("Currywurst", "Scharf, ohne Pommes"), 5
		));

		BieterTerminal b1 = new BieterTerminal(new Bieter("Micky", "Maus"), jbay);
		BieterTerminal b2 = new BieterTerminal(new Bieter("Donald", "Duck"), jbay);
	}
}

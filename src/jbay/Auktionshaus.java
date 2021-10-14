package jbay;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matrikel-Nr. 3354235
 */
public class Auktionshaus {

	private List<Auktion> auktionen = new ArrayList<>();
	private List<BieterTerminal> terminals = new ArrayList<>();

	public void addAuktion(Auktion a) {
		this.auktionen.add(a);
	}

	public void removeAuktion(Auktion a) {
		this.auktionen.remove(a);
	}

	public List<Auktion> getAuktionen() {
		return auktionen;
	}

	public void register(BieterTerminal bt) {
		this.terminals.add(bt);
	}

	public void unregister(BieterTerminal bt) {
		this.terminals.remove(bt);
	}

	public void updateTerminals() {
		for (BieterTerminal bt :
				this.terminals) {
			bt.update();
		}
	}
}

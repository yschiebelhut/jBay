package jbay;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.HashMap;

/**
 * @author Matrikel-Nr. 3354235
 */
public class BieterTerminal extends JFrame {

	private Bieter bieter;
	private Auktionshaus ah;

	private JLabel time = new JLabel();
	private HashMap<Auktion, JLabel> pricesMap = new HashMap<>();
	private HashMap<Auktion, JLabel> bieterMap = new HashMap<>();

	public BieterTerminal(Bieter bieter, Auktionshaus ah) {
		this.bieter = bieter;
		this.ah = ah;
		this.ah.register(this);

		this.setTitle(bieter.getFullName());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel timePanel = new JPanel();
		time.setText(Calendar.getInstance().getTime().toString());
		timePanel.add(time);
		this.add(timePanel, BorderLayout.NORTH);

		JPanel auktionenPanel = new JPanel(new GridLayout(ah.getAuktionen().size(), 5, 5, 5));
		for (Auktion a :
				ah.getAuktionen()) {
			auktionenPanel.add(new JLabel(a.getWare().getTitle()));

			JLabel price = new JLabel(String.valueOf(a.getPreis()));
			this.pricesMap.put(a, price);
			auktionenPanel.add(price);

			JLabel bieterLabel = new JLabel();
			if (a.getHoechstes() != null) {
				bieterLabel.setText(a.getHoechstes().getBieter().getFullName());
			} else {
				bieterLabel.setText("---");
			}
			bieterMap.put(a, bieterLabel);
			auktionenPanel.add(bieterLabel);

			auktionenPanel.add(new JLabel(a.getEnde().getTime().toString()));

			JButton bietenButton = new JButton("Gebot");
			bietenButton.addActionListener(e -> {
				if (a.getEnde().after(Calendar.getInstance())) {
					String message = "Bitte neues Gebot eingeben.\n";
					message += String.format("Mindestens %.2f Euro", a.getPreis() + Auktion.increment);
					boolean success = false;
					try {
						double betrag = Double.parseDouble(JOptionPane.showInputDialog(this, message, a.getPreis() + Auktion.increment));
						success = a.gebotAbgeben(new Gebot(betrag, this.bieter));
					} catch (NumberFormatException ex) {
					}
					if (success) {
						JOptionPane.showMessageDialog(this, "Sie sind Höchstbietender!");
						this.ah.updateTerminals();
					} else {
						JOptionPane.showMessageDialog(this, "Gebot zu gering!");
					}
				} else {
					JOptionPane.showMessageDialog(this, "Die Auktion ist leider schon vorbei...");
				}
			});
			auktionenPanel.add(bietenButton);
		}
		this.add(auktionenPanel, BorderLayout.CENTER);

		this.pack();
		this.setVisible(true);

		Thread t = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.time.setText(Calendar.getInstance().getTime().toString());
			}
		});
		t.start();
	}

	public void update() {
		for (Auktion a :
				ah.getAuktionen()) {
			if (a.getHoechstes() != null) {
				this.bieterMap.get(a).setText(a.getHoechstes().getBieter().getFullName());
			} else {
				this.bieterMap.get(a).setText("---");
			}

			this.pricesMap.get(a).setText(String.valueOf(a.getPreis()));
		}
	}
}

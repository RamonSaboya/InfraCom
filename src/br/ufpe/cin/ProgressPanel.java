package br.ufpe.cin;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ProgressPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public ProgressPanel() {
		setLayout(null);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setForeground(Color.GREEN);
		progressBar.setBounds(100, 165, 500, 25);
		add(progressBar);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(200, 125, 300, 25);
		add(textPane);
		
		JButton btnStart = new JButton("START");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				textPane.setText("Começando");
				progressBar.setValue(50);
			}
		});
		btnStart.setBounds(150, 250, 100, 25);
		add(btnStart);
		
		JButton btnReset = new JButton("RESET");
		btnReset.setBounds(300, 250, 100, 25);
		add(btnReset);
		
		JButton btnStop = new JButton("STOP");
		btnStop.setBounds(450, 250, 100, 25);
		add(btnStop);

	}
}

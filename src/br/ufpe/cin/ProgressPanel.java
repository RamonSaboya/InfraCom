package br.ufpe.cin;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

@SuppressWarnings("serial")
public class ProgressPanel extends JPanel implements ActionListener, PropertyChangeListener {

	private JProgressBar progressBar;
	private JTextPane textPane;

	private JButton btnStart;
	private JButton btnReset;
	private JButton btnStop;

	private Task task;

	public ProgressPanel() {
		setLayout(null);

		progressBar = new JProgressBar(0, 60);
		progressBar.setForeground(Color.GREEN);
		progressBar.setBounds(100, 165, 500, 25);
		progressBar.setValue(0);
		add(progressBar);

		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(200, 125, 300, 25);
		textPane.setText("Contador: 0");
		StyledDocument doc = textPane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		add(textPane);

		btnStart = new JButton("START");
		btnStart.addActionListener(this);
		btnStart.setBounds(150, 250, 100, 25);
		add(btnStart);

		btnReset = new JButton("RESET");
		btnReset.setEnabled(false);
		btnReset.setBounds(300, 250, 100, 25);
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnStart.setEnabled(true);
				btnStop.setEnabled(true);
				btnReset.setEnabled(false);
				textPane.setText("Contador: 0");
				progressBar.setValue(0);
			}
		});
		add(btnReset);

		btnStop = new JButton("STOP");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnReset.setEnabled(true);
				task.stop();
				btnStop.setEnabled(false);
			}
		});
		btnStop.setBounds(450, 250, 100, 25);
		add(btnStop);

	}

	private class Task extends SwingWorker<Void, Void> {
		
		private boolean stopped;

		@Override
		protected Void doInBackground() throws Exception {
			stopped = false;
			int progress = 0;
			setProgress(0);
			while (progress < 60 && !stopped) {
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException ignore) {}
				if(!stopped) {
					setProgress(++progress);
				}
			}
			return null;
		}

		@Override
		public void done() {
			setCursor(null);
			if(!stopped) {
				Toolkit.getDefaultToolkit().beep();
				textPane.setText("FIM!");
			}
		}
		
		public void stop() {
			setCursor(null);
			stopped = true;
		}

	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("progress")) {
            int progress = (Integer) event.getNewValue();
            
            progressBar.setValue(progress);
            textPane.setText("Contador: "  + progress);
        } 
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		btnStart.setEnabled(false);
		btnReset.setEnabled(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		task = new Task();
		task.addPropertyChangeListener(this);
		task.execute();
	}

}

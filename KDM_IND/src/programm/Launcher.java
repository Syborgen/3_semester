package programm;

import javax.swing.*;

public class Launcher {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() ->
		{
			BaseFrame frame = new BaseFrame();
			frame.setTitle("Поиск фундаментальных циклов графа");
			frame.setResizable(false);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			frame.setVisible(true);
		});

	}
}

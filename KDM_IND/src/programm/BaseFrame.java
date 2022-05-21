package programm;

import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Point2D.Double;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class BaseFrame extends JFrame
{		
	static boolean find = false;//���� ����������� ����� ��� ��� ����. ������
	
	static int n = 0;//���������� ������
	static int count = 0;//������� ����� ������
	static int jC = 0;//������� ����� ������
	static int nC = -1;//������� ����� ������
	
	static int[] Mark;//��������� ����� ������
	static int[] C;//���� ��������� ������ �����
	static Point2D[] coords;//������ ��������� ������
	
	static FileReader fr = null;//���� ��� ������
	static FileWriter fw = null;//���� ��� ������
	static Scanner filescan = null;//���������� � �����
	static JFileChooser fileChooser = new JFileChooser("Files");//����� �����
	static JTable AdjTable = null;//������� ���������
	static DefaultTableModel AdjTableModel = new DefaultTableModel()//������ ������� � ������� ���������
	{
		
		public boolean isCellEditable(int row, int col)//��������� �������������� ����� � ������� ������ � ��������� � 0
		{
			if(row == 0 || col == 0 || row == col)
				return false;
			else
				return true;
		}
		
		public void setValueAt(Object v, int row, int col)//��������� ������� � ������� ������ 0 � 1
		{  
			if(row == 0 || col == 0)
			{
				super.setValueAt(v, row, col);
			}
			else
			{
				if(v.toString().equals("1") || v.toString().equals("0"))
				{
					super.setValueAt(v, row, col);
				}
				else
				JOptionPane.showMessageDialog(null, "� ������� ������ 0 � 1!");
			}
		}
		
	};
	static JScrollPane AdjScroll = null;//������ ��������� ��� ������� ���������
	static JTextArea resultText = null;//���� ��� ������ ����������
	static JPanel res = null;//������ ��� ���� � ������������
	
	static//����������� ���� �������������
	{
		fileChooser.setFileFilter(new FileNameExtensionFilter("Text only (*.txt)","txt"));//������ ��� ������ ������
		
		AdjTable = new JTable(AdjTableModel);
		AdjTable.getTableHeader().setReorderingAllowed(false);
		AdjTable.setRowHeight(25);
		AdjTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		AdjTable.setTableHeader(null);
		AdjTable.setIgnoreRepaint(false);
		
		AdjScroll = new JScrollPane(AdjTable);
		AdjScroll.setSize(new Dimension(380,380));
		AdjScroll.setLocation(new Point(1140,10));
		
		resultText = new JTextArea(14,27);
		resultText.setFont(new Font("Sans Serif", Font.PLAIN, 16));
		resultText.setEditable(false);
		
		res = new JPanel();
		res.setSize(new Dimension(380,380));
		res.setLocation(new Point(1140,400));
		res.add(new JScrollPane(resultText));
	}
	
	public BaseFrame()
	{
		setLocationByPlatform(true);
		setLayout(null);
		
		JPanel panel = new JPanel()//������ � ������� �������� ����
		{
			
			Point2D center;//����� ����� �� �������� ������������� �������
			double radius;//������ ����� �� �������� ������������� �������
			double vertRadius = 15;//������ ������
			
			public void paintComponent(Graphics g)//����� ��������� ��������
			{
				super.paintComponent(g);
				if(n == 0)//���� ���-�� ������ 0 ������ �� ������
					return;
				Graphics2D g2 = (Graphics2D) g;
				setSize(getPreferredSize());//������ ������ � ����������� �� ���-�� ������
				center = new Point2D.Double(getWidth() / 2, getHeight() / 2);
				radius = center.getX() - 30D;
				
				Coords();//���������� ���������� ������
				
				if(AdjTable.getCellEditor() != null)//���� ������� ������������� ������������ ����� ����
				{
					find = false;//����� ����������� ����� ��� ����. ������
					resultText.setText("");
				}
				
					try
					{
					
						if(!find)//������ ���� ������ �� ������: ������ � ����. ������� ��� ���
						{
							drawGraph(g2); 
						}
						else
						{
							drawCycle(g2);
							drawMark(g2,Color.ORANGE, Color.BLACK);
						}
					}catch(NullPointerException ne)
					{
						return;
					}
					this.repaint();
			}
			
			public void Coords()//���������� ��������� ������ ����� ��� �����������
			{
				coords = new Point2D[n];
				
				double angl = 360D / n;
				double a = angl;
				for(int i = 0; i < coords.length; i++)
				{
					double x = (center.getX() - center.getX());
					double y = (center.getY() - radius - center.getY());
					coords[i] = new Point2D.Double( ( (x * Math.cos(Math.toRadians(a))) - (y * Math.sin(Math.toRadians(a)))) +  center.getX(),
													( (x * Math.sin(Math.toRadians(a))) + (y * Math.cos(Math.toRadians(a)))) + center.getY() );
					a += angl;
				}
			}
			
			public void drawGraph(Graphics2D g2)//��������� ����� � ����������� �� ��� ����������������� ��� �������������������
			{
				boolean isNeo = isNeoGraph();
				for(int v = 1; v < AdjTable.getRowCount(); v++)
				{
					for(int adj = 1; adj < AdjTable.getColumnCount(); adj++)
					{
						if(AdjTable.getValueAt(v, adj).toString().equals("1"))
						{
							if(isNeo)
							{
								drawNeoEdge(g2, v , adj, Color.BLACK);
							}
							else
								drawOrEdge(g2, v, adj, Color.BLACK);	
						}
					}
				}
				drawMark(g2, Color.BLACK, Color.WHITE);
			}
			
			public void drawOrEdge(Graphics2D g2, int v, int adj, Color color)//��������� ���������������� �����
			{
					g2.setColor(color);
					double k = vertRadius/Math.sqrt(Math.pow(coords[v - 1].getX() - coords[adj - 1].getX(),2)
							+ Math.pow(coords[v - 1].getY() - coords[adj - 1].getY(),2) );
					
					Point2D c = new Point2D.Double((coords[adj - 1]).getX() + k*(coords[v - 1].getX() - coords[adj - 1].getX()),
										   (coords[adj - 1]).getY() + k*(coords[v - 1].getY() - coords[adj - 1].getY()));
					
					double a = 165D;
					
					double x = (coords[adj - 1].getX() - c.getX());
					double y = (coords[adj - 1].getY() - c.getY());
					Point2D first = new Point2D.Double( ( (x * Math.cos(Math.toRadians(a))) - (y * Math.sin(Math.toRadians(a)))) +  c.getX(),
											( (x * Math.sin(Math.toRadians(a))) + (y * Math.cos(Math.toRadians(a)))) + c.getY() );
					Point2D second = new Point2D.Double( ( (x * Math.cos(Math.toRadians(-a))) - (y * Math.sin(Math.toRadians(-a)))) +  c.getX(),
											( (x * Math.sin(Math.toRadians(-a))) + (y * Math.cos(Math.toRadians(-a)))) + c.getY() );
					
					int[] xPoints = {(int) c.getX(), (int) first.getX(), (int) second.getX()};
					int[] yPoints = {(int) c.getY(), (int) first.getY(), (int) second.getY()};
					
					g2.fillPolygon(xPoints, yPoints, 3);
					
					drawNeoEdge(g2, v, adj, color);
			}
			
			public void drawNeoEdge(Graphics2D g2, int v, int adj, Color color)//��������� ������������������ �����
			{
				g2.setColor(color);
				g2.draw(new Line2D.Double(coords[v - 1],coords[adj - 1]));
			}
			
			public void drawCycle(Graphics2D g2)//��������� ����. ������
			{
				for(int k = 0; k < Mark.length; k++)
					Mark[k] = 0;
				count = 0;
				nC = -1;
				for(int v = 0; v < n; v++)
				{
					if(Mark[v] == 0)
					{
						nC++;
						C[nC] = v;
						Cycle(v,0,g2);
						nC--;
					}
				}
			}
			
			public void drawMark(Graphics2D g2, Color vert, Color mark)//��������� ����� ������
			{
				for(int i = 0; i < coords.length; i++)
				{
					g2.setColor(vert);
					g2.fill(new Ellipse2D.Double(coords[i].getX() - vertRadius,
												 coords[i].getY() - vertRadius,
												 vertRadius * 2,
												 vertRadius * 2));
					g2.setColor(mark);
					g2.setFont(new Font("Sans Serif", Font.PLAIN, 14));
					g2.drawString((i+1) + "", (int)(coords[i].getX() - 4), (int)(coords[i].getY() + 4));
				}
			}
			
			public void Cycle(int x, int y,Graphics2D g2)//����������� ������� ��������� ������
			{
				count++;
				Mark[x] = count; //������� �����������
				
				for(int i = 1; i < AdjTable.getColumnCount(); i++)
				{
					if(AdjTable.getValueAt(x + 1, i).toString().equals("1"))
					{
						int v = i - 1;
						nC++;
						C[nC] = v; //������� � ����
						if(Mark[v] == 0)
						{
							g2.setColor(Color.ORANGE);
							g2.draw(new Line2D.Double(coords[v],coords[C[nC-1]]));
							Cycle(v,x,g2);
						}
						else
						{
							if((Mark[v] < Mark[x]) & v!=y)// v!=y ��������� ����������� ����� � ������� ������� ��� ������
							{
								g2.setColor(Color.RED);
								g2.draw(new Line2D.Double(coords[v],coords[x]));
							}
						}
						nC--;//������� ������������� ������� �� �����
					}
				}////for
			}
			
			public Dimension getPreferredSize()//���������������� ������ ������ � ����������� �� ������
			{
				if(n < 5)
					return new Dimension(200,200);
				else
				return new Dimension((n/2) * 100, (n/2) * 100);
			}
		};
		
		panel.setBackground(Color.WHITE);
		
		JScrollPane scroll = new JScrollPane(panel);//������ ��������� ��� ������ �����
		scroll.setLocation(new Point(10,10));
		scroll.setSize(new Dimension(1100,700));
		
		JPanel buttons = new JPanel();
		buttons.setSize(new Dimension(300,100));
		buttons.setLocation(new Point(100,730));
		
		JButton findButton = new JButton("����� ��������������� �����");
		findButton.addActionListener(event ->
					{
						if(AdjTable.getCellEditor() != null)//������� ����� �� ������� ���������
							AdjTable.getCellEditor().stopCellEditing();
						if(n == 0)//���� ���� �� ����� ������ ���������
						{
							JOptionPane.showMessageDialog(null, "������� ����!");
							return;
						}
						if(!isNeoGraph())//���� ���� ��������������� ������ ��������� � ������������ ������� ������
						{
							JOptionPane.showMessageDialog(null, "�������� ���� ��������� ������ ��� ���������!");
							return;
						}
						try {
							fw = new FileWriter("Listing.txt");
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "������ ������� ���� ��� ����������!");
							return;
						}
						DepthCycle();//������� �������� ������ ����. ������
						find = true;//����� ����������� ����. ������
						panel.repaint();
						
						try {
							fw.close();
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, "������ ������� ���� ��� ����������!");
							return;
						}
					}
				);
		
		JPanel matrixButtons = new JPanel();
		matrixButtons.setSize(new Dimension(600,380));
		matrixButtons.setLocation(new Point(850,730));
		
		JPanel file = new JPanel();
		file.setSize(new Dimension(400,400));
		file.setLocation(new Point(450,730));
		
		JButton openFile = new JButton("������� ����");//������ �������� �����
		openFile.addActionListener(event ->
		{
			if(AdjTable.getCellEditor() != null)
				AdjTable.getCellEditor().stopCellEditing();
			fileChooser.setDialogTitle("����� �����");
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = fileChooser.showOpenDialog(BaseFrame.this);
			if(result == JFileChooser.APPROVE_OPTION)
			{
				JOptionPane.showMessageDialog(BaseFrame.this, "������ ����: " + fileChooser.getSelectedFile());
				if(FileReading())//���� ���� �������� ������, �� ������������ ����
				{
					quadrMatrix();//������ � ���� ���������
					find = false;
				}
				else//���� ��� � �� ������������ ������
				{
					matrixOrder(n = 0);
				}
				panel.repaint();
				resultText.setText("");
			}
		}
		);
		
		JButton saveFile = new JButton("��������� ����");//������ ���������� ������� ������� ���������
		saveFile.addActionListener(event ->
		{
			if(AdjTable.getCellEditor() != null)
				AdjTable.getCellEditor().stopCellEditing();
			if(n == 0)
			{
				JOptionPane.showMessageDialog(null, "������� ����!");
				return;
			}
			fileChooser.setDialogTitle("���������� �����");
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = fileChooser.showSaveDialog(BaseFrame.this);
			if (result == JFileChooser.APPROVE_OPTION )
			{
				FileWriting();//������ � ����
				JOptionPane.showMessageDialog(BaseFrame.this, 
		                              "���� " + fileChooser.getSelectedFile() + 
		                              ".txt ��������");
			}
		});
		
		JTextField orderField = new JTextField(4);
		orderField.setFont(new Font("Sans Serif", Font.PLAIN, 14));
		
		JButton orderButton = new JButton("������ ���������� ������");//����������� ���-�� ������ � ����� ������� ���������
		orderButton.addActionListener(event ->
		{
			if(AdjTable.getCellEditor() != null)
				AdjTable.getCellEditor().stopCellEditing();
			int tmp;
			if(orderField.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "������� ���������� ������!");
				return;
			}
			try
			{
				tmp = Integer.parseInt(orderField.getText());
			}catch(Exception e)//�������� ������������� �����
			{
				JOptionPane.showMessageDialog(null, "������������ ���� ���������� ������! ��������� ����.");
				orderField.setText("");
				return;
			}
			if(tmp < 1 || tmp > 1000)//�����������
			{
				JOptionPane.showMessageDialog(null, "���������� ������ � �������� �� 1 �� 1000!");
				orderField.setText("");
				return;
			}
			n = tmp;
			matrixOrder(n+1);
			
			Mark = new int[n];//��������� ����� ������
			C = new int[n+1];//���� ��������� ������ �����

			
			for(int i = 1; i < AdjTable.getColumnCount(); i++)//���������� ����� ������ ������� ���������
			{
				AdjTable.setValueAt(i + "", 0, i);
				AdjTable.setValueAt(i + "", i, 0);
			}
			
			for(int i = 1; i < AdjTable.getColumnCount(); i++)//��������� ������� ������
			{
				for(int j = 1; j < AdjTable.getColumnCount(); j++)
				{
					AdjTable.setValueAt("0", i, j);
				}
			}
			quadrMatrix();//���������� ������ �������
			find = false;//����� ����������� ����. ������ ��������
			panel.repaint();
			resultText.setText("");
			orderField.setText("");
		}
		);
		
		JButton symmetric = new JButton("�������� ������� ����������� �������");//�������� ������� ���������� ������� � ������� ���������
																				//����� ������� ���� �����������������
		symmetric.addActionListener(event ->
		{
			if(n == 0)
			{
				JOptionPane.showMessageDialog(null, "������� ����!");
				return;
			}
			find = false;
			if(AdjTable.getCellEditor() != null)
				AdjTable.getCellEditor().stopCellEditing();
			for(int v = 1; v < AdjTable.getRowCount(); v++)
			{
				for(int adj = 1 + v; adj < AdjTable.getColumnCount(); adj++)
				{
					AdjTable.setValueAt(AdjTable.getValueAt(v, adj), adj, v);
				}
			}
			resultText.setText("");
			panel.repaint();
		}
		);
		
		file.add(openFile);
		file.add(saveFile);
		matrixButtons.add(orderField);
		matrixButtons.add(orderButton);
		matrixButtons.add(symmetric);
		
		buttons.add(findButton);
		
		add(scroll);
		add(buttons);
		add(AdjScroll);
		add(matrixButtons);
		add(res);
		add(file);
	}
	
	
	
	static boolean FileReading()//������ �����
	{
		try {
			fr = new FileReader(fileChooser.getSelectedFile());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "��� ������� � �����!");
			return false;
		}
		filescan = new Scanner(fr);
		try {
			n = filescan.nextInt();//���-�� ������
			filescan.nextLine();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "���� ���� ��� ��������!");
			filescan.close();
			try {
				fr.close();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "���� ������ �������!");
				return false;
			}
			return false;
		}
		if(n > 0 & n < 1001)//�����������
		{
			Mark = new int[n];//��������� ����� ������
			C = new int[n+1];//���� ��������� ������ �����
		}else
		{
			JOptionPane.showMessageDialog(null, "���������� ������ �� 1 �� 1000!");
			filescan.close();
			try {
				fr.close();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "���� ������ �������!");
				return false;
			}
			return false;
		}
		
		matrixOrder(n+1);//������ ������� �������
		
		for(int i = 0; i < n; i++)//�������� ����� � �������� ������� ���������
		{
			try
			{ 
				if(filescan.nextInt() != i+1)
				{
					JOptionPane.showMessageDialog(null, "���� ��������: ����� ������ � ������� ���������! �������: " + (i+1));
					filescan.close();
					try {
						fr.close();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "���� ������ �������!");
						return false;
					}
					return false;
				}
				else
					AdjTable.setValueAt((i+1) + "", 0, i+1);
			}catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "���� ��������: ��� ����� ������");
				filescan.close();
				try {
					fr.close();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "���� ������ �������!");
					return false;
				}
				return false;
			}
		}
		filescan.nextLine();
		
		for(int i = 0; i < n; i++)
		{
			try {
					int mark = filescan.nextInt();            //����� �������
					if(mark != i+1)//�������� ����� � ������� ������� ���������
					{
						JOptionPane.showMessageDialog(null, "���� ��������: ����� ������ � ������� ���������! ������: " + (i+1));
						filescan.close();
						try {
							fr.close();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null, "���� ������ �������!");
							return false;
						}
						return false;

					}
					else
						AdjTable.setValueAt((i+1) + "", i+1, 0);
					for(int j = 0; j < n;j++)//�������� ������� ���������
					{
						try
						{
							int tmp = filescan.nextInt();
							if(j == i & tmp != 0)
							{
								JOptionPane.showMessageDialog(null, "���� ��������: � ��������� ������ 0 ! ������: " + (i+1));
								filescan.close();
								try {
									fr.close();
								} catch (IOException e1) {
									JOptionPane.showMessageDialog(null, "���� ������ �������!");
									return false;
								}
								return false;
							}
							if(tmp == 1)
							{
								AdjTable.setValueAt(1 + "", i + 1, j + 1);
							}else
							{
								if(tmp != 0)
								{
									JOptionPane.showMessageDialog(null, "���� ��������: � ������� ��������� ����� ������ 0 � 1! ������: " + (i+1) + " �������: " + (j+1));
									filescan.close();
									try {
										fr.close();
									} catch (IOException e1) {
										JOptionPane.showMessageDialog(null, "���� ������ �������!");
										return false;
									}
									return false;
								}
								else
									AdjTable.setValueAt(0 + "", i + 1, j + 1);
							}
					}catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, "���� ��������!");
						filescan.close();
						try {
							fr.close();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null, "���� ������ �������!");
							return false;
						}
						return false;
					}

				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "���� ��������!");
				filescan.close();
				try {
					fr.close();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "���� ������ �������!");
					return false;
				}
				return false;
			}
			if(filescan.hasNextLine())
			filescan.nextLine();
		}/////for �������
		try {
			fr.close();
			filescan.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "���� ������ �������!");
			return false;
		}
		return true;
	}
	
	static void FileWriting()//���������� � ���� ������� ������� ���������
	{
		try {
			fw = new FileWriter(fileChooser.getSelectedFile() + ".txt");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "������ ������� ���� ��� ����������!");
			return;
		}
		
		try {
			fw.write(n + "\n");
			for(int i = 0; i < AdjTable.getColumnCount(); i++)
			{
				for(int j = 0; j < AdjTable.getColumnCount(); j++)
				{
					if(AdjTable.getValueAt(i, j) == null)
						fw.write("  ");
					else
						fw.write(AdjTable.getValueAt(i, j) + " ");
				}
				fw.write("\n");
			}
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "� ���� ���������� ������ ��������!");
			try {
				fw.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "���� ���������� ������ �������!");
				return;
			}
			return;
		}
		
		try {
			fw.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "������ ������� ���� ��� ����������!");
			return;
		}
	}
	
	static void DepthCycle()//�������� ���� ������ ����. ������
	{
		try {
		fw.write("������ ���������!\n");
		resultText.setText("");
		Mark = new int[n];//������ ���� �������� ��������� ����� 0
		count = 0;//�������� ������� ����� ������
		jC = 0;//�������� ������� ������
		fw.write("����� ������: " + n + "\n");
		fw.write("������ ���� �������� ��������� ����� 0\n");
		int countKomp = 0; //�������//������� ��������� ���������
		fw.write("����� ������� � ����������� �������� � ��������� ������ 0 � ������ ���������� ��������� � �������� ����� � �������\n");
				for(int v = 0; v < n; v++)
				{
					if(Mark[v] == 0)
					{
						/* �������
						 * ��������� ����� ������� ��������� ���������, ���� ���� ���������
						 */
						countKomp++;//�������//
						fw.write("----------------------------------------\n" + countKomp + "-� ��������� ���������\n----------------------------------------\n");
						fw.write("������� " + (v + 1) + " ����� ��������� ������� 0. ������ �������� ��.\n");
						resultText.append("----------------------------------------\n" + countKomp + "-� ��������� ���������\n----------------------------------------\n");
						nC++;
						fw.write("������� " + (v+1) + " ��������� � ���� ��������� ������ �����\n");
						C[nC] = v;//��������� ������� � ���� ��������� ������ �����
						fw.write("����: " + stackString() + "\n");
						Cycle(v,0);
						fw.write("������� ������� " + (v+1) + " �� �����, ��� ��� ��� ��� ����������\n");
						nC--;
						fw.write("����: " + stackString() + "\n");
					}
					fw.write("������� " + (v+1) + " ����� ��������� ������� >0\n");
				}
				fw.write("����� ���������!\n\n\n");
				fw.write("��������� ������ ���������!\n");
				fw.write(resultText.getText());	
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "� ���� ���������� ������ ��������!");
				try {
					fw.close();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "���� ���������� ������ �������!");
					return;
				}
				return;
			}/////
	}
	
	static void Cycle(int x, int y)//����������� ������� ������ ����. ������
	{
		try
		{
			count++;
			Mark[x] = count; //������� �����������
			fw.write("������� " + (x+1) + " ���� ��������� ����� " + count + "\n");
			fw.write("����� �������� � ������� �� ��������, ���� ��� ����\n");
			for(int i = 1; i < AdjTable.getColumnCount(); i++)
			{
				if(AdjTable.getValueAt(x + 1, i).toString().equals("1"))
				{
					int v = i - 1;
					fw.write("������a " + (x+1) + " ������ ������� " + (v+1) + "\n");
					if(v == y)////new// v == y ��������� ����������� ����� � ������� ������� ��� ������
					{
						fw.write("��� ������� ���������� ��� ��� �� �� ��� ������\n");
						continue;
					}//new
					if((Mark[v] > Mark[x]))///new
					{
						fw.write("��� ������� ���������� ��� ��� ��� ����� ������� ��������� �����, � ������ ��� ��� �����������\n");
						continue;
					}///new
					fw.write("������� " + (v+1) + " ��������� � ���� ��������� ������ �����\n");
					nC++;
					C[nC] = v; //������� � ����
					fw.write("����: " + stackString() + "\n");
					if(Mark[v] == 0)
					{
						fw.write("������a " + (v+1) + " ����� ��������� ����� 0, ������� ����� �������� ��\n");
						Cycle(v,x);
					}
					else
					{
							fw.write("������a " + (v+1) + " ����� ��������� ����� >0, �� ����� �������, ��� \n");
							fw.write("������� �������� ����� (" + (x+1) + "," + (v+1) + "), � ������ ������ ��������������� ����:\n");
							jC++; //�������� ����� (x,v), ������ ����
							WriteCycle(v, C, nC);//������ ����� � ����
					}
					fw.write("������� ������� " + (v+1) + " �� �����, ��� ��� ��� ��� �����������\n");
					nC--;//������� ������������� ������� �� �����
					fw.write("����: " + stackString() + "\n");
				}
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "� ���� ���������� ������ ��������!");
			try {
				fw.close();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "���� ���������� ������ �������!");
				return;
			}
			return;
		}/////
	}
	
	static void WriteCycle(int x, int[] C, int nC)
	{
		try {
			fw.write(jC + ") ");//������ ������ �����
			resultText.append(jC + ") ");
			do
			{
				fw.write("  " + (C[nC] + 1));//������ ������� �� �����
				resultText.append("  " + (C[nC] + 1));
				nC--;
			}while(!(C[nC] == x));
			fw.write("\n");
			resultText.append("\n");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "� ���� ���������� ������ ��������!");
			try {
				fw.close();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "���� ���������� ������ �������!");
				return;
			}
			return;
		}
	}
	
	static void matrixOrder(int order)//������� ������� �������
	{
		AdjTableModel.setRowCount(order);
		AdjTableModel.setColumnCount(order);
	}
	
	static void quadrMatrix()//������� ������� ������ ������ ������� � ���� ���������
	{
		for(int p = 0; p < AdjTable.getColumnCount(); p++)
		{
			AdjTable.getColumnModel().getColumn(p).setPreferredWidth(25);
		}
	}
	
	static public boolean isNeoGraph()//������� �������� �������� �� ���� �����������������
	{
		for(int v = 1; v < AdjTable.getRowCount(); v++)
		{
			for(int adj = 1 + v; adj < AdjTable.getColumnCount(); adj++)
			{
				if(!(AdjTable.getValueAt(v, adj).toString().equals(AdjTable.getValueAt(adj, v).toString())))
				{
					return false;
				}
			}
		}
		return true;
	}
	
	static String stackString()//������� ������ ����� ��������� ������ �����
	{
		String str = new String();
			str+="[";
			for(int i = 0; i <= nC; i++)
			{
				if(i == nC)
					str+= " " + (C[i] + 1);
				else
					str+= " " + (C[i] + 1) + ",";
			}
			str+="]";
			return str;
	}
}

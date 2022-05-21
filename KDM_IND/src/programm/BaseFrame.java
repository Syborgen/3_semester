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
	static boolean find = false;//флаг отображение графа или его фунд. циклов
	
	static int n = 0;//количество вершин
	static int count = 0;//счетчик меток вершин
	static int jC = 0;//счетчик числа циклов
	static int nC = -1;//вершина стека циклов
	
	static int[] Mark;//начальные метки вершин
	static int[] C;//стек выделения циклов графа
	static Point2D[] coords;//массив координат вершин
	
	static FileReader fr = null;//файл для чтения
	static FileWriter fw = null;//файл для записи
	static Scanner filescan = null;//считывание с файла
	static JFileChooser fileChooser = new JFileChooser("Files");//выбор файла
	static JTable AdjTable = null;//матрица смежности
	static DefaultTableModel AdjTableModel = new DefaultTableModel()//модель таблицы — матрицы смежности
	{
		
		public boolean isCellEditable(int row, int col)//запрещает редактирование ячеек с метками вершин и диагональ с 0
		{
			if(row == 0 || col == 0 || row == col)
				return false;
			else
				return true;
		}
		
		public void setValueAt(Object v, int row, int col)//разрешает вносить в таблицу только 0 и 1
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
				JOptionPane.showMessageDialog(null, "В таблице только 0 и 1!");
			}
		}
		
	};
	static JScrollPane AdjScroll = null;//полосы прокрутки для матрицы смежности
	static JTextArea resultText = null;//поле для вывода результата
	static JPanel res = null;//панель для поле с результатами
	
	static//статический блок инициализации
	{
		fileChooser.setFileFilter(new FileNameExtensionFilter("Text only (*.txt)","txt"));//фильтр для выбора файлов
		
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
		
		JPanel panel = new JPanel()//панель в которой рисуется граф
		{
			
			Point2D center;//центр круга по которому располагаются вершины
			double radius;//радиус круга по которому располагаются вершины
			double vertRadius = 15;//радиус вершин
			
			public void paintComponent(Graphics g)//метод отрисовки объектов
			{
				super.paintComponent(g);
				if(n == 0)//если кол-во вершин 0 ничего не рисуем
					return;
				Graphics2D g2 = (Graphics2D) g;
				setSize(getPreferredSize());//меняем размер в зависимости от кол-ва вершин
				center = new Point2D.Double(getWidth() / 2, getHeight() / 2);
				radius = center.getX() - 30D;
				
				Coords();//определяем координаты вершин
				
				if(AdjTable.getCellEditor() != null)//если матрица редактируется отрисовываем новый граф
				{
					find = false;//режим отображение графа без фунд. циклов
					resultText.setText("");
				}
				
					try
					{
					
						if(!find)//рисуем граф смотря от режима: рисуем с фунд. циклами или без
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
			
			public void Coords()//вычисление координат вершин графа для отображения
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
			
			public void drawGraph(Graphics2D g2)//отрисовка графа в зависимости от его ориентированности или неориентированности
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
			
			public void drawOrEdge(Graphics2D g2, int v, int adj, Color color)//отрисовка ориентированного ребра
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
			
			public void drawNeoEdge(Graphics2D g2, int v, int adj, Color color)//отрисовка неориентированного ребра
			{
				g2.setColor(color);
				g2.draw(new Line2D.Double(coords[v - 1],coords[adj - 1]));
			}
			
			public void drawCycle(Graphics2D g2)//отрисовка фунд. циклов
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
			
			public void drawMark(Graphics2D g2, Color vert, Color mark)//отрисовка меток вершин
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
			
			public void Cycle(int x, int y,Graphics2D g2)//рекурсивная функция отрисовки циклов
			{
				count++;
				Mark[x] = count; //вершина исследована
				
				for(int i = 1; i < AdjTable.getColumnCount(); i++)
				{
					if(AdjTable.getValueAt(x + 1, i).toString().equals("1"))
					{
						int v = i - 1;
						nC++;
						C[nC] = v; //вершину в стек
						if(Mark[v] == 0)
						{
							g2.setColor(Color.ORANGE);
							g2.draw(new Line2D.Double(coords[v],coords[C[nC-1]]));
							Cycle(v,x,g2);
						}
						else
						{
							if((Mark[v] < Mark[x]) & v!=y)// v!=y исключаем возвращение назад в вершину которую уже прошли
							{
								g2.setColor(Color.RED);
								g2.draw(new Line2D.Double(coords[v],coords[x]));
							}
						}
						nC--;//удаляем исследованную вершину из стека
					}
				}////for
			}
			
			public Dimension getPreferredSize()//предпочтительный размер панели в зависимости от вершин
			{
				if(n < 5)
					return new Dimension(200,200);
				else
				return new Dimension((n/2) * 100, (n/2) * 100);
			}
		};
		
		panel.setBackground(Color.WHITE);
		
		JScrollPane scroll = new JScrollPane(panel);//полосы прокрутки для панели графа
		scroll.setLocation(new Point(10,10));
		scroll.setSize(new Dimension(1100,700));
		
		JPanel buttons = new JPanel();
		buttons.setSize(new Dimension(300,100));
		buttons.setLocation(new Point(100,730));
		
		JButton findButton = new JButton("Найти фундаментальные циклы");
		findButton.addActionListener(event ->
					{
						if(AdjTable.getCellEditor() != null)//убираем фокус от матрицы смежности
							AdjTable.getCellEditor().stopCellEditing();
						if(n == 0)//если граф не занан выдаем сообщение
						{
							JOptionPane.showMessageDialog(null, "Задайте граф!");
							return;
						}
						if(!isNeoGraph())//если граф ориентированный выдаем сообщение о неправильных входных данных
						{
							JOptionPane.showMessageDialog(null, "Алгоритм дает результат только для неографов!");
							return;
						}
						try {
							fw = new FileWriter("Listing.txt");
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Нельзя открыть файл для сохранения!");
							return;
						}
						DepthCycle();//главный алгоритм поиска фунд. циклов
						find = true;//режим отображения фунд. циклов
						panel.repaint();
						
						try {
							fw.close();
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, "Нельзя закрыть файл для сохранения!");
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
		
		JButton openFile = new JButton("Открыть файл");//кнопка открытия файла
		openFile.addActionListener(event ->
		{
			if(AdjTable.getCellEditor() != null)
				AdjTable.getCellEditor().stopCellEditing();
			fileChooser.setDialogTitle("Выбор файла");
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = fileChooser.showOpenDialog(BaseFrame.this);
			if(result == JFileChooser.APPROVE_OPTION)
			{
				JOptionPane.showMessageDialog(BaseFrame.this, "Выбран файл: " + fileChooser.getSelectedFile());
				if(FileReading())//если файл считался удачно, то отрисовываем граф
				{
					quadrMatrix();//ячейки в виде квадратов
					find = false;
				}
				else//если нет — не отрисовываем ничего
				{
					matrixOrder(n = 0);
				}
				panel.repaint();
				resultText.setText("");
			}
		}
		);
		
		JButton saveFile = new JButton("Сохранить файл");//кнопка сохранения текущей матрицы смежности
		saveFile.addActionListener(event ->
		{
			if(AdjTable.getCellEditor() != null)
				AdjTable.getCellEditor().stopCellEditing();
			if(n == 0)
			{
				JOptionPane.showMessageDialog(null, "Задайте граф!");
				return;
			}
			fileChooser.setDialogTitle("Сохранение файла");
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = fileChooser.showSaveDialog(BaseFrame.this);
			if (result == JFileChooser.APPROVE_OPTION )
			{
				FileWriting();//запись в файл
				JOptionPane.showMessageDialog(BaseFrame.this, 
		                              "Файл " + fileChooser.getSelectedFile() + 
		                              ".txt сохранен");
			}
		});
		
		JTextField orderField = new JTextField(4);
		orderField.setFont(new Font("Sans Serif", Font.PLAIN, 14));
		
		JButton orderButton = new JButton("Задать количество вершин");//определение кол-ва вершин в новой матрице смежности
		orderButton.addActionListener(event ->
		{
			if(AdjTable.getCellEditor() != null)
				AdjTable.getCellEditor().stopCellEditing();
			int tmp;
			if(orderField.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "Задайте количество вершин!");
				return;
			}
			try
			{
				tmp = Integer.parseInt(orderField.getText());
			}catch(Exception e)//перехват некорректного ввода
			{
				JOptionPane.showMessageDialog(null, "Некорректный ввод количества вершин! Повторите ввод.");
				orderField.setText("");
				return;
			}
			if(tmp < 1 || tmp > 1000)//ограничения
			{
				JOptionPane.showMessageDialog(null, "Количество вершин в пределах от 1 до 1000!");
				orderField.setText("");
				return;
			}
			n = tmp;
			matrixOrder(n+1);
			
			Mark = new int[n];//начальные метки вершин
			C = new int[n+1];//стек выделения циклов графа

			
			for(int i = 1; i < AdjTable.getColumnCount(); i++)//заполнение меток вершин матрицы смежности
			{
				AdjTable.setValueAt(i + "", 0, i);
				AdjTable.setValueAt(i + "", i, 0);
			}
			
			for(int i = 1; i < AdjTable.getColumnCount(); i++)//запонение матрицы нулями
			{
				for(int j = 1; j < AdjTable.getColumnCount(); j++)
				{
					AdjTable.setValueAt("0", i, j);
				}
			}
			quadrMatrix();//квадратные ячейки матрицы
			find = false;//режим отображение фунд. циклов выключен
			panel.repaint();
			resultText.setText("");
			orderField.setText("");
		}
		);
		
		JButton symmetric = new JButton("Отразить верхнюю треугольную матрицу");//отразить верхнюю тругольную матрицу в матрице смежности
																				//чтобы сделать граф неориентированным
		symmetric.addActionListener(event ->
		{
			if(n == 0)
			{
				JOptionPane.showMessageDialog(null, "Задайте граф!");
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
	
	
	
	static boolean FileReading()//чтение файла
	{
		try {
			fr = new FileReader(fileChooser.getSelectedFile());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Нет доступа к файлу!");
			return false;
		}
		filescan = new Scanner(fr);
		try {
			n = filescan.nextInt();//кол-во вершин
			filescan.nextLine();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Файл пуст или испорчен!");
			filescan.close();
			try {
				fr.close();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Файл нельзя закрыть!");
				return false;
			}
			return false;
		}
		if(n > 0 & n < 1001)//ограничения
		{
			Mark = new int[n];//начальные метки вершин
			C = new int[n+1];//стек выделения циклов графа
		}else
		{
			JOptionPane.showMessageDialog(null, "Количество вершин от 1 до 1000!");
			filescan.close();
			try {
				fr.close();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Файл нельзя закрыть!");
				return false;
			}
			return false;
		}
		
		matrixOrder(n+1);//задаем порядок матрицы
		
		for(int i = 0; i < n; i++)//проверка меток в столбцах матрицы смежности
		{
			try
			{ 
				if(filescan.nextInt() != i+1)
				{
					JOptionPane.showMessageDialog(null, "Файл испорчен: Метки вершин в таблице смежности! Столбец: " + (i+1));
					filescan.close();
					try {
						fr.close();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "Файл нельзя закрыть!");
						return false;
					}
					return false;
				}
				else
					AdjTable.setValueAt((i+1) + "", 0, i+1);
			}catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "Файл испорчен: Нет меток вершин");
				filescan.close();
				try {
					fr.close();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Файл нельзя закрыть!");
					return false;
				}
				return false;
			}
		}
		filescan.nextLine();
		
		for(int i = 0; i < n; i++)
		{
			try {
					int mark = filescan.nextInt();            //метка вершины
					if(mark != i+1)//проверка меток в строках матрицы смежности
					{
						JOptionPane.showMessageDialog(null, "Файл испорчен: Метки вершин в таблице смежности! Строка: " + (i+1));
						filescan.close();
						try {
							fr.close();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null, "Файл нельзя закрыть!");
							return false;
						}
						return false;

					}
					else
						AdjTable.setValueAt((i+1) + "", i+1, 0);
					for(int j = 0; j < n;j++)//проверка матрицы смежности
					{
						try
						{
							int tmp = filescan.nextInt();
							if(j == i & tmp != 0)
							{
								JOptionPane.showMessageDialog(null, "Файл испорчен: В диагонале только 0 ! Строка: " + (i+1));
								filescan.close();
								try {
									fr.close();
								} catch (IOException e1) {
									JOptionPane.showMessageDialog(null, "Файл нельзя закрыть!");
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
									JOptionPane.showMessageDialog(null, "Файл испорчен: В таблице смежности числа только 0 и 1! Строка: " + (i+1) + " Столбец: " + (j+1));
									filescan.close();
									try {
										fr.close();
									} catch (IOException e1) {
										JOptionPane.showMessageDialog(null, "Файл нельзя закрыть!");
										return false;
									}
									return false;
								}
								else
									AdjTable.setValueAt(0 + "", i + 1, j + 1);
							}
					}catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, "Файл испорчен!");
						filescan.close();
						try {
							fr.close();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null, "Файл нельзя закрыть!");
							return false;
						}
						return false;
					}

				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Файл испорчен!");
				filescan.close();
				try {
					fr.close();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Файл нельзя закрыть!");
					return false;
				}
				return false;
			}
			if(filescan.hasNextLine())
			filescan.nextLine();
		}/////for вершины
		try {
			fr.close();
			filescan.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Файл нельзя закрыть!");
			return false;
		}
		return true;
	}
	
	static void FileWriting()//Сохранение в файл текущей матрицы смежности
	{
		try {
			fw = new FileWriter(fileChooser.getSelectedFile() + ".txt");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Нельзя открыть файл для сохранения!");
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
			JOptionPane.showMessageDialog(null, "В файл сохранения нельзя записать!");
			try {
				fw.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Файл сохранения нельзя закрыть!");
				return;
			}
			return;
		}
		
		try {
			fw.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Нельзя закрыть файл для сохранения!");
			return;
		}
	}
	
	static void DepthCycle()//основной цикл поиска фунд. циклов
	{
		try {
		fw.write("НАЧАЛО АЛГОРИТМА!\n");
		resultText.setText("");
		Mark = new int[n];//задаем всем вершинам начальные метки 0
		count = 0;//обнуляем счетчик меток вершин
		jC = 0;//обнуляем счетчик циклов
		fw.write("Число вершин: " + n + "\n");
		fw.write("Задаем всем вершинам начальные метки 0\n");
		int countKomp = 0; //Добавка//счетчик компонент связности
		fw.write("Берем вершину с минимальной пометкой и начальной меткой 0 в каждой компоненте связности и начинаем поиск в глубину\n");
				for(int v = 0; v < n; v++)
				{
					if(Mark[v] == 0)
					{
						/* Добавка
						 * Добавляем вывод номеров компонент связности, если граф несвязный
						 */
						countKomp++;//Добавка//
						fw.write("----------------------------------------\n" + countKomp + "-я комонента связности\n----------------------------------------\n");
						fw.write("Вершина " + (v + 1) + " имеет начальную пометку 0. Значит выбираем ее.\n");
						resultText.append("----------------------------------------\n" + countKomp + "-я комонента связности\n----------------------------------------\n");
						nC++;
						fw.write("Вершину " + (v+1) + " добавляем в стек выделения циклов графа\n");
						C[nC] = v;//добавляем вершину в стек выделение циклов графа
						fw.write("Стек: " + stackString() + "\n");
						Cycle(v,0);
						fw.write("Удаляем вершину " + (v+1) + " из стека, так как она уже иследована\n");
						nC--;
						fw.write("Стек: " + stackString() + "\n");
					}
					fw.write("Вершина " + (v+1) + " имеет начальную пометку >0\n");
				}
				fw.write("КОНЕЦ АЛГОРИТМА!\n\n\n");
				fw.write("РЕЗУЛЬТАТ РАБОТЫ АЛГОРИТМА!\n");
				fw.write(resultText.getText());	
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "В файл сохранения нельзя записать!");
				try {
					fw.close();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Файл сохранения нельзя закрыть!");
					return;
				}
				return;
			}/////
	}
	
	static void Cycle(int x, int y)//рекурсивная функция поиска фунд. циклов
	{
		try
		{
			count++;
			Mark[x] = count; //вершина исследована
			fw.write("Вершине " + (x+1) + " даем начальную метку " + count + "\n");
			fw.write("Далее переходи к смежным ей вершинам, если они есть\n");
			for(int i = 1; i < AdjTable.getColumnCount(); i++)
			{
				if(AdjTable.getValueAt(x + 1, i).toString().equals("1"))
				{
					int v = i - 1;
					fw.write("Вершинa " + (x+1) + " смежна вершине " + (v+1) + "\n");
					if(v == y)////new// v == y исключаем возвращение назад в вершину которую уже прошли
					{
						fw.write("Эту вершину пропускаем так как мы из нее пришли\n");
						continue;
					}//new
					if((Mark[v] > Mark[x]))///new
					{
						fw.write("Эту вершину пропускаем так как она имеет большую начальную метку, а значит она уже исследована\n");
						continue;
					}///new
					fw.write("Вершину " + (v+1) + " добавляем в стек выделения циклов графа\n");
					nC++;
					C[nC] = v; //вершину в стек
					fw.write("Стек: " + stackString() + "\n");
					if(Mark[v] == 0)
					{
						fw.write("Вершинa " + (v+1) + " имеет начальную метку 0, поэтому далее выбираем ее\n");
						Cycle(v,x);
					}
					else
					{
							fw.write("Вершинa " + (v+1) + " имеет начальную метку >0, из этого следует, что \n");
							fw.write("найдено обратное ребро (" + (x+1) + "," + (v+1) + "), а значит найден фундаментальный цикл:\n");
							jC++; //обратное ребро (x,v), найден цикл
							WriteCycle(v, C, nC);//запись цикла в файл
					}
					fw.write("Удаляем вершину " + (v+1) + " из стека, так как она уже исследована\n");
					nC--;//удаляем исследованную вершину из стека
					fw.write("Стек: " + stackString() + "\n");
				}
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "В файл сохранения нельзя записать!");
			try {
				fw.close();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Файл сохранения нельзя закрыть!");
				return;
			}
			return;
		}/////
	}
	
	static void WriteCycle(int x, int[] C, int nC)
	{
		try {
			fw.write(jC + ") ");//печать номера цикла
			resultText.append(jC + ") ");
			do
			{
				fw.write("  " + (C[nC] + 1));//печать вершины из стека
				resultText.append("  " + (C[nC] + 1));
				nC--;
			}while(!(C[nC] == x));
			fw.write("\n");
			resultText.append("\n");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "В файл сохранения нельзя записать!");
			try {
				fw.close();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Файл сохранения нельзя закрыть!");
				return;
			}
			return;
		}
	}
	
	static void matrixOrder(int order)//задание порядка матрицы
	{
		AdjTableModel.setRowCount(order);
		AdjTableModel.setColumnCount(order);
	}
	
	static void quadrMatrix()//функция которая делает ячейки таблицы в виде квадратов
	{
		for(int p = 0; p < AdjTable.getColumnCount(); p++)
		{
			AdjTable.getColumnModel().getColumn(p).setPreferredWidth(25);
		}
	}
	
	static public boolean isNeoGraph()//функция проверки является ли граф неориентированным
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
	
	static String stackString()//функция печати стека выделения циклов графа
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;


public class SomFrame extends JFrame implements ActionListener, ItemListener
{
private static final Graphics G2 = null;
private static final Graphics G = null;
private SomPanel p;
protected Font comics30 = new Font("Comics Sans MS", Font.BOLD, 30);
protected Font comics18 = new Font("Comics Sans MS", Font.BOLD, 18);
protected Font comics40 = new Font("Comics Sans MS", Font.BOLD, 70);

protected Font arial  = new Font("Arial", Font.BOLD, 12);
protected Font arial10  = new Font("Arial", Font.BOLD, 10);
protected Font arial14  = new Font("Arial", Font.BOLD, 14);
protected Font dialog   = new Font("Dialog", Font.BOLD + Font.ITALIC, 15);

private JButton Demarrer = new JButton("Démarrer");
private JButton Arreter  = new JButton("Arrêter");
private JButton Apropos  = new JButton("?");
private JComboBox combo  = new JComboBox();
private JComboBox combo2  = new JComboBox();
private JLabel    label  = new JLabel("Nbre de Neurones:");
private JLabel    label2  = new JLabel("Forme:");

private JToolBar Bar = null;

	
public SomFrame()
{
		setTitle("Simulation Kohonen");
		setSize(612-96, 634-96+83);
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	

		//this.setContentPane(p);
	
		Container c=getContentPane();
	//	initialiserMenu();
		//fonctionMENU();
		initPanneaux();
		p=new SomPanel();
		c.add(p);	
		
	
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////   
// CREATION des PANNEAUX:

void initPanneaux()
{
//pan : panneau principal
JPanel pan = new JPanel();
pan.setBackground(Color.white);
pan.setPreferredSize(new Dimension(500,33));

//pan1.se

Demarrer.setFont(arial);
Arreter.setFont(arial);
Arreter.setEnabled(false);
Apropos.setFont(arial);
Apropos.setPreferredSize(new Dimension(40,25));

combo.setPreferredSize(new Dimension(55,20));
//combo.addItem("-");
combo.addItem("10000");
combo.addItem("1000");
combo.addItem("500");
combo.addItem("300");
combo.addItem("200");
combo.addItem("100");
combo.setSelectedIndex(0);

combo2.setPreferredSize(new Dimension(75,20));
combo2.addItem("Rectangle");
combo2.addItem("Cercle");

pan.add(Demarrer);
pan.add(Arreter);
pan.add(label);
pan.add(combo);
pan.add(label2);
pan.add(combo2);
pan.add(Apropos);
//pan1.setBorder(new LineBorder(Color.blue, 5));

JLabel TitreLabel  = new JLabel   ("Kohonen SOM");
//TitreLabel.setHorizontalAlignment(JLabel.NORTH);
TitreLabel.setFont(comics30);

JPanel pan2 = new JPanel();
pan2.setPreferredSize(new Dimension(500,50));
pan2.add(TitreLabel);

this.getContentPane().add(pan2,BorderLayout.NORTH);
this.getContentPane().add(pan, BorderLayout.SOUTH);


Demarrer.addActionListener(this);
Arreter.addActionListener(this);
Apropos.addActionListener(this);
combo.addItemListener(this);

}
@Override
public void itemStateChanged(ItemEvent arg0) {
	Object obj=combo.getSelectedItem();
	String chaine = (String)obj;
	if(chaine=="10000"){ p.n=4000; p.W = new double [p.n][2]; }
	if(chaine=="1000"){ p.n=1000; p.W = new double [p.n][2]; }
	if(chaine=="500"){ p.n=500; p.W = new double [p.n][2]; }
	if(chaine=="300"){ p.n=300; p.W = new double [p.n][2]; }
	if(chaine=="200"){ p.n=200; p.W = new double [p.n][2]; }
	if(chaine=="100"){ p.n=100; p.W = new double [p.n][2]; }
}


@Override
public void actionPerformed(ActionEvent e) {
	if((JButton)e.getSource() == Apropos)
	{
		JOptionPane.showMessageDialog(null,
		          "Créateur      : ABBAZI Yassine\n" +
		          "Master         : SécuRISE\n" +
		          "Professeur : M. JEDRA",
		          "Informations", JOptionPane.NO_OPTION);
	}
	if((JButton)e.getSource() == Demarrer)
	{
		//SomPanel pan = new SomPanel();
		p.annimation=true;
		p.t=0;
		Object obj2=combo2.getSelectedItem();
		String chaine2 = (String)obj2;
		if(chaine2=="Rectangle"){p.AFFICHAGE(p.X); p.x=true;  p.c=false;} // p.W = new double [p.n][2]; }
		if(chaine2=="Cercle")   {p.AFFICHAGE(p.C); p.x=false; p.c=true;} // p.W = new double [p.n][2]; }
		//p.AFFICHAGE(p.x);
		p.initialisation();
		Demarrer.setEnabled(false);
		Arreter.setEnabled(true);
		combo.setEnabled(false);
		
		//p.repaint();
	}
	if((JButton)e.getSource() == Arreter)
	{
		Demarrer.setEnabled(true);
		Arreter.setEnabled(false);
		combo.setEnabled(true);
		p.annimation=false;
				
	}
}





}
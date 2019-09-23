import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JPanel;


public class SomPanel extends JPanel
{
	protected int n;
	double X[][] = new double       [25*4+1][2];
	double C[][] = new double       [25*4+1][2];
	//double x[][]; //= new double       [25*4+1][2]; // pas encore utilisé
	boolean x = false;
	boolean c = false;

	protected double W[][] = new double [1000][2];  // les lignes pour le nombre de neurones les deux colonnes pour X et Y
	//double    x[][] = new double          [26][26];
	//double    y[][] = new double          [26][26];

	protected boolean annimation;

	Runnable T;

	double h;
	double Distance;
	double Sigma;
	double Sigma0=120; // 15 <Sigma0<=30
	//double Sigma0=80; 

	int g;
	int s;
	//int s1;
	//int s2;

	double Gama;
	double Gama0=0.12;

	int t=0 ;
	int tmax=2000;

	public void AFFICHAGE(final double y[][])
	{	
		// CLASSE IMPLEMENTs RUNNABLE:
		T=new Runnable(){

			public void run() {

				while(t<tmax && annimation)
				{
					Calcul(y);
					try
					{
						Thread.sleep(50) ;
					}
					catch (InterruptedException e)
					{
						e.printStackTrace() ;
					}
					repaint();
					t++;
				}

			}
		};

	}

	public SomPanel()
	{
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////LES FONCTIONs  DE L'ALGORITHME KOHONEN
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/*
X[0][0]= 120 ;              X[0][1]= 120;
X[1][0]= 500-120;           X[1][1]= 120;
X[2][0]= 120 ;              X[2][1]= 500-120;
X[3][0]= 500-120;           X[3][1]= 500-120;
X[4][0]= 500/2 ;            X[4][1]= 160;
X[5][0]= 500/2;             X[5][1]= 500-120;
X[6][0]= 120;               X[6][1]= 500/2;
X[7][0]= 500-120;           X[7][1]= 500/2;
		 */
		// Dessin d'un rectangle

		for(int i=0;i<25;i++)
		{
			X[i][0]=120+(10*(i%25));
			X[i][1]=120;
		}
		for(int i=25;i<(25*2)+1;i++)
		{
			X[i][0]=500-130;
			X[i][1]=120+(10*(i%26));
		}
		for(int i=(25*2)+1;i<(25*3)+1;i++)
		{
			X[i][0]=120+(10*(i%25));
			X[i][1]=500-130;
		}
		for(int i=(25*3)+1;i<(25*4)+1;i++)
		{
			X[i][0]=120;
			X[i][1]=120+(10*(i%25));
		}

		int x=250;//getWidth()/2;
		int y=250;//getHeight()/2;
		int r=(500-(120*2))/2;

		// Dessin d'un cercle

		for(int i=0;i<C.length;i++)
		{
			C[i][0]= x+r*Math.cos(i*Math.PI*4/180);
			C[i][1]= y+r*Math.sin(i*Math.PI*4/180);
		}

		//////////////////////////////////////////////////////////////////////////////////////
		/*for(int i=104;i<(104+20);i++)
		{
			X[i][0]=120+30+(10*(i%26));
			X[i][1]=120+30;
		}
		for(int i=(104+20);i<104+20*2;i++)
		{
			X[i][0]=500-(120+30);
			X[i][1]=120+30+(10*(i%26));
		}
		for(int i=104+20*2;i<104+20*3;i++)
		{
			X[i][0]=120+30+(10*(i%26));
			X[i][1]=500-(120+30);
		}
		for(int i=104+20*3;i<104+20*4;i++)
		{
			X[i][0]=120+30;
			X[i][1]=120+30+(10*(i%26));
		}*/

		//X[8][0]= 792/2+(792-2*260)/4;           X[8][1]= 494/2;
		/*for(int i=0;i<26;i++)
     	{
     		for(int j=0;j<26;j++)
     		{
     			x[i][j]=120+(j*10);
     			y[j][i]=120+(j*10);

     		}
     	}
		 */

		// 1:
		//initialisation();

	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////                            LES FONCTIONs  DE L'ALGORITHME KOHONEN
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 1. Initialisation des poids
	public void initialisation()
	{
		int posX=0;
		int posY=0;

		for(int i=0;i<W.length;i++)
		{
			posX = (int)(Math.random() * (500));
			posY = (int)(Math.random() * (500));

			//posX = (int)(Math.random() * (500-2*120)+120);
			//posY = (int)(Math.random() * (500-2*120)+120);
			//posX = (int)(Math.random() * ((500/2+10)-(500/2-10))+500/2-10);
			//posY = (int)(Math.random() * ((500/2+10)-(500/2-10))+500/2-10);
			W[i][0]=posX;
			W[i][1]=posY;

			/*W[i][0]=getWidth()/i;
			W[i][1]=getHeight()/i;*/ 
		}
		new Thread(T).start();
	}


	// 2. Présentation d'un stimulu X:
	public int Stimuli(int max)
	{
		int posA=0;
		posA = (int)(Math.random() * max);
		return posA;
	}

	/*public void fonction()
	{
		int posX=0;
		int posY=0;
		posX=(int)(Math.random() * ((((792-260)-260)+260)));
		posY=(int)(Math.random() * ((((498-160)-160)+160)));
		X[0][0]=posX % (792-260*2)/3+260;
		X[0][1]=posY % (498-160*2)/3+160;

		for(int i=0;i<X.length;i++)
		{
			X[i][0]=
		}

	}*/

	// 3. Distance (Détermination du gagnant et distance entre g et i):
	// - Gagnant :
	public int Gagnant(double [][]w, double [][]x,int s)
	//public int Gagnant(double [][]w, double [][]x,double [][]y,int s1,int s2)
	{
		double T =0;

		double min=1000000000;
		int position=0;

		for(int i=0;i<W.length;i++)
		{
			T= (double) Math.sqrt(Math.pow((w[i][0] - x[s][0]),2) + Math.pow((w[i][1] - x[s][1]),2));

			// T= (double) Math.sqrt(Math.pow((w[i][0] - x[s1][s2]),2) + Math.pow((w[i][1] - y[s1][s2]),2));

			if(min>T)
			{
				min=T;
				position=i;
			}
		}
		return position;
	} 

	// - Distance (i,g) :
	public double Distance(double w[], double x[])
	{
		double D=0;
		D= (double) Math.sqrt(Math.pow((w[0] - x[0]),2) + Math.pow((w[1] - x[1]),2));
		return D;
	}

	// 4. Adaptation des poids:
	public void Adaptation(double x[][])
	{

		Sigma = Sigma0;
		//double tau2= 1000;
		//double tau1 =1000/Math.log(Sigma);
		//Sigma0--;
		///Gama=Gama0*Math.exp(-1/1000);
		Gama=Gama0*Math.exp(-t/1000);
		//Sigma=Sigma0*Math.exp(-t/tmax);
		Sigma=1000/Math.log(Sigma);
		Sigma = Sigma0*Math.exp(-t/Sigma);
		for(int i=0;i<W.length;i++)
		{
			double D=Distance(W[i],W[g]);
			//System.out.println("g ++> "+g);
			h=Math.exp(-Math.pow(D,2)/(2*Math.pow(Sigma,2)));
			//W[i][0]=W[i][0]+Gama*h*(X[s][0]-W[i][0]);
			//W[i][1]=W[i][1]+Gama*h*(X[s][1]-W[i][1]);
			W[i][0]=W[i][0]+Gama*h*(x[s][0]-W[i][0]);
			W[i][1]=W[i][1]+Gama*h*(x[s][1]-W[i][1]);
			//W[i][0]=W[i][0]+Gama*h*(x[s1][s2]-W[i][0]);
			//W[i][1]=W[i][1]+Gama*h*(y[s1][s2]-W[i][1]);	
		}
	}

	// 5.FONCTION CALCUL ET AFFICHAGE
	public void Calcul(double y[][])
	{
		// 2:
		s=Stimuli(y.length);

		// 3:
		g=Gagnant(W,y,s);

		// 4:
		Adaptation(y);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	//////////////                             FONCTION DE DESSIN
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	public void paintComponent(Graphics G2)
	{
		super.paintComponent(G2);
		Graphics2D g=(Graphics2D) G2;
		g.setStroke(new BasicStroke(3,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
		setBackground(Color.white);
		if(x==true && c==false)
		{
			g.setColor(Color.blue);
			g.drawRect(120,120,getWidth()-120*2,getHeight()-120*2);
		}
		if(x==false && c==true)
		{
			g.setColor(Color.red);     
			for(int i=1;i<C.length-1;i++)
				g.drawLine((int)C[i-1][0], (int)C[i-1][1], (int)C[i][0],(int)C[i][1]);
		}    
		//g.drawLine((int)C[C.length-1][0], (int)C[C.length-1][1], (int)C[0][0],(int)C[0][1]);
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		///////                                  AFFICHAGE       

		//g.drawOval(150,150,100, 100);
		for(int i=0;i<W.length;i++)
		{
			g.setStroke(new BasicStroke((float) 0.5,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
			g.fillOval((int)W[i][0],(int)W[i][1], 10, 10);
			//System.out.println("t ---->  "+t);

		}

	}

}

import java.awt.*;
import java.net.URL;

public class proiectAAV extends Frame {
	Toolkit tool;
	int ww,hh;
	public Image backg;
	public ProprietiesPanel PPanel;
	public DataPanel dataPanel;
	public Desen desen;
	Font f = new Font("TimesRoman", 1, 14);
	
	public static void main(String args[]){new proiectAAV();}
	
	public proiectAAV(){
		
		tool=getToolkit();
		Dimension res=tool.getScreenSize();
		ww=res.width;
		hh=res.height;
		setResizable(false);
		setTitle("Proiect Java Avadanei Andrei-Vasile");
		setIconImage(tool.getImage(GetResources("images/ico.gif")));
		setBackground(new Color(166, 88, 205));
		setLayout(null);
		setVisible(true);
		
	
		dataPanel = new DataPanel(this);
		add(dataPanel);
		dataPanel.setBounds(275,55,80,20);
		
		PPanel = new ProprietiesPanel(this);
		add(PPanel);
		PPanel.setBounds(25,425,600,300);
		
		desen = new Desen(this);
		add(desen);
		desen.setBounds(25,425,600,300);
		
		resize(ww,hh);
		move(0,0);
		setVisible(true);
	}
	
	public java.net.URL GetResources(String s){return this.getClass().getResource(s);}
}

class DataPanel extends Panels{
	TextField tf;
	Button start;
	
public DataPanel(proiectAAV buffon) {
	super(buffon.backg);
	this.f=buffon.f;
}

public void paint(Graphics g){
	super.paint(g);
	g.setFont(f);
	int w=50;
	g.setColor(col1);
	g.drawString("a11:", w, 25);
			tf = new TextField("");
			tf.setForeground(Color.blue);
			tf.setFont(f);
			tf.requestFocus();
			add(tf);
			tf.setBounds(w+300,25,80,20);
	g.drawString("a12:", w, 50);
			tf = new TextField("");
			tf.setForeground(Color.blue);
			tf.setFont(f);
			tf.requestFocus();
			add(tf);
			tf.setBounds(w+300,50,80,20);
	g.drawString("a13:", w, 75);
			tf = new TextField("");
			tf.setForeground(Color.blue);
			tf.setFont(f);
			tf.requestFocus();
			add(tf);
			tf.setBounds(w+300,75,80,20);
	g.drawString("a22:", w, 100);
			tf = new TextField("");
			tf.setForeground(Color.blue);
			tf.setFont(f);
			tf.requestFocus();
			add(tf);
			tf.setBounds(w+300,100,80,20);
	g.drawString("a23:", w, 125);
			tf = new TextField("");
			tf.setForeground(Color.blue);
			tf.setFont(f);
			tf.requestFocus();
			add(tf);
			tf.setBounds(w+300,125,80,20);
	g.drawString("a33:", w, 150);
			tf = new TextField("");
			tf.setForeground(Color.blue);
			tf.setFont(f);
			tf.requestFocus();
			add(tf);
			tf.setBounds(w+300,150,80,20);
			
	start = new Button("DESENEAZA");
	add(start);
	start.setBounds(w+300,175,80,20);
			
}
}

class ProprietiesPanel extends Panels{
	
public ProprietiesPanel(proiectAAV buffon) {
	super(buffon.backg);
	this.f=buffon.f;
}

public void paint(Graphics g){
	super.paint(g);
	g.setFont(f);
	int w=50;
	g.setColor(col1);
	delta=a11*a22-a12*a12;
	DELTA=a11*a22*a33+a12*a23*a13+a12*a23*a13-a13*a22*a13-a12*a12*a33-a23*a23*a11;
	yCentru=(-a23*a11+a13*a12)/(a11*a22-a12*a12);
	xCentru=(-a13-a12*yCentru)/a11;
	I=a11+a22+a33;
	DELTA1=a11*a22-a12*a12+a11*a33-a13*a13+a22*a33-a23*a23;
	g.drawString("Valorile determinantilor sunt:", w, 25);
	g.drawString("delta" + delta, w + 300, 25);
	g.drawString("DELTA" + DELTA, w + 300, 50);
	g.drawString("Coordonatele centrului C(" + xCentru + " , " + yCentru +" )", w + 300, 75);
	if(	delta>0 ) 
	{	if(DELTA!=0)
				if(I*DELTA<0)
					g.drawString("Tipul conicei este elipsa nedegenerata",w+300,100);
				else if(I*DELTA>0)
						g.drawString("Tipul conicei este elipsa imaginara nedegenerata",w+300,100);
					else 
						g.drawString("Tipul conicei este eliptic degenerat",w+300,100);
		caz=1;
	}
	else if(delta<0)
			{if(DELTA!=0)
				g.drawString("Tipul conicei este hiperbola nedegenerata",w+300,100);
			else g.drawString("Tipul conicei este hiperbola degenerata",w+300,100);
			caz=2;}
		else{
				if(DELTA!=0)
					g.drawString("Tipul conicei este parabola nedegenerata",w+300,100);
				else if(DELTA1<0)
						g.drawString("Tipul conicei este drepte paralele degenerate",w+300,100);
					else if(DELTA1==0)
							g.drawString("Tipul conicei este dreapta dubla degenerata",w+300,100);
						else 
							g.drawString("Tipul conicei este dreapte imaginare pararlele degenerate",w+300,100);
				caz=3;
			}
					
}
}

class Desen extends Panels{
	proiectAAV buffon;
	int w=1360,h=768;
	
	public Desen(proiectAAV buffon){
		super(buffon.backg);
		this.buffon=buffon;
	}
	
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(new Color(0,0,0));
		g.drawLine(w/2,1,w/2,h);
		g.drawLine(1,h/2,w,h/2);
		g.fillOval(w/2,h/2,1,1);
		g.setColor(new Color(0,0,255));
		g.fillOval(xCentru,yCentru,1,1);
		
		
		
		if(caz==1)
		{
			int I1,I2,a,b;
			I1=a11+a22;
			I2=a11*a22-a12*a12;
			double d=I1*I1-4*I2;
			a=(int)(I1-Math.pow(d,1.0/2))/2;
			b=(int)(I1+Math.pow(d,1.0/2))/2;
			g.drawOval(xCentru,yCentru,a,b);
		}else 
			if(caz==2)
			{
				 //caz hiperbola
			 
			}
			else if(caz==3) 
			{ 
				 //caz parabola
			}
			else g.drawString("Reintroduceti datele.");
		}	
}

class Panels extends Panel{
	Font f;
	Color col1 = Color.white;
	public int delta,DELTA,xCentru,yCentru,I,DELTA1;
	public int a11,a12,a13,a22,a23,a33;
	int caz=0;
	public Image im, im1;
	public Panels(Image im){this.im=im;}
	public void update(Graphics g){paint(g);}
	public void paint(Graphics g){
		super.paint(g);
		Dimension dimension = size();
		im1 = createImage(dimension.width, dimension.height);
		pan(im1.getGraphics());
		g.drawImage(im1, 0, 0, this);
	}
	
	public void pan(Graphics g){
		Dimension dimension = size();
		int w = dimension.width;
		int h = dimension.height;
		Color color = getBackground();
		g.setColor(color);
		g.fillRect(0,0,w,h);
		for(int k = 0; k<w;k+=im.getWidth(this))
			for(int l = 0; l<h; l+=im.getHeight(this))
				g.drawImage(im, k, l, this);
		g.setColor(color.brighter());
		g.drawRect(1,1, w-2, h-2);
		g.setColor(color.darker());
		g.drawRect(0,0,w-2, h-2);
	}
}
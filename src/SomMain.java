import javax.swing.ImageIcon;
import javax.swing.UIManager;


public class SomMain {

	public static void main(String[] args) {

		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch(Exception e){

		}

		SomFrame f = new SomFrame();
		
		f.setVisible(true);
	}
}

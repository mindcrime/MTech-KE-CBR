/**
 * Travel Recommender example for the jCOLIBRI2 framework. 
 * @author Juan A. Recio-Garc�a.
 * GAIA - Group for Artificial Intelligence Applications
 * http://gaia.fdi.ucm.es
 * 25/07/2006
 */
package jcolibri.mtechkecbr.RecipeRecommender.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.UIManager;

import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.mtechkecbr.RecipeRecommender.RecipeRecommender;
import jcolibri.method.retrieve.RetrievalResult;
import jcolibri.util.FileIO;

/**
 * Adaptation dialgo
 * @author Juan A. Recio-Garcia
 * @version 1.0
 */
public class AutoAdaptationDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	JLabel image;
	
	JCheckBox servings_cooktime;
	JCheckBox difficulty_preptime;
	
	
	ArrayList<RetrievalResult> cases;
	int currentCase;
	Collection<CBRCase> selectedcases;
	CBRQuery query;
	
	public AutoAdaptationDialog(JFrame main)
	{
		super(main,true);
		configureFrame();
	}
	
	private void configureFrame()
	{
		try
		{
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1)
		{
		}
		
		this.setTitle("Adaptation");

		
		image = new JLabel();
		image.setIcon(new ImageIcon(FileIO.findFile("jcolibri/mtechkecbr/RecipeRecommender/gui/step4.png")));
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(image, BorderLayout.WEST);
		
		
		/**********************************************************/
		JPanel panel = new JPanel();
		panel.setLayout(new SpringLayout());
		
		panel.add( servings_cooktime = new JCheckBox("Direct proportion between \"Serving size\" and \"Cooking time"));
		panel.add( difficulty_preptime = new JCheckBox("Direct proportion between \"Difficulty level\" and \"Prep time"));
		
		Utils.makeCompactGrid(panel,
                2, 1, //rows, cols
                20, 20,        //initX, initY
                30, 10);       //xPad, yPad
		
		JPanel panelAux = new JPanel();
		panelAux.setLayout(new BorderLayout());
		panelAux.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		panelAux.add(panel,BorderLayout.NORTH);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new BorderLayout());
		
		JButton ok = new JButton("Adapt Cases using Direct Proportions >>");
		ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				next();
			}
		});
		buttons.add(ok,BorderLayout.CENTER);
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					RecipeRecommender.getInstance().postCycle();
				} catch (Exception ex) {
					org.apache.commons.logging.LogFactory.getLog(RecipeRecommender.class).error(ex);
				}
				System.exit(-1);
			}
		});
		buttons.add(exit,BorderLayout.WEST);
		
		panelAux.add(buttons, BorderLayout.SOUTH);
		this.getContentPane().add(panelAux, BorderLayout.CENTER);
		
		/**********************************************************/
		
		
		this.pack();
		this.setSize(600, this.getHeight());
		this.setResizable(false);
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((screenSize.width - this.getWidth()) / 2,
			(screenSize.height - this.getHeight()) / 2, 
			getWidth(),
			getHeight());
	}
	
	void next()
	{
		this.setVisible(false);
	}
	
	
	public boolean adapt_Servings_Cooktime()
	{
		return this.servings_cooktime.isSelected();
	}

	public boolean adapt_Difficulty_Preptime()
	{
		return this.difficulty_preptime.isSelected();
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AutoAdaptationDialog qf = new AutoAdaptationDialog(null);
		qf.setVisible(true);
		System.out.println("Bye");
	}

	

}

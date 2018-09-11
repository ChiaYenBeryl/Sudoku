package Sudoku;
import java.awt.*; // Uses AWT's Layout Managers
import java.awt.event.*; // Uses AWT's Event Handlers
import java.net.URL;

import javax.swing.*; // Uses Swing's Container/Components
import java.util.Random;


/**
 * The Sudoku game. To solve the number puzzle, each row, each column, and each
 * of the nine 3กั3 sub-grids shall contain all of the digits from 1 to 9
 */
public class Sudoku extends JFrame {
	// Name-constants for the game properties
	public static final int GRID_SIZE = 9; // Size of the board
	public static final int SUBGRID_SIZE = 3; // Size of the sub-grid

	// Name-constants for UI control (sizes, colors and fonts)
	public static final int CELL_SIZE = 60; // Cell width/height in pixels
	public static final int CANVAS_WIDTH = CELL_SIZE * GRID_SIZE;
	public static final int CANVAS_HEIGHT = CELL_SIZE * GRID_SIZE;
	public static int LEVEL=1;
	// Board width/height in pixels
	public static final Color OPEN_CELL_BGCOLOR = Color.YELLOW;
	public static final Color OPEN_CELL_TEXT_YES = new Color(0, 255, 0); // RGB
	public static final Color OPEN_CELL_TEXT_NO = Color.RED;
	public static final Color CLOSED_CELL_BGCOLOR = new Color(240, 240, 240); // RGB
	public static final Color CLOSED_CELL_TEXT = Color.BLACK;
	public static final Font FONT_NUMBERS = new Font("Century Gothic", Font.BOLD, 20);
	
	

	// The game board composes of 9x9 JTextFields,
	// each containing String "1" to "9", or empty String
	private JTextField[][] tfCells = new JTextField[GRID_SIZE][GRID_SIZE];

	// Puzzle to be solved and the mask (which can be used to control the
	// difficulty level).
	// Hardcoded here. Extra credit for automatic puzzle generation
	// with various difficulty levels.
	private static int[][] puzzle = { { 5, 3, 4, 6, 7, 8, 9, 1, 2 }, { 6, 7, 2, 1, 9, 5, 3, 4, 8 },
			{ 1, 9, 8, 3, 4, 2, 5, 6, 7 }, { 8, 5, 9, 7, 6, 1, 4, 2, 3 }, { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
			{ 7, 1, 3, 9, 2, 4, 8, 5, 6 }, { 9, 6, 1, 5, 3, 7, 2, 8, 4 }, { 2, 8, 7, 4, 1, 9, 6, 3, 5 },
			{ 3, 4, 5, 2, 8, 6, 1, 7, 9 } };
	// For testing, open only 2 cells.
	static boolean[][] masks = { { false, false, false, false, false, true, false, false, false },
			{ false, false, false, false, false, false, false, false, true },
			{ false, false, false, false, false, false, false, false, false },
			{ false, false, false, false, false, false, false, false, false },
			{ false, false, false, false, false, false, false, false, false },
			{ false, false, false, false, false, false, false, false, false },
			{ false, false, false, false, false, false, false, false, false },
			{ false, false, false, false, false, false, false, false, false },
			{ false, false, false, false, false, false, false, false, false } };	

	/**
	 * Constructor to setup the game and the UI Components
	 */
	public Sudoku() {
		// Container cp = getContentPane();

		JPanel cp = new JPanel();
		cp.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE,1,1)); // 9x9 GridLayout
		//status bar sb
		JTextField sb = new JTextField();
		int unsolved = 0;
		 for (int row = 0; row < GRID_SIZE; ++row) {
	 			for (int col = 0; col < GRID_SIZE; ++col) {
	 				if (masks[row][col]) {
	 					unsolved++;
	 				}
	 			}
	 		}
		
		sb.setText(unsolved + " to solve. " );
		sb.setLayout(new BorderLayout());
		sb.setEditable(false);

		add(cp, BorderLayout.CENTER);
		add(sb, BorderLayout.SOUTH);
		 
		//menu bar mb
		JMenuBar mb = new JMenuBar();
		JMenuItem ng= new JMenuItem("New Game");
		JMenuItem reset= new JMenuItem("Reset");
		JMenuItem hint=new JMenuItem("Answer");
		mb.add(ng);
		mb.add(reset);
		mb.add(hint);
		ng.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				e.getSource();
				String[] difficulty = {"Super Easy", "Standard","Hell"};
				int SelectedLevel = JOptionPane.showOptionDialog(null, "Choose Difficulty:", 
									"New Game",
									JOptionPane.YES_NO_CANCEL_OPTION,
									JOptionPane.PLAIN_MESSAGE,
									null,
									difficulty, 
									difficulty[0]);
				
				if (SelectedLevel == JOptionPane.YES_OPTION){
					//new Masks("Super Easy");	
					new Masks(20);
					LEVEL=1;
				}else if (SelectedLevel == JOptionPane.NO_OPTION){
					//new Masks("Standard");
					new Masks(40);
					LEVEL=1;
					
				}else if (SelectedLevel == JOptionPane.CANCEL_OPTION){
					//new Masks("Hell");
					new Masks(60);
					LEVEL=1;
					
				}
				BGMusic.stop();
				main(null);				
			}		
		});
		reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				e.getSource();
				for (int row = 0; row < GRID_SIZE; row++){
					for(int col = 0; col < GRID_SIZE;col++ ){
						if(tfCells[row][col].isEditable()){
							tfCells[row][col].setBackground(OPEN_CELL_BGCOLOR);
							tfCells[row][col].setText("");
						}else{
							tfCells[row][col].setBackground(CLOSED_CELL_BGCOLOR);
						}
					}
				}
				for (int row = 3; row < 6; ++row) {
					for (int col = 0; col < 3; ++col) {
						if(!tfCells[row][col].isEditable())
						tfCells[row][col].setBackground(Color.WHITE);
					}
				}
				for (int row = 6; row < 9; ++row) {
					for (int col = 3; col < 6; ++col) {
						if(!tfCells[row][col].isEditable())
						tfCells[row][col].setBackground(Color.WHITE);
					}
				}
				for (int row = 0; row < 3; ++row) {
					for (int col = 3; col < 6; ++col) {
						if(!tfCells[row][col].isEditable())
						tfCells[row][col].setBackground(Color.WHITE);
					}
				}
				for (int row = 3; row < 6; ++row) {
					for (int col = 6; col < 9; ++col) {
						if(!tfCells[row][col].isEditable())
						tfCells[row][col].setBackground(Color.WHITE);
					}
				}
				
			}
			
		});
		hint.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				e.getSource();
					for (int row = 0; row < GRID_SIZE; row++){
						for(int col = 0; col < GRID_SIZE;col++ ){
							if(tfCells[row][col].isEditable()){							
								tfCells[row][col].setText(""+puzzle[row][col]);
								tfCells[row][col].setBackground(OPEN_CELL_TEXT_YES);
								masks[row][col]=false;
							}else{
								tfCells[row][col].setBackground(CLOSED_CELL_BGCOLOR);
								
							}
						}
					}
					for (int row = 3; row < 6; ++row) {
						for (int col = 0; col < 3; ++col) {
							if(!tfCells[row][col].isEditable())
							tfCells[row][col].setBackground(Color.WHITE);
						}
					}
					for (int row = 6; row < 9; ++row) {
						for (int col = 3; col < 6; ++col) {
							if(!tfCells[row][col].isEditable())
							tfCells[row][col].setBackground(Color.WHITE);
						}
					}
					for (int row = 0; row < 3; ++row) {
						for (int col = 3; col < 6; ++col) {
							if(!tfCells[row][col].isEditable())
							tfCells[row][col].setBackground(Color.WHITE);
						}
					}
					for (int row = 3; row < 6; ++row) {
						for (int col = 6; col < 9; ++col) {
							if(!tfCells[row][col].isEditable())
							tfCells[row][col].setBackground(Color.WHITE);
						}
					}
				
				}
			
		});
		add(mb, BorderLayout.NORTH);
		//bgmusic
		new BGMusic();
		

		// Allocate a common listener as the ActionEvent listener for all the
		// JTextFields
		InputListener listener = new InputListener();

		// Construct 9x9 JTextFields and add to the content-pane
		for (int row = 0; row < GRID_SIZE; ++row) {
			for (int col = 0; col < GRID_SIZE; ++col) {
				tfCells[row][col] = new JTextField(); // Allocate element of
														// array
				cp.add(tfCells[row][col]); // ContentPane adds JTextField
				if (masks[row][col]) {
					tfCells[row][col].setText(""); // set to empty string
					tfCells[row][col].setEditable(true);
					tfCells[row][col].setBackground(OPEN_CELL_BGCOLOR);

					// Add ActionEvent listener to process the input
					tfCells[row][col].addActionListener(listener);
				} else {
					tfCells[row][col].setText(puzzle[row][col] + "");
					tfCells[row][col].setEditable(false);
					tfCells[row][col].setBackground(CLOSED_CELL_BGCOLOR);
					tfCells[row][col].setForeground(CLOSED_CELL_TEXT);
				}
				// Beautify all the cells
				tfCells[row][col].setHorizontalAlignment(JTextField.CENTER);
				tfCells[row][col].setFont(FONT_NUMBERS);
			}
		}
		
		for (int row = 3; row < 6; ++row) {
			for (int col = 0; col < 3; ++col) {
				if(!tfCells[row][col].isEditable())
				tfCells[row][col].setBackground(Color.WHITE);
			}
		}
		for (int row = 6; row < 9; ++row) {
			for (int col = 3; col < 6; ++col) {
				if(!tfCells[row][col].isEditable())
				tfCells[row][col].setBackground(Color.WHITE);
			}
		}
		for (int row = 0; row < 3; ++row) {
			for (int col = 3; col < 6; ++col) {
				if(!tfCells[row][col].isEditable())
				tfCells[row][col].setBackground(Color.WHITE);
			}
		}
		for (int row = 3; row < 6; ++row) {
			for (int col = 6; col < 9; ++col) {
				if(!tfCells[row][col].isEditable())
				tfCells[row][col].setBackground(Color.WHITE);
			}
		}
		
		
		
		// Set the size of the content-pane and pack all the components
		// under this container.
		cp.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		pack();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Handle window closing
		setTitle("Sudoku");
		setVisible(true);
	}

	/** The entry main() entry method */
	public static void main(String[] args) {
		// [TODO 1] (Now)
		// Check Swing program template on how to run the constructor
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				//randomize puzzle
				Random r = new Random();
				int ranNum = r.nextInt(8)+1;
				for (int row = 0; row <9; ++row) {
					for (int col = 0; col < 9; ++col) {
						puzzle[row][col] = (puzzle[row][col] + ranNum)%9 +1;			
					}
				}
				
				
				new Sudoku();
			}
			
		});
		
		
	}

	// Define the Listener Inner Class
	// Inner class to be used as ActionEvent listener for ALL JTextFields
	private class InputListener implements ActionListener {

		@Override
         public void actionPerformed(ActionEvent e) {
            // All the 9*9 JTextFileds invoke this handler. We need to determine
            // which JTextField (which row and column) is the source for this invocation.
            int rowSelected = -1;
            int colSelected = -1;
    
            // Get the source object that fired the event
            JTextField source = (JTextField)e.getSource();
            // Scan JTextFileds for all rows and columns, and match with the source object
            boolean found = false;
            for (int row = 0; row < GRID_SIZE && !found; ++row) {
               for (int col = 0; col < GRID_SIZE && !found; ++col) {
                  if (tfCells[row][col] == source) {
                     rowSelected = row;
                     colSelected = col;
                     found = true;  // break the inner/outer loops
                  }
               }
            }
           

           
       		
            /*
             * [TODO 5]
             * 1. Get the input String via tfCells[rowSelected][colSelected].getText()
             * 2. Convert the String to int via Integer.parseInt().
             * 3. Assume that the solution is unique. Compare the input number with
             *    the number in the puzzle[rowSelected][colSelected].  If they are the same,
             *    set the background to green (Color.GREEN); otherwise, set to red (Color.RED).
             */
            
            //COLORSSSSSS
            int Entered=Integer.parseInt(tfCells[rowSelected][colSelected].getText());
            //for correct input
            if (puzzle[rowSelected][colSelected]==Entered){
    			masks[rowSelected][colSelected]=false;
    			new Sound("COMPLETE.wav");
    			
      		}
            
            for (int row = 0; row < GRID_SIZE; row++){
            	for(int col = 0; col < GRID_SIZE;col++ ){
            		
            		
            		
        			//change color if  wrong number typed for same column
        			if (puzzle[row][colSelected]==Entered && !tfCells[row][colSelected].isEditable()){
            			tfCells[row][colSelected].setBackground(OPEN_CELL_TEXT_NO);      
            			tfCells[rowSelected][colSelected].setBackground(OPEN_CELL_TEXT_NO);
            			if(masks[row][colSelected] && tfCells[row][colSelected].isEditable())tfCells[row][colSelected].setBackground(OPEN_CELL_BGCOLOR);	
            			masks[rowSelected][colSelected]=true;
            			new Sound("CORRECT.wav");
            			
               		}//for same row
            		if (puzzle[rowSelected][col]==Entered&& !tfCells[rowSelected][col].isEditable()){
            			tfCells[rowSelected][col].setBackground(OPEN_CELL_TEXT_NO);		
            			tfCells[rowSelected][colSelected].setBackground(OPEN_CELL_TEXT_NO);
            			if(masks[rowSelected][col] && tfCells[rowSelected][col].isEditable())tfCells[rowSelected][col].setBackground(OPEN_CELL_BGCOLOR);
            			masks[rowSelected][colSelected]=true;
            			new Sound("CORRECT.wav");
            			
            		}
            		//change back the color of cells if another wrong input is entered        		
            		if(!masks[row][colSelected] && !tfCells[row][colSelected].isEditable())tfCells[row][colSelected].setBackground(CLOSED_CELL_BGCOLOR);	
        			if(!masks[rowSelected][col] && !tfCells[rowSelected][col].isEditable())tfCells[rowSelected][col].setBackground(CLOSED_CELL_BGCOLOR);
        			if(masks[row][colSelected] && tfCells[row][colSelected].isEditable())tfCells[row][colSelected].setBackground(OPEN_CELL_BGCOLOR);	
        			if(masks[rowSelected][col] && tfCells[rowSelected][col].isEditable())tfCells[rowSelected][col].setBackground(OPEN_CELL_BGCOLOR);

        			
            	}
            }
            
          
            
            //colors for white grids
            for (int row = 3; row < 6; ++row) {
    			for (int col = 0; col < 3; ++col) {
    				if(!tfCells[row][col].isEditable())
    				tfCells[row][col].setBackground(Color.WHITE);
    				if (puzzle[row][colSelected]==Entered && !tfCells[row][colSelected].isEditable()){//for another wrong input
            			tfCells[row][colSelected].setBackground(OPEN_CELL_TEXT_NO);      
            			tfCells[rowSelected][colSelected].setBackground(OPEN_CELL_TEXT_NO);
            			if(masks[row][colSelected] && tfCells[row][colSelected].isEditable())tfCells[row][colSelected].setBackground(OPEN_CELL_BGCOLOR);	
            			masks[rowSelected][colSelected]=true;
            			
               		}
            		if (puzzle[rowSelected][col]==Entered&& !tfCells[rowSelected][col].isEditable()){
            			tfCells[rowSelected][col].setBackground(OPEN_CELL_TEXT_NO);		
            			tfCells[rowSelected][colSelected].setBackground(OPEN_CELL_TEXT_NO);
            			if(masks[rowSelected][col] && tfCells[rowSelected][col].isEditable())tfCells[rowSelected][col].setBackground(OPEN_CELL_BGCOLOR);
               			masks[rowSelected][colSelected]=true;
            		}
    			}
    		}
    		for (int row = 6; row < 9; ++row) {
    			for (int col = 3; col < 6; ++col) {
    				if(!tfCells[row][col].isEditable())
    				tfCells[row][col].setBackground(Color.WHITE);
    				if (puzzle[row][colSelected]==Entered && !tfCells[row][colSelected].isEditable()){//for another wrong input
            			tfCells[row][colSelected].setBackground(OPEN_CELL_TEXT_NO);      
            			tfCells[rowSelected][colSelected].setBackground(OPEN_CELL_TEXT_NO);
            			if(masks[row][colSelected] && tfCells[row][colSelected].isEditable())tfCells[row][colSelected].setBackground(OPEN_CELL_BGCOLOR);	
            			masks[rowSelected][colSelected]=true;
            			
               		}
            		if (puzzle[rowSelected][col]==Entered&& !tfCells[rowSelected][col].isEditable()){
            			tfCells[rowSelected][col].setBackground(OPEN_CELL_TEXT_NO);		
            			tfCells[rowSelected][colSelected].setBackground(OPEN_CELL_TEXT_NO);
            			if(masks[rowSelected][col] && tfCells[rowSelected][col].isEditable())tfCells[rowSelected][col].setBackground(OPEN_CELL_BGCOLOR);
                   		masks[rowSelected][colSelected]=true;
            		}
    			}
    		}
    		for (int row = 0; row < 3; ++row) {
    			for (int col = 3; col < 6; ++col) {
    				if(!tfCells[row][col].isEditable())
    				tfCells[row][col].setBackground(Color.WHITE);
    				if (puzzle[row][colSelected]==Entered && !tfCells[row][colSelected].isEditable()){//for another wrong input
            			tfCells[row][colSelected].setBackground(OPEN_CELL_TEXT_NO);      
            			tfCells[rowSelected][colSelected].setBackground(OPEN_CELL_TEXT_NO);
            			if(masks[row][colSelected] && tfCells[row][colSelected].isEditable())tfCells[row][colSelected].setBackground(OPEN_CELL_BGCOLOR);	
            			masks[rowSelected][colSelected]=true;
            			
               		}
            		if (puzzle[rowSelected][col]==Entered&& !tfCells[rowSelected][col].isEditable()){
            			tfCells[rowSelected][col].setBackground(OPEN_CELL_TEXT_NO);		
            			tfCells[rowSelected][colSelected].setBackground(OPEN_CELL_TEXT_NO);
            			if(masks[rowSelected][col] && tfCells[rowSelected][col].isEditable())tfCells[rowSelected][col].setBackground(OPEN_CELL_BGCOLOR);
            			masks[rowSelected][colSelected]=true;
            		}
    			}
    		}
    		for (int row = 3; row < 6; ++row) {
    			for (int col = 6; col < 9; ++col) {
    				if(!tfCells[row][col].isEditable())
    				tfCells[row][col].setBackground(Color.WHITE);
    				if (puzzle[row][colSelected]==Entered && !tfCells[row][colSelected].isEditable()){//for another wrong input
            			tfCells[row][colSelected].setBackground(OPEN_CELL_TEXT_NO);      
            			tfCells[rowSelected][colSelected].setBackground(OPEN_CELL_TEXT_NO);
            			if(masks[row][colSelected] && tfCells[row][colSelected].isEditable())tfCells[row][colSelected].setBackground(OPEN_CELL_BGCOLOR);	
            			masks[rowSelected][colSelected]=true;
                   		}
            		if (puzzle[rowSelected][col]==Entered&& !tfCells[rowSelected][col].isEditable()){
            			tfCells[rowSelected][col].setBackground(OPEN_CELL_TEXT_NO);		
            			tfCells[rowSelected][colSelected].setBackground(OPEN_CELL_TEXT_NO);
            			if(masks[rowSelected][col] && tfCells[rowSelected][col].isEditable())tfCells[rowSelected][col].setBackground(OPEN_CELL_BGCOLOR);
            			masks[rowSelected][colSelected]=true;
            		}
            		
    			}
    		}
    		  
    		//for all correct cells 
    		
            for (int row = 0; row < GRID_SIZE; row++){
            	for(int col = 0; col < GRID_SIZE;col++ ){
            		if(tfCells[row][col].isEditable() && !masks[row][col]){
            			tfCells[row][col].setBackground(OPEN_CELL_TEXT_YES);
              		}
            	}
            }	

            //for completion of puzzle 
            boolean completed=true;
            for (int row = 0; row < GRID_SIZE; row++){
            	for(int col = 0; col < GRID_SIZE;col++ ){
            		if (masks[row][col])
            			completed=false;//if any masks[][] is true, the puzzle is not solved
            	}
            }
            if(completed){
            	new Sound("COMPLETE.wav");
            	
            	JOptionPane.showMessageDialog(null, "Congratulation!"); 
            	
            }
         }
	}
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg1;

/**
 *
 * @author AnhTam
 */
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BombermanGUI
{
	private JFrame frame;
	private static JLabel[][] squares; 
	private static Map board; 
	private static Player[] players;
	private static int AIDifficulty;
	
	public BombermanGUI() throws Exception
	{
		frame = new JFrame( "Bomberman" );
		JPanel panel = (JPanel) frame.getContentPane();
		panel.setLayout( new GridLayout( 13, 15 ) );
		squares = new JLabel[13][15];
		for( int row = 0; row < 13; row++ )
		{
			for( int column = 0; column < 15; column++ )
			{
				squares[row][column] = new JLabel();
				panel.add( squares[row][column] );
			}
		}
		
		frame.setContentPane( panel );
		frame.setSize( 750, 650 );
		frame.setVisible( true );
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		String[] choices = { "Nomarl", "AI" };
		AIDifficulty = JOptionPane.showOptionDialog(
		                               null                     
		                             , "What level do you want the AI to be?"     
		                             , "Choose the difficulty"             
		                             , JOptionPane.YES_NO_OPTION
		                             , JOptionPane.PLAIN_MESSAGE 
		                             , null                     
		                             , choices                    
		                             , null
		                           );
		AIDifficulty++;
		players = new Player[4];
                players[0] = new Player( 0, 0 );
                players[1] = new Player( 0, 0 );
                players[2] = new Player( 0, 0 );
                players[3] = new Player( 0, 0 );
		board = new Map();
		playerSetup();
	}
	public void addListener( BombermanListener listener )
	{
		frame.addKeyListener( listener );
	}
	
	public static void setSquare( int row, int column, Map.Piece type )
	{
		String s = "Images/player-down.jpg";
		if( type == Map.Piece.BLOCK )
			s = "Images/block.jpg";
		else if( type == Map.Piece.BOMB )
			s = "Images/bomb.png";
		else if( type == Map.Piece.BLOCK )
			s = "Images/block.jpg";
		else if ( type == Map.Piece.COMPUTER )
		{
			int i;
			for(i = 0;i<3;i++){
				if( row == players[i].getRow() && column == players[i].getColumn() && players[i].isComputer() )
				{
					if( players[i].getDx() == 1 )
						s = "Images/computer-right.jpg";
					else if( players[i].getDx() == -1 )
						s = "Images/computer-left.jpg";
					else if( players[i].getDy() == 1 )
						s = "Images/computer-down.jpg";
					else
						s = "Images/computer-up.jpg";
				}
			}}
		else if( type == Map.Piece.CRATE )
			s = "Images/crate.jpg";
		else if( type == Map.Piece.EXPLOSION )
			s = "Images/explosion.jpg";
		else if( type == Map.Piece.NOTHING )
			s = "Images/nothing.jpg";
		else if( type == Map.Piece.PLAYER )
		{
				if( row == players[3].getRow() && column == players[3].getColumn() && !players[3].isComputer() )
				{
					if( players[3].getDx() == 1 )
						s = "Images/player-right.jpg";
					else if( players[3].getDx() == -1 )
						s = "Images/player-left.jpg";
					else if( players[3].getDy() == 1 )
						s = "Images/player-down.jpg";
					else
						s = "Images/player-up.jpg";
				}
			}
		
		squares[row][column].setIcon( new ImageIcon( s ) );
	}
	
	public static void playerSetup()
	{
		int playerCount = 0; 
		
		for( int r = 0; r < 13; r++ )
		{
			for( int c = 0; c < 15; c++ )
			{
				if( board.getBoard()[r][c] == Map.Piece.PLAYER )
				{
					players[playerCount].setRow( r );
					players[playerCount].setColumn( c );
					players[playerCount].setComputer( false );
					
					players[playerCount].time.bombs.makeNew();
					
					playerCount++;
				}
				else if( board.getBoard()[r][c] == Map.Piece.COMPUTER )
				{
					players[playerCount].setRow( r );
					players[playerCount].setColumn( c );
					players[playerCount].setComputer( true );
					players[playerCount].time.bombs.makeNew();
					players[playerCount].setAI( AIDifficulty );
					AITimer ai = players[playerCount].getAITime();
					ai.setPlayer( players[playerCount] );
					players[playerCount].setAITime( ai );
					
					playerCount++;
				}
			}
		}
	}
	public static void resetBoard() throws Exception
	{
		board = new Map();
		playerSetup();
	}
	public void setBoard(Map board) {
		this.board = board;
	}
	public static Map getBoard() {
		return board;
	}
	public void setPlayers(Player[] players) {
		this.players = players;
	}
	public static Player[] getPlayers() {
		return players;
	}
}
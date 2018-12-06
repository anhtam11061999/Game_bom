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
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * Player class
 * Keeps track of information for one player
 */
public class Player
{
	private int row;
	private int column;
	private int score;
	private boolean computer;
	private int AI = 2;
	private AITimer AITime;
	public BombTimer time;
	private int dx;
	private int dy;
	private int keyMove;
	public Player( int r, int c )
	{
		row = r;
		column = c;
		time = new BombTimer( new Bomb( 0, 0, -1 ) );
		AITime = new AITimer( this );
		dx = 0;
		dy = 0;
	}
	public void move( int direction ) throws Exception
	{
		dx = dy = 0;
		if( direction == KeyEvent.VK_UP )
			dy = -1;
		else if( direction == KeyEvent.VK_DOWN )
			dy = 1;
		else if( direction == KeyEvent.VK_RIGHT )
			dx = 1;
		else if( direction == KeyEvent.VK_LEFT )
			dx = -1;
		Map.Piece[][] temp = BombermanGUI.getBoard().getBoard();
		if( row + dy >= 0 && row + dy < 13 && column + dx >= 0 && column + dx < 15 
				&& temp[row + dy][column + dx] != Map.Piece.BLOCK 
				&& temp[row + dy][column + dx] != Map.Piece.CRATE )
		{
			if( temp[ row + dy ][ column + dx ] != Map.Piece.EXPLOSION )
			{
				boolean set = false; 
				Player[] p = BombermanGUI.getPlayers();
				for( int i = 0; i < 2; i++ )
				{
					if( p[i].time.bombs.getRow() == row && p[i].time.bombs.getColumn() == column && !p[i].time.bombs.isExploding() )
					{
						temp[row][column] = Map.Piece.BOMB;
						BombermanGUI.setSquare( row, column, Map.Piece.BOMB );
						set = true;
					}
					if( p[i].time.getBombs().isExploding() )
					{
						int[] dxx = { 0, 0, 1, 0, -1 };
						int[] dyy = { 0, -1, 0, 1, 0 };
						for( int j = 0; j < dxx.length; j++ )
						{
							if( row == ( p[i].time.bombs.getRow() + dyy[j] ) && column == p[i].time.bombs.getColumn() + dxx[j] )
							{       
                                                                
								temp[row][column] = Map.Piece.EXPLOSION;
								BombermanGUI.setSquare( row + dyy[j], column + dxx[j], Map.Piece.EXPLOSION );
								set = true;
							}
						}
					}
					if( p[i] != this )
					{
						if( p[i].row == row && p[i].column == column )
						{
							if( p[i].isComputer() )
							{
								temp[row][column] = Map.Piece.COMPUTER;
								BombermanGUI.setSquare( row, column, Map.Piece.COMPUTER );
							}
							else
							{
								temp[row][column] = Map.Piece.PLAYER;
								BombermanGUI.setSquare( row, column, Map.Piece.PLAYER );
							}
							set = true;
						}
					}
				}
				if( !set )
				{
					temp[row][column] = Map.Piece.NOTHING;
					BombermanGUI.setSquare( row, column, Map.Piece.NOTHING );
				}
			}
			row += dy;
			column += dx;
		}
		if( computer )
		{
			BombermanGUI.setSquare( row, column, Map.Piece.COMPUTER );
			temp[row][column] = Map.Piece.COMPUTER;
		}
		else
		{
			BombermanGUI.setSquare( row, column, Map.Piece.PLAYER );
			temp[row][column] = Map.Piece.PLAYER;
		}
	}
	public void dropBomb()
	{
		if( time.bombs.getStartTime() == -1 )
		{
			time.bombs.setRow( row );
			time.bombs.setColumn( column );
			time.bombs.setStartTime( System.currentTimeMillis() );
		}
	}
	
	public void randomMove() throws Exception
	{
		int dir = new Random().nextInt(4);
		dx = dy = 0;
		switch( dir )
		{
		case 0:
			keyMove = KeyEvent.VK_UP;
			dy = 1;
			break;
		case 1:
			keyMove = KeyEvent.VK_DOWN;
			dy = -1;
			break;
		case 2:
			keyMove = KeyEvent.VK_RIGHT;
			dx = 1;
			break;
		case 3:
			keyMove = KeyEvent.VK_LEFT;
			dx = -1;
			break;
		default:
			break;
		}
	}
	
	public void AI( int level ) throws Exception
	{
		if( level == 1)
			level1();
		else if( level == 2 )
			level2();
	}
	public void level1() throws Exception
	{
		randomMove();
		move( keyMove );
                if( BombermanGUI.getBoard().getBoard()[row + dy][column + dx] == Map.Piece.PLAYER){
                    JOptionPane.showMessageDialog( null, "You lose!" );
                    BombermanGUI.resetBoard();
                    return;
                }
                        
	}	
//	public void level2() throws Exception
//	{
//		boolean foundMove = false;
//		int[] dxArray = { 0, 1, 0, -1 };
//		int[] dyArray = { -1, 0, 1, 0 };
//		int[] keyMoves = { KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT };
//		int[] keyOpposites = { KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_RIGHT };
//		
//		for( int r = 0; r < 13; r++ )
//		{
//			for( int c = 0; c < 15; c++ )
//			{
//				if( BombermanGUI.getBoard().getBoard()[r][c] == Map.Piece.BOMB )
//				{
//					for( int i = 0; i < dxArray.length; i++ )
//					{
//						if( r == row + dyArray[i] && c == column + dxArray[i] )
//						{
//							dx = -dxArray[i];
//							dy = -dyArray[i];
//							keyMove = keyOpposites[i];
//							foundMove = true;
//							break;
//						}
//					}
//				}
//			}
//		}
//		if( foundMove )
//			move( keyMove );
//		else
//		{
//			randomMove();
//			if( BombermanGUI.getBoard().getBoard()[row + dy][column + dx] != Map.Piece.EXPLOSION )
//			{
//				move( keyMove );
//			}
//		}
//		for( int i = 0; i < dxArray.length; i++ )
//		{
//			if( BombermanGUI.getBoard().getBoard()[row + dyArray[i]][column + dxArray[i]] == Map.Piece.CRATE )
//			{
//				dropBomb();
//				break; 
//			}
//		}
//	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getRow() {
		return row;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public int getColumn() {
		return column;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getScore() {
		return score;
	}
	public void setComputer(boolean computer) {
		this.computer = computer;
	}
	public boolean isComputer() {
		return computer;
	}
	public int getAI() {
		return AI;
	}
	public void setAI(int aI) {
		AI = aI;
	}
	public AITimer getAITime() {
		return AITime;
	}
	public void setAITime(AITimer aITime) {
		AITime = aITime;
	}
	public int getDx() {
		return dx;
	}
	public void setDx(int dx) {
		this.dx = dx;
	}
	public int getDy() {
		return dy;
	}
	public void setDy(int dy) {
		this.dy = dy;
	}
}
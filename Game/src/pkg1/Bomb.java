/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg1;

import javax.swing.JOptionPane;

public class Bomb
{
	private long startTime; 
	private int row; 
	private int column; 
	private boolean exploding; 
	public Bomb( int r, int c, long time )
	{
		
		row = r;
		column = c;
		startTime = time;
		exploding = false;
	}
	
	public int checkState()
	{
		long time;
		int state;
		
		if( exploding )
		{
			time = 1000;
			state = 3;
		}
		else
		{
			time = 1500;
			state = 1;
		}
		
		if( startTime != -1 && System.currentTimeMillis() - startTime > time )
			return state;
		else
			return state - 1;
	}
	public void explode() throws Exception
	{
		Player[] p = BombermanGUI.getPlayers();
		Map.Piece[][] b = BombermanGUI.getBoard().getBoard();
		int[] dx = { 0, 1, 0, -1, 0 };
		int[] dy = { 0, 0, 1, 0, -1 };
		
		for( int i = 0; i < dx.length; i++ )
		{
			if( b[row + dy[i]][column + dx[i]] != Map.Piece.BLOCK )
			{
				if( b[row + dy[i]][column + dx[i]] == Map.Piece.PLAYER )
				{
					JOptionPane.showMessageDialog( null, "You lose!" );
					BombermanGUI.resetBoard();
					return;
				}
				else if( b[row + dy[i]][column + dx[i]] == Map.Piece.COMPUTER )
				{
					JOptionPane.showMessageDialog( null, "You win!" );
					BombermanGUI.resetBoard();
					return;
				}
				b[row + dy[i]][column + dx[i]] = Map.Piece.EXPLOSION;
				BombermanGUI.setSquare(row + dy[i], column + dx[i], Map.Piece.EXPLOSION );
			}
		}
		
		exploding = true;
	}
   
	public void makeNew()
	{
		row = 0;
		column = 0;
		startTime = -1;
		exploding = false;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getStartTime() {
		return startTime;
	}
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

	public void setExploding(boolean exploding) {
		this.exploding = exploding;
	}
	public boolean isExploding() {
		return exploding;
	}
}
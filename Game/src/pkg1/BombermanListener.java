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
import java.awt.event.KeyListener;

public class BombermanListener implements KeyListener
{
	private BombermanGUI gui;
	
	public BombermanListener( BombermanGUI g )
	{
		
		this.gui = g;
		gui.addListener( this );
	}
	public void keyPressed( KeyEvent event )
	{
		int keycode = event.getKeyCode();
		if( keycode == KeyEvent.VK_UP || keycode == KeyEvent.VK_DOWN || 
				keycode == KeyEvent.VK_RIGHT || keycode == KeyEvent.VK_LEFT )
		{
			for( int i = 0; i < 2; i++ )
				if( !gui.getPlayers()[i].isComputer() )
				{
					try
					{
						gui.getPlayers()[i].move( event.getKeyCode() );
					}
					catch( Exception e ) { }
				}
		}
		else if( keycode == KeyEvent.VK_SPACE )
		{
			for( int i = 0; i < 2; i++ )
				if( !gui.getPlayers()[i].isComputer() )
					gui.getPlayers()[i].dropBomb();
		}
	}
	
	public void keyReleased( KeyEvent event )
	{
	}
	public void keyTyped( KeyEvent event )
	{
	}
}
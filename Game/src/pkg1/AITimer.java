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
import java.util.Timer;
import java.util.TimerTask;
public class AITimer extends Timer
{
	Player player;
	Task t;
	public AITimer( Player p )
	{
		super();
		player = p;
		t = new Task();
		scheduleAtFixedRate( t, 0, 300 );
	}
	public class Task extends TimerTask
	{
		public void run()
		{
			try
			{
				player.AI( player.getAI() );
			}
			catch( Exception e ) { }
		}
	}
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
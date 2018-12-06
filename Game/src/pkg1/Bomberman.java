/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg1;

public class Bomberman
{
	public static void main( String[] args ) throws Exception
	{
		BombermanGUI gui = new BombermanGUI();
		BombermanListener listener = new BombermanListener( gui );
	}
}
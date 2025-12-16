package com.wheresmyboat;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class WheresMyBoatTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(WheresMyBoat.class);
		RuneLite.main(args);
	}
}
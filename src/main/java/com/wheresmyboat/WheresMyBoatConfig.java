package com.wheresmyboat;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("wheresmyboat")
public interface WheresMyBoatConfig extends Config
{
	
	@ConfigSection(
		name = "Sailing Panel",
		description = "Settings for the Sailing Panel display",
		position = 1
	)
	String sailingPanel = "sailingPanel";

	@ConfigItem(
		keyName = "sailingPanelEnabled",
		name = "Sailing Panel Display",
		description = "Show where your ships are docked in the Sailing Panel when not on a ship.",
		section = "sailingPanel"
	)
	default boolean sailingPanelEnabled()
	{
		return true;
	}

	@ConfigSection(
		name = "World Markers",
		description = "Settings for World Map display",
		position = 1
	)
	String worldMarkers = "worldMarkers";

	@ConfigItem(
		keyName = "worldMarkersEnabled",
		position = 0,
		name = "Ship Markers",
		description = "Show where your ships are docked on the World Map.",
		section = "worldMarkers"
	)
	default boolean worldMarkersEnabled()
	{
		return true;
	}

	@ConfigItem(
		keyName = "snapToEdge",
		position = 1,
		name = "Snap to Edge",
		description = "Snap Ship Markers to map edge.",
		section = "worldMarkers"
	)
	default boolean snapToEdge()
	{
		return false;
	}
}

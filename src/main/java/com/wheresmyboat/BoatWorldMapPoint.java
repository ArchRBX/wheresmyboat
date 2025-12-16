package com.wheresmyboat;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import net.runelite.api.Point;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.worldmap.WorldMapPoint;
import net.runelite.client.util.ImageUtil;

class BoatWorldMapPoint extends WorldMapPoint
{
	private final BufferedImage clueScrollWorldImage;
	private final Point clueScrollWorldImagePoint;

	BoatWorldMapPoint(String boatName, final WorldPoint worldPoint)
	{
		super(worldPoint, null);

		clueScrollWorldImage = ImageUtil.loadImageResource(getClass(), "/boatmarker.png");
 
		clueScrollWorldImagePoint = new Point(
			clueScrollWorldImage.getWidth() / 2,
			clueScrollWorldImage.getHeight() + 10
		);
		
		//this.setSnapToEdge(true); // config?
		//this.setJumpOnClick(true);
		this.setName(boatName);
		this.setImage(clueScrollWorldImage);
		this.setImagePoint(clueScrollWorldImagePoint);
	}
}
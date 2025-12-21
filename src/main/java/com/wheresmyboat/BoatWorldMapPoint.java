package com.wheresmyboat;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import org.apache.commons.text.WordUtils;

import net.runelite.api.Point;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.worldmap.WorldMapPoint;
import net.runelite.client.util.ImageUtil;

class BoatWorldMapPoint extends WorldMapPoint
{
	private final BufferedImage clueScrollWorldImage;
	private final Point clueScrollWorldImagePoint;

	BoatWorldMapPoint(int boatNum, String boatName, final WorldPoint worldPoint, WheresMyBoatConfig config)
	{
		super(worldPoint, null);

		final Font shipFont = FontManager.getDefaultFont().deriveFont(1,8);

		BufferedImage imgSrc = ImageUtil.loadImageResource(getClass(), "/boatmarker.png");
		
		int width = imgSrc.getWidth();

		int srcHeight = imgSrc.getHeight();
		int height = srcHeight + 8;

		clueScrollWorldImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = clueScrollWorldImage.createGraphics();

		Color hueCol = Color.getHSBColor(boatNum / 5.0f,1.0f,1f);

		graphics.drawImage(imgSrc, 0, 8, width, srcHeight, null);

		graphics.setColor(hueCol);
		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.5f));

		graphics.fillRect(0,0,width,height);

		// draw the initials

		String inits = WordUtils.initials(boatName);

		FontRenderContext frc = graphics.getFontRenderContext();
        TextLayout tl = new TextLayout(inits, shipFont, frc);
        Shape shape = tl.getOutline(null);

		Rectangle txtBounds = shape.getBounds();

		AffineTransform transform = graphics.getTransform();
        transform.translate(width/2 - txtBounds.getWidth()/2, txtBounds.getHeight()+1);
        graphics.transform(transform);
		
		graphics.setComposite(AlphaComposite.Src);

		graphics.setColor(new Color(0.0f,0.0f,0.0f,0.75f));
        graphics.setStroke(new BasicStroke(2f));
        graphics.draw(shape);
        graphics.setColor(Color.white);
        graphics.fill(shape);

		graphics.dispose();

		// set up the actual marker

		clueScrollWorldImagePoint = new Point(
			width / 2 + boatNum*2, // staggered by boatNum so they don't overlap each other too hard
			height + 10 + boatNum*2
		);

		if (config.snapToEdge()) {
			this.setSnapToEdge(true);
			this.setJumpOnClick(true);
		}

		this.setName(boatName);
		this.setImage(clueScrollWorldImage);
		this.setImagePoint(clueScrollWorldImagePoint);
	}
}
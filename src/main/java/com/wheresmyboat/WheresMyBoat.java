package com.wheresmyboat;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.FontID;
import net.runelite.client.callback.ClientThread;
import net.runelite.api.GameState;
import net.runelite.api.ScriptID;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.ScriptPostFired;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetPositionMode;
import net.runelite.api.widgets.WidgetType;
import net.runelite.api.gameval.DBTableID;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.worldmap.WorldMapPointManager;

import com.google.gson.Gson;
import com.google.inject.Provides;

import com.wheresmyboat.enums.BoatType;
import com.wheresmyboat.enums.Port;

@Slf4j
@PluginDescriptor(
	name = "Dude, Where's My Boat?",
	description = "Widgets and map markers to keep track of your boats"
)
public class WheresMyBoat extends Plugin
{
	@Inject
	private Client client;
	
	@Inject
	private ClientThread clientThread;
	
	@Inject
	private WheresMyBoatConfig config;
	
	@Inject
	private ConfigManager configManager;
	
	@Inject
	private Gson gson;
	
	@Inject
	private WorldMapPointManager worldMapPointManager;
	
	private Boat[] boats;
	private BoatCache[] boatCache;
	
	private static final String configGroup = "wheresmyboat";
	
	@Override
	protected void startUp() throws Exception
	{
		log.debug("Starting DWMB...");
		
		boats = new Boat[5];
		boatCache = new BoatCache[5];
		
		clientThread.invoke(() -> {
			if(client.getGameState() == GameState.LOGGED_IN) {
				for (int i = 0; i < 5; i++) {
					Boat boat = new Boat(client,i+1);
					boats[i] = boat;
				}
				
				loadBoatCache();
			}
		});
	}
	
	private void loadBoatCache() {
		String boatCacheJSON = configManager.getConfiguration(configGroup, "boat_cache");
		
		if (boatCacheJSON == null || boatCacheJSON.length() <= 0) {
			return;
		}
	}
	
	public void updateBoatCache() {
		for (int i = 0; i < 5; i++) {
			Boat boat = boats[i];
			
			Port port = boat.getPort();
			float health = boat.getHealth();
			
			BoatCache cache = boatCache[i];
			if (cache == null) {
				cache = new BoatCache(port, health);
			}
			else {
				if (port != null) {
					cache.setPort(port);
				}
				
				if (health >= 0) {
					cache.setHealth(health);
				}
			}
		}
		
		saveBoatCache();
	}
	
	private void saveBoatCache() {
		String boatCacheJSON = gson.toJson(boatCache);
		
		configManager.setConfiguration(configGroup, "boat_cache", boatCacheJSON);
	}
	
	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if(gameStateChanged.getGameState() == GameState.LOGGED_IN) {
			for (int i = 0; i < 5; i++) {
				Boat boat = new Boat(client,i+1);
				boats[i] = boat;
			}
		}
	}
	
	@Override
	protected void shutDown() throws Exception
	{
		log.debug("Stopped DWMB.");
	}
	
	@Subscribe
	public void onVarbitChanged(VarbitChanged varbitChanged)
	{
		int id = varbitChanged.getVarbitId();
		
		//update name
		if (id == VarbitID.SAILING_BOAT_1_NAME_1 || id == VarbitID.SAILING_BOAT_1_NAME_2 || id == VarbitID.SAILING_BOAT_1_NAME_3)
			boats[0].updateName();
		if (id == VarbitID.SAILING_BOAT_2_NAME_1 || id == VarbitID.SAILING_BOAT_2_NAME_2 || id == VarbitID.SAILING_BOAT_2_NAME_3)
			boats[1].updateName();
		if (id == VarbitID.SAILING_BOAT_3_NAME_1 || id == VarbitID.SAILING_BOAT_3_NAME_2 || id == VarbitID.SAILING_BOAT_3_NAME_3)
			boats[2].updateName();
		if (id == VarbitID.SAILING_BOAT_4_NAME_1 || id == VarbitID.SAILING_BOAT_4_NAME_2 || id == VarbitID.SAILING_BOAT_4_NAME_3)
			boats[3].updateName();
		if (id == VarbitID.SAILING_BOAT_5_NAME_1 || id == VarbitID.SAILING_BOAT_5_NAME_2 || id == VarbitID.SAILING_BOAT_5_NAME_3)
			boats[4].updateName();
		
		// update port
		
		switch (id) {
			case VarbitID.SAILING_BOAT_1_PORT:
				boats[0].updatePort();
				updateBoatCache();
				break;
			case VarbitID.SAILING_BOAT_2_PORT:
				boats[1].updatePort();
				updateBoatCache();
				break;
			case VarbitID.SAILING_BOAT_3_PORT:
				boats[2].updatePort();
				updateBoatCache();
				break;
			case VarbitID.SAILING_BOAT_4_PORT:
				boats[3].updatePort();
				updateBoatCache();
				break;
			case VarbitID.SAILING_BOAT_5_PORT:
				boats[4].updatePort();
				updateBoatCache();
				break;

			
		}

		switch (id) {
			case VarbitID.SAILING_BOAT_1_STORED_HP:
			case VarbitID.SAILING_BOAT_1_STORED_MAXHP:
				boats[0].updateHealth();
				updateBoatCache();
				break;
			case VarbitID.SAILING_BOAT_2_STORED_HP:
			case VarbitID.SAILING_BOAT_2_STORED_MAXHP:
				boats[1].updateHealth();
				updateBoatCache();
				break;
			case VarbitID.SAILING_BOAT_3_STORED_HP:
			case VarbitID.SAILING_BOAT_3_STORED_MAXHP:
				boats[2].updateHealth();
				updateBoatCache();
				break;
			case VarbitID.SAILING_BOAT_4_STORED_HP:
			case VarbitID.SAILING_BOAT_4_STORED_MAXHP:
				boats[3].updateHealth();
				updateBoatCache();
				break;
			case VarbitID.SAILING_BOAT_5_STORED_HP:
			case VarbitID.SAILING_BOAT_5_STORED_MAXHP:
				boats[4].updateHealth();
				updateBoatCache();
				break;
		}

		// update ownership
		if (id == VarbitID.SAILING_BOAT_1_OWNED)
			boats[0].updateOwned();
		if (id == VarbitID.SAILING_BOAT_2_OWNED)
			boats[1].updateOwned();
		if (id == VarbitID.SAILING_BOAT_3_OWNED)
			boats[2].updateOwned();
		if (id == VarbitID.SAILING_BOAT_4_OWNED)
			boats[3].updateOwned();
		if (id == VarbitID.SAILING_BOAT_5_OWNED)
			boats[4].updateOwned();
		
		// update type
		if (id == VarbitID.SAILING_BOAT_1_TYPE)
			boats[0].updateType();
		if (id == VarbitID.SAILING_BOAT_2_TYPE)
			boats[1].updateType();
		if (id == VarbitID.SAILING_BOAT_3_TYPE)
			boats[2].updateType();
		if (id == VarbitID.SAILING_BOAT_4_TYPE)
			boats[3].updateType();
		if (id == VarbitID.SAILING_BOAT_5_TYPE)
			boats[4].updateType();
		
		updateSailingPanel();
	}
	
	private void updateMapIcons() {
		worldMapPointManager.removeIf(BoatWorldMapPoint.class::isInstance);
		
		if (config.worldMarkersEnabled()) {
			for (int i = 0; i < 5; i++) {
				Boat boat = boats[i];
				if (boat == null || !boat.isOwned()) continue;
				
				Port port = boat.getPort();
				if (port != null) {
					worldMapPointManager.add(new BoatWorldMapPoint(i,boat.getBoatName(),port.getNavigationLocation(),config));
				}
			}
		}
	}
	
	private void updateSailingPanel() {
		if (!config.sailingPanelEnabled()) return;
		
		int panelState = client.getVarbitValue(VarbitID.SAILING_SIDEPANEL_TABS); // if we not on the facilities tab don't update it
		if (panelState != 0) return;
		
		int hasBoarded = client.getVarbitValue(VarbitID.SAILING_BOARDED_BOAT); // if we on a boat don't update it
		if (hasBoarded != 0) return;
		
		Widget titleWidget = client.getWidget(InterfaceID.SailingSidepanel.TAB_TITLE);
		if (titleWidget == null) return;
		
		Widget titleLabel = titleWidget.getChild(0);
		if (titleLabel != null)
			titleLabel.setText("Docked Ships");
		
		Widget facilitiesWidget = client.getWidget(InterfaceID.SailingSidepanel.FACILITIES_CONTENT);
		if (facilitiesWidget == null) return;
		
		facilitiesWidget.deleteAllChildren();
		
		//
		
		for (int i = 0; i < 5; i++) {
			Boat boat = boats[i];
			BoatCache cache = boatCache[i];
			
			if (boat == null) {
				continue;
			}
			
			int portId = boat.getPortId();

			String boatName = boat.getBoatName();
			String portName = Integer.toString(portId);
			
			int boatColor = 0xff981f;
			int portColor = 0x77eebb;
			
			int portSpriteID = 2851;
			int typeSpriteID = 7077;
			
			boolean isOwned = boat.isOwned();
			
			if (isOwned) {
				Port port = boat.getPort();
				if (port == null && cache != null) {
					port = cache.getPort();
				}
				
				if (port != null) {
					portName = port.toString();
					portSpriteID = port.getSpriteID();
				}
				else {
					// no enum found for port so we use dbtables to fill in the details. won't show up on the world map but better than nothing!
					var portRows = client.getDBRowsByValue(DBTableID.SailingDock.ID,DBTableID.SailingDock.COL_DOCK_ID,0,boat.getPortId());
					
					if (!portRows.isEmpty()) {
						int dbRow = portRows.get(0);
						portName = (String) client.getDBTableField(dbRow,DBTableID.SailingDock.COL_NICE_NAME, 0)[0];
						portSpriteID = (int) client.getDBTableField(dbRow,DBTableID.SailingDock.COL_DOCK_SPRITE_SMALL, 0)[0];
					}
				}
				
				if (portName.equals("255")) {
					portName = "Bottled";
				}
				
				BoatType boatType = boat.getBoatType();
				if (boatType != null) {
					typeSpriteID = boatType.getSpriteID();
				}
			}
			else {
				portName = "Not Owned";
				portColor = 0x91bea9;
			}
			
			float health = boat.getHealth();
			if (health < 0 && cache != null) {
				health = cache.getHealth();
			}
			
			int layerWidth = facilitiesWidget.getWidth();
			
			Widget boatLayer = facilitiesWidget.createChild(WidgetType.LAYER);
			boatLayer.setName("FACILITIES_BOAT_ITEM");
			boatLayer.setSize(layerWidth, 30);
			boatLayer.setPos(0,i*30);
			boatLayer.revalidate();
			
			int PADDING_X = 23;
			
			Widget boatLabel = boatLayer.createChild(WidgetType.TEXT);
			boatLabel.setPos(PADDING_X,2);
			boatLabel.setOriginalHeight(12);
			boatLabel.setOriginalWidth(layerWidth-20);
			boatLabel.setTextShadowed(true);
			boatLabel.setText(boatName);
			boatLabel.setFontId(FontID.PLAIN_11);
			boatLabel.setTextColor(boatColor);
			boatLabel.revalidate();
			
			Widget typeIcon = boatLayer.createChild(WidgetType.GRAPHIC);
			typeIcon.setPos(0,3);
			typeIcon.setSize(20,20);
			typeIcon.setSpriteId(typeSpriteID);
			typeIcon.revalidate();
			
			if (isOwned) {
				Widget portIcon = boatLayer.createChild(WidgetType.GRAPHIC);
				portIcon.setPos(0,4);
				portIcon.setXPositionMode(WidgetPositionMode.ABSOLUTE_RIGHT);
				portIcon.setSize(18,18);
				portIcon.setSpriteId(portSpriteID);
				portIcon.revalidate();
			}
			
			Widget portLabel = boatLayer.createChild(WidgetType.TEXT);
			portLabel.setPos(PADDING_X,15);
			portLabel.setOriginalHeight(12);
			portLabel.setOriginalWidth(layerWidth-20);
			portLabel.setTextShadowed(true);
			portLabel.setText(portName);
			portLabel.setFontId(FontID.PLAIN_11);
			portLabel.setTextColor(portColor);
			portLabel.revalidate();
			
			Widget divider = boatLayer.createChild(WidgetType.LINE);
			divider.setOriginalHeight(0);
			divider.setOriginalWidth(layerWidth);
			divider.setPos(0,29);
			divider.setTextColor(0x666666);
			divider.revalidate();
		}
	}
	
	@Subscribe
	public void onScriptPostFired(ScriptPostFired scriptPostFired)
	{
		int id = scriptPostFired.getScriptId();
		
		if (id == 8712) {
			updateSailingPanel();
		}
		if (id == ScriptID.WORLDMAP_LOADMAP) {
			updateMapIcons();
		}
	}
	
	@Subscribe
	public void onConfigChanged(final ConfigChanged event)
	{
		if (!(event.getGroup()).equals(configGroup))
			return;
		
		updateSailingPanel();
		updateMapIcons();
	}
	
	@Provides
	WheresMyBoatConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(WheresMyBoatConfig.class);
	}
}

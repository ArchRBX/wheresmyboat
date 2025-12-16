package com.wheresmyboat;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.client.callback.ClientThread;
import net.runelite.api.GameState;
import net.runelite.api.annotations.Varbit;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.ScriptPostFired;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.Widget;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import com.wheresmyboat.enums.Port;

@Slf4j
@PluginDescriptor(
	name = "Dude, Where's My Boat?"
)
public class WheresMyBoat extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private WheresMyBoatConfig config;

	private Boat[] boats;

	@Override
	protected void startUp() throws Exception
	{
		log.debug("Starting DWMB...");

		boats = new Boat[5];

		clientThread.invoke(() -> {
			if(client.getGameState() == GameState.LOGGED_IN) {
				for (int i = 0; i < 5; i++) {
					Boat boat = new Boat(client,i+1);
					boats[i] = boat;
				}
			}
		});
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
		if (id == VarbitID.SAILING_BOAT_1_PORT)
			boats[0].updatePort();
		if (id == VarbitID.SAILING_BOAT_2_PORT)
			boats[1].updatePort();
		if (id == VarbitID.SAILING_BOAT_3_PORT)
			boats[2].updatePort();
		if (id == VarbitID.SAILING_BOAT_4_PORT)
			boats[3].updatePort();
		if (id == VarbitID.SAILING_BOAT_5_PORT)
			boats[4].updatePort();
	}

	private void updateSailingPanel() {
		int panelState = client.getVarbitValue(VarbitID.SAILING_SIDEPANEL_TABS); // if we not on the facilities tab don't update it
		if (panelState != 0) return;

		int hasBoarded = client.getVarbitValue(VarbitID.SAILING_BOARDED_BOAT); // if we on a boat don't update it
		if (hasBoarded != 0) return;

		Widget titleWidget = client.getWidget(InterfaceID.SailingSidepanel.TAB_TITLE);
		if (titleWidget == null) return;

		Widget titleLabel = titleWidget.getChild(0);
		titleLabel.setText("Docked Ships");

		Widget facilitiesWidget = client.getWidget(InterfaceID.SailingSidepanel.FACILITIES_CONTENT);
		if (facilitiesWidget == null) return;

		String summary = "";

		for (int i = 0; i < 5; i++) {
			Boat boat = boats[i];
			if (boat == null) continue;
			
			Port port = boat.getPort();
			if (port != null) {
				summary += ("<br><col=ff981f>" + boat.getBoatName() + "</col>: <col=77eeaa>"+port.toString()+"</col>");
			}
			else {				
				summary += ("<br><col=ff981f>" + boat.getBoatName() + "</col>: <col=77eeaa>PORT "+boat.getPortId()+"</col>");
			}
		}

		Widget facilitiesContent = facilitiesWidget.getChild(0);
		
		facilitiesContent.setText(summary);
		facilitiesContent.setXTextAlignment(0);
		facilitiesContent.setTextColor(0xffffff);
	}

	@Subscribe
	public void onScriptPostFired(ScriptPostFired scriptPostFired)
	{
		int id = scriptPostFired.getScriptId();

		if (id == 8712) {
			updateSailingPanel();
		}
	}

	@Provides
	WheresMyBoatConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(WheresMyBoatConfig.class);
	}
}

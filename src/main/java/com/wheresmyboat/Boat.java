package com.wheresmyboat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.wheresmyboat.enums.Port;

import net.runelite.api.Client;
import net.runelite.client.callback.ClientThread;

import net.runelite.api.annotations.Varbit;
import net.runelite.api.gameval.DBTableID;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.plugins.Plugin;
import net.runelite.api.events.VarbitChanged;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

public class Boat {
	protected int boatID;
	protected Client client;

	@Getter
	private String boatName;

	@Getter
	private Port port;

	@Getter
	private int portId;

	private boolean owned;

	private int name1_vb;
	private int name2_vb;
	private int name3_vb;
	private int port_vb;
	private int hp_vb;
	private int owned_vb;
	
	public Boat(Client client, int boatID) {
		this.boatID = boatID;
		this.client = client;

		if (boatID == 1) {
			name1_vb = VarbitID.SAILING_BOAT_1_NAME_1;
			name2_vb = VarbitID.SAILING_BOAT_1_NAME_2;
			name3_vb = VarbitID.SAILING_BOAT_1_NAME_3;
			port_vb = VarbitID.SAILING_BOAT_1_PORT;
			hp_vb = VarbitID.SAILING_BOAT_1_STORED_HP;
			owned_vb = VarbitID.SAILING_BOAT_1_OWNED;
		} else if (boatID == 2) {
			name1_vb = VarbitID.SAILING_BOAT_2_NAME_1;
			name2_vb = VarbitID.SAILING_BOAT_2_NAME_2;
			name3_vb = VarbitID.SAILING_BOAT_2_NAME_3;
			port_vb = VarbitID.SAILING_BOAT_2_PORT;
			hp_vb = VarbitID.SAILING_BOAT_2_STORED_HP;
			owned_vb = VarbitID.SAILING_BOAT_2_OWNED;
		} else if (boatID == 3) {
			name1_vb = VarbitID.SAILING_BOAT_3_NAME_1;
			name2_vb = VarbitID.SAILING_BOAT_3_NAME_2;
			name3_vb = VarbitID.SAILING_BOAT_3_NAME_3;
			port_vb = VarbitID.SAILING_BOAT_3_PORT;
			hp_vb = VarbitID.SAILING_BOAT_3_STORED_HP;
			owned_vb = VarbitID.SAILING_BOAT_3_OWNED;
		} else if (boatID == 4) {
			name1_vb = VarbitID.SAILING_BOAT_4_NAME_1;
			name2_vb = VarbitID.SAILING_BOAT_4_NAME_2;
			name3_vb = VarbitID.SAILING_BOAT_4_NAME_3;
			port_vb = VarbitID.SAILING_BOAT_4_PORT;
			hp_vb = VarbitID.SAILING_BOAT_4_STORED_HP;
			owned_vb = VarbitID.SAILING_BOAT_4_OWNED;
		} else if (boatID == 5) {
			name1_vb = VarbitID.SAILING_BOAT_5_NAME_1;
			name2_vb = VarbitID.SAILING_BOAT_5_NAME_2;
			name3_vb = VarbitID.SAILING_BOAT_5_NAME_3;
			port_vb = VarbitID.SAILING_BOAT_5_PORT;
			hp_vb = VarbitID.SAILING_BOAT_5_STORED_HP;
			owned_vb = VarbitID.SAILING_BOAT_5_OWNED;
		}

		updateName();
		updatePort();
	}

	public boolean isOwned() {
		return this.owned;
	}

	public void updatePort() {
		int owned = client.getVarbitValue(owned_vb);
		if (owned == 0) {
			this.owned = false;
		}
		else {
			this.owned = true;
		}

		int portbit = client.getVarbitValue(port_vb);

		this.portId = portbit;
		this.port = Port.getPort(portbit);
	}

	public void updateName() {
		int name1bit = client.getVarbitValue(name1_vb);
		int name2bit = client.getVarbitValue(name2_vb);
		int name3bit = client.getVarbitValue(name3_vb);
		
		int opt = name1bit == 0 ? DBTableID.SailingBoatNameOptions.COL_DEFAULT : DBTableID.SailingBoatNameOptions.COL_OPTION;
		int num = name1bit == 0 ? 0 : name1bit - 1;
		Object[] res = client.getDBTableField(DBTableID.SailingBoatNameOptions.Row.SAILING_BOAT_NAME_PREFIX_OPTIONS, opt, 0);
		String name1 = res == null || res.length < num || res[num] == null ? "" : (String)res[num];

		opt = name2bit == 0 ? DBTableID.SailingBoatNameOptions.COL_DEFAULT : DBTableID.SailingBoatNameOptions.COL_OPTION;
		num = name2bit == 0 ? 0 : name2bit - 1;
		res = client.getDBTableField(DBTableID.SailingBoatNameOptions.Row.SAILING_BOAT_NAME_DESCRIPTOR_OPTIONS, opt, 0);
		String name2 = res == null || res.length < num || res[num] == null ? "" : (String)res[num];

		opt = name3bit == 0 ? DBTableID.SailingBoatNameOptions.COL_DEFAULT : DBTableID.SailingBoatNameOptions.COL_OPTION;
		num = name3bit == 0 ? 0 : name3bit - 1;
		res = client.getDBTableField(DBTableID.SailingBoatNameOptions.Row.SAILING_BOAT_NAME_NOUN_OPTIONS, opt, 0);
		String name3 = res == null || res.length < num || res[num] == null ? "" : (String)res[num];

		ArrayList<String> names = new ArrayList<String>();
		
		if (name1 != null && name1.length() > 0) names.add(name1);
		if (name2 != null && name2.length() > 0) names.add(name2);
		if (name3 != null && name3.length() > 0) names.add(name3);
		
		boatName = String.join(" ", names);
	}
}
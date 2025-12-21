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

	@Getter	private String boatName;
	@Getter	private Port port;
	@Getter private float health;

	@Getter
	private int portId;

	private boolean owned;

	private int name1_vb;
	private int name2_vb;
	private int name3_vb;
	private int port_vb;
	private int hp_vb;
	private int maxhp_vb;
	private int owned_vb;
	
	public Boat(Client client, int boatID) {
		this.boatID = boatID;
		this.client = client;

		switch (boatID) {
			case 1:
				name1_vb = VarbitID.SAILING_BOAT_1_NAME_1;
				name2_vb = VarbitID.SAILING_BOAT_1_NAME_2;
				name3_vb = VarbitID.SAILING_BOAT_1_NAME_3;
				port_vb = VarbitID.SAILING_BOAT_1_PORT;
				hp_vb = VarbitID.SAILING_BOAT_1_STORED_HP;
				maxhp_vb = VarbitID.SAILING_BOAT_1_STORED_MAXHP;
				owned_vb = VarbitID.SAILING_BOAT_1_OWNED;
				break;
			case 2:
				name1_vb = VarbitID.SAILING_BOAT_2_NAME_1;
				name2_vb = VarbitID.SAILING_BOAT_2_NAME_2;
				name3_vb = VarbitID.SAILING_BOAT_2_NAME_3;
				port_vb = VarbitID.SAILING_BOAT_2_PORT;
				hp_vb = VarbitID.SAILING_BOAT_2_STORED_HP;
				maxhp_vb = VarbitID.SAILING_BOAT_2_STORED_MAXHP;
				owned_vb = VarbitID.SAILING_BOAT_2_OWNED;
				break;
			case 3:
				name1_vb = VarbitID.SAILING_BOAT_3_NAME_1;
				name2_vb = VarbitID.SAILING_BOAT_3_NAME_2;
				name3_vb = VarbitID.SAILING_BOAT_3_NAME_3;
				port_vb = VarbitID.SAILING_BOAT_3_PORT;
				hp_vb = VarbitID.SAILING_BOAT_3_STORED_HP;
				maxhp_vb = VarbitID.SAILING_BOAT_3_STORED_MAXHP;
				owned_vb = VarbitID.SAILING_BOAT_3_OWNED;
				break;
			case 4:
				name1_vb = VarbitID.SAILING_BOAT_4_NAME_1;
				name2_vb = VarbitID.SAILING_BOAT_4_NAME_2;
				name3_vb = VarbitID.SAILING_BOAT_4_NAME_3;
				port_vb = VarbitID.SAILING_BOAT_4_PORT;
				hp_vb = VarbitID.SAILING_BOAT_4_STORED_HP;
				maxhp_vb = VarbitID.SAILING_BOAT_4_STORED_MAXHP;
				owned_vb = VarbitID.SAILING_BOAT_4_OWNED;
				break;
			case 5:
				name1_vb = VarbitID.SAILING_BOAT_5_NAME_1;
				name2_vb = VarbitID.SAILING_BOAT_5_NAME_2;
				name3_vb = VarbitID.SAILING_BOAT_5_NAME_3;
				port_vb = VarbitID.SAILING_BOAT_5_PORT;
				hp_vb = VarbitID.SAILING_BOAT_5_STORED_HP;
				maxhp_vb = VarbitID.SAILING_BOAT_5_STORED_MAXHP;
				owned_vb = VarbitID.SAILING_BOAT_5_OWNED;
				break;
			default:
				break;
		}

		updateName();
		updatePort();
		updateOwned();
	}

	public boolean isOwned() {
		return this.owned;
	}

	public void updateHealth() {
		int hp = client.getVarbitValue(hp_vb);
		int maxhp = client.getVarbitValue(maxhp_vb);
		
		health = (float) hp / (float) maxhp;
	}

	public void updateOwned() {
		// not sure why the owned varbit isn't reliable but hey-ho!
		if (client.getVarbitValue(owned_vb) > 0 || client.getVarbitValue(port_vb) > 0) {
			this.owned = true;
		}
		else {
			this.owned = false;
		}
	}

	public void updatePort() {
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
package com.wheresmyboat;

import java.util.ArrayList;

import net.runelite.api.Client;
import net.runelite.api.gameval.DBTableID;
import net.runelite.api.gameval.VarbitID;

import com.wheresmyboat.enums.BoatType;
import com.wheresmyboat.enums.Port;

import lombok.Getter;

public class Boat {
	protected int boatID;
	protected Client client;

	@Getter	private String boatName;
	@Getter	private Port port;
	@Getter private BoatType boatType;
	@Getter private float health;

	@Getter	private int portId;

	private boolean owned;

	private int name1_vb;
	private int name2_vb;
	private int name3_vb;
	private int port_vb;
	private int hp_vb;
	private int maxhp_vb;
	private int owned_vb;
	private int type_vb;
	
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
				type_vb = VarbitID.SAILING_BOAT_1_TYPE;
				break;
			case 2:
				name1_vb = VarbitID.SAILING_BOAT_2_NAME_1;
				name2_vb = VarbitID.SAILING_BOAT_2_NAME_2;
				name3_vb = VarbitID.SAILING_BOAT_2_NAME_3;
				port_vb = VarbitID.SAILING_BOAT_2_PORT;
				hp_vb = VarbitID.SAILING_BOAT_2_STORED_HP;
				maxhp_vb = VarbitID.SAILING_BOAT_2_STORED_MAXHP;
				owned_vb = VarbitID.SAILING_BOAT_2_OWNED;
				type_vb = VarbitID.SAILING_BOAT_2_TYPE;
				break;
			case 3:
				name1_vb = VarbitID.SAILING_BOAT_3_NAME_1;
				name2_vb = VarbitID.SAILING_BOAT_3_NAME_2;
				name3_vb = VarbitID.SAILING_BOAT_3_NAME_3;
				port_vb = VarbitID.SAILING_BOAT_3_PORT;
				hp_vb = VarbitID.SAILING_BOAT_3_STORED_HP;
				maxhp_vb = VarbitID.SAILING_BOAT_3_STORED_MAXHP;
				owned_vb = VarbitID.SAILING_BOAT_3_OWNED;
				type_vb = VarbitID.SAILING_BOAT_3_TYPE;
				break;
			case 4:
				name1_vb = VarbitID.SAILING_BOAT_4_NAME_1;
				name2_vb = VarbitID.SAILING_BOAT_4_NAME_2;
				name3_vb = VarbitID.SAILING_BOAT_4_NAME_3;
				port_vb = VarbitID.SAILING_BOAT_4_PORT;
				hp_vb = VarbitID.SAILING_BOAT_4_STORED_HP;
				maxhp_vb = VarbitID.SAILING_BOAT_4_STORED_MAXHP;
				owned_vb = VarbitID.SAILING_BOAT_4_OWNED;
				type_vb = VarbitID.SAILING_BOAT_4_TYPE;
				break;
			case 5:
				name1_vb = VarbitID.SAILING_BOAT_5_NAME_1;
				name2_vb = VarbitID.SAILING_BOAT_5_NAME_2;
				name3_vb = VarbitID.SAILING_BOAT_5_NAME_3;
				port_vb = VarbitID.SAILING_BOAT_5_PORT;
				hp_vb = VarbitID.SAILING_BOAT_5_STORED_HP;
				maxhp_vb = VarbitID.SAILING_BOAT_5_STORED_MAXHP;
				owned_vb = VarbitID.SAILING_BOAT_5_OWNED;
				type_vb = VarbitID.SAILING_BOAT_5_TYPE;
				break;
			default:
				break;
		}

		updateName();
		updatePort();
		updateOwned();
		updateType();
	}

	public boolean isOwned() {
		return this.owned;
	}

	public void updateHealth() {
		int hp = client.getVarbitValue(hp_vb);
		int maxhp = client.getVarbitValue(maxhp_vb);

		if (maxhp > 0) {
			health = (float) hp / (float) maxhp;
		}
		else {
			health = 1; // might need to store the last hp ourselves
		}
	}

	public void updateType() {
		boatType = BoatType.fromId(client.getVarbitValue(type_vb));
	}

	public void updateOwned() {
		// not sure why the owned varbit isn't reliable but hey-ho!
		if (client.getVarbitValue(owned_vb) > 0 || client.getVarbitValue(port_vb) > 0 || client.getVarbitValue(name2_vb) > 0) {
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

		// if none of the names are set we don't own the ship yet
		if ((name1bit + name2bit + name3bit) == 0) {
			boatName = "Boat "+boatID;
			return;
		}

		// get indices
		name1bit--;
		name2bit--;
		name3bit--;

		int opt_col = DBTableID.SailingBoatNameOptions.COL_OPTION;

		Object[] prefix_opts = client.getDBTableField(DBTableID.SailingBoatNameOptions.Row.SAILING_BOAT_NAME_PREFIX_OPTIONS, opt_col, 0);
		Object[] desc_opts = client.getDBTableField(DBTableID.SailingBoatNameOptions.Row.SAILING_BOAT_NAME_DESCRIPTOR_OPTIONS, opt_col, 0);
		Object[] noun_opts = client.getDBTableField(DBTableID.SailingBoatNameOptions.Row.SAILING_BOAT_NAME_NOUN_OPTIONS, opt_col, 0);

		String name1 = (String) prefix_opts[name1bit];
		String name2 = (String) desc_opts[name2bit];
		String name3 = (String) noun_opts[name3bit];

		ArrayList<String> names = new ArrayList<String>();
		
		if (name1 != null && name1.length() > 0) names.add(name1);
		if (name2 != null && name2.length() > 0) names.add(name2);
		if (name3 != null && name3.length() > 0) names.add(name3);
		
		boatName = String.join(" ", names);
	}
}
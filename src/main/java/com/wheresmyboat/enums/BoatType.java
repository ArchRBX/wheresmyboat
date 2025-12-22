package com.wheresmyboat.enums;

import lombok.Getter;

@Getter
public enum BoatType
{
	RAFT(8110,0,"Raft",7111),
	SKIFF(8111,1,"Sloop",7112),
	SLOOP(8112,2,"Sloop",7113),
	TUTORIAL(8113,3,"Will and Anne's boat",7112)
	;
	private final int dbRow;
	private final int id;
	private final String name;
	private final int spriteID;

	BoatType(int dbRow, int id, String name, int spriteID)
	{
		this.dbRow = dbRow;
		this.id = id;
		this.name = name;
		this.spriteID = spriteID;
	}

	static
	{
		// init
	}

	public static BoatType fromDBRow(int dbRow) {
		for (BoatType p : BoatType.values()) {
			if (p.dbRow == dbRow) {
				return p;
			}
		}
		return null;
	}

	public static BoatType fromId(int id) {
		for (BoatType p : BoatType.values()) {
			if (p.id == id) {
				return p;
			}
		}
		return null;
	}

	@Override
	public String toString()
	{
		return name;
	}
}
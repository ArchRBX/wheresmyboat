package com.wheresmyboat;

import com.wheresmyboat.enums.Port;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoatCache {
	Port port;
	float health;

	BoatCache(Port port, float health)
	{
		if (port == null)
			port = Port.PORT_SARIM; // fallback

		if (health < 0)
			health = 1; // fallback
				
		this.port = port;
		this.health = health;
	}
}

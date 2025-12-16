/*
 * Copyright (c) 2025, nucleon <https://github.com/nucleon>
 * Copyright (c) 2025, Cooper Morris <https://github.com/coopermor>
 * Copyright (c) 2025, AtchRBX <https://github.com/AtchRBX>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.wheresmyboat.enums;

import lombok.Getter;
import net.runelite.api.coords.WorldPoint;

@Getter
public enum Port
{
	PORT_SARIM(0,"Port Sarim", 1, new WorldPoint(3050, 3192, 0)),
	THE_PANDEMONIUM(1,"The Pandemonium", 1, new WorldPoint(3069, 2986, 0)),
	LANDS_END(2,"Land's End", 5, new WorldPoint(1506, 3402, 0)),
	MUSA_POINT(3,"Musa Point", 10, new WorldPoint(2960, 3147, 0)),
	HOSIDIUS(4,"Hosidius", 5, new WorldPoint(1726, 3452, 0)),
	RIMMINGTON(5,"Rimmington", 18, new WorldPoint(2905, 3226, 0)),
	CATHERBY(6,"Catherby", 20, new WorldPoint(2796, 3412, 0)),
	PORT_PISCARILLIUS(7,"Port Piscarillius", 15, new WorldPoint(1845, 3687, 0)),
	BRIMHAVEN(8,"Brimhaven", 25, new WorldPoint(2757, 3229, 0)),
	ARDOUGNE(9,"Ardougne", 28, new WorldPoint(2671, 3265, 0)),
	PORT_KHAZARD(10,"Port Khazard", 30, new WorldPoint(2685, 3161, 0)),
	WITCHAVEN(11,"Witchaven", 34, new WorldPoint(2746, 3304, 0)),
	ENTRANA(12,"Entrana", 36, new WorldPoint(2878, 3335, 0)),
	CIVITAS_ILLA_FORTIS(13,"Civitas illa Fortis", 38, new WorldPoint(1774, 3141, 0)),
	CORSAIR_COVE(14,"Corsair Cove", 40, new WorldPoint(2579, 2843, 0)),
	SUNSET_COAST(16,"Sunset Coast", 44, new WorldPoint(1511, 2975, 0)),
	THE_SUMMER_SHORE(17,"The Summer Shore", 45, new WorldPoint(3174, 2367, 0)),
	ALDARIN(18,"Aldarin", 46, new WorldPoint(1452, 2970, 0)),
	DOGNOSE_ISLAND(35,"Dognose Island", 40, new WorldPoint(3061, 2639, 0)),
	REMOTE_ISLAND(36,"Remote Island", 45, new WorldPoint(2971, 2603, 0)),
	THE_LITTLE_PEARL(37,"The Little Pearl", 45, new WorldPoint(3354, 2216, 0)),
	VATRACHOS_ISLAND(41,"Vatrachos Island", 46, new WorldPoint(1872, 2985, 0)),
	CHINCHOMPA_ISLAND(52,"Chinchompa Island", 42, new WorldPoint(1892, 3429, 0)),
	// the following are educated guesses based on sprites and level reqs
	CAIRN_ISLE(15,"Cairn Isle", 42, new WorldPoint(2749, 2951, 0)),
	RUINS_OF_UNKAH(19,"Ruins of Unkah", 48, new WorldPoint(3143, 2824, 0)),
	VOID_KNIGHTS_OUTPOST(20,"Void Knights' Outpost", 50, new WorldPoint(2651, 2678, 0)),
	PORT_ROBERTS(21,"Port Roberts", 50, new WorldPoint(1860, 3306, 0)),
	//
	// no clue! help appreciated.
	THE_ONYX_CREST(-1,"The Onyx Crest", 47, new WorldPoint(2997, 2288, 0)),
	SHIMMERING_ATOLL(-1,"Shimmering Atoll", 49, new WorldPoint(1557, 2771, 0)),
	ANGLERS_RETREAT(-1,"Anglers' Retreat", 51, new WorldPoint(2467, 2721, 0)),
	MINOTAURS_REST(-1,"Minotaurs' Rest", 54, new WorldPoint(1958, 3117, 0)),
	ISLE_OF_SOULS(-1,"Isle of Souls", 55, new WorldPoint(2282, 2823, 0)),
	ISLE_OF_BONES(-1,"Isle of Bones", 56, new WorldPoint(2532, 2531, 0)),
	LAGUNA_AURORAE(-1,"Laguna Aurorae", 58, new WorldPoint(1202, 2733, 0)),
	CHARRED_ISLAND(-1,"Charred Island", 60, new WorldPoint(2660, 2395, 0)),
	TEAR_OF_THE_SOUL(-1,"Tear of the Soul", 61, new WorldPoint(2318, 2774, 0)),
	RELLEKKA(-1,"Rellekka", 62, new WorldPoint(2630, 3705, 0)),
	WINTUMBER_ISLAND(-1,"Wintumber Island", 63, new WorldPoint(2058, 2606, 0)),
	THE_CROWN_JEWEL(-1,"The Crown Jewel", 64, new WorldPoint(1765, 2659, 0)),
	ETCETERIA(-1,"Etceteria", 65, new WorldPoint(2611, 3840, 0)),
	PORT_TYRAS(-1,"Port Tyras", 66, new WorldPoint(2144, 3120, 0)),
	LLEDRITH_ISLAND(-1,"Lledrith Island", 66, new WorldPoint(2097, 3188, 0)),
	DEEPFIN_POINT(-1,"Deepfin Point", 67, new WorldPoint(1923, 2758, 0)),
	JATIZSO(-1,"Jatizso", 68, new WorldPoint(2412, 3780, 0)),
	NETIZNOT(-1,"Netiznot", 68, new WorldPoint(2308, 3783, 0)),
	RAINBOWS_END(-1,"Rainbow's End", 69, new WorldPoint(2344, 2270, 0)),
	PRIFDDINAS(-1,"Prifddinas", 70, new WorldPoint(2158, 3324, 0)),
	SUNBLEAK_ISLAND(-1,"Sunbleak Island", 72, new WorldPoint(2189, 2327, 0)),
	YNYSDAIL(-1,"Ynysdail", 73, new WorldPoint(2222, 3466, 0)),
	WATERBIRTH_ISLAND(-1,"Waterbirth Island", 74, new WorldPoint(2543, 3765, 0)),
	PISCATORIS(-1,"Piscatoris", 75, new WorldPoint(2303, 3690, 0)),
	LUNAR_ISLE(-1,"Lunar Isle", 76, new WorldPoint(2151, 3880, 0)),
	BUCCANEERS_HAVEN(-1,"Buccaneers' Haven", 76, new WorldPoint(2080, 3690, 0)),
	DRUMSTICK_ISLE(-1,"Drumstick Isle", 79, new WorldPoint(2150, 3530, 0)),
	WEISS(-1,"Weiss", 80, new WorldPoint(2860, 3972, 0)),
	BRITTLE_ISLE(-1,"Brittle Isle", 81, new WorldPoint(1954, 4056, 0)),
	GRIMSTONE(-1,"Grimstone", 87, new WorldPoint(2927, 4056, 0)),
	;

	private final int id;
	private final String name;
	private final Integer sailingLevelRequired;
	private final WorldPoint navigationLocation;
	
	Port(int portId, String name, Integer sailingLevelRequired, WorldPoint navigationLocation)
	{
		this.id = portId;
		this.name = name;
		this.sailingLevelRequired = sailingLevelRequired;
		this.navigationLocation = navigationLocation;
	}

	static
	{
		// init
	}

	public static Port getPort(int portId) {
		for (Port p : Port.values()) {
			if (p.id == portId) {
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
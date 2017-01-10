--Programme lua pour l'interaction avec PIGOU et Orbiter
--Pour l'interaction en entrée lua procède au découpage d'un code en 2 parties
--MODE,VALEUR

-----------------------FUNCTION------------------------------------------

function split(inputstr, sep)
    if sep == nil then
            sep = "%s"
    end
    local t={} ; i=1
    for str in string.gmatch(inputstr, "([^"..sep.."]+)") do
            t[i] = str
            i = i + 1
    end
    return t
end

function round(num, numDecimalPlaces)
  local mult = 10^(numDecimalPlaces or 0)
  return math.floor(num * mult + 0.5) / mult
end

-----------------------INIT----------------------------------------------

local msg = oapi.create_annotation()
--initialisation variable socket
local s = require("socket")
local tcp = assert(socket.tcp())
--la connection doit être établi pour poursuivre
tcp:connect("127.0.0.1", 14000);
tcp:settimeout(0)
--initialisation des valeurs pour le vaisseau
local v = vessel.get_interface('GL-01') --préciser le nom du vaisseau
local vMainFuel = v:get_propellanthandle(0) --réservoir principal
local vRcsFuel = v:get_propellanthandle(1) --réservoir RCS
--variable de controle blocage des réservoirs
local currentFuelMain = 0
local currentFuelRcs = 0
local fuelMainIsLock = 0
local fuelRcsIsLock = 0
--variable de controle remplissage carburant
local fuelMainIsSupply = 0
local fuelRcsIsSupply = 0
--variable de controle vidange carburant
local fuelMainIsDump = 0
local fuelRcsIsDump = 0
--variable de controle transfert de carburant
local fuelTransfert = 0 --0 rien, 1 main vers rcs, 2 rcs vers main

--elimine le tableau de bord virtuel du vaisseau pour une meilleur immersion
--60
for i=1,60 do 
	--TODO
end


------------------------MAIN PROGRAM------------------------------

-- ce circuit fonctionnera en permanence
local boucle = true
repeat

	--------------------INPUT----------------------------
	local s, status, partial = tcp:receive()
	if s == nil then
	else
		--recuperation valeur
		local r = split(s,",")
		local mode = r[1]
		local value = r[2]

		--message entrant
		if mode == "MSG" then
			msg:set_text(value) 
		end

		--blocage carburant
		if mode == "FUELLOCK" then
			if (value == "MAIN") then
				if fuelMainIsLock == 0 then
					currentFuelMain = round(v:get_propellantmass(vMainFuel))
					v:set_propellantmass(vMainFuel, 0)
					fuelMainIsLock = 1
				else
					v:set_propellantmass(vMainFuel, currentFuelMain)
					fuelMainIsLock = 0
				end
			end
			if (value == "RCS") then
				if fuelRcsIsLock == 0 then
					currentFuelRcs = round(v:get_propellantmass(vRcsFuel))
					v:set_propellantmass(vRcsFuel, 0)
					fuelRcsIsLock = 1
				else
					v:set_propellantmass(vRcsFuel, currentFuelRcs)
					fuelRcsIsLock = 0
				end
			end
		end
		
		--switch des états
		if mode == "FUELSUPPLY" then
			if (value == "MAIN") then
				if fuelMainIsSupply == 1 then
					fuelMainIsSupply = 0
				else
					fuelMainIsSupply = 1
				end
			end
			if (value == "RCS") then
				if fuelRcsIsSupply == 1 then
					fuelRcsIsSupply = 0
				else
					fuelRcsIsSupply = 1
				end
			end
		end
		if mode == "FUELDUMP" then
			if (value == "MAIN") then
				if fuelMainIsDump == 1 then
					fuelMainIsDump = 0
				else
					fuelMainIsDump = 1
				end
			end
			if (value == "RCS") then
				if fuelRcsIsDump == 1 then
					fuelRcsIsDump = 0
				else
					fuelRcsIsDump = 1
				end
			end
		end
		if mode == "FUELTRANSFERT" then
			if value == "MAINTORCS" then
				fuelTransfert = 1
			end
			if value == "RCSTOMAIN" then
				fuelTransfert = 2
			end
			if value == "NONE" then
				fuelTransfert = 0
			end
		end

		--commande d'action du vaisseau
		if mode == "CMD" then
			--changement mode HUD
			if value == "HUDMODE" then
				v:send_bufferedkey(OAPI_KEY.H)
			end
			--changement mode RCS linéaire ou rotation
			if value == "RCSMODE" then
				v:send_bufferedkey(OAPI_KEY.DIVIDE)
			end
			--dedocking
			if value == "DEDOCK" then
				v:undock()
			end
			--commande auto pilote, kill rot, pro, retro, level horizon, orbit+, orbit-
			if value == "APLH" then
				v:send_bufferedkey(OAPI_KEY.L)
			end
			if value == "APPRO" then
				v:send_bufferedkey(OAPI_KEY.LBRACKET)
			end
			if value == "APRETRO" then
				v:send_bufferedkey(OAPI_KEY.RBRACKET)
			end
			if value == "APORBITMORE" then
				v:send_bufferedkey(OAPI_KEY.SEMICOLON)
			end
			if value == "APORBITLESS" then
				v:send_bufferedkey(OAPI_KEY.APOSTROPHE)
			end
			if value == "APKILLROT" then
				v:send_bufferedkey(OAPI_KEY.NUMPAD5)
			end
			if value == "APHOLDALTITUDE" then
				v:send_bufferedkey(OAPI_KEY.A)
			end

			--ouverture/fermeture nez de l'appareil
			if value == "NOSE" then
				v:send_bufferedkey(OAPI_KEY.K)
			end
			--ouverture/fermeture train d'atterissage
			if value == "GEAR" then
				v:send_bufferedkey(OAPI_KEY.G)
			end

			oapi.dbg_out(s)
			s = nil
		end
	end
	--effectue le plein si possible
	if fuelMainIsSupply == 1 then
		v:set_propellantmass(vMainFuel, v:get_propellantmass(vMainFuel) + 50)
		if v:get_propellantmass(vMainFuel) > 9600 then
			v:set_propellantmass(vMainFuel, 9600)
		end
	end
	if fuelRcsIsSupply == 1 then
		v:set_propellantmass(vRcsFuel, v:get_propellantmass(vRcsFuel) + 20)
		if v:get_propellantmass(vRcsFuel) > 600 then
			v:set_propellantmass(vRcsFuel, 600)
		end
	end
	--vidange
	if fuelMainIsDump == 1 then
		v:set_propellantmass(vMainFuel, v:get_propellantmass(vMainFuel) - 50)
	end
	if fuelRcsIsDump == 1 then
		v:set_propellantmass(vRcsFuel, v:get_propellantmass(vRcsFuel) - 20)
	end
	--transfert carburant
	if fuelTransfert > 0 then
		--mode transfert main to rcs
		if fuelTransfert == 1 then
			if v:get_propellantmass(vMainFuel) > 0 then
				v:set_propellantmass(vRcsFuel, v:get_propellantmass(vRcsFuel) + 10)
				v:set_propellantmass(vMainFuel, v:get_propellantmass(vMainFuel) - 10)
				--arrêt du transfert si plus de jus ou reservoir entrant plein
				if v:get_propellantmass(vMainFuel) < 0 or v:get_propellantmass(vRcsFuel) >= 600 then
					fuelTransfert = 0
				end
				if v:get_propellantmass(vRcsFuel) > 600 then
					v:set_propellantmass(vRcsFuel, 600)
				end
			end
		end
		--mode transfert rcs to main
		if fuelTransfert == 2 then
			if v:get_propellantmass(vRcsFuel) > 0 then
				v:set_propellantmass(vMainFuel, v:get_propellantmass(vMainFuel) + 10)
				v:set_propellantmass(vRcsFuel, v:get_propellantmass(vRcsFuel) - 10)
				--arrêt du transfert si plus de jus ou reservoir entrant plein
				if v:get_propellantmass(vRcsFuel) < 0 or v:get_propellantmass(vMainFuel) >= 9600 then
					fuelTransfert = 0
				end
				if v:get_propellantmass(vMainFuel) > 9600 then
					v:set_propellantmass(vRcsFuel, 9600)
				end
			end
		end
	end
	
	--------------------OUTPUT----------------------------
	-------sortie séparé par des virugules : ordre ALTITUDE,MAINFUEL,RCSFUEL

	local output = ""

	--altitude du vaisseau
	output = output .. round(v:get_altitude()) .. ","

	--état du carburant sur le simulateur par rapport au blocage carburant
	if fuelMainIsLock == 1 then
		output = output .. currentFuelMain
	else
		output = output .. round(v:get_propellantmass(vMainFuel))
	end
	if fuelRcsIsLock == 1 then
		output = output .. "," .. currentFuelRcs .. "\n"
	else
		output = output .. "," .. round(v:get_propellantmass(vRcsFuel)) .. "\n"
	end
	tcp:send(output)

	--en seconde précise le temps d'accorder du temps au simulateur
	proc.wait_simdt(0.5)
until boucle == false
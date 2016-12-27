-----------------------INIT----------------------------------------------

--initialisation variable socket
local s = require("socket")
local tcp = assert(socket.tcp())
--la connection doit être établi pour poursuivre
tcp:connect("127.0.0.1", 14000);
--initialisation des valeurs pour le vaisseau
local v = vessel.get_interface('GL-01') --préciser le nom du vaisseau
local vMainFuel = v:get_propellanthandle(0) --réservoir principal
local vRcsFuel = v:get_propellanthandle(1) --réservoir RCS

------------------------MAIN PROGRAM------------------------------

-- ce circuit fonctionnera en permanence
local boucle = true
repeat

	--------------------INPUT----------------------------



	--------------------OUTPUT----------------------------



	--en seconde précise le temps d'accorder du temps au simulateur
	proc.wait_simdt(0.5)
until boucle == false
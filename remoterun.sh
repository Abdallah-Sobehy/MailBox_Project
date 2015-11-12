#!/bin/bash          
 
#if it does not work try any other working machine
#Change b02-08 with the machine on where the server is deployed
ssh b02-07.int-evry.fr "java -Dorg.omg.CORBA.ORBInitialHost=b02-08.int-evry.fr  -classpath $CLASSPATH:/mci/msc2014/sobehy_a/workspace/MailBox_Project client_dm.AutoClient_dm"


#java -Dorg.omg.CORBA.ORBInitialHost=b02-08.int-evry.fr  -classpath $CLASSPATH:/mci/msc2014/sobehy_a/workspace/MailBox_Project client_dm.AutoClient_dm



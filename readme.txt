This project is distributed into separate contents.

1. Code folder:
This contains the java code for the 3 required sorts.

2.Scripts folder:
This contains the automation scripts which was used for configuring slave nodes. The script teach.sh was placed on master node. The filr list, contained all the slave hostnames. The script from teach.sh would read one hostname at a time from list , log in to that host and execute all the commands specified in the file command.
This was helpful in mounting additional storage, changing the configuration of the hadoop xml files and set the environment variables.

3. Output folder:
This folder contains the first 10 lines and last 10 lines of the sorted output from each of the experiments.

4. Log folder:
This contains the runtime logs for hadoop and spark

5. Report.doc:
This is the final report which contains the steps for environment setup of hadoop and spark. This contains the ovservations and conclusions.

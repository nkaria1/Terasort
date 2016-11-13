
for i in {1..16}
do
HOST=$(head -1 list)
VAR=$(head -1 var)
SCRIPT=$(head -1 command)
SCRIPT=`sed s/VAR/$VAR/ command`
echo "processing for $i"
ssh ${HOST} "${SCRIPT}"
sed -i '1d' list
sed -i '1d' var
#echo $HOST
#echo $SCRIPT

done

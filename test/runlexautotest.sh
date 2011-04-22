#!/bin/bash
#
#

dir=`dirname $PWD/$0`
count=$1
tmpdir=`mktemp -d --tmpdir "lextest-XXXX"`

echo "Executing $count random tests..."

passcount=0


for i in `seq 1 $count`; do

    mkfifo $tmpdir/tokenstream


    seed=$RANDOM

    echo -n -e "\r ["`printf "%05d" $i`"/$count] seed="`printf "%05d" $seed`" running..."

    result=`$dir/lextestgen/lextestgen $seed 2>$tmpdir/tokenstream | \
	java -cp $dir/../bin/ edu.kit.pp.minijava.MiniJava --lextest /dev/stdin | \
	diff -u $tmpdir/tokenstream -`

    case $? in
	0) echo -n -e "\tpassed"
	    passcount=$(($passcount+1))
	    ;;
	1) echo -e "\tfailed"
	    echo "$result" > failed-seed-$seed.diff
	    ;;
	*) echo -e "\tinternal error"
	    ;;
    esac
    

    rm $tmpdir/tokenstream    
done;

rm -rf $tmpdir

echo "$((($passcount*100)/count))% of $count tests passed"
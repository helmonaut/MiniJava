#!/bin/bash
#
#

dir=`dirname $PWD/$0`
count=$1
tmpdir=`mktemp -d --tmpdir "lextest-XXXX"`

echo "Executing $count random tests..."

passcount=0


for i in `seq 1 $count`; do

    seed=$RANDOM

    echo -n -e "\r ["`printf "%05d" $i`"/$count] seed="`printf "%05d" $seed`" generating..."

    $dir/lextestgen/lextestgen $seed 2>$tmpdir/tokens >$tmpdir/source

    echo -n -e "\r ["`printf "%05d" $i`"/$count] seed="`printf "%05d" $seed`" running...   "

    java -cp $dir/../bin/ edu.kit.pp.minijava.MiniJava --lextest $tmpdir/source > $tmpdir/lexout

    echo -n -e "\r ["`printf "%05d" $i`"/$count] seed="`printf "%05d" $seed`" verifying... "

    result=`diff -u $tmpdir/tokens $tmpdir/lexout`

    case $? in
	0) passcount=$(($passcount+1))
	    ;;
	1) echo -e "\tfailed"
	    echo "$result" > failed-seed-$seed.diff
	    ;;
	*) echo -e "\tinternal error"
	    ;;
    esac
    

    rm $tmpdir/tokens
    rm $tmpdir/source
    rm $tmpdir/lexout
done;

rm -rf $tmpdir

echo "$((($passcount*100)/count))% of $count tests passed"
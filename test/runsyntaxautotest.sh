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

    java -cp $dir/../bin/ edu.kit.pp.minijava.MiniJava --syntaxcheck $tmpdir/source > $tmpdir/out

    echo -n -e "\r ["`printf "%05d" $i`"/$count] seed="`printf "%05d" $seed`" verifying... "

    result=`tail -1 $tmpdir/out`

	if [ $result="Correct Syntax" ]; then
		echo -n -e "passed"
		passcount=$(($passcount+1))
	else
		echo "failed"
		cat $tmpdir/out
	fi

    rm $tmpdir/tokens
    rm $tmpdir/source
    rm $tmpdir/out
done;

rm -rf $tmpdir

echo "\n$((($passcount*100)/count))% of $count tests passed"
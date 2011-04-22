#!/bin/bash
#
#

dir=`dirname $PWD/$0`
source="$dir/$1.java"
tokens="$dir/$1.tok"


if [ ! -e $source ]; then
    echo "Source file $source missing"
    exit -1
fi

if [ ! -e $tokens ]; then
    echo "Token file $source missing"
    exit -1
fi

java -cp $dir/../bin/ edu.kit.pp.minijava.MiniJava --lextest $source | diff -u $tokens -

exit $?